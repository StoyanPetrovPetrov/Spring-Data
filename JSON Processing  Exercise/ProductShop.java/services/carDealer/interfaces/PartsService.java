package com.softuni.productsshop.services.carDealer.interfaces;

import com.softuni.productsshop.models.carDealer.entities.Part;

import java.util.Set;

public interface PartsService {
    void saveAll(Iterable<Part> parts);
    Set<Part> getRandomParts();
}
