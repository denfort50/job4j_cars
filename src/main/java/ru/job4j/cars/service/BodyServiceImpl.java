package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Body;
import ru.job4j.cars.repository.BodyRepository;

import java.util.List;


@Service
@AllArgsConstructor
public class BodyServiceImpl implements BodyService {

    private final BodyRepository bodyRepository;

    @Override
    public List<Body> findAll() {
        return bodyRepository.findAll();
    }

    @Override
    public Body findById(int id) {
        return bodyRepository.findById(id);
    }
}
