package softuni.exam.service;

import java.io.IOException;
import javax.xml.bind.JAXBException;

public interface TaskService {

    boolean areImported();

    String readTasksFileContent() throws IOException;

    String importTasks() throws IOException, JAXBException;

    String getCoupeCarTasksOrderByPrice();
}
