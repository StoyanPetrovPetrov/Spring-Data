package com.softuni.gamestore.services.implementations;

import com.softuni.gamestore.models.dtos.GameAddDto;
import com.softuni.gamestore.models.dtos.UserLoginDto;
import com.softuni.gamestore.models.dtos.UserRegisterDto;
import com.softuni.gamestore.models.entities.Game;
import com.softuni.gamestore.models.entities.User;
import com.softuni.gamestore.services.interfaces.*;
import org.modelmapper.ValidationException;
import org.modelmapper.spi.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
@Service
public class AppEngineImpl implements AppEngine {
    private final ConsoleService console;
    private final UserService userService;
    private final GameService gameService;
    private final OrderService orderService;

    @Autowired
    public AppEngineImpl(ConsoleService console, UserService userService, GameService gameService, OrderService orderService) {
        this.console = console;
        this.userService = userService;
        this.gameService = gameService;
        this.orderService = orderService;
    }

    @Override
    public void run() throws IOException {
        while (true) {
            try {
                String[] commands = console.readStringFromConsole("Enter your command (or Exit): ").split("\\|");
                switch (commands[0]) {
                    case "RegisterUser" -> console.printInfoMessage(registerUser(commands));
                    case "LoginUser" -> console.printInfoMessage(loginUser(commands));
                    case "Logout" -> console.printInfoMessage(logout());
                    case "AddGame" -> console.printInfoMessage(addGame(commands));
                    case "EditGame" -> console.printInfoMessage(editGame(commands));
                    case "DeleteGame" -> console.printInfoMessage(deleteGame(Integer.parseInt(commands[1])));
                    case "AllGames" -> console.printInfoMessage(allGames());
                    case "DetailGame" -> console.printInfoMessage(gameDetails(commands[1]));
                    case "OwnedGames" -> console.printInfoMessage(ownedGames());
                    case "AddItem" -> console.printInfoMessage(addItemToShoppingCard(commands[1]));
                    case "RemoveItem" -> console.printInfoMessage(removeItemFromShoppingCard(commands[1]));
                    case "BuyItem" -> console.printInfoMessage(buyItems());
                    case "Exit" -> {
                        console.printSuccessMessage("Buy buy!!!");
                        return;
                    }
                    default -> console.printErrorMessage("Invalid command!");
                }
            } catch (ValidationException ve) {
                printValidationException(ve);
            }
        }
    }

    @Transactional
    String buyItems() {
        Set<Game> boughtGames = this.orderService.buyItems(this.userService.getCurrentUser());
        return this.userService.addGamesToUser(boughtGames);
    }

    private String removeItemFromShoppingCard(String gameTitle) {
        User currentUser = this.userService.getCurrentUser();
        Game game = this.gameService.getGame(gameTitle);
        return this.orderService.removeFromOrder(game, currentUser);
    }

    private String addItemToShoppingCard(String gameTitle) {
        User currentUser = this.userService.getCurrentUser();
        Game game = this.gameService.getGame(gameTitle);
        return this.orderService.addToOrder(game, currentUser);
    }

    private String ownedGames() {
        return this.userService.getCurrentUser()
                .getGames().stream()
                .map(Game::getTitle)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private String gameDetails(String title) {
        return this.gameService.getGameDetails(title);
    }

    private String allGames() {
        return this.gameService.getAll();
    }

    private String deleteGame(int id) {
        return this.gameService.deleteGame(id);
    }

    private String editGame(String[] commands) {
        int id = Integer.parseInt(commands[1]);
        return this.gameService.editGame(id, Arrays.copyOfRange(commands, 2, commands.length));
    }

    private String addGame(String[] commands) {
        ensureAdmin();
        GameAddDto gameAddDto = getGameAddDto(commands);

        return this.gameService.add(gameAddDto);
    }

    private GameAddDto getGameAddDto(String[] commands) {
        return new GameAddDto(commands[1],
                BigDecimal.valueOf(Double.parseDouble(commands[2])),
                BigDecimal.valueOf(Double.parseDouble(commands[3])),
                commands[4], commands[5], commands[6], commands[7]);
    }

    private void ensureAdmin() {
        if (!this.userService.isAdminLogged()) {
            throw new ValidationException(
                    List.of(new ErrorMessage("Operation can be performed only by Administrator")));
        }
    }

    private String logout() {
        return this.userService.logout();
    }

    private String loginUser(String[] commands) {
        return this.userService.loginUser(new UserLoginDto(commands[1], commands[2]));
    }

    private String registerUser(String[] commands) {
        return this.userService.registerUser(
                new UserRegisterDto(commands[1], commands[2], commands[3], commands[4]));
    }

    private void printValidationException(ValidationException ve) {
        this.console.printErrorMessage(
                ve.getErrorMessages()
                        .stream()
                        .map(ErrorMessage::getMessage)
                        .collect(Collectors.joining(System.lineSeparator())));
    }


}