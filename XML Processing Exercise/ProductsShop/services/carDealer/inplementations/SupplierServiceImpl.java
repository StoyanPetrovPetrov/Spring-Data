package com.softuni.productsshop.services.carDealer.inplementations;

import com.softuni.productsshop.models.carDealer.dtos.LocalSupplierDto;
import com.softuni.productsshop.models.carDealer.dtos.LocalSupplierRootDto;
import com.softuni.productsshop.models.carDealer.entities.Supplier;
import com.softuni.productsshop.services.carDealer.SupplierRepository;
import com.softuni.productsshop.services.carDealer.interfaces.SupplierService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class SupplierServiceImpl implements SupplierService {
    private final SupplierRepository repository;
    private final ModelMapper mapper;

    @Autowired
    public SupplierServiceImpl(SupplierRepository repository, ModelMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public void saveAll(Iterable<Supplier> suppliers) {
        this.repository.saveAllAndFlush(suppliers);
    }

    @Override
    public boolean hasNoRecords() {
        return this.repository.count() == 0;
    }

    @Override
    public Supplier getRandomSupplier() {
        long randomId = ThreadLocalRandom
                .current()
                .nextLong(1, this.repository.count() + 1);
        return this.repository.getById(randomId);
    }

    @Override
    public LocalSupplierRootDto gatAllLocalSuppliers() {
        return new LocalSupplierRootDto(
                this.repository.findAllByImporter(false)
                        .stream()
                        .map(s -> mapper.map(s, LocalSupplierDto.class))
                        .collect(Collectors.toList()));
    }
}
