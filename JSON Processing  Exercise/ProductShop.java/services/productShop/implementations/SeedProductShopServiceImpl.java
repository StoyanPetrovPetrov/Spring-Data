package com.softuni.productsshop.services.productShop.implementations;

import com.softuni.productsshop.models.productsShop.dtos.CategoryJsonReadDto;
import com.softuni.productsshop.models.productsShop.dtos.ProductJsonReadDto;
import com.softuni.productsshop.models.productsShop.dtos.UserJsonReadDto;
import com.softuni.productsshop.models.productsShop.entities.Category;
import com.softuni.productsshop.models.productsShop.entities.Product;
import com.softuni.productsshop.models.productsShop.entities.User;
import com.softuni.productsshop.services.common.FileService;
import com.softuni.productsshop.services.productShop.interfaces.CategoryService;
import com.softuni.productsshop.services.productShop.interfaces.ProductService;
import com.softuni.productsshop.services.productShop.interfaces.SeedProductShopService;
import com.softuni.productsshop.services.productShop.interfaces.UserService;
import com.softuni.productsshop.utils.ValidationsUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.stream.Collectors;

import static com.softuni.productsshop.utils.CommonConstants.*;

@Service
public class SeedProductShopServiceImpl implements SeedProductShopService {
    private final FileService fileService;
    private final ValidationsUtil validator;
    private final ModelMapper modelMapper;
    private final ProductService productService;
    private final UserService userService;
    private final CategoryService categoryService;

    public SeedProductShopServiceImpl(FileService fileService,
                                      ValidationsUtil validator,
                                      ModelMapper modelMapper,
                                      ProductService productService,
                                      UserService userService,
                                      CategoryService categoryService) {
        this.fileService = fileService;
        this.validator = validator;
        this.modelMapper = modelMapper;
        this.productService = productService;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @Override
    public void seed() throws IOException {
        seedCategories();
        seedUsers();
        seedProducts();
    }

    private void seedProducts() throws IOException {
        this.productService.saveAll(
                Arrays.stream(fileService
                                .readFile(RESOURCE_FILE_PATH + PRODUCTS_FILE, ProductJsonReadDto[].class))
                        .filter(validator::isValid)
                        .map(prod -> modelMapper.map(prod, Product.class))
                        .map(prod -> {
                            prod.setSeller(this.userService.getRandomUser());
                            if (prod.getPrice().compareTo(BigDecimal.valueOf(800L)) < 0) {
                                prod.setBuyer(this.userService.getRandomUser());
                            }
                            prod.setCategories(this.categoryService.getRandomCategories());
                            return prod;
                        })
                        .collect(Collectors.toList())
        );
    }

    private void seedUsers() throws IOException {
        this.userService.saveAll(
                Arrays.stream(fileService
                                .readFile(RESOURCE_FILE_PATH + USERS_FILE, UserJsonReadDto[].class))
                        .filter(validator::isValid)
                        .map(usr -> modelMapper.map(usr, User.class))
                        .collect(Collectors.toList())
        );
    }


    private void seedCategories() throws IOException {
        this.categoryService.saveAll(
                Arrays.stream(fileService
                                .readFile(RESOURCE_FILE_PATH + CATEGORIES_FILE, CategoryJsonReadDto[].class))
                        .filter(validator::isValid)
                        .map(cat -> modelMapper.map(cat, Category.class))
                        .collect(Collectors.toList())
        );
    }
}
