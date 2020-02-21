package com.game.kalah.validator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import com.game.kalah.exception.InvalidIdException;

public class KalahValidatorTest {

	private final KalahValidatorImpl kalahValidator = new KalahValidatorImpl();
	
	private final Integer validPitId = 1;
	private final Integer pitIdOutOfBound = 25;
	private final Integer firstPlayerHomePitId = 7;
	private final Integer secondPlayerHomePitId = 14;

	@Test
	public void testValidationForValidPitId() {
		kalahValidator.validatePitId(validPitId);
	}

	@Test(expected = InvalidIdException.class)
	public void testValidationThrowInvalidIdExceptionIfInvalidPitId() throws InvalidIdException {
		kalahValidator.validatePitId(pitIdOutOfBound);
	}

	@Test(expected = InvalidIdException.class)
	public void testValidationThrowInvalidIdExceptionIfPitIdIsFirstPlayerHome() throws InvalidIdException {
		kalahValidator.validatePitId(firstPlayerHomePitId);
	}

	@Test(expected = InvalidIdException.class)
	public void testValidationThrowInvalidIdExceptionIfPitIdIsSecondPlayerHome() throws InvalidIdException {
		kalahValidator.validatePitId(secondPlayerHomePitId);
	}

}
