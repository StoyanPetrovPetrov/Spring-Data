package com.softuni.gamestore.services.interfaces;

import com.softuni.gamestore.models.entities.Game;
import com.softuni.gamestore.models.entities.User;

import java.util.Set;

public interface OrderService {
    String addToOrder(Game game, User user);

    String removeFromOrder(Game game, User user);

    Set<Game> buyItems(User user);
}
