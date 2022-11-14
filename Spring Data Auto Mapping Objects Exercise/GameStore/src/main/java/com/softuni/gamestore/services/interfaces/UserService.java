package com.softuni.gamestore.services.interfaces;

import com.softuni.gamestore.models.dtos.UserLoginDto;
import com.softuni.gamestore.models.dtos.UserRegisterDto;
import com.softuni.gamestore.models.entities.Game;
import com.softuni.gamestore.models.entities.User;

import java.util.Set;

public interface UserService {
    String registerUser(UserRegisterDto mode);

    String loginUser(UserLoginDto model);

    String logout();

    boolean isAdminLogged();

    User getCurrentUser();

    String addGamesToUser(Set<Game> games);
}
