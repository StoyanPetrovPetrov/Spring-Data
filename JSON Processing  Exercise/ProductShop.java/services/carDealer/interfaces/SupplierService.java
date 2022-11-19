package com.softuni.productsshop.services.carDealer.interfaces;

import com.softuni.productsshop.models.carDealer.dtos.LocalSupplierDto;
import com.softuni.productsshop.models.carDealer.entities.Supplier;

import java.util.List;

public interface SupplierService {
    void saveAll(Iterable<Supplier> suppliers);
    boolean hasNoRecords();
    Supplier getRandomSupplier();
    List<LocalSupplierDto> gatAllLocalSuppliers();
}
