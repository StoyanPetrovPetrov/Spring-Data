package com.softuni.productsshop.services.productShop.interfaces;

import com.softuni.productsshop.models.productsShop.dtos.ProductsInRangeDto;
import com.softuni.productsshop.models.productsShop.entities.Product;

import java.math.BigDecimal;

public interface ProductService {
    void saveAll(Iterable<Product> collect);
    Iterable<ProductsInRangeDto> getNotBoughtProductsWithPriceInRange(BigDecimal min, BigDecimal max);

    boolean hasNoRecords();
}
