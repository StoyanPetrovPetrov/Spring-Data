package com.softuni.productsshop.repositories.carDealer;

import com.softuni.productsshop.models.carDealer.entities.Part;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartRepository extends JpaRepository<Part,Long> {
}
