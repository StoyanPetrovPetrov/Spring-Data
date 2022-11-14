package com.softuni.gamestore.repositories;

import com.softuni.gamestore.models.entities.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game,Integer> {
    Optional<Game> findOneById(int id);

    Optional<Game> findOneByTitle(String title);
}
