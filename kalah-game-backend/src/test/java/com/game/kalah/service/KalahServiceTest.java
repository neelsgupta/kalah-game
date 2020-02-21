package com.game.kalah.service;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.game.kalah.domain.Game;
import com.game.kalah.domain.GameStatus;
import com.game.kalah.exception.GameEndedException;
import com.game.kalah.exception.InvalidIdException;
import com.game.kalah.handler.KalahHandler;
import com.game.kalah.mapper.KalahMapper;
import com.game.kalah.repository.GameRepository;

@RunWith(MockitoJUnitRunner.class)
public class KalahServiceTest {

	@InjectMocks
	private KalahServiceImpl kalahService;

	@Mock
	private KalahMapper kalahMapper;

	@Mock
	private GameRepository repository;

	@Mock
	private KalahHandler kalahHandler;

	private String gameId = "anyGameId";
	private Integer pitId = 1;

	@Test
	public void testCreateGame() {
		Game game = new Game();
		when(kalahMapper.createGame()).thenReturn(game);
		when(repository.save(game)).thenReturn(game);
		kalahService.create();

		verify(kalahMapper).createGame();
		verify(repository).save(game);
	}

	@Test
	public void testPlayGame() throws Exception {
		Game game = new Game();
		game.setGameStatus(GameStatus.IN_PROGRESS);
		Optional<Game> optionalGame = Optional.ofNullable(game);
		when(repository.findById(gameId)).thenReturn(optionalGame);
		doNothing().when(kalahHandler).makeMove(game, pitId);
		when(repository.save(game)).thenReturn(game);
		kalahService.play(gameId, pitId);

		verify(repository).findById(gameId);
		verify(kalahHandler).makeMove(game, pitId);
		verify(repository).save(game);
		verify(repository, never()).delete(game);
	}

	@Test(expected = InvalidIdException.class)
	public void testPlayGameThrowExceptionIfGameNotExisted() throws Exception {
		Game game = new Game();
		doThrow(InvalidIdException.class).when(repository).findById(gameId);
		kalahService.play(gameId, pitId);

		verify(repository).findById(gameId);
		verify(kalahHandler, never()).makeMove(game, pitId);
		verify(repository, never()).save( game);
		verify(repository, never()).delete(game);
	}

	@Test(expected = GameEndedException.class)
	public void testPlayGameThrowExceptionIfGameNotInProgress() throws Exception {
		Game game = new Game();
		Optional<Game> optionalGame = Optional.ofNullable(game);
		when(repository.findById(gameId)).thenReturn(optionalGame);
		kalahService.play(gameId, pitId);

		verify(repository).findById(gameId);
		verify(repository).delete(game);
		verify(kalahHandler, never()).makeMove(game, pitId);
		verify(repository, never()).save( game);
	}

}
