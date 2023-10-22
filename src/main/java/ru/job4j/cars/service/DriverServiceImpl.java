package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Driver;
import ru.job4j.cars.repository.DriverRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DriverServiceImpl implements DriverService {

    private final DriverRepository driverRepository;

    @Override
    public Driver create(Driver driver) {
        return driverRepository.create(driver);
    }

    @Override
    public void update(Driver driver) {
        driverRepository.update(driver);
    }

    @Override
    public void delete(Driver driver) {
        driverRepository.delete(driver);
    }

    @Override
    public List<Driver> findAllOrderById() {
        return driverRepository.findAllOrderById();
    }

    @Override
    public Optional<Driver> findById(int driverId) {
        return driverRepository.findById(driverId);
    }

    @Override
    public void deleteAll() {
        driverRepository.deleteAll();
    }
}
