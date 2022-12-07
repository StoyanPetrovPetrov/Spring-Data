package softuni.exam.service;

import java.io.IOException;
import softuni.exam.models.entity.Part;

public interface PartService {

    boolean areImported();

    String readPartsFileContent() throws IOException;

    String importParts() throws IOException;

    Part getById(Long id);
}
