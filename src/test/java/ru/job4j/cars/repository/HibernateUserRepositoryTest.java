package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.User;
import ru.job4j.cars.repository.classes.CrudRepositoryImpl;
import ru.job4j.cars.repository.classes.HibernateUserRepository;
import ru.job4j.cars.repository.interfaces.CrudRepository;
import ru.job4j.cars.repository.interfaces.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;
import static ru.job4j.cars.util.SessionFactoryLoader.getSessionFactory;

class HibernateUserRepositoryTest {

    private final SessionFactory sessionFactory = getSessionFactory();
    private final CrudRepository crudRepository = new CrudRepositoryImpl(sessionFactory);
    private final UserRepository userRepository = new HibernateUserRepository(crudRepository);

    @BeforeEach
    @AfterEach
    void cleanTable() {
        userRepository.deleteAll();
    }

    @Test
    void whenCreateThenSuccess() {
        User user = new User("Denis", "password");
        user = userRepository.create(user);
        User userFromDB = userRepository.findById(user.getId()).orElseThrow();
        assertThat(userFromDB.getLogin()).isEqualTo(user.getLogin());
    }

    @Test
    void whenUpdateThenSuccess() {
        User user = new User("Denis", "password");
        user = userRepository.create(user);
        User updUser = new User("Denis Kalchenko", "password");
        updUser.setId(user.getId());
        userRepository.update(updUser);
        User userFromDB = userRepository.findById(updUser.getId()).orElseThrow();
        assertThat(userFromDB.getLogin()).isEqualTo(updUser.getLogin());
    }

    @Test
    void whenDeleteThenSuccess() {
        User user = new User("Denis", "password");
        user = userRepository.create(user);
        userRepository.delete(user.getId());
        Optional<User> userFromDB = userRepository.findById(user.getId());
        assertThat(userFromDB).isEmpty();
    }

    @Test
    void whenFindAllThenSuccess() {
        User user1 = new User("Denis", "password");
        User user2 = new User("Alex", "password");
        userRepository.create(user1);
        userRepository.create(user2);
        List<User> usersFromDB = userRepository.findAllOrderById();
        assertThat(usersFromDB.stream().map(User::getLogin).collect(Collectors.toList()))
                .contains(user1.getLogin(), user2.getLogin());
    }

    @Test
    void whenFindByLikeLoginThenSuccess() {
        User user = new User("Denis", "password");
        user = userRepository.create(user);
        List<User> usersFromDB = userRepository.findByLikeLogin("Den");
        assertThat(usersFromDB.stream().map(User::getLogin).collect(Collectors.toList()))
                .contains(user.getLogin());
    }

    @Test
    void whenFindByLoginThenSuccess() {
        User user = new User("Denis", "password");
        user = userRepository.create(user);
        User userFromDB = userRepository.findByLogin(user.getLogin()).orElseThrow();
        assertThat(userFromDB.getId()).isEqualTo(user.getId());
    }

    @Test
    void whenDeleteAllThenSuccess() {
        User user1 = new User("Denis", "password");
        User user2 = new User("Alex", "password");
        userRepository.create(user1);
        userRepository.create(user2);
        userRepository.deleteAll();
        assertThat(userRepository.findAllOrderById()).isEmpty();
    }
}