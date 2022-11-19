package com.softuni.productsshop.services.productShop.interfaces;

import com.softuni.productsshop.models.productsShop.dtos.CategoriesByProductsDto;
import com.softuni.productsshop.models.productsShop.entities.Category;

import java.util.List;
import java.util.Set;

public interface CategoryService {
    Set<Category> getRandomCategories();

    void saveAll(Iterable<Category> cate);

    List<CategoriesByProductsDto> getCategoriesOrderByNumberOfProducts();
}
