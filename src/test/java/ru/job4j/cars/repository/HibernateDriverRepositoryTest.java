package ru.job4j.cars.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.config.TestHibernateConfig;
import ru.job4j.cars.model.Driver;
import ru.job4j.cars.model.User;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

class HibernateDriverRepositoryTest {

    private final TestHibernateConfig testHibernateConfig = new TestHibernateConfig();
    private final CrudRepository crudRepository = testHibernateConfig.getCrudRepository();
    private final DriverRepository driverRepository = new HibernateDriverRepository(crudRepository);
    private final UserRepository userRepository = new HibernateUserRepository(crudRepository);

    @AfterEach
    void cleanTable() {
        driverRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void whenCreateThenSuccess() {
        User user = new User("Denis", "password");
        userRepository.create(user);
        Driver driver = new Driver("Denis", user);
        driverRepository.create(driver);
        Driver driverFromDB = driverRepository.findById(driver.getId()).orElseThrow();
        assertThat(driverFromDB.getName()).isEqualTo(driver.getName());
    }

    @Test
    void whenUpdateThenSuccess() {
        User user = new User("Denis", "password");
        userRepository.create(user);
        Driver driver1 = new Driver("Denis", user);
        Driver driver2 = new Driver("Denis Kalchenko", user);
        driverRepository.create(driver1);
        driver2.setId(driver1.getId());
        driverRepository.update(driver2);
        Driver driverFromDB = driverRepository.findById(driver1.getId()).orElseThrow();
        assertThat(driverFromDB.getName()).isEqualTo(driver2.getName());
    }

    @Test
    void whenDeleteThenSuccess() {
        User user = new User("Denis", "password");
        userRepository.create(user);
        Driver driver = new Driver("Denis", user);
        driverRepository.create(driver);
        driverRepository.delete(driver.getId());
        Optional<Driver> driverFromDB = driverRepository.findById(driver.getId());
        assertThat(driverFromDB).isEmpty();
    }

    @Test
    void whenFindAllThenSuccess() {
        User user1 = new User("Denis", "password");
        User user2 = new User("Alex", "password");
        userRepository.create(user1);
        userRepository.create(user2);
        Driver driver1 = new Driver("Denis", user1);
        Driver driver2 = new Driver("Alex", user2);
        driverRepository.create(driver1);
        driverRepository.create(driver2);
        List<Driver> driversFromDB = driverRepository.findAllOrderById();
        assertThat(driversFromDB.stream().map(Driver::getName).collect(Collectors.toList()))
                .contains(driver1.getName(), driver2.getName());
    }

    @Test
    void whenDeleteAllThenSuccess() {
        User user1 = new User("Denis", "password");
        User user2 = new User("Alex", "password");
        userRepository.create(user1);
        userRepository.create(user2);
        Driver driver1 = new Driver("Denis", user1);
        Driver driver2 = new Driver("Alex", user2);
        driverRepository.create(driver1);
        driverRepository.create(driver2);
        driverRepository.deleteAll();
        assertThat(driverRepository.findAllOrderById()).isEmpty();
    }
}