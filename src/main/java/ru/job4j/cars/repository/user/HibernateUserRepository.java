package ru.job4j.cars.repository.user;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.User;
import ru.job4j.cars.repository.CrudRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@Repository
public class HibernateUserRepository implements UserRepository {

    private final CrudRepository crudRepository;

    private static final String FIND_ALL_ORDER_BY_ID = "FROM User u ORDER BY u.id";
    private static final String FIND_BY_ID = "FROM User u WHERE u.id = :fId";
    private static final String FIND_BY_LIKE_LOGIN = "FROM User u WHERE u.login LIKE :fKey";
    private static final String FIND_BY_LOGIN = "FROM User u WHERE u.login = :fLogin";
    private static final String FIND_BY_LOGIN_AND_PASSWORD = "FROM User u WHERE u.login = :fLogin and u.password = :fPassword";
    private static final String DELETE_ALL = "DELETE User";

    /**
     * Сохранить в базе
     *
     * @param user пользователь
     * @return пользователь с id
     */
    public Optional<User> create(User user) {
        crudRepository.run(session -> session.save(user));
        return Optional.of(user);
    }

    /**
     * Обновить в базе пользователя
     *
     * @param user пользователь
     */
    public void update(User user) {
        crudRepository.run(session -> session.merge(user));
    }

    /**
     * Удалить пользователя по id
     *
     * @param user пользователь
     */
    public void delete(User user) {
        crudRepository.run(session -> session.delete(user));
    }

    /**
     * Список пользователь отсортированных по id
     *
     * @return список пользователей
     */
    public List<User> findAllOrderById() {
        return crudRepository.queryAndGetList(FIND_ALL_ORDER_BY_ID, User.class);
    }

    /**
     * Найти пользователя по ID
     *
     * @return пользователь
     */
    public Optional<User> findById(int userId) {
        return crudRepository.queryAndGetOptional(FIND_BY_ID, User.class, Map.of("fId", userId));
    }

    /**
     * Список пользователей по login LIKE %key%
     *
     * @param key key
     * @return список пользователей
     */
    public List<User> findByLikeLogin(String key) {
        return crudRepository.queryAndGetList(FIND_BY_LIKE_LOGIN, User.class, Map.of("fKey", "%" + key + "%"));
    }

    /**
     * Найти пользователя по login
     *
     * @param login login
     * @return Optional of user
     */
    public Optional<User> findByLogin(String login) {
        return crudRepository.queryAndGetOptional(FIND_BY_LOGIN, User.class, Map.of("fLogin", login));
    }

    /**
     * Найти пользователя по логину и паролю.
     *
     * @param login    логин
     * @param password пароль
     * @return Optional of user.
     */
    public Optional<User> findByLoginAndPassword(String login, String password) {
        return crudRepository.queryAndGetOptional(FIND_BY_LOGIN_AND_PASSWORD, User.class, Map.of("fLogin", login, "fPassword", password));
    }

    /**
     * Удалить всех пользователей
     */
    @Override
    public void deleteAll() {
        crudRepository.run(DELETE_ALL);
    }
}
