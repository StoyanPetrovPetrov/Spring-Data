package com.softuni.productsshop.services.carDealer.inplementations;

import com.softuni.productsshop.models.carDealer.dtos.CarAndPartsDto;
import com.softuni.productsshop.models.carDealer.dtos.CarAndPartsRootDto;
import com.softuni.productsshop.models.carDealer.dtos.CarsByMakeDto;
import com.softuni.productsshop.models.carDealer.dtos.CarsByMakeRootDto;
import com.softuni.productsshop.models.carDealer.entities.Car;
import com.softuni.productsshop.repositories.carDealer.CarRepository;
import com.softuni.productsshop.services.carDealer.interfaces.CarService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements CarService {
    private final CarRepository repository;
    private final ModelMapper mapper;

    public CarServiceImpl(CarRepository repository, ModelMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public void saveAll(Iterable<Car> cars) {
        this.repository.saveAllAndFlush(cars);
    }

    @Override
    public Car getRandomCar() {
        return this.repository.getById(
                ThreadLocalRandom.current().nextLong(1L, this.repository.count() + 1));
    }

    @Override
    public CarsByMakeRootDto getCarsByMake(String make) {
        return new CarsByMakeRootDto(
                this.repository.findAllByMakeOrderByModelAscTravelledDistanceDesc(make)
                        .stream()
                        .map(c -> mapper.map(c, CarsByMakeDto.class))
                        .collect(Collectors.toList()));

    }

    @Override
    public CarAndPartsRootDto getAllCars() {
        return new CarAndPartsRootDto(
                this.repository.findAll()
                        .stream()
                        .map(c -> mapper.map(c, CarAndPartsDto.class))
                        .collect(Collectors.toList())
        );
    }

    @Override
    public boolean hasNoRecords() {
        return this.repository.count() == 0;
    }
}
