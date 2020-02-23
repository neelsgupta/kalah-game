package com.game.kalah.validator;

import org.junit.Test;

import com.game.kalah.exception.InvalidIdException;

public class KalahValidatorTest {

	private final KalahValidatorImpl kalahValidator = new KalahValidatorImpl();

	private static final Integer VALID_PIT_ID = 1;
	private static final Integer PIT_ID_OUTOFBOUND = 25;
	private static final Integer FIRST_PLAYER_HOME_PIT_ID = 7;
	private static final Integer SECOND_PLAYER_HOME_PIT_ID = 14;

	@Test
	public void testValidationForValidPitId() {
		kalahValidator.validatePitId(VALID_PIT_ID);
	}

	@Test(expected = InvalidIdException.class)
	public void testValidationThrowInvalidIdExceptionIfInvalidPitId() {
		kalahValidator.validatePitId(PIT_ID_OUTOFBOUND);
	}

	@Test(expected = InvalidIdException.class)
	public void testValidationThrowInvalidIdExceptionIfPitIdIsFirstPlayerHome() {
		kalahValidator.validatePitId(FIRST_PLAYER_HOME_PIT_ID);
	}

	@Test(expected = InvalidIdException.class)
	public void testValidationThrowInvalidIdExceptionIfPitIdIsSecondPlayerHome() {
		kalahValidator.validatePitId(SECOND_PLAYER_HOME_PIT_ID);
	}

}
