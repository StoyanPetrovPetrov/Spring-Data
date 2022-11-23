package com.softuni.productsshop.services.carDealer.inplementations;

import com.softuni.productsshop.models.carDealer.entities.Part;
import com.softuni.productsshop.repositories.carDealer.PartRepository;
import com.softuni.productsshop.services.carDealer.interfaces.PartsService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class PartsServiceImpl implements PartsService {
    private final PartRepository repository;

    public PartsServiceImpl(PartRepository repository) {
        this.repository = repository;
    }

    @Override
    public void saveAll(Iterable<Part> parts) {
        this.repository.saveAllAndFlush(parts);
    }

    @Override
    public Set<Part> getRandomParts() {
        int count = ThreadLocalRandom.current().nextInt(3, 6);
        Set<Part> parts = new HashSet<>();
        for (int i = 0; i < count; i++) {
            parts.add(
                    this.repository
                            .findById(
                                    ThreadLocalRandom.current().nextLong(1L, this.repository.count() + 1))
                            .orElse(null)
            );
        }
        return parts;
    }

    @Override
    public boolean hasNoRecords() {
        return repository.count() == 0;
    }
}
