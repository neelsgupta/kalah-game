package com.game.kalah.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.game.kalah.domain.Game;
import com.game.kalah.dto.KalahInitResponse;
import com.game.kalah.dto.KalahMovedResponse;
import com.game.kalah.mapper.KalahMapper;
import com.game.kalah.service.KalahService;
import com.game.kalah.validator.KalahValidator;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class KalahController {

	private static final String GAMES = "/games";
	private static final String GET_GAME = "/games/{gameId}";
	private static final String PLAY_GAME = "/games/{gameId}/pits/{pitId}";

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	public KalahValidator kalahValidator;

	@Autowired
	public KalahService kalahService;

	@Autowired
	public KalahMapper kalahMapper;

	@PostMapping(GAMES)
	public ResponseEntity<KalahInitResponse> createGame() {
		log.info("entering to createGame endpoint.");

		Game game = kalahService.create();
		KalahInitResponse kalahInitResponse = kalahMapper.mapToIntiDto(game);
		HttpHeaders headers = new HttpHeaders();
		headers.add("URL", kalahInitResponse.getUri());

		log.info("exiting to createGame endpoint.");
		return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(kalahInitResponse);
	}

	@GetMapping(GET_GAME)
	public ResponseEntity<KalahMovedResponse> getGame(@PathVariable("gameId") String gameId) {
		log.info("entering to getGame endpoint.");

		Game game = kalahService.get(gameId);
		KalahMovedResponse kalahMovedResponse = kalahMapper.mapToMovedDto(game);

		log.info("exiting to getGame endpoint.");
		return ResponseEntity.status(HttpStatus.OK).body(kalahMovedResponse);
	}

	@PutMapping(PLAY_GAME)
	public ResponseEntity<KalahMovedResponse> playGame(@PathVariable("gameId") String gameId,
			@PathVariable("pitId") Integer pitId) throws Exception {
		log.info("entering to playGame endpoint.");

		kalahValidator.validatePitId(pitId);
		Game game = kalahService.play(gameId, pitId);
		KalahMovedResponse kalahMovedResponse = kalahMapper.mapToMovedDto(game);

		log.info("exiting to playGame endpoint.");
		return ResponseEntity.status(HttpStatus.OK).body(kalahMovedResponse);
	}

}
