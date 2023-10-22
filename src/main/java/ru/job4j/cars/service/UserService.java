package ru.job4j.cars.service;

import ru.job4j.cars.model.User;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс описывает методы сервиса, через который происходит взаимодействие с хранилищем пользователей
 *
 * @author Denis Kalchenko
 */
public interface UserService {

    /**
     * Метод сохраняет пользователя
     *
     * @param user пользователь
     * @return возвращает пользователя
     */
    Optional<User> create(User user);

    /**
     * Метод обновляет пользователя
     *
     * @param user пользователь
     */
    void update(User user);

    /**
     * Метод удаляет пользователя
     *
     * @param user пользователь
     */
    void delete(User user);

    /**
     * Метод находит всех пользователей
     *
     * @return возвращает пользователя
     */
    List<User> findAllOrderById();

    /**
     * Метод находит пользователя по ID
     *
     * @param userId идентификатор пользователя
     * @return возвращает Optional пользователя
     */
    Optional<User> findById(int userId);

    /**
     * Метод находит пользователей по части логина
     *
     * @param key часть логина
     * @return возвращает список пользователей
     */
    List<User> findByLikeLogin(String key);

    /**
     * Метод находит пользователя по логину
     *
     * @param login логин
     * @return возвращает Optional пользователя
     */
    Optional<User> findByLogin(String login);

    /**
     * Метод находит пользователя по логину и паролю
     *
     * @param login    логин
     * @param password пароль
     * @return возвращает Optional пользователя
     */
    Optional<User> findByLoginAndPassword(String login, String password);

    /**
     * Метод удаляет всех пользователей
     */
    void deleteAll();
}
