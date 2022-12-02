package com.example.football.service.impl;

import com.example.football.models.dto.StatImportDto;
import com.example.football.models.dto.StatWrapperDto;
import com.example.football.models.entity.Stat;
import com.example.football.repository.StatRepository;
import com.example.football.service.StatService;
import com.example.football.util.Messages;
import com.example.football.util.PathFiles;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StatServiceImpl implements StatService {
    private final StatRepository statRepository;

    private final Validator validator;
    private final ModelMapper mapper;

    public StatServiceImpl(StatRepository statRepository, Validator validator, ModelMapper mapper) {
        this.statRepository = statRepository;
        this.validator = validator;
        this.mapper = mapper;
    }


    @Override
    public boolean areImported() {

        return this.statRepository.count()>0;
    }

    @Override
    public String readStatsFileContent() throws IOException {

        return Files.readString(PathFiles.STATS_PATH);
    }

    @Override
    public String importStats() throws FileNotFoundException, JAXBException {

        final FileReader fileReader = new FileReader(PathFiles.STATS_PATH.toFile());

        final JAXBContext context = JAXBContext.newInstance(StatWrapperDto.class);
        final Unmarshaller unmarshaller = context.createUnmarshaller();
        final StatWrapperDto statsDto = (StatWrapperDto) unmarshaller.unmarshal(fileReader);


        return statsDto
                .getStats()
                .stream()
                .map(this::importStat)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private String importStat(StatImportDto dto) {

        Set<ConstraintViolation<StatImportDto>> errors =
                this.validator.validate(dto);

        if (!errors.isEmpty()) {
            return Messages.INVALID + Messages.STAT;
        }

        Optional<Stat> statExist =
                this.statRepository
                        .findByEnduranceAndPassingAndShooting(dto.getEndurance(), dto.getPassing(), dto.getShooting());

        if (statExist.isPresent()) {
            return Messages.INVALID + Messages.STAT;
        }

        Stat stat = this.mapper.map(dto, Stat.class);

        this.statRepository.save(stat);

        return Messages.SUCCESSFULLY + Messages.STAT + Messages.INTERVAL + stat;
    }
    }

