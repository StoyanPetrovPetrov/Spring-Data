package com.example.football.service.impl;

import com.example.football.models.dto.TownImportDto;
import com.example.football.models.entity.Town;
import com.example.football.repository.TownRepository;
import com.example.football.service.TownService;
import com.example.football.util.Messages;
import com.example.football.util.PathFiles;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
public class TownServiceImpl implements TownService {

    private final TownRepository townRepository;
    private final Gson gson;
    private final ModelMapper mapper;
    private final Validator validator;

    public TownServiceImpl(TownRepository townRepository, Gson gson, ModelMapper mapper, Validator validator) {
        this.townRepository = townRepository;
        this.gson = gson;
        this.mapper = mapper;
        this.validator = validator;
    }


    @Override
    public boolean areImported() {

        return this.townRepository.count()> 0;
    }

    @Override
    public String readTownsFileContent() throws IOException {

        return Files.readString(PathFiles.TOWNS_PATH);
    }

    @Override
    public String importTowns() throws IOException {

        final String json = this.readTownsFileContent();

        final TownImportDto[] importTowns = this.gson.fromJson(json, TownImportDto[].class);

        final List<String> result = new ArrayList<>();

        for (TownImportDto importTown : importTowns) {

            final Set<ConstraintViolation<TownImportDto>> validationErrors =
                    this.validator.validate(importTown);

            if (validationErrors.isEmpty()) {

                final Optional<Town> townExist = this.townRepository.findByName(importTown.getName());

                boolean canAdded = townExist.isEmpty();

                if (canAdded) {

                    Town town = this.mapper.map(importTown, Town.class);

                    this.townRepository.save(town);

                    final String msg = Messages.SUCCESSFULLY + Messages.TOWN + Messages.INTERVAL + town;

                    result.add(msg);

                } else {
                    result.add(Messages.INVALID + Messages.TOWN);
                }

            } else {
                result.add(Messages.INVALID + Messages.TOWN);
            }
        }
        return String.join(System.lineSeparator(), result);
    }
    }

