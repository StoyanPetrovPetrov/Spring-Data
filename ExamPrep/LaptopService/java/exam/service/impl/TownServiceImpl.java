package exam.service.impl;

import exam.model.Town;
import exam.model.dtos.TownImportDto;
import exam.model.dtos.TownImportWrapperDto;
import exam.repository.TownRepository;
import exam.service.TownService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.validation.Validator;
import javax.xml.bind.Unmarshaller;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static exam.util.Paths.TOWNS_XML_PATH;

@Service
public class TownServiceImpl implements TownService {

    private final TownRepository townRepository;
    private final Validator validator;
    private final ModelMapper modelMapper;

    public TownServiceImpl(TownRepository townRepository, Validator validator, ModelMapper modelMapper) {
        this.townRepository = townRepository;
        this.validator = validator;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return this.townRepository.count() > 0;
    }

    @Override
    public String readTownsFileContent() throws IOException {
        return Files.readString(TOWNS_XML_PATH);
    }

    @Override
    public String importTowns() throws JAXBException, FileNotFoundException {
        JAXBContext context = JAXBContext.newInstance(TownImportWrapperDto.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        TownImportWrapperDto townDtos =
                (TownImportWrapperDto) unmarshaller.unmarshal(TOWNS_XML_PATH.toFile());

        List<TownImportDto> towns = townDtos.getTowns();
        List<String> results = new ArrayList<>();
        for (TownImportDto townImportDto : towns) {
            Set<ConstraintViolation<TownImportDto>> errors =
                    this.validator.validate(townImportDto);

            if (errors.isEmpty()) {
                Town town = this.modelMapper.map(townImportDto, Town.class);
                this.townRepository.save(town);
                results.add(String.format("Successfully imported Town %s",
                        town.getName()));
            } else {
                results.add("Invalid town");
            }

        }
        return String.join(System.lineSeparator(), results);
    }
}
