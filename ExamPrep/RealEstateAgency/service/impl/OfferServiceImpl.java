package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.OfferImportDto;
import softuni.exam.models.dto.OfferWrapperDto;
import softuni.exam.models.entity.Agent;
import softuni.exam.models.entity.Apartment;
import softuni.exam.models.entity.ApartmentType;
import softuni.exam.models.entity.Offer;
import softuni.exam.repository.AgentRepository;
import softuni.exam.repository.ApartmentRepository;
import softuni.exam.repository.OfferRepository;
import softuni.exam.service.OfferService;
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
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OfferServiceImpl implements OfferService {

    private final ApartmentType apartmentType = ApartmentType.three_rooms;

    private final Validator validator;
    private final ModelMapper mapper;

    private final OfferRepository offerRepository;
    private final AgentRepository agentRepository;
    private final ApartmentRepository apartmentRepository;

    public OfferServiceImpl(Validator validator, ModelMapper mapper, OfferRepository offerRepository,
                            AgentRepository agentRepository, ApartmentRepository apartmentRepository) {
        this.validator = validator;
        this.mapper = mapper;
        this.offerRepository = offerRepository;
        this.agentRepository = agentRepository;
        this.apartmentRepository = apartmentRepository;
    }

    @Override
    public boolean areImported() {
        return this.offerRepository.count()>0;
    }

    @Override
    public String readOffersFileContent() throws IOException {
        return Files.readString(PathFiles.OFFERS_PATH);
    }

    @Override
    public String importOffers() throws IOException, JAXBException {
        final FileReader fileReader = new FileReader(PathFiles.OFFERS_PATH.toFile());

        final JAXBContext context = JAXBContext.newInstance(OfferWrapperDto.class);
        final Unmarshaller unmarshaller = context.createUnmarshaller();
        final OfferWrapperDto offersDto = (OfferWrapperDto) unmarshaller.unmarshal(fileReader);

        return offersDto
                .getOffers()
                .stream()
                .map(this::importOffer)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private String importOffer(OfferImportDto dto) {

        Set<ConstraintViolation<OfferImportDto>> errors =
                this.validator.validate(dto);

        if (!errors.isEmpty()) {
            return Messages.INVALID + Messages.OFFER;
        }

        Optional<Agent> agentExist =
                this.agentRepository.findByFirstName(dto.getAgent().getName());

        if (agentExist.isEmpty()) {
            return Messages.INVALID + Messages.OFFER;
        }

        Offer offer = this.mapper.map(dto, Offer.class);

        Optional<Apartment> apartment = this.apartmentRepository.findById(dto.getApartment().getId());

        offer.setAgent(agentExist.get());

        offer.setApartment(apartment.get());

        this.offerRepository.save(offer);

        return Messages.SUCCESSFULLY + Messages.OFFER + Messages.INTERVAL + offer.importInfo();
    }


    @Override
    public String exportOffers() {
        return this.offerRepository
                .findByApartment_ApartmentTypeOrderByApartment_AreaDescPriceAsc(apartmentType)
                .orElseThrow(NoSuchElementException::new)
                .stream()
                .map(Offer::toString)
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
