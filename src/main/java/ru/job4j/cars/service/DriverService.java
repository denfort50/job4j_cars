package ru.job4j.cars.service;

import ru.job4j.cars.model.Driver;

import java.util.List;
import java.util.Optional;

public interface DriverService {

    Driver create(Driver driver);

    void update(Driver driver);

    void delete(int driverId);

    List<Driver> findAllOrderById();

    Optional<Driver> findById(int driverId);

    void deleteAll();
}
