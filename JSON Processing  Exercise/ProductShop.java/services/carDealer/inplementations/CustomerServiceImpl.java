package com.softuni.productsshop.services.carDealer.inplementations;

import com.softuni.productsshop.models.carDealer.dtos.CustomerSalesDto;
import com.softuni.productsshop.models.carDealer.dtos.OrderedCustomersDto;
import com.softuni.productsshop.models.carDealer.entities.Customer;
import com.softuni.productsshop.repositories.carDealer.CustomerRepository;
import com.softuni.productsshop.services.carDealer.interfaces.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository repository;
    private final ModelMapper mapper;

    @Autowired
    public CustomerServiceImpl(CustomerRepository repository, ModelMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public void saveAll(Iterable<Customer> customers) {
        this.repository.saveAllAndFlush(customers);
    }

    @Override
    public Customer getRandomCustomer() {
        return this.repository.getById(
                ThreadLocalRandom.current().nextLong(1L, this.repository.count() + 1));
    }

    @Override
    public List<OrderedCustomersDto> getCustomersOrderedByBirthDateAndYoungerDrivers() {
        return this.repository.findAllByOrderByBirthDateAscYoungerDriverAsc()
                .stream()
                .map(c->mapper.map(c, OrderedCustomersDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<CustomerSalesDto> getAllCustomersWithTotalOfTheSales() {
        return this.repository.findAllCustomersWithThereSales();
    }
}
