package com.softuni.productsshop.services.productShop.implementations;

import com.softuni.productsshop.models.productsShop.dtos.UserAndSoldProductsDto;
import com.softuni.productsshop.models.productsShop.dtos.UserSoldDto;
import com.softuni.productsshop.models.productsShop.dtos.UsersAndProductsDto;
import com.softuni.productsshop.models.productsShop.entities.User;
import com.softuni.productsshop.repositories.productShop.UserRepository;
import com.softuni.productsshop.services.productShop.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final ModelMapper mapper;

    public UserServiceImpl(UserRepository repository, ModelMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }


    @Override
    public User getRandomUser() {
        long randomId = ThreadLocalRandom
                .current()
                .nextLong(1, this.repository.count() + 1);

        return this.repository.findById(randomId).orElse(null);
    }

    @Override
    public void saveAll(Iterable<User> users) {
        this.repository.saveAllAndFlush(users);
    }

    @Override
    public List<UserSoldDto> getAllUsersWithMoreThanOneSoldProducts() {
        return repository.findAllUsersWithMoreThanOneSoldProductOrderByLastNameThenFirstName()
                .stream()
                .map(user -> mapper.map(user, UserSoldDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public UsersAndProductsDto getAllUsersWithMoreThanOneSoldProductsOrderByProductSoldDescThenByLastName() {
        List<UserAndSoldProductsDto> users =
                repository.findAllUsersWithMoreThanOneSoldProductOrderByLastNameThenFirstName()
                        .stream()
                        .map(u->this.mapper.map(u, UserAndSoldProductsDto.class))
                        .collect(Collectors.toList());

        return new UsersAndProductsDto(users);
    }
}
