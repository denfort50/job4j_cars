package ru.job4j.cars.repository;

import ru.job4j.cars.model.Body;

import java.util.List;

/**
 * Интерфейс описывает методы взаимодействия с хранилищем кузовов
 *
 * @author Denis Kalchenko
 */
public interface BodyRepository {

    /**
     * Метод сохраняет тип кузова в базу данных
     *
     * @param body кузов автомобиля
     * @return возвращает объект кузова
     */
    Body create(Body body);

    /**
     * Метод находит все кузова
     *
     * @return возвращает список кузовов
     */
    List<Body> findAll();

    /**
     * Метод находит кузов по ID
     *
     * @param id идентификатор
     * @return возвращает объект кузова
     */
    Body findById(int id);

    /**
     * Метод удаляет все кузова
     */
    void deleteAll();
}
