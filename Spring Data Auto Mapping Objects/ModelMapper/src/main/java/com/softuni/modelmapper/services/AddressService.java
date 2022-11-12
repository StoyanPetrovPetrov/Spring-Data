package com.softuni.modelmapper.services;

import com.softuni.modelmapper.entities.Address;
import com.softuni.modelmapper.entities.dtos.AddressDTO;

public interface AddressService {
    Address create(AddressDTO data);
}
