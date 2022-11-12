package com.softuni.modelmapper.repositories;

import com.softuni.modelmapper.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address,Long> {
    Optional<Address> findByCountryAndCity(String country, String city);
}
