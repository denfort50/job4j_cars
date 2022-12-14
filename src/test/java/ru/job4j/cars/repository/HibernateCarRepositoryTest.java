package ru.job4j.cars.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.config.TestHibernateConfig;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.Engine;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

class HibernateCarRepositoryTest {

    private final TestHibernateConfig testHibernateConfig = new TestHibernateConfig();
    private final CrudRepository crudRepository = testHibernateConfig.getCrudRepository();
    private final CarRepository carRepository = new HibernateCarRepository(crudRepository);
    private final EngineRepository engineRepository = new HibernateEngineRepository(crudRepository);

    @AfterEach
    void cleanTable() {
        carRepository.deleteAll();
        engineRepository.deleteAll();
    }

    @Test
    void whenCreateThenSuccess() {
        Engine engine = new Engine("TDI 3.0");
        engineRepository.create(engine);
        Car car = new Car("Audi", "S7", "Sportback", engine);
        carRepository.create(car);
        Car carFromDB = carRepository.findById(car.getId()).orElseThrow();
        assertThat(carFromDB.getBrand()).isEqualTo(car.getBrand());
    }

    @Test
    void whenUpdateThenSuccess() {
        Engine engine1 = new Engine("TDI 3.0");
        Engine engine2 = new Engine("TFSI 5.0");
        engineRepository.create(engine1);
        engineRepository.create(engine2);
        Car car = new Car("Audi", "S7", "Sportback", engine1);
        carRepository.create(car);
        Car updCar = new Car("Audi", "S7", "Sportback", engine2);
        updCar.setId(car.getId());
        carRepository.update(updCar);
        Car carFromDB = carRepository.findById(car.getId()).orElseThrow();
        assertThat(carFromDB.getEngine().getName()).isEqualTo(updCar.getEngine().getName());
    }

    @Test
    void whenDeleteThenSuccess() {
        Engine engine = new Engine("TDI 3.0");
        engineRepository.create(engine);
        Car car = new Car("Audi", "S7", "Sportback", engine);
        carRepository.create(car);
        carRepository.delete(car.getId());
        Optional<Car> carFromDB = carRepository.findById(car.getId());
        assertThat(carFromDB).isEmpty();
    }

    @Test
    void whenFindAllThenSuccess() {
        Engine engine1 = new Engine("TDI 3.0");
        Engine engine2 = new Engine("2JZ-GTE");
        engineRepository.create(engine1);
        engineRepository.create(engine2);
        Car car1 = new Car("Audi", "S7", "Sportback", engine1);
        Car car2 = new Car("Nissan", "GT-R", "Coupe", engine2);
        carRepository.create(car1);
        carRepository.create(car2);
        List<Car> carsFromDB = carRepository.findAllOrderById();
        assertThat(carsFromDB.stream().map(Car::getBrand).collect(Collectors.toList()))
                .contains(car1.getBrand(), car2.getBrand());
    }

    @Test
    void whenDeleteAllThenSuccess() {
        Engine engine1 = new Engine("TDI 3.0");
        Engine engine2 = new Engine("2JZ-GTE");
        engineRepository.create(engine1);
        engineRepository.create(engine2);
        Car car1 = new Car("Audi", "S7", "Sportback", engine1);
        Car car2 = new Car("Nissan", "GT-R", "Coupe", engine2);
        carRepository.create(car1);
        carRepository.create(car2);
        carRepository.deleteAll();
        assertThat(carRepository.findAllOrderById()).isEmpty();
    }
}