package com.softuni.productsshop.services.productShop.interfaces;

import com.softuni.productsshop.models.productsShop.dtos.UserSoldDto;
import com.softuni.productsshop.models.productsShop.dtos.UsersAndProductsDto;
import com.softuni.productsshop.models.productsShop.entities.User;

import java.util.List;

public interface UserService {
    User getRandomUser();

    void saveAll(Iterable<User> collect);

    List<UserSoldDto> getAllUsersWithMoreThanOneSoldProducts();

    UsersAndProductsDto getAllUsersWithMoreThanOneSoldProductsOrderByProductSoldDescThenByLastName();
}
