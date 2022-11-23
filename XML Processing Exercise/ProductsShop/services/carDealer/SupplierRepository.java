package com.softuni.productsshop.services.carDealer;

import com.softuni.productsshop.models.carDealer.entities.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    List<Supplier> findAllByImporter(Boolean importer);
}
