package softuni.exam.instagraphlite.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.instagraphlite.models.Picture;
import softuni.exam.instagraphlite.models.Post;
import softuni.exam.instagraphlite.models.User;
import softuni.exam.instagraphlite.models.dtos.PostImportDto;
import softuni.exam.instagraphlite.models.dtos.PostImportWrapperDto;
import softuni.exam.instagraphlite.repository.PictureRepository;
import softuni.exam.instagraphlite.repository.PostRepository;
import softuni.exam.instagraphlite.repository.UserRepository;
import softuni.exam.instagraphlite.service.PostService;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static softuni.exam.instagraphlite.util.Paths.POSTS_XML_PATH;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;

    private final UserRepository userRepository;

    private final PictureRepository pictureRepository;

    private final ModelMapper modelMapper;

    private final Validator validator;

    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository,
                           PictureRepository pictureRepository, ModelMapper modelMapper, Validator validator) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.pictureRepository = pictureRepository;
        this.modelMapper = modelMapper;
        this.validator = validator;
    }

    @Override
    public boolean areImported() {
        return this.postRepository.count()>0;
    }

    @Override
    public String readFromFileContent() throws IOException {
        return Files.readString(POSTS_XML_PATH);
    }

    @Override
    public String importPosts() throws IOException, JAXBException {
        JAXBContext context = JAXBContext.newInstance(PostImportWrapperDto.class);

        Unmarshaller unmarshaller = context.createUnmarshaller();

        PostImportWrapperDto postDtos =
                (PostImportWrapperDto) unmarshaller.unmarshal(POSTS_XML_PATH.toFile());

        List<PostImportDto> posts = postDtos.getPosts();

        List<String> results = new ArrayList<>();

        for (PostImportDto postImportDto : posts) {
            Set<ConstraintViolation<PostImportDto>> errors =
                    this.validator.validate(postImportDto);

            if(errors.isEmpty()) {
                Optional<Picture> optionalPicture =
                        this.pictureRepository.findByPath(postImportDto.getPicture().getPath());

                Optional<User> optionalUser =
                        this.userRepository.findByUsername(postImportDto.getUser().getUsername());

                if(optionalPicture.isPresent() && optionalUser.isPresent()) {
                    Post post = this.modelMapper.map(postImportDto, Post.class);

                    post.setUser(optionalUser.get());
                    post.setPicture(optionalPicture.get());

                    this.postRepository.save(post);

                    results.add(String.format("Successfully imported Post, made by %s",
                            optionalUser.get().getUsername()));

                } else {
                    results.add("Invalid Post");
                }
            } else {
                results.add("Invalid Post");
            }
        }
        return String.join(System.lineSeparator(), results);
    }
    }

