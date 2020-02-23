package com.game.kalah.validator;

public interface KalahValidator {

	/**
	 * This is to validate pitId selected to make a move
	 * @param pitId
	 * @throws com.game.kalah.exception.InvalidIdException
	 * 		if pitId selected is one of player home location i.e. 7 or 14
	 * 		or pitId selected is out of bound i.e. greater then 14
	 */
	public void validatePitId(Integer pitId);

}
