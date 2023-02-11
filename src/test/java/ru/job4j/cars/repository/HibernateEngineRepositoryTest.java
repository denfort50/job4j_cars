package ru.job4j.cars.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.configuration.TestDataSourceConfig;
import ru.job4j.cars.model.Engine;
import ru.job4j.cars.repository.engine.EngineRepository;
import ru.job4j.cars.repository.engine.HibernateEngineRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

class HibernateEngineRepositoryTest {

    private final TestDataSourceConfig testDataSourceConfig = new TestDataSourceConfig();
    private final CrudRepository crudRepository = testDataSourceConfig.getCrudRepository();
    private final EngineRepository engineRepository = new HibernateEngineRepository(crudRepository);

    @AfterEach
    void cleanTable() {
        engineRepository.deleteAll();
    }

    @Test
    void whenCreateThenSuccess() {
        Engine engine = new Engine("TDI 3.0");
        engineRepository.create(engine);
        Engine engineFromDB = engineRepository.findById(engine.getId()).orElseThrow();
        assertThat(engineFromDB.getIndex()).isEqualTo(engine.getIndex());
    }

    @Test
    void whenUpdateThenSuccess() {
        Engine engine1 = new Engine("TDI 3.0");
        engineRepository.create(engine1);
        Engine engine2 = new Engine("TFSI 5.0");
        engine2.setId(engine1.getId());
        engineRepository.update(engine2);
        Engine engineFromDB = engineRepository.findById(engine2.getId()).orElseThrow();
        assertThat(engineFromDB.getIndex()).isEqualTo(engine2.getIndex());
    }

    @Test
    void whenDeleteThenSuccess() {
        Engine engine = new Engine("TDI 3.0");
        engineRepository.create(engine);
        engineRepository.delete(engine);
        Optional<Engine> engineFromDB = engineRepository.findById(engine.getId());
        assertThat(engineFromDB).isEmpty();
    }

    @Test
    void whenFindAllThenSuccess() {
        Engine engine1 = new Engine("TDI 3.0");
        Engine engine2 = new Engine("TFSI 5.0");
        engineRepository.create(engine1);
        engineRepository.create(engine2);
        List<Engine> enginesFromDB = engineRepository.findAllOrderById();
        assertThat(enginesFromDB.stream().map(Engine::getIndex).collect(Collectors.toList()))
                .contains(engine1.getIndex(), engine2.getIndex());
    }

    @Test
    void whenDeleteAllThenSuccess() {
        Engine engine1 = new Engine("TDI 3.0");
        Engine engine2 = new Engine("TFSI 5.0");
        engineRepository.create(engine1);
        engineRepository.create(engine2);
        engineRepository.deleteAll();
        assertThat(engineRepository.findAllOrderById()).isEmpty();
    }
}