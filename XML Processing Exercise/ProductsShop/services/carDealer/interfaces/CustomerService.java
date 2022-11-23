package com.softuni.productsshop.services.carDealer.interfaces;

import com.softuni.productsshop.models.carDealer.dtos.CustomerSalesDto;
import com.softuni.productsshop.models.carDealer.dtos.CustomerSalesRootDto;
import com.softuni.productsshop.models.carDealer.dtos.OrderedCustomersDto;
import com.softuni.productsshop.models.carDealer.dtos.OrderedCustomersRootDto;
import com.softuni.productsshop.models.carDealer.entities.Customer;

import java.util.List;

public interface CustomerService {
    void saveAll(Iterable<Customer> customers);
    Customer getRandomCustomer();
    OrderedCustomersRootDto getCustomersOrderedByBirthDateAndYoungerDrivers();

    CustomerSalesRootDto getAllCustomersWithTotalOfTheSales();

    boolean hasNoRecords();
}
