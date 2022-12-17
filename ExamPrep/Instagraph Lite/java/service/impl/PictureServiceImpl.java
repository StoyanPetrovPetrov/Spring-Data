package softuni.exam.instagraphlite.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.instagraphlite.models.Picture;
import softuni.exam.instagraphlite.models.dtos.PictureImportDto;
import softuni.exam.instagraphlite.repository.PictureRepository;
import softuni.exam.instagraphlite.service.PictureService;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static softuni.exam.instagraphlite.util.Paths.PICTURES_JSON_PATH;

@Service
public class PictureServiceImpl implements PictureService {

    private final PictureRepository pictureRepository;

    private final ModelMapper modelMapper;

    private final Gson gson;

    private final Validator validator;

    public PictureServiceImpl(PictureRepository pictureRepository, ModelMapper modelMapper,
                              Gson gson, Validator validator) {
        this.pictureRepository = pictureRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validator = validator;
    }

    @Override
    public boolean areImported() {
        return this.pictureRepository.count()>0;
    }

    @Override
    public String readFromFileContent() throws IOException {
        return Files.readString(PICTURES_JSON_PATH);
    }

    @Override
    public String importPictures() throws IOException {
        String jsonPictures = readFromFileContent();

        PictureImportDto[] pictureImportDtos =
                this.gson.fromJson(jsonPictures, PictureImportDto[].class);

        List<String> results = new ArrayList<>();

        for (PictureImportDto pictureImportDto : pictureImportDtos) {
            Set<ConstraintViolation<PictureImportDto>> errors =
                    this.validator.validate(pictureImportDto);

            if(errors.isEmpty()) {
                Picture picture = this.modelMapper.map(pictureImportDto, Picture.class);

                Optional<Picture> optionalPicture =
                        this.pictureRepository.findByPath(picture.getPath());

                if(optionalPicture.isEmpty()) {
                    this.pictureRepository.save(picture);

                    results.add(String.format("Successfully imported Picture, with size %.2f",
                            picture.getSize()));

                } else {
                    results.add("Invalid Picture");
                }
            } else {
                results.add("Invalid Picture");
            }
        }
        return String.join(System.lineSeparator(), results);
    }

    @Override
    public String exportPictures() {
        Double pictureSize = 30000d;

        List<Picture> pictures = this.pictureRepository.findAllBySizeAfterOrderBySizeAsc(pictureSize);

        return pictures
                .stream()
                .map(Picture::toString)
                .collect(Collectors.joining("\n"));

    }
}
