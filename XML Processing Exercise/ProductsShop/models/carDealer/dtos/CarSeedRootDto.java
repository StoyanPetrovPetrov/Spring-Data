package com.softuni.productsshop.models.carDealer.dtos;

import com.softuni.productsshop.services.carDealer.inplementations.CarSeedDto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashSet;
import java.util.Set;

@XmlRootElement(name="cars")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarSeedRootDto {
    @XmlElement(name = "car")
    private Set<CarSeedDto> cars;

    public CarSeedRootDto() {
        this.cars = new HashSet<>();
    }

    public Set<CarSeedDto> getCars() {
        return cars;
    }
}
