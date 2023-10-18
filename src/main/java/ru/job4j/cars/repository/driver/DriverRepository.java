package ru.job4j.cars.repository.driver;

import ru.job4j.cars.model.Driver;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс описывает методы взаимодействия с хранилищем водителей
 */
public interface DriverRepository {

    /**
     * Метод сохраняет водителя
     *
     * @param driver водитель
     * @return возвращает водителя
     */
    Driver create(Driver driver);

    /**
     * Метод обновляет водителя
     *
     * @param driver водитель
     */
    void update(Driver driver);

    /**
     * Метод удаляет водителя
     *
     * @param driver водитель
     */
    void delete(Driver driver);

    /**
     * Метод находит всех водителей
     *
     * @return возвращает список водителей
     */
    List<Driver> findAllOrderById();

    /**
     * Метод находит водителя по ID
     *
     * @param driverId идентификатор водителя
     * @return возвращает водителя
     */
    Optional<Driver> findById(int driverId);

    /**
     * Метод удаляет всех водителей
     */
    void deleteAll();
}
