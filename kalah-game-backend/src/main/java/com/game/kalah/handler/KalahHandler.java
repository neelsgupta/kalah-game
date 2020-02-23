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

	/**
	 * Make move for given pitId to respective game.
	 * 
	 * @param game
	 * @param pitId
	 */
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
				putStonesToKalahHomePit(currentPit, scoreBoard, 1);
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
		checkPitLastStoneLanded(lastPit, game);

		if (!playerGotFreeTurn(lastPit, game.getPlayer())) {
			game.setPlayer(game.getPlayer().getNextPlayer());
		}
		if (isGameEnded(game)) {
			GameStatus winner = checkWinner(game);
			game.setGameStatus(winner);
		}
		log.info("makeMove method ended.");
	}

	/**
	 * If pitId selected belongs to other player throws InvalidIdException if
	 * pitId selected is empty pit throws InvalidIdException.
	 * 
	 * @param pitId
	 *            selected to make a move
	 * @param game
	 */
	private void validatePitId(int pitId, Game game) {
		Player player = game.getPlayer();

		if (!isPlayerPit(pitId, player)) {
			log.error("The pitId selected does not belong to the current player turn.");
			throw new InvalidIdException(String.valueOf(pitId), "It is other player turn.");
		}
		if (game.getScoreBoard().get(pitId) == 0) {
			log.error("The pitId selected is empty. ");
			throw new InvalidIdException(String.valueOf(pitId), "pitId selected is an empty pit.");
		}
	}

	/**
	 * Checks pit where the last stone landed. If the pit is own empty pit
	 * captures this stone and all stones in the opposite pit and puts them into
	 * own kalah home.
	 *
	 * @param lastPit
	 *            pitId selected
	 * @param game
	 */
	private void checkPitLastStoneLanded(int lastPit, Game game) {
		if (isLastStoneLandedPitOwnEmptyPit(lastPit, game)) {
			int oppositePit = getOppositeSidePit(lastPit);
			int oppositePitStoneCount = game.getScoreBoard().get(oppositePit);
			if (oppositePitStoneCount != 0) {
				clearPit(oppositePit, game.getScoreBoard());
				clearPit(lastPit, game.getScoreBoard());
				putStonesToKalahHomePit(game.getPlayer().getHomeId(), game.getScoreBoard(), oppositePitStoneCount + 1);
			}
		}
	}

	/**
	 * If the game is ended adds all remained stones to kalah home for each
	 * player.
	 *
	 * @param game
	 * @return true if the game is terminated.
	 */
	private boolean isGameEnded(Game game) {
		Player player = game.getPlayer();
		List<Integer> pits = player.getPits();
		Map<Integer, Integer> scoreBoard = game.getScoreBoard();

		boolean currentPlayerAllPitsEmpty = pits.stream().map(scoreBoard::get)
				.allMatch(stoneNumbers -> stoneNumbers == 0);

		boolean oppositePlayerAllPitsEmpty = player.getNextPlayer().getPits().stream().map(scoreBoard::get)
				.allMatch(stoneNumbers -> stoneNumbers == 0);

		if (currentPlayerAllPitsEmpty || oppositePlayerAllPitsEmpty) {
			moveStonesToKalahHome(player, scoreBoard);
			moveStonesToKalahHome(player.getNextPlayer(), scoreBoard);
			return true;
		}
		return false;
	}

	private void moveStonesToKalahHome(Player player, Map<Integer, Integer> scoreBoard) {
		player.getPits().forEach(pit -> {
			int amount = scoreBoard.get(pit);
			if (amount != 0) {
				int kalahId = player.getHomeId();
				scoreBoard.replace(kalahId, scoreBoard.get(kalahId) + amount);
				clearPit(pit, scoreBoard);
			}
		});
	}

	/**
	 * checks the winner of the game.
	 * 
	 * @param game
	 * @return
	 */
	private GameStatus checkWinner(Game game) {
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

	private boolean isLastStoneLandedPitOwnEmptyPit(int lastPitId, Game game) {
		Map<Integer, Integer> scoreBoard = game.getScoreBoard();
		return scoreBoard.get(lastPitId) == 1 && isPlayerPit(lastPitId, game.getPlayer());
	}

	/**
	 * checks the pitId selected belongs to the player.
	 * 
	 * @param pitId selected pitId
	 * @param player current turn
	 * @return
	 */
	private boolean isPlayerPit(int pitId, Player player) {
		return player.getPits().contains(pitId);
	}

	private int getOppositeSidePit(int pitId) {
		return KalahConstants.LAST_PIT_ID - pitId;
	}

	private boolean playerGotFreeTurn(int lastPitId, Player player) {
		return player.getHomeId() == lastPitId;
	}

	private void putStonesToKalahHomePit(int pitId, Map<Integer, Integer> scoreBoard, int numberOfStones) {
		log.debug("add stones for pitId: {}" , pitId);
		scoreBoard.replace(pitId, scoreBoard.get(pitId) + numberOfStones);
	}

	private void clearPit(int pitId, Map<Integer, Integer> scoreBoard) {
		log.debug("clearing pit for pitId: {}" , pitId);
		scoreBoard.replace(pitId, 0);
	}

}
