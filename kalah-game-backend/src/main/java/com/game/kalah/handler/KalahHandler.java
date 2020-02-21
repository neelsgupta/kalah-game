package com.game.kalah.handler;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.game.kalah.constant.KalahConstants;
import com.game.kalah.domain.Game;
import com.game.kalah.domain.GameStatus;
import com.game.kalah.domain.Player;
import com.game.kalah.exception.InvalidIdException;

@Service
public class KalahHandler {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	public void makeMove(Game game, int pitId) {
		log.info("makeMove method started.");
		validatePitId(pitId, game);

		Map<Integer, Integer> scoreBoard = game.getScoreBoard();
		int numberOfStones = scoreBoard.get(pitId);
		int lastIndex = pitId + numberOfStones;
		clearPit(pitId, game.getScoreBoard());

		int lastPit = lastIndex;
		for (int currentIndex = pitId + 1; currentIndex <= lastIndex; currentIndex++) {
			int currentPit = currentIndex;
			if (currentIndex == KalahConstants.LAST_PIT_ID && lastIndex != KalahConstants.LAST_PIT_ID) {
				lastIndex = lastIndex - currentIndex;
				currentIndex = 0;
			}
			if (game.getPlayer().getNextPlayer().getHomeId() != currentPit) {
				addStonesToPit(currentPit, scoreBoard, 1);
			} else {
				if (currentIndex != KalahConstants.LAST_PIT_ID) {
					lastIndex++;
				} else {
					lastIndex = KalahConstants.FIRST_PIT_ID;
					currentIndex = 0;
				}
			}
		}
		lastPit = lastPit > KalahConstants.LAST_PIT_ID ? lastIndex : lastPit;
		checkLastPit(lastPit, game);

		if (!playerGotFreeTurn(lastPit, game.getPlayer())) {
			game.setPlayer(game.getPlayer().getNextPlayer());
		}
		if (isGameTerminated(game)) {
			GameStatus winner = findTheWinner(game);
			game.setGameStatus(winner);
		}
		log.info("makeMove method ended.");
	}
	
	private void validatePitId(int pitId, Game game) {
		Player player = game.getPlayer();

		if (!isPlayerPit(pitId, player)) {
			log.error("The pitId selected does not belong to the current player turn.");
			throw new InvalidIdException(String.valueOf(pitId), "It is other player turn.");
		}
		if (game.getScoreBoard().get(pitId) == 0) {
			log.error("The pitId selected is empty. " + pitId);
			throw new InvalidIdException(String.valueOf(pitId), "pitId selected is an empty pit.");
		}
	}

	/**
	 * Checks pit where the last stone landed. If the pit is own empty pit
	 * captures this stone and all stones in the opposite pit and puts them in
	 * own Kalah.
	 *
	 * @param lastPit
	 * @param game
	 */
	private void checkLastPit(int lastPit, Game game) {
		if (lastPitWasOwnEmptyPit(lastPit, game)) {
			int oppositePit = getOppositePit(lastPit);
			int oppositePitAmount = game.getScoreBoard().get(oppositePit);
			if (oppositePitAmount != 0) {
				clearPit(oppositePit, game.getScoreBoard());
				clearPit(lastPit, game.getScoreBoard());
				addStonesToPit(game.getPlayer().getHomeId(), game.getScoreBoard(), oppositePitAmount + 1);
			}
		}
	}

	/**
	 * If the game is terminated adds all remained stones to Kalah of each
	 * player.
	 *
	 * @param game
	 * @return true if the game is terminated.
	 */
	private boolean isGameTerminated(Game game) {
		Player player = game.getPlayer();
		List<Integer> pits = player.getPits();
		Map<Integer, Integer> scoreBoard = game.getScoreBoard();

		boolean playerPitsAreEmpty = pits.stream().map(scoreBoard::get).allMatch(stoneNumbers -> stoneNumbers == 0);

		boolean oppositePlayerPitsAreEmpty = player.getNextPlayer().getPits().stream().map(scoreBoard::get)
				.allMatch(stoneNumbers -> stoneNumbers == 0);

		if (playerPitsAreEmpty || oppositePlayerPitsAreEmpty) {
			addAllRemainedStonesToHome(player, scoreBoard);
			addAllRemainedStonesToHome(player.getNextPlayer(), scoreBoard);
			return true;
		}
		return false;
	}

	private void addAllRemainedStonesToHome(Player player, Map<Integer, Integer> scoreBoard) {
		player.getPits().forEach(pit -> {
			int amount = scoreBoard.get(pit);
			if (amount != 0) {
				int kalahId = player.getHomeId();
				scoreBoard.replace(kalahId, scoreBoard.get(kalahId) + amount);
				clearPit(pit, scoreBoard);
			}
		});
	}

	private GameStatus findTheWinner(Game game) {
		Map<Integer, Integer> board = game.getScoreBoard();
		int firstPlayerStones = board.get(Player.FIRST_PLAYER.getHomeId());
		int secondPlayerStones = board.get(Player.SECOND_PLAYER.getHomeId());
		if (firstPlayerStones > secondPlayerStones) {
			return GameStatus.FIRST_PLAYER_WON;
		} else if (firstPlayerStones < secondPlayerStones) {
			return GameStatus.SECOND_PLAYER_WON;
		} else {
			return GameStatus.DRAW;
		}
	}

	private boolean lastPitWasOwnEmptyPit(int lastPitId, Game game) {
		Map<Integer, Integer> scoreBoard = game.getScoreBoard();
		return scoreBoard.get(lastPitId) == 1 && isPlayerPit(lastPitId, game.getPlayer());
	}

	private boolean isPlayerPit(int pitId, Player player) {
		return player.getPits().contains(pitId);
	}

	private int getOppositePit(int pitId) {
		return KalahConstants.LAST_PIT_ID - pitId;
	}

	private boolean playerGotFreeTurn(int lastPitId, Player player) {
		return player.getHomeId() == lastPitId;
	}

	private void addStonesToPit(int pitId, Map<Integer, Integer> scoreBoard, int numberOfStones) {
		log.debug("add stones for pitId: " + pitId);
		scoreBoard.replace(pitId, scoreBoard.get(pitId) + numberOfStones);
	}

	private void clearPit(int pitId, Map<Integer, Integer> scoreBoard) {
		log.debug("clearing pit for pitId: " + pitId);
		scoreBoard.replace(pitId, 0);
	}

}
