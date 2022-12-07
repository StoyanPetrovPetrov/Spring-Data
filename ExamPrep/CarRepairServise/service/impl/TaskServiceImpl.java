package softuni.exam.service.impl;

import java.io.IOException;
import java.util.List;
import javax.xml.bind.JAXBException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.TasksSeedRootDTO;
import softuni.exam.models.entity.Mechanic;
import softuni.exam.models.entity.Task;
import softuni.exam.models.enums.CarType;
import softuni.exam.repository.TaskRepository;
import softuni.exam.service.CarService;
import softuni.exam.service.MechanicService;
import softuni.exam.service.PartService;
import softuni.exam.service.TaskService;
import softuni.exam.util.FileUtil;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

@Service
public class TaskServiceImpl implements TaskService {

    private static final String TASKS_FILE_PATH = "src/main/resources/files/xml/tasks.xml";

    private final MechanicService mechanicService;

    private final CarService carService;

    private final PartService partService;

    private final TaskRepository taskRepository;

    private final XmlParser xmlParser;

    private final ModelMapper modelMapper;

    private final ValidationUtil validationUtil;

    private final FileUtil fileUtil;

    public TaskServiceImpl(MechanicService mechanicService, CarService carService,
        PartService partService, TaskRepository taskRepository, XmlParser xmlParser,
        ModelMapper modelMapper, ValidationUtil validationUtil, FileUtil fileUtil) {
        this.mechanicService = mechanicService;
        this.carService = carService;
        this.partService = partService;
        this.taskRepository = taskRepository;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.fileUtil = fileUtil;
    }

    @Override
    public boolean areImported() {
        return this.taskRepository.count() > 0;
    }

    @Override
    public String readTasksFileContent() throws IOException {
        return this.fileUtil.readFile(TASKS_FILE_PATH);
    }

    @Override
    public String importTasks() throws IOException, JAXBException {
        StringBuilder stringBuilder = new StringBuilder();

        xmlParser
            .parseXml(TasksSeedRootDTO.class, TASKS_FILE_PATH)
            .getTasks()
            .stream()
            .filter(taskSeedDTO -> {
                boolean isValid = validationUtil.isValid(taskSeedDTO);

                Mechanic mechanic = this.mechanicService.findByFirstName(taskSeedDTO.getMechanic().getFirstName());

                if (mechanic == null) {
                    isValid = false;
                }

                stringBuilder.append(isValid ?
                        String.format("Successfully imported task %.2f",
                            taskSeedDTO.getPrice())
                        : "Invalid task")
                    .append(System.lineSeparator());

                return isValid;
            })
            .map(taskSeedDTO ->  {
                Task task = modelMapper.map(taskSeedDTO, Task.class);

                task.setCar(this.carService.getById(taskSeedDTO.getCar().getId()));
                task.setMechanic(this.mechanicService.findByFirstName(taskSeedDTO.getMechanic().getFirstName()));
                task.setPart(this.partService.getById(taskSeedDTO.getPart().getId()));

                return task;
            })
            .forEach(this.taskRepository::save);

        return stringBuilder.toString().trim();
    }

    @Override
    public String getCoupeCarTasksOrderByPrice() {
        StringBuilder stringBuilder = new StringBuilder();

        List<Task> tasks = this.taskRepository
            .findByCar_CarTypeOrderByPriceDesc(CarType.coupe);

        tasks.forEach(t -> stringBuilder.append(String.format(
            """
                Car %s %s with %dkm
                -Mechanic: %s %s - task №%d:
                --Engine: %.1f
                ---Price: %.2f$
                """,
            t.getCar().getCarMake(), t.getCar().getCarModel(),
            t.getCar().getKilometers(), t.getMechanic().getFirstName(),
            t.getMechanic().getLastName(), t.getId(),
            t.getCar().getEngine(), t.getPrice()
        )));

        return stringBuilder.toString().trim();
    }
}
