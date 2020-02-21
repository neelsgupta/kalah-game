package com.game.kalah.mapper;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.game.kalah.domain.Game;
import com.game.kalah.domain.GameStatus;
import com.game.kalah.domain.Player;
import com.game.kalah.dto.KalahInitResponse;
import com.game.kalah.dto.KalahMovedResponse;

@Component
@ConfigurationProperties
public class KalahMapper {

	@Value("${server.port}")
	private String port;

	private Logger log = LoggerFactory.getLogger(this.getClass());

	public KalahInitResponse mapToIntiDto(Game game) {
		log.debug("mapToIntiDto method started.");
		KalahInitResponse kalahInit = new KalahInitResponse();
		String gameId = game.getId();
		kalahInit.setId(gameId);
		kalahInit.setUri(createGameURL(gameId));
		log.debug("mapToIntiDto method ended.");
		return kalahInit;
	}

	public KalahMovedResponse mapToMovedDto(Game game) {
		log.debug("mapToMovedDto method started.");
		KalahMovedResponse response = new KalahMovedResponse();
		String gameId = game.getId();
		response.setId(gameId);
		response.setUri(createGameURL(gameId));
		response.setScore(game.getScoreBoard());
		response.setGameStatus(game.getGameStatus());
		response.setNextPlayer(String.valueOf(game.getPlayer().getPlayerId()));
		log.debug("mapToMovedDto method ended.");
		return response;
	}

	public Game createGame() {
		log.info("createGame method started.");
		Game game = new Game();
		String gameId = UUID.randomUUID().toString();
		game.setId(gameId);
		game.setScoreBoard(setInitScoreBoard());
		game.setGameStatus(GameStatus.IN_PROGRESS);
		game.setPlayer(Player.FIRST_PLAYER);
		log.info("createGame method ended.");
		return game;
	}

	private Map<Integer, Integer> setInitScoreBoard() {

		Map<Integer, Integer> scoreBoard = new LinkedHashMap<>();
		for (int i = 1; i <= 14; i++) {
			int value = (i != 7 && i != 14) ? 6 : 0;
			scoreBoard.put(i, value);
		}
		return scoreBoard;
	}

	private String createGameURL(String gameId) {
		String url = "http://localhost:" + port + "/games/" + gameId;
		return url;
	}

}
