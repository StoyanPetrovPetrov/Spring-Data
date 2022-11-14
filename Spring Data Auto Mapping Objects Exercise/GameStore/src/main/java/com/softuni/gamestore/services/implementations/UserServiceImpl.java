package com.softuni.gamestore.services.implementations;

import com.softuni.gamestore.models.dtos.UserLoginDto;
import com.softuni.gamestore.models.dtos.UserRegisterDto;
import com.softuni.gamestore.models.entities.Game;
import com.softuni.gamestore.models.entities.User;
import com.softuni.gamestore.repositories.UserRepository;
import com.softuni.gamestore.services.interfaces.UserService;
import com.softuni.gamestore.utils.ValidationsUtil;
import org.modelmapper.ModelMapper;
import org.modelmapper.ValidationException;
import org.modelmapper.spi.ErrorMessage;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final ModelMapper modelMapper;
    private final ValidationsUtil validator;
    private User currentUser;

    public UserServiceImpl(UserRepository repository, ModelMapper modelMapper, ValidationsUtil validator) {
        this.repository = repository;
        this.modelMapper = modelMapper;
        this.validator = validator;
        this.currentUser = null;
    }


    @Override
    public String registerUser(UserRegisterDto model) {
        this.validator.validateModel(model);
        validatePasswordsEquality(model);
        User user = this.modelMapper.map(model, User.class);
        makeUserAdminIfIsFirst(user);
        this.repository.saveAndFlush(user);
        return String.format("%s was registered", user.getFullName());
    }

    @Override
    public String loginUser(UserLoginDto model) {
        this.validator.validateModel(model);
        User user = this.repository.findOneByEmailAndPassword(model.getEmail(), model.getPassword()).
                orElseThrow(() -> new org.modelmapper.ValidationException(List.of(new ErrorMessage("Incorrect username / password"))));
        this.currentUser = user;
        return String.format("Successfully logged in %s", user.getFullName());
    }

    @Override
    public String logout() {
        if (this.currentUser == null) {
            throw new org.modelmapper.ValidationException(List.of(new ErrorMessage("Cannot log out. No user was logged in.")));
        }
        String message = String.format("User %s successfully logged out", this.currentUser.getFullName());
        this.currentUser = null;
        return message;
    }

    @Override
    public boolean isAdminLogged() {
        boolean isAdmin = false;
        if (this.currentUser != null) {
            isAdmin = this.currentUser.getIsAdministrator();
        }
        return isAdmin;
    }

    @Override
    public User getCurrentUser() {
        ensureUserLoggedIn();
        return this.currentUser;
    }

    @Override
    public String addGamesToUser(Set<Game> games) {
        ensureUserLoggedIn();
        addGames(games);
        this.repository.saveAndFlush(currentUser);
        return String.format("Successfully bought games: %s",
                (games.isEmpty() ? "" : getGameTitles(games)));
    }

    private String getGameTitles(Set<Game> games) {
        return System.lineSeparator() + games.stream()
                .map(g -> String.format(" -%s", g.getTitle()))
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private void addGames(Set<Game> games) {
        this.currentUser.getGames().addAll(games);
    }

    private void ensureUserLoggedIn() {
        if (this.currentUser == null)
            throw new org.modelmapper.ValidationException(List.of(new ErrorMessage("No user logged in.")));
    }

    private void makeUserAdminIfIsFirst(User user) {
        if (this.repository.count() == 0) {
            user.setIsAdministrator(true);
        }
    }

    private void validatePasswordsEquality(UserRegisterDto model) {
        if (!model.getPassword().equals(model.getConfirmPassword())) {
            throw new ValidationException(List.of(new ErrorMessage("Password and confirm password must be same")));
        }
    }
}
