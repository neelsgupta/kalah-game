package com.game.kalah.repository;

import org.springframework.data.repository.CrudRepository;

import com.game.kalah.domain.Game;

public interface GameRepository extends CrudRepository<Game, String> {

}
