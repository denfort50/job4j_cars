package ru.job4j.cars.configuration;

import liquibase.integration.spring.SpringLiquibase;
import lombok.AllArgsConstructor;
import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import ru.job4j.cars.Main;
import ru.job4j.cars.repository.CrudRepository;
import ru.job4j.cars.repository.CrudRepositoryImpl;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.Properties;

/**
 * Класс представляет собой конфигурацию ORM Hibernate
 * @author Denis Kalchenko
 * @version 1.0
 */
@Configuration
@AllArgsConstructor
public class TestDataSourceConfig {



    /**
     * Метод создает объект SessionFactory для многократного использования в приложении
     * @return возвращает объект SessionFactory
     */
    @Bean(destroyMethod = "close")
    public SessionFactory getSessionFactory() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        return new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }

    @Bean
    public CrudRepository getCrudRepository() {
        return new CrudRepositoryImpl(getSessionFactory());
    }

    private Properties loadDbProperties() {
        Properties cfg = new Properties();
        try (BufferedReader io = new BufferedReader(
                new InputStreamReader(
                        Objects.requireNonNull(Main.class.getClassLoader()
                                .getResourceAsStream("application.properties"))
                )
        )) {
            cfg.load(io);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        try {
            Class.forName(cfg.getProperty("db.test.driver"));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        return cfg;
    }

    private Properties getProperties() {
        return loadDbProperties();
    }

    @Bean
    public DataSource getDataSource() {
        Properties cfg = getProperties();
        BasicDataSource pool = new BasicDataSource();
        pool.setDriverClassName(cfg.getProperty("db.test.driver"));
        pool.setUrl(cfg.getProperty("db.test.url"));
        pool.setUsername(cfg.getProperty("db.test.username"));
        pool.setPassword(cfg.getProperty("db.test.password"));
        pool.setMinIdle(5);
        pool.setMaxIdle(10);
        pool.setMaxOpenPreparedStatements(100);
        return pool;
    }

    @Bean
    public SpringLiquibase liquibase() {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog(getProperties().getProperty("db.test.changeLogFile"));
        liquibase.setDataSource(getDataSource());
        return liquibase;
    }
}
