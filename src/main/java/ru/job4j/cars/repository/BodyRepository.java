package ru.job4j.cars.repository;

import ru.job4j.cars.model.Body;

import java.util.List;

public interface BodyRepository {

    List<Body> findAll();

    Body findById(int id);
}
