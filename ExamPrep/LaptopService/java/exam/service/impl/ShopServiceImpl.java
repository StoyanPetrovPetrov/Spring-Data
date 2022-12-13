package exam.service.impl;

import exam.model.Shop;
import exam.model.Town;
import exam.model.dtos.ShopImportDto;
import exam.model.dtos.ShopImportWrapperDto;
import exam.repository.ShopRepository;
import exam.repository.TownRepository;
import exam.service.ShopService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static exam.util.Paths.SHOPS_XML_PATH;

@Service
public class ShopServiceImpl implements ShopService {
    private final ShopRepository shopRepository;

    private final TownRepository townRepository;

    private final ModelMapper modelMapper;

    private final Validator validator;

    public ShopServiceImpl(ShopRepository shopRepository, TownRepository townRepository,
                           ModelMapper modelMapper, Validator validator) {
        this.shopRepository = shopRepository;
        this.townRepository = townRepository;
        this.modelMapper = modelMapper;
        this.validator = validator;
    }

    @Override
    public boolean areImported() {
        return this.shopRepository.count() > 0;
    }

    @Override
    public String readShopsFileContent() throws IOException {
        return Files.readString(SHOPS_XML_PATH);
    }

    @Override
    public String importShops() throws JAXBException, FileNotFoundException {
        JAXBContext context = JAXBContext.newInstance(ShopImportWrapperDto.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        ShopImportWrapperDto shopDtos =
                (ShopImportWrapperDto) unmarshaller.unmarshal(SHOPS_XML_PATH.toFile());
        List<ShopImportDto> shops = shopDtos.getShops();

        List<String> results = new ArrayList<>();
        for (ShopImportDto shopImportDto : shops) {
            Set<ConstraintViolation<ShopImportDto>> errors =
                    this.validator.validate(shopImportDto);
            if (errors.isEmpty()) {
                Optional<Shop> optionalShop =
                        this.shopRepository.findByName(shopImportDto.getName());
                if (optionalShop.isEmpty()) {
                    Shop shop = this.modelMapper.map(shopImportDto, Shop.class);

                    Optional<Town> town =
                            this.townRepository.findByName(shopImportDto.getTown().getName());

                    shop.setTown(town.get());

                    this.shopRepository.save(shop);

                    results.add(String.format("Successfully imported Shop %s - %f",
                            shop.getName(),
                            shop.getIncome()));

                } else {
                    results.add("Invalid shop");
                }
            } else {
                results.add("Invalid shop");
            }


        }
        return String.join(System.lineSeparator(), results);
    }
}
