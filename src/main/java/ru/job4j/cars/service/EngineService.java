package ru.job4j.cars.service;

import ru.job4j.cars.model.Engine;

import java.util.List;
import java.util.Optional;

public interface EngineService {

    Engine create(Engine engine);

    void update(Engine engine);

    void delete(int engineId);

    List<Engine> findAllOrderById();

    Optional<Engine> findById(int engineId);

    void deleteAll();
}
