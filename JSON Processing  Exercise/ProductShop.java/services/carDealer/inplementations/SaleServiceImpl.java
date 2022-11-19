package com.softuni.productsshop.services.carDealer.inplementations;

import com.softuni.productsshop.models.carDealer.dtos.SalesDiscountsDto;
import com.softuni.productsshop.models.carDealer.entities.Sale;
import com.softuni.productsshop.repositories.carDealer.SaleRepository;
import com.softuni.productsshop.services.carDealer.interfaces.SaleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaleServiceImpl implements SaleService {
    private final SaleRepository repository;

    public SaleServiceImpl(SaleRepository repository) {
        this.repository = repository;
    }

    @Override
    public void saveAll(Iterable<Sale> sales) {
        this.repository.saveAllAndFlush(sales);
    }

    @Override
    public List<SalesDiscountsDto> getAllSalesWithDiscount() {
        return this.repository.findAllWithDiscounts();
    }
}
