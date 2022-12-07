package softuni.exam.service.impl;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.Arrays;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.MechanicSeedDTO;
import softuni.exam.models.entity.Mechanic;
import softuni.exam.repository.MechanicRepository;
import softuni.exam.service.MechanicService;
import softuni.exam.util.FileUtil;
import softuni.exam.util.ValidationUtil;

@Service
public class MechanicServiceImpl implements MechanicService {

    private static final String MECHANICS_FILE_PATH = "src/main/resources/files/json/mechanics.json";

    private final MechanicRepository mechanicRepository;

    private final Gson gson;

    private final ModelMapper modelMapper;

    private final ValidationUtil validationUtil;

    private final FileUtil fileUtil;

    public MechanicServiceImpl(MechanicRepository mechanicRepository, Gson gson,
        ModelMapper modelMapper, ValidationUtil validationUtil, FileUtil fileUtil) {
        this.mechanicRepository = mechanicRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.fileUtil = fileUtil;
    }

    @Override
    public boolean areImported() {
        return this.mechanicRepository.count() > 0;
    }

    @Override
    public String readMechanicsFromFile() throws IOException {
        return this.fileUtil.readFile(MECHANICS_FILE_PATH);
    }

    @Override
    public String importMechanics() throws IOException {
        StringBuilder builder = new StringBuilder();

        Arrays.stream(gson
                .fromJson(readMechanicsFromFile(), MechanicSeedDTO[].class))
            .filter(mechanicSeedDTO -> {
                boolean isValid = validationUtil.isValid(mechanicSeedDTO);

                Mechanic mechanic = this. mechanicRepository.findByEmail(mechanicSeedDTO.getEmail());

                if (mechanic != null) {
                    isValid = false;
                }

                builder.append(isValid ?
                        String.format("Successfully imported mechanic %s %s",
                            mechanicSeedDTO.getFirstName(), mechanicSeedDTO.getLastName())
                        : "Invalid mechanic")
                    .append(System.lineSeparator());

                return isValid;
            })
            .map(mechanicSeedDTO -> modelMapper.map(mechanicSeedDTO, Mechanic.class))
            .forEach(this.mechanicRepository::save);

        return builder.toString().trim();
    }

    @Override
    public Mechanic findByFirstName(String firstName) {
        return this.mechanicRepository.findByFirstName(firstName);
    }
}
