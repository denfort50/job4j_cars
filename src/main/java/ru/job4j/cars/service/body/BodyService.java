package ru.job4j.cars.service.body;

import ru.job4j.cars.model.Body;

import java.util.List;

/**
 * Интерфейс описывает методы сервиса, через который происходит взаимодействие с хранилищем кузовов
 */
public interface BodyService {

    /**
     * Метод находит все кузова
     * @return возвращает список кузовов
     */
    List<Body> findAll();

    /**
     * Метод находит кузов по ID
     * @param id идентификатор
     * @return возвращает объект кузова
     */
    Body findById(int id);
}
