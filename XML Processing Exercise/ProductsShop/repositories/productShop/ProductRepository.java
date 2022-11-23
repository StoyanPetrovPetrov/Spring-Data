package com.softuni.productsshop.repositories.productShop;

import com.softuni.productsshop.models.productsShop.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Set;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Set<Product> findByBuyerIsNullAndPriceBetweenOrderByPriceAsc(BigDecimal min, BigDecimal max);
}
