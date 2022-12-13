package exam.service.impl;

import com.google.gson.Gson;
import exam.model.Laptop;
import exam.model.Shop;
import exam.model.dtos.LaptopImportDto;
import exam.repository.LaptopRepository;
import exam.repository.ShopRepository;
import exam.service.LaptopService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static exam.util.Paths.LAPTOPS_JSON_PATH;

@Service
public class LaptopServiceImpl implements LaptopService {

    private final LaptopRepository laptopRepository;

    private final ShopRepository shopRepository;

    private final ModelMapper modelMapper;

    private final Gson gson;

    private final Validator validator;

    @Autowired
    public LaptopServiceImpl(LaptopRepository laptopRepository, ShopRepository shopRepository,
                             ModelMapper modelMapper, Gson gson, Validator validator) {
        this.laptopRepository = laptopRepository;
        this.shopRepository = shopRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validator = validator;
    }

    @Override
    public boolean areImported() {
        return this.laptopRepository.count()>0;
    }

    @Override
    public String readLaptopsFileContent() throws IOException {
        return Files.readString(LAPTOPS_JSON_PATH);
    }

    @Override
    public String importLaptops() throws IOException {
        String jsonLaptops = readLaptopsFileContent();

        LaptopImportDto[] laptopImportDtos =
                this.gson.fromJson(jsonLaptops, LaptopImportDto[].class);

        List<String> results = new ArrayList<>();

        for (LaptopImportDto laptopImportDto : laptopImportDtos) {
            Set<ConstraintViolation<LaptopImportDto>> errors =
                    this.validator.validate(laptopImportDto);

            if(errors.isEmpty()) {
                Optional<Laptop> optionalLaptop =
                        this.laptopRepository.findByMacAddress(laptopImportDto.getMacAddress());

                if(optionalLaptop.isEmpty()) {
                    Laptop laptop = this.modelMapper.map(laptopImportDto, Laptop.class);

                    Optional<Shop> shop =
                            this.shopRepository.findByName(laptopImportDto.getShop().getName());


                    laptop.setShop(shop.get());

                    this.laptopRepository.save(laptop);

                    results.add(String.format("Successfully imported Laptop %s - %.2f - %d - %d",
                            laptop.getMacAddress(),
                            laptop.getCpuSpeed(),
                            laptop.getRam(),
                            laptop.getStorage()));

                } else {
                    results.add("Invalid Laptop");
                }
            } else {
                results.add("Invalid Laptop");
            }
        }
        return String.join(System.lineSeparator(), results);
    }
    @Override
    public String exportBestLaptops() {
        List<Laptop> bestLaptops =
                this.laptopRepository.findBestLaptops();


        return bestLaptops
                .stream()
                .map(Laptop::toString)
                .collect(Collectors.joining("\n"));
    }
}
