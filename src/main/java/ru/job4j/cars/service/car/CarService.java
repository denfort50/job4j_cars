package ru.job4j.cars.service.car;

import ru.job4j.cars.model.Car;

import java.util.List;
import java.util.Optional;

/**
 * * Интерфейс описывает методы сервиса, через который происходит взаимодействие с хранилищем автомобилей
 */
public interface CarService {

    /**
     * Метод сохраняет автомобиль
     *
     * @param car автомобиль
     * @return возвращает автомобиль
     */
    Car create(Car car);

    /**
     * Метод обновляет автомобиль
     *
     * @param car автомобиль
     */
    void update(Car car);

    /**
     * Метод удаляет автомобиль
     *
     * @param car автомобиль
     */
    void delete(Car car);

    /**
     * Метод находит все автомобили
     *
     * @return возвращает список всех автомобилей
     */
    List<Car> findAllOrderById();

    /**
     * Метод находит автомобиль по ID
     *
     * @param carId идентификатор автомобиля
     * @return возвращает Optional автомобиля
     */
    Optional<Car> findById(int carId);

    /**
     * Метод удаляет все автомобили
     */
    void deleteAll();
}
