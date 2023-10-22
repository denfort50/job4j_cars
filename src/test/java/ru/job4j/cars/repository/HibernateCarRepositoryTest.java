package ru.job4j.cars.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.configuration.TestDataSourceConfig;
import ru.job4j.cars.model.Body;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.Engine;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

class HibernateCarRepositoryTest {

    private final TestDataSourceConfig testDataSourceConfig = new TestDataSourceConfig();
    private final CrudRepository crudRepository = testDataSourceConfig.getCrudRepository();
    private final CarRepository carRepository = new HibernateCarRepository(crudRepository);
    private final EngineRepository engineRepository = new HibernateEngineRepository(crudRepository);
    private final BodyRepository bodyRepository = new HibernateBodyRepository(crudRepository);

    @AfterEach
    void cleanTable() {
        carRepository.deleteAll();
        engineRepository.deleteAll();
        bodyRepository.deleteAll();
    }

    @Test
    void whenCreateThenSuccess() {
        Engine engine = new Engine("TDI 3.0");
        engineRepository.create(engine);
        Body body = new Body("Sportback");
        bodyRepository.create(body);
        Car car = new Car("Audi", "S7", body, engine);
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
        Body body = new Body("Sportback");
        bodyRepository.create(body);
        Car car = new Car("Audi", "S7", body, engine1);
        carRepository.create(car);
        Car updCar = new Car("Audi", "S7", body, engine2);
        updCar.setId(car.getId());
        carRepository.update(updCar);
        Car carFromDB = carRepository.findById(car.getId()).orElseThrow();
        assertThat(carFromDB.getEngine().getIndex()).isEqualTo(updCar.getEngine().getIndex());
    }

    @Test
    void whenDeleteThenSuccess() {
        Engine engine = new Engine("TDI 3.0");
        engineRepository.create(engine);
        Body body = new Body("Sportback");
        bodyRepository.create(body);
        Car car = new Car("Audi", "S7", body, engine);
        carRepository.create(car);
        carRepository.delete(car);
        Optional<Car> carFromDB = carRepository.findById(car.getId());
        assertThat(carFromDB).isEmpty();
    }

    @Test
    void whenFindAllThenSuccess() {
        Engine engine1 = new Engine("TDI 3.0");
        Engine engine2 = new Engine("2JZ-GTE");
        engineRepository.create(engine1);
        engineRepository.create(engine2);
        Body body1 = new Body("Sportback");
        bodyRepository.create(body1);
        Body body2 = new Body("Coupe");
        bodyRepository.create(body2);
        Car car1 = new Car("Audi", "S7", body1, engine1);
        Car car2 = new Car("Nissan", "GT-R", body2, engine2);
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
        Body body1 = new Body("Sportback");
        bodyRepository.create(body1);
        Body body2 = new Body("Coupe");
        bodyRepository.create(body2);
        Car car1 = new Car("Audi", "S7", body1, engine1);
        Car car2 = new Car("Nissan", "GT-R", body2, engine2);
        carRepository.create(car1);
        carRepository.create(car2);
        carRepository.deleteAll();
        assertThat(carRepository.findAllOrderById()).isEmpty();
    }
}