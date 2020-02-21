package com.game.kalah.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.game.kalah.exception.InvalidIdException;

@Component
public class KalahValidatorImpl implements KalahValidator {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public void validatePitId(Integer pitId) throws InvalidIdException {

		if (!(pitId > 0 && pitId < 14)) {
			log.error("Validation failed for pitId, entered pitId is out of bound. " + String.valueOf(pitId));
			throw new InvalidIdException(String.valueOf(pitId),
					"Entered pitId is out of valid range, please enter valid pitId");
		}

		if (!(pitId != 7 && pitId != 14)) {
			log.error(
					"Validation failed for pitId, entered pitId is one of kalah home pitid: " + String.valueOf(pitId));
			throw new InvalidIdException(String.valueOf(pitId), "Invalid pitId, you cannot enter Home pitId");
		}
	}

}
