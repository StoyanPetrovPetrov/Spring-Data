package com.example.football.service.impl;

import com.example.football.models.dto.TeamImportDto;
import com.example.football.models.entity.Team;
import com.example.football.models.entity.Town;
import com.example.football.repository.TeamRepository;
import com.example.football.repository.TownRepository;
import com.example.football.service.TeamService;
import com.example.football.util.Messages;
import com.example.football.util.PathFiles;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

@Service
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;

    private final Gson gson;
    private final Validator validator;
    private final ModelMapper mapper;
    private final TownRepository townRepository;

    @Autowired
    public TeamServiceImpl(
            TeamRepository teamRepository,
            Gson gson,
            Validator validator,
            ModelMapper mapper,
            TownRepository townRepository) {
        this.teamRepository = teamRepository;
        this.gson = gson;
        this.validator = validator;
        this.mapper = mapper;
        this.townRepository = townRepository;
    }


    @Override
    public boolean areImported() {
        return this.teamRepository.count() > 0;
    }

    @Override
    public String readTeamsFileContent() throws IOException {
        return Files.readString(PathFiles.TEAMS_PATH);
    }

    @Override
    public String importTeams() throws IOException {
        final String json = this.readTeamsFileContent();

        final TeamImportDto[] importTeams = this.gson.fromJson(json, TeamImportDto[].class);

        final List<String> result = new ArrayList<>();

        for (TeamImportDto importTeam : importTeams) {

            final Set<ConstraintViolation<TeamImportDto>> validationErrors =
                    this.validator.validate(importTeam);

            if (validationErrors.isEmpty()) {

                final Optional<Team> teamExist = this.teamRepository.findByName(importTeam.getName());

                boolean canAdded = teamExist.isEmpty();

                if (canAdded) {

                    Team team = this.mapper.map(importTeam, Team.class);

                    final Town town = this.townRepository
                            .findByName(importTeam.getTownName())
                            .orElseThrow(NoSuchElementException::new);

                    team.setTown(town);

                    this.teamRepository.save(team);

                    final String msg = Messages.SUCCESSFULLY + Messages.TEAM + Messages.INTERVAL + team;

                    result.add(msg);

                } else {
                    result.add(Messages.INVALID + Messages.TEAM);
                }

            } else {
                result.add(Messages.INVALID + Messages.TEAM);
            }
        }
        return String.join(System.lineSeparator(), result);
    }
}
