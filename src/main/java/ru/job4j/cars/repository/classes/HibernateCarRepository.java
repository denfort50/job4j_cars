package ru.job4j.cars.repository.classes;

import lombok.AllArgsConstructor;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.repository.interfaces.CarRepository;
import ru.job4j.cars.repository.interfaces.CrudRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
public class HibernateCarRepository implements CarRepository {

    private final CrudRepository crudRepository;

    private static final String DELETE = "DELETE Car c WHERE c.id = :fId";
    private static final String FIND_ALL_ORDER_BY_ID = "FROM Car c ORDER BY c.id";
    private static final String FIND_BY_ID = "FROM Car c WHERE c.id = :fId";
    private static final String DELETE_ALL = "DELETE Car";

    @Override
    public Car create(Car car) {
        crudRepository.run(session -> session.save(car));
        return car;
    }

    @Override
    public void update(Car car) {
        crudRepository.run(session -> session.merge(car));
    }

    @Override
    public void delete(int carId) {
        crudRepository.queryAndGetBoolean(DELETE, Map.of("fId", carId));
    }

    @Override
    public List<Car> findAllOrderById() {
        return crudRepository.queryAndGetList(FIND_ALL_ORDER_BY_ID, Car.class);
    }

    @Override
    public Optional<Car> findById(int carId) {
        return crudRepository.queryAndGetOptional(FIND_BY_ID, Car.class, Map.of("fId", carId));
    }

    @Override
    public void deleteAll() {
        crudRepository.run(DELETE_ALL);
    }

}
