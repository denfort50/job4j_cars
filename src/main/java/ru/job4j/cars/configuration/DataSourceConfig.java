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
import ru.job4j.cars.repository.CrudRepository;
import ru.job4j.cars.repository.CrudRepositoryImpl;

import javax.sql.DataSource;

/**
 * Класс представляет собой конфигурацию ORM Hibernate
 *
 * @author Denis Kalchenko
 * @version 1.0
 */
@Configuration
@AllArgsConstructor
public class DataSourceConfig {

    private final Environment environment;

    /**
     * Метод создает объект SessionFactory для многократного использования в приложении
     *
     * @return возвращает объект SessionFactory
     */
    @Bean(destroyMethod = "close")
    public SessionFactory getSessionFactory() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        return new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }

    /**
     * Метод создает объект CrudRepository для взаимодействия с базой данных
     *
     * @return возвращает объект CrudRepository
     */
    @Bean
    public CrudRepository getCrudRepository() {
        return new CrudRepositoryImpl(getSessionFactory());
    }

    @Bean
    public DataSource getDataSource() {
        BasicDataSource pool = new BasicDataSource();
        pool.setDriverClassName(environment.getProperty("db.datasource.driver"));
        pool.setUrl(environment.getProperty("db.datasource.url"));
        pool.setUsername(environment.getProperty("db.datasource.username"));
        pool.setPassword(environment.getProperty("db.datasource.password"));
        return pool;
    }

    @Bean
    public SpringLiquibase liquibase() {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog(environment.getProperty("db.datasource.changeLogFile"));
        liquibase.setDataSource(getDataSource());
        return liquibase;
    }
}
