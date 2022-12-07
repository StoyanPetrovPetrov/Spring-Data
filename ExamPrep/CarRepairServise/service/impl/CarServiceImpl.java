package softuni.exam.service.impl;

import java.io.IOException;
import javax.xml.bind.JAXBException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.CarsSeedRootDTO;
import softuni.exam.models.entity.Car;
import softuni.exam.repository.CarRepository;
import softuni.exam.service.CarService;
import softuni.exam.util.FileUtil;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

@Service
public class CarServiceImpl implements CarService {

    private static final String CARS_FILE_PATH = "src/main/resources/files/xml/cars.xml";

    private final CarRepository carRepository;

    private final XmlParser xmlParser;

    private final ModelMapper modelMapper;

    private final ValidationUtil validationUtil;

    private final FileUtil fileUtil;

    public CarServiceImpl(CarRepository carRepository, XmlParser xmlParser, ModelMapper modelMapper,
        ValidationUtil validationUtil, FileUtil fileUtil) {
        this.carRepository = carRepository;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.fileUtil = fileUtil;
    }

    @Override
    public boolean areImported() {
        return this.carRepository.count() > 0;
    }

    @Override
    public String readCarsFromFile() throws IOException {
        return this.fileUtil.readFile(CARS_FILE_PATH);
    }

    @Override
    public String importCars() throws IOException, JAXBException {
        StringBuilder stringBuilder = new StringBuilder();

        xmlParser
            .parseXml(CarsSeedRootDTO.class, CARS_FILE_PATH)
            .getCars()
            .stream()
            .filter(carSeedDTO -> {
                boolean isValid = validationUtil.isValid(carSeedDTO);

                Car car = this.carRepository.findByPlateNumber(carSeedDTO.getPlateNumber());

                if (car != null) {
                    isValid = false;
                }

                stringBuilder.append(isValid ?
                        String.format("Successfully imported car %s - %s",
                            carSeedDTO.getCarMake(), carSeedDTO.getCarModel())
                        : "Invalid car")
                    .append(System.lineSeparator());

                return isValid;
            })
            .map(carSeedDTO -> modelMapper.map(carSeedDTO, Car.class))
            .forEach(this.carRepository::save);

        return stringBuilder.toString().trim();
    }

    @Override
    public Car getById(Long id) {
        return this.carRepository.getById(id);
    }
}
