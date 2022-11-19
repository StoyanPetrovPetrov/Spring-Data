package com.softuni.productsshop.services.carDealer.interfaces;

import com.softuni.productsshop.models.carDealer.dtos.SalesDiscountsDto;
import com.softuni.productsshop.models.carDealer.entities.Sale;

import java.util.List;

public interface SaleService {
    void saveAll(Iterable<Sale> sales);

    List<SalesDiscountsDto> getAllSalesWithDiscount();
}
