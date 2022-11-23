package com.softuni.productsshop.services.carDealer.interfaces;

import com.softuni.productsshop.models.carDealer.dtos.CarAndPartsDto;
import com.softuni.productsshop.models.carDealer.dtos.CarAndPartsRootDto;
import com.softuni.productsshop.models.carDealer.dtos.CarsByMakeDto;
import com.softuni.productsshop.models.carDealer.dtos.CarsByMakeRootDto;
import com.softuni.productsshop.models.carDealer.entities.Car;

import java.util.List;

public interface CarService {
    void saveAll(Iterable<Car> cars);

    Car getRandomCar();

    CarsByMakeRootDto getCarsByMake(String make);

    CarAndPartsRootDto getAllCars();

    boolean hasNoRecords();
}
