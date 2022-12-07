package softuni.exam.service;


import java.io.IOException;
import javax.xml.bind.JAXBException;
import softuni.exam.models.entity.Car;

public interface CarService {

    boolean areImported();

    String readCarsFromFile() throws IOException;

    String importCars() throws IOException, JAXBException;

    Car getById(Long id);
}
