package ru.job4j.cars.service;

import ru.job4j.cars.model.Body;

import java.util.List;

public interface BodyService {

    List<Body> findAll();

    Body findById(int id);
}
