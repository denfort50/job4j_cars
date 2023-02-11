package ru.job4j.cars.repository;

import org.hibernate.Session;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Интерфейс описывает методы взаимодействия с базой данных
 */
public interface CrudRepository {

    /**
     * Метод выполняет транзакцию
     * @param command CRUD операция
     */
    void run(Consumer<Session> command);

    /**
     * Метод выполняет транзакцию
     * @param query запрос на jpql
     */
    void run(String query);

    /**
     * Метод выполняет транзакцию с получением результата
     * @param query запрос на jpql
     * @param tClass класс, экземпляр которого участвует в операции
     * @return возвращает список объектов
     * @param <T> ссылочный тип данных участвующего в операции объекта
     */
    <T> List<T> queryAndGetList(String query, Class<T> tClass);

    /**
     * Метод выполняет транзакцию с получением результата
     * @param query запрос на jpql
     * @param tClass класс, экземпляр которого участвует в операции
     * @param args аргументы для составления jpql запроса
     * @return возвращает список объектов
     * @param <T> ссылочный тип данных участвующего в операции объекта
     */
    <T> List<T> queryAndGetList(String query, Class<T> tClass, Map<String, Object> args);

    /**
     * Метод выполняет транзакцию с получением результата
     * @param query запрос на jpql
     * @param tClass класс, экземпляр которого участвует в операции
     * @param parameter передаваемый параметр
     * @param ids список идентификаторов
     * @return возвращает список объектов
     * @param <T> ссылочный тип данных участвующего в операции объекта
     */
    <T> List<T> queryAndGetList(String query, Class<T> tClass, String parameter, List<Integer> ids);

    /**
     * Метод выполняет транзакцию с получением результата
     * @param query запрос на jpql
     * @param tClass класс, экземпляр которого участвует в операции
     * @param args аргументы для составления jpql запроса
     * @return возвращает Optional объекта
     * @param <T> ссылочный тип данных участвующего в операции объекта
     */
    <T> Optional<T> queryAndGetOptional(String query, Class<T> tClass, Map<String, Object> args);

    /**
     * Метод выполняет транзакцию с получением результата
     * @param query запрос на jpql
     * @return возвращает true, если операция прошла успешно
     * @param <T> ссылочный тип данных участвующего в операции объекта
     */
    <T> boolean queryAndGetBoolean(String query);

    /**
     * Метод выполняет транзакцию с получением результата
     * @param query запрос на jpql
     * @param args аргументы для составления jpql запроса
     * @return возвращает true, если операция прошла успешно
     * @param <T> ссылочный тип данных участвующего в операции объекта
     */
    <T> boolean queryAndGetBoolean(String query, Map<String, Object> args);

    /**
     * Метод выполняет транзакцию с получением результата
     * @param query запрос на jpql
     * @param tClass класс, экземпляр которого участвует в операции
     * @param args аргументы для составления jpql запроса
     * @return возвращает объект
     * @param <T> ссылочный тип данных участвующего в операции объекта
     */
    <T> Object queryAndGetObject(String query, Class<T> tClass, Map<String, Object> args);

    /**
     * Метод выполняет транзакционный запрос
     * @param command передаваемая CRUD операция
     * @return возвращает результат выполнения транзакции
     * @param <T> ссылочный тип данных участвующего в операции объекта
     */
    <T> T executeTransaction(Function<Session, T> command);
}
