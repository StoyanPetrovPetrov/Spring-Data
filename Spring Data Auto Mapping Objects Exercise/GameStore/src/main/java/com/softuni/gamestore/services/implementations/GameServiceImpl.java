package com.softuni.gamestore.services.implementations;

import com.softuni.gamestore.models.dtos.GameAddDto;
import com.softuni.gamestore.models.dtos.GameDetailsDto;
import com.softuni.gamestore.models.dtos.GameViewDto;
import com.softuni.gamestore.models.entities.Game;
import com.softuni.gamestore.repositories.GameRepository;
import com.softuni.gamestore.services.interfaces.GameService;
import com.softuni.gamestore.utils.ValidationsUtil;
import org.modelmapper.ModelMapper;
import org.modelmapper.ValidationException;
import org.modelmapper.spi.ErrorMessage;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameServiceImpl implements GameService {
    private final GameRepository repository;
    private final ModelMapper modelMapper;
    private final ValidationsUtil validator;

    public GameServiceImpl(GameRepository repository, ModelMapper modelMapper, ValidationsUtil validator) {
        this.repository = repository;
        this.modelMapper = modelMapper;
        this.validator = validator;
    }

    @Override
    public String add(GameAddDto model) {
        this.validator.validateModel(model);
        Game game = this.modelMapper.map(model, Game.class);
        this.repository.saveAndFlush(game);
        return String.format("Added %s", game.getTitle());
    }

    @Override
    public String editGame(int id, String... values) {
        Game game = getGameIfExists(id);
        updateGameProperties(game, values);
        GameAddDto gameModel = this.modelMapper.map(game, GameAddDto.class);
        this.validator.validateModel(gameModel);
        this.repository.saveAndFlush(game);
        return String.format("Edited %s", game.getTitle());
    }

    @Override
    public String deleteGame(int id) {
        Game game = getGameIfExists(id);
        this.repository.delete(game);
        return String.format("Deleted %s", game.getTitle());
    }

    @Override
    public String getAll() {
        return this.repository.findAll()
                .stream()
                .map(g -> this.modelMapper.map(g, GameViewDto.class))
                .map(GameViewDto::toString)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    @Override
    public String getGameDetails(String title) {
        Game game = getGameIfExists(title);
        return this.modelMapper.map(game, GameDetailsDto.class).toString();
    }

    @Override
    public Game getGame(String gameTitle) {
        return this.getGameIfExists(gameTitle);
    }


    private void updateGameProperties(Game game, String[] values) {
        for (String value : values) {
            String[] tokens = value.split("=");
            String propName = tokens[0];
            String newValue = tokens[1];
            setPropToGame(game, propName, newValue);
        }
    }

    private void setPropToGame(Game game, String propName, String newValue) {

        switch (propName) {
            case "title":
                game.setTitle(newValue);
                break;
            case "trailerUrl":
                game.setTrailerUrl(newValue);
                break;
            case "imageThumbnailUrl":
                game.setImageThumbnailUrl(newValue);
                break;
            case "description":
                game.setDescription(newValue);
                break;
            case "size":
                game.setSize(new BigDecimal(newValue));
                break;
            case "price":
                game.setPrice(new BigDecimal(newValue));
                break;
            case "releaseDate":
                game.setReleaseDate(LocalDate.parse(newValue, DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                break;
        }
    }

    private Game getGameIfExists(String title) {
        return this.repository.findOneByTitle(title).orElseThrow(() -> new ValidationException(List.of(
                new ErrorMessage(String.format("Game '%s' not found", title)))));
    }

    private Game getGameIfExists(int id) {
        return this.repository.findOneById(id)
                .orElseThrow(() -> new org.modelmapper.ValidationException(List.of(
                        new ErrorMessage(String.format("Game ID %d not found", id)))));
    }
}
