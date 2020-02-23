package com.game.kalah.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.game.kalah.dto.KalahErrorResponse;

@ControllerAdvice
public class KalahExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	protected ResponseEntity<KalahErrorResponse> handleConflict(InvalidIdException ex) {
		KalahErrorResponse kalahErrorResponse = new KalahErrorResponse();
		kalahErrorResponse.setId(ex.getId());
		kalahErrorResponse.setMessage(ex.getMessage());
		return new ResponseEntity<>(kalahErrorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(GameEndedException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	protected ResponseEntity<KalahErrorResponse> handleConflict(GameEndedException ex) {
		KalahErrorResponse kalahErrorResponse = new KalahErrorResponse();
		kalahErrorResponse.setMessage(ex.getMessage());
		kalahErrorResponse.setGameStatus(ex.getGameStatus());
		return new ResponseEntity<>(kalahErrorResponse, HttpStatus.CONFLICT);
	}
}
