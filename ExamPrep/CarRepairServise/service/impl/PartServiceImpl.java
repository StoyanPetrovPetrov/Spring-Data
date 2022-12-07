package softuni.exam.service.impl;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.Arrays;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.PartSeedDTO;
import softuni.exam.models.entity.Part;
import softuni.exam.repository.PartRepository;
import softuni.exam.service.PartService;
import softuni.exam.util.FileUtil;
import softuni.exam.util.ValidationUtil;

@Service
public class PartServiceImpl implements PartService {

    private static final String PARTS_FILE_PATH = "src/main/resources/files/json/parts.json";

    private final PartRepository partRepository;

    private final Gson gson;

    private final ModelMapper modelMapper;

    private final ValidationUtil validationUtil;

    private final FileUtil fileUtil;

    public PartServiceImpl(PartRepository partRepository, Gson gson, ModelMapper modelMapper,
        ValidationUtil validationUtil, FileUtil fileUtil) {
        this.partRepository = partRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.fileUtil = fileUtil;
    }

    @Override
    public boolean areImported() {
        return this.partRepository.count() > 0;
    }

    @Override
    public String readPartsFileContent() throws IOException {
        return this.fileUtil.readFile(PARTS_FILE_PATH);
    }

    @Override
    public String importParts() throws IOException {
        StringBuilder builder = new StringBuilder();

        Arrays.stream(gson
                .fromJson(readPartsFileContent(), PartSeedDTO[].class))
            .filter(partSeedDTO -> {
                boolean isValid = validationUtil.isValid(partSeedDTO);

                Part part = this.partRepository.getPartByPartName(partSeedDTO.getPartName());

                if (part != null) {
                    isValid = false;
                }

                builder.append(isValid ?
                        String.format("Successfully imported part %s - %.2f",
                            partSeedDTO.getPartName(), partSeedDTO.getPrice())
                        : "Invalid part")
                    .append(System.lineSeparator());

                return isValid;
            })
            .map(partSeedDTO -> modelMapper.map(partSeedDTO, Part.class))
            .forEach(this.partRepository::save);

        return builder.toString().trim();
    }

    @Override
    public Part getById(Long id) {
        return this.partRepository.getById(id);
    }
}
