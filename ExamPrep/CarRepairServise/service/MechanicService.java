package softuni.exam.service;


import java.io.IOException;
import softuni.exam.models.entity.Mechanic;

public interface MechanicService {

    boolean areImported();

    String readMechanicsFromFile() throws IOException;

    String importMechanics() throws IOException;

    Mechanic findByFirstName(String firstName);
}
