package softuni.exam.instagraphlite.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.instagraphlite.models.Picture;
import softuni.exam.instagraphlite.models.User;
import softuni.exam.instagraphlite.models.dtos.UserImportDto;
import softuni.exam.instagraphlite.repository.PictureRepository;
import softuni.exam.instagraphlite.repository.UserRepository;
import softuni.exam.instagraphlite.service.UserService;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static softuni.exam.instagraphlite.util.Paths.USERS_JSON_PATH;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final PictureRepository pictureRepository;

    private final ModelMapper modelMapper;

    private final Gson gson;

    private final Validator validator;

    public UserServiceImpl(UserRepository userRepository, PictureRepository pictureRepository,
                           ModelMapper modelMapper, Gson gson, Validator validator) {
        this.userRepository = userRepository;
        this.pictureRepository = pictureRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validator = validator;
    }

    @Override
    public boolean areImported() {
        return this.userRepository.count()>0;
    }

    @Override
    public String readFromFileContent() throws IOException {
        return Files.readString(USERS_JSON_PATH);
    }

    @Override
    public String importUsers() throws IOException {
        String jsonUsers = readFromFileContent();

        UserImportDto[] userImportDtos =
                this.gson.fromJson(jsonUsers, UserImportDto[].class);

        List<String> results = new ArrayList<>();

        for (UserImportDto userImportDto : userImportDtos) {
            Set<ConstraintViolation<UserImportDto>> errors =
                    this.validator.validate(userImportDto);

            if(errors.isEmpty()) {
                Optional<Picture> optionalPicture =
                        this.pictureRepository
                                .findByPath(userImportDto.getProfilePicture());

                Optional<User> optionalUser =
                        this.userRepository.findByUsername(userImportDto.getUsername());

                if(optionalPicture.isPresent() && optionalUser.isEmpty()) {
                    User user = this.modelMapper.map(userImportDto, User.class);

                    user.setProfilePicture(optionalPicture.get());

                    this.userRepository.save(user);

                    results.add(String.format("Successfully imported User: %s",
                            user.getUsername()));

                } else {
                    results.add("Invalid User");
                }
            } else {
                results.add("Invalid User");
            }
        }
        return String.join(System.lineSeparator(), results);
    }

    @Override
    public String exportUsersWithTheirPosts() {
        List<User> usersWithPosts = this.userRepository.findAllUsersOrderedByCountOfPostsDescUserIdAsc();

        return usersWithPosts
                .stream()
                .map(User::toString)
                .collect(Collectors.joining("\n"));
    }
}
