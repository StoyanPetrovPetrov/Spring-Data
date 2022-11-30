package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ApartmentImportDto;
import softuni.exam.models.dto.ApartmentWrapperDto;
import softuni.exam.models.entity.Apartment;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.ApartmentRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.ApartmentService;
import softuni.exam.util.Messages;
import softuni.exam.util.PathFiles;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ApartmentServiceImpl implements ApartmentService {

    private final Validator validator;
    private final ModelMapper mapper;

    private final ApartmentRepository apartmentRepository;
    private final TownRepository townRepository;

    public ApartmentServiceImpl(Validator validator, ModelMapper mapper,
                                ApartmentRepository apartmentRepository, TownRepository townRepository) {
        this.validator = validator;
        this.mapper = mapper;
        this.apartmentRepository = apartmentRepository;
        this.townRepository = townRepository;
    }

    @Override
    public boolean areImported() {
        return this.apartmentRepository.count()>0;
    }

    @Override
    public String readApartmentsFromFile() throws IOException {
        return Files.readString(PathFiles.APARTMENTS_PATH);
    }

    @Override
    public String importApartments() throws IOException, JAXBException {
        final FileReader fileReader = new FileReader(PathFiles.APARTMENTS_PATH.toFile());

        final JAXBContext context = JAXBContext.newInstance(ApartmentWrapperDto.class);
        final Unmarshaller unmarshaller = context.createUnmarshaller();
        final ApartmentWrapperDto apartmentsDto = (ApartmentWrapperDto) unmarshaller.unmarshal(fileReader);

        return apartmentsDto
                .getApartments()
                .stream()
                .map(this::importApartment)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private String importApartment(ApartmentImportDto dto) {

        Set<ConstraintViolation<ApartmentImportDto>> errors =
                this.validator.validate(dto);

        if (!errors.isEmpty()) {
            return Messages.INVALID + Messages.APARTMENT;
        }

        Optional<Apartment> apartmentExist =
                this.apartmentRepository.findByTown_TownNameAndArea(dto.getTownName(), dto.getArea());

        if (apartmentExist.isPresent()) {
            return Messages.INVALID + Messages.APARTMENT;
        }

        Apartment apartment = this.mapper.map(dto, Apartment.class);

        Optional<Town> town = this.townRepository.findByTownName(dto.getTownName());

        apartment.setTown(town.get());

        this.apartmentRepository.save(apartment);

        return Messages.SUCCESSFULLY + Messages.APARTMENT + Messages.INTERVAL + apartment;
    }
    }

