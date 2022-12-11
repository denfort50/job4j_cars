package ru.job4j.cars.repository.classes;

import lombok.AllArgsConstructor;
import ru.job4j.cars.model.Driver;
import ru.job4j.cars.repository.interfaces.CrudRepository;
import ru.job4j.cars.repository.interfaces.DriverRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
public class HibernateDriverRepository implements DriverRepository {

    private final CrudRepository crudRepository;

    private static final String DELETE = "DELETE Driver d WHERE d.id = :fId";
    private static final String FIND_ALL_ORDER_BY_ID = "FROM Driver d ORDER BY d.id";
    private static final String FIND_BY_ID = "FROM Driver d WHERE d.id = :fId";
    private static final String DELETE_ALL = "DELETE Driver";

    @Override
    public Driver create(Driver driver) {
        crudRepository.run(session -> session.save(driver));
        return driver;
    }

    @Override
    public void update(Driver driver) {
        crudRepository.run(session -> session.merge(driver));
    }

    @Override
    public void delete(int driverId) {
        crudRepository.queryAndGetBoolean(DELETE, Map.of("fId", driverId));
    }

    @Override
    public List<Driver> findAllOrderById() {
        return crudRepository.queryAndGetList(FIND_ALL_ORDER_BY_ID, Driver.class);
    }

    @Override
    public Optional<Driver> findById(int driverId) {
        return crudRepository.queryAndGetOptional(FIND_BY_ID, Driver.class, Map.of("fId", driverId));
    }

    @Override
    public void deleteAll() {
        crudRepository.run(DELETE_ALL);
    }
}
