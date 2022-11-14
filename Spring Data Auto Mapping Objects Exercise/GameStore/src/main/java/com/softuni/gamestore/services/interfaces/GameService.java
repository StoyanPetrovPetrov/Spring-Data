package com.softuni.gamestore.services.interfaces;

import com.softuni.gamestore.models.dtos.GameAddDto;
import com.softuni.gamestore.models.entities.Game;

public interface GameService {
    String add(GameAddDto gameAddDto);

    String editGame(int id, String... values);

    String deleteGame(int id);

    String getAll();

    String getGameDetails(String title);

    Game getGame(String gameTitle);
}
