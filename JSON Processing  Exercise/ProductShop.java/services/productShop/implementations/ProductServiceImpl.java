package com.softuni.productsshop.services.productShop.implementations;

import com.softuni.productsshop.models.productsShop.dtos.ProductsInRangeDto;
import com.softuni.productsshop.models.productsShop.entities.Product;
import com.softuni.productsshop.repositories.productShop.ProductRepository;
import com.softuni.productsshop.services.productShop.interfaces.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Set;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repository;
    private final ModelMapper mapper;

    @Autowired
    public ProductServiceImpl(ProductRepository repository, ModelMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public void saveAll(Iterable<Product> collect) {
        this.repository.saveAllAndFlush(collect);
    }

    @Override
    public Iterable<ProductsInRangeDto> getNotBoughtProductsWithPriceInRange(BigDecimal min, BigDecimal max) {
        return Set.of(
                mapper.map(
                        this.repository.findByBuyerIsNullAndPriceBetweenOrderByPriceDesc(min, max),
                        ProductsInRangeDto[].class));
    }

    @Override
    public boolean hasNoRecords() {
        return this.repository.count() == 0;
    }
}
