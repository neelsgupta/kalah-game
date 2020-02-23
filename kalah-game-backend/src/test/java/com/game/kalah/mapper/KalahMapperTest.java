package com.game.kalah.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;

import com.game.kalah.domain.Game;
import com.game.kalah.domain.GameStatus;
import com.game.kalah.domain.Player;
import com.game.kalah.dto.KalahInitResponse;
import com.game.kalah.dto.KalahMovedResponse;

@RunWith(SpringRunner.class)
public class KalahMapperTest {

	@InjectMocks
	private KalahMapper kalahMapper;

	private String gameId = "anyId";

	@Test
	public void testCreateGame() {
		Game game = kalahMapper.createGame();
		assertNotNull(game.getId());
		assertNotNull(game.getScoreBoard());
		assertEquals(Player.FIRST_PLAYER, game.getPlayer());
		assertEquals(GameStatus.IN_PROGRESS, game.getGameStatus());
	}

	@Test
	public void testMapToIntiDto() {
		KalahInitResponse kalahInit = kalahMapper.mapToIntiDto(initGame());
		assertEquals(gameId, kalahInit.getId());
		assertNotNull(kalahInit.getUri());
	}

	@Test
	public void testMapToMovedDto() {
		KalahMovedResponse kalahMove = kalahMapper.mapToMovedDto(initGame());
		assertEquals(gameId, kalahMove.getId());
		assertNotNull(kalahMove.getUri());
		assertNotNull(kalahMove.getScore());
		assertEquals(String.valueOf(Player.FIRST_PLAYER.getPlayerId()), kalahMove.getNextPlayer());
		assertEquals(GameStatus.IN_PROGRESS, kalahMove.getGameStatus());
	}

	private Game initGame() {
		Game game = new Game();
		game.setId(gameId);
		Map<Integer, Integer> scoreBoard = new LinkedHashMap<>();
		for (int i = 1; i <= 14; i++) {
			int value = (i != 7 && i != 14) ? 6 : 0;
			scoreBoard.put(i, value);
		}
		game.setScoreBoard(scoreBoard);
		game.setPlayer(Player.FIRST_PLAYER);
		game.setGameStatus(GameStatus.IN_PROGRESS);
		return game;
	}

}
