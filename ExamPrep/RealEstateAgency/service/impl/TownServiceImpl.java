package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.TownImportDto;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.TownService;
import softuni.exam.util.Messages;
import softuni.exam.util.PathFiles;

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

    private final Gson gson;
    private final TownRepository townRepository;
    private final ModelMapper mapper;
    private final Validator validator;

    @Autowired
    public TownServiceImpl(Gson gson, TownRepository townRepository, ModelMapper mapper, Validator validator) {
        this.gson = gson;
        this.townRepository = townRepository;
        this.mapper = mapper;

        this.validator = validator;
    }

    @Override
    public boolean areImported() {
        return this.townRepository.count() > 0;
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

                final Optional<Town> townExist = this.townRepository.findByTownName(importTown.getTownName());

                if(townExist.isEmpty()) {

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

