package com.softuni.productsshop.repositories.productShop;

import com.softuni.productsshop.models.productsShop.dtos.CategoriesByProductsDto;
import com.softuni.productsshop.models.productsShop.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findById(long id);

    @Query("SELECT NEW com.softuni.productsshop.models.productsShop.dtos.CategoriesByProductsDto(c.name,  COUNT(p), AVG(p.price), SUM(p" +
            ".price)) " +
            "FROM Category c JOIN c.products p " +
            "GROUP BY c " +
            "ORDER BY SIZE(c.products) ")
    List<CategoriesByProductsDto> findAllOrderByProductCount();
}
