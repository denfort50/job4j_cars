package ru.job4j.cars.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.configuration.TestDataSourceConfig;
import ru.job4j.cars.model.User;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

class HibernateUserRepositoryTest {

    private final TestDataSourceConfig testDataSourceConfig = new TestDataSourceConfig();
    private final CrudRepository crudRepository = testDataSourceConfig.getCrudRepository();
    private final UserRepository userRepository = new HibernateUserRepository(crudRepository);

    @BeforeEach
    @AfterEach
    void cleanTable() {
        userRepository.deleteAll();
    }

    @Test
    void whenCreateThenSuccess() {
        User user = new User("Denis", "Denis", "password");
        user = userRepository.create(user).orElseThrow();
        User userFromDB = userRepository.findById(user.getId()).orElseThrow();
        assertThat(userFromDB.getLogin()).isEqualTo(user.getLogin());
    }

    @Test
    void whenUpdateThenSuccess() {
        User user = new User("Denis", "Denis", "password");
        user = userRepository.create(user).orElseThrow();
        User updUser = new User("Denis Kalchenko", "Denis", "password");
        updUser.setId(user.getId());
        userRepository.update(updUser);
        User userFromDB = userRepository.findById(updUser.getId()).orElseThrow();
        assertThat(userFromDB.getLogin()).isEqualTo(updUser.getLogin());
    }

    @Test
    void whenDeleteThenSuccess() {
        User user = new User("Denis", "Denis", "password");
        user = userRepository.create(user).orElseThrow();
        userRepository.delete(user);
        Optional<User> userFromDB = userRepository.findById(user.getId());
        assertThat(userFromDB).isEmpty();
    }

    @Test
    void whenFindAllThenSuccess() {
        User user1 = new User("Denis", "Denis", "password");
        User user2 = new User("Alex", "Alex", "password");
        userRepository.create(user1);
        userRepository.create(user2);
        List<User> usersFromDB = userRepository.findAllOrderById();
        assertThat(usersFromDB.stream().map(User::getLogin).collect(Collectors.toList()))
                .contains(user1.getLogin(), user2.getLogin());
    }

    @Test
    void whenFindByLikeLoginThenSuccess() {
        User user = new User("Denis", "Denis", "password");
        user = userRepository.create(user).orElseThrow();
        List<User> usersFromDB = userRepository.findByLikeLogin("Den");
        assertThat(usersFromDB.stream().map(User::getLogin).collect(Collectors.toList()))
                .contains(user.getLogin());
    }

    @Test
    void whenFindByLoginThenSuccess() {
        User user = new User("Denis", "Denis", "password");
        user = userRepository.create(user).orElseThrow();
        User userFromDB = userRepository.findByLogin(user.getLogin()).orElseThrow();
        assertThat(userFromDB.getId()).isEqualTo(user.getId());
    }

    @Test
    void whenDeleteAllThenSuccess() {
        User user1 = new User("Denis", "Denis", "password");
        User user2 = new User("Denis", "Alex", "password");
        userRepository.create(user1);
        userRepository.create(user2);
        userRepository.deleteAll();
        assertThat(userRepository.findAllOrderById()).isEmpty();
    }
}