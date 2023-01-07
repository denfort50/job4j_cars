package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Body;

import java.util.List;
import java.util.Map;

@Repository
@AllArgsConstructor
public class HibernateBodyRepository implements BodyRepository {

    private static final String SELECT_ALL_BODIES = "FROM Body";
    private static final String SELECT_BODY_BY_ID = "FROM Body b WHERE b.id = :fId";

    private final CrudRepository crudRepository;

    @Override
    public List<Body> findAll() {
        return crudRepository.queryAndGetList(SELECT_ALL_BODIES, Body.class);
    }

    @Override
    public Body findById(int id) {
        return (Body) crudRepository.queryAndGetObject(SELECT_BODY_BY_ID, Body.class, Map.of("fId", id));
    }
}
