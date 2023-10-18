package ru.job4j.cars.repository.engine;

import ru.job4j.cars.model.Engine;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс описывает методы взаимодействия с хранилищем двигателей
 */
public interface EngineRepository {

    /**
     * Метод сохраняет двигатель
     *
     * @param engine двигатель
     * @return возвращает двигатель
     */
    Engine create(Engine engine);

    /**
     * Метод обновляет двигатель
     *
     * @param engine двигатель
     */
    void update(Engine engine);

    /**
     * Метод удаляет двигатель
     *
     * @param engine двигатель
     */
    void delete(Engine engine);

    /**
     * Метод находит все двигатели
     *
     * @return возвращает список двигателей
     */
    List<Engine> findAllOrderById();

    /**
     * Метод находит двигатель по ID
     *
     * @param engineId идентификатор двигателя
     * @return возвращает Optional двигателя
     */
    Optional<Engine> findById(int engineId);

    /**
     * Метод удаляет все двигатели
     */
    void deleteAll();
}
