package com.game.kalah.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.game.kalah.domain.Game;
import com.game.kalah.domain.GameStatus;
import com.game.kalah.exception.GameEndedException;
import com.game.kalah.exception.InvalidIdException;
import com.game.kalah.handler.KalahHandler;
import com.game.kalah.mapper.KalahMapper;
import com.game.kalah.repository.GameRepository;

@Service
public class KalahServiceImpl implements KalahService {

	@Autowired
	private GameRepository gameRepository;

	@Autowired
	private KalahMapper kalahMapper;

	@Autowired
	private KalahHandler kalahHandler;

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	@Transactional
	public Game create() {
		log.info("create game started");
		Game game = kalahMapper.createGame();
		gameRepository.save(game);

		log.info("create game ended with gameId: " + game.getId());
		return game;
	}

	@Override
	@Transactional
	public Game get(String gameId) {
		log.info("get game started");

		Game game = gameRepository.findById(gameId)
				.orElseThrow(() -> new InvalidIdException(gameId, "Game not found for gameId: " + gameId));

		log.info("create get ended with gameId: " + game.getId());
		return game;
	}

	@Override
	@Transactional
	public Game play(String gameId, Integer pitId) {
		log.info("game play method started for gameId: {}" , gameId);
		Game game = get(gameId);
		checkGameStatus(game);
		kalahHandler.makeMove(game, pitId);
		gameRepository.save(game);
		log.info("game play method ended for gameId: {}" , gameId);
		return game;
	}

	private void checkGameStatus(Game game) {
		GameStatus status = game.getGameStatus();
		if (status != GameStatus.IN_PROGRESS) {
			gameRepository.delete(game);
			log.error("Game already termianated for gameId: " + game.getId());
			throw new GameEndedException(game.getId(), "Game has been already terminated with status:" + status,
					status);
		}
	}

}
