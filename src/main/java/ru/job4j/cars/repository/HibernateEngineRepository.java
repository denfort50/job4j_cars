package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Engine;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@Repository
public class HibernateEngineRepository implements EngineRepository {

    private final CrudRepository crudRepository;

    private static final String FIND_ALL_ORDER_BY_ID = "FROM Engine e ORDER BY e.id";
    private static final String FIND_BY_ID = "FROM Engine e WHERE e.id = :fId";
    private static final String DELETE_ALL = "DELETE Engine";

    @Override
    public Engine create(Engine engine) {
        crudRepository.run(session -> session.save(engine));
        return engine;
    }

    @Override
    public void update(Engine engine) {
        crudRepository.run(session -> session.merge(engine));
    }

    @Override
    public void delete(Engine engine) {
        crudRepository.run(session -> session.delete(engine));
    }

    @Override
    public List<Engine> findAllOrderById() {
        return crudRepository.queryAndGetList(FIND_ALL_ORDER_BY_ID, Engine.class);
    }

    @Override
    public Optional<Engine> findById(int engineId) {
        return crudRepository.queryAndGetOptional(FIND_BY_ID, Engine.class, Map.of("fId", engineId));
    }

    @Override
    public void deleteAll() {
        crudRepository.run(DELETE_ALL);
    }
}
