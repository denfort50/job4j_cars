package ru.job4j.cars.repository;

import org.hibernate.Session;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Интерфейс описывает методы взаимодействия с базой данных
 *
 * @author Denis Kalchenko
 */
public interface CrudRepository {

    /**
     * Метод выполняет транзакцию
     *
     * @param command CRUD операция
     */
    void run(Consumer<Session> command);

    /**
     * Метод выполняет транзакцию
     *
     * @param query запрос на jpql
     */
    void run(String query);

    /**
     * Метод выполняет транзакцию с получением результата
     *
     * @param query  запрос на jpql
     * @param tClass класс, экземпляр которого участвует в операции
     * @param <T>    ссылочный тип данных участвующего в операции объекта
     * @return возвращает список объектов
     */
    <T> List<T> queryAndGetList(String query, Class<T> tClass);

    /**
     * Метод выполняет транзакцию с получением результата
     *
     * @param query  запрос на jpql
     * @param tClass класс, экземпляр которого участвует в операции
     * @param args   аргументы для составления jpql запроса
     * @param <T>    ссылочный тип данных участвующего в операции объекта
     * @return возвращает список объектов
     */
    <T> List<T> queryAndGetList(String query, Class<T> tClass, Map<String, Object> args);

    /**
     * Метод выполняет транзакцию с получением результата
     *
     * @param query     запрос на jpql
     * @param tClass    класс, экземпляр которого участвует в операции
     * @param parameter передаваемый параметр
     * @param ids       список идентификаторов
     * @param <T>       ссылочный тип данных участвующего в операции объекта
     * @return возвращает список объектов
     */
    <T> List<T> queryAndGetList(String query, Class<T> tClass, String parameter, List<Integer> ids);

    /**
     * Метод выполняет транзакцию с получением результата
     *
     * @param query  запрос на jpql
     * @param tClass класс, экземпляр которого участвует в операции
     * @param args   аргументы для составления jpql запроса
     * @param <T>    ссылочный тип данных участвующего в операции объекта
     * @return возвращает Optional объекта
     */
    <T> Optional<T> queryAndGetOptional(String query, Class<T> tClass, Map<String, Object> args);

    /**
     * Метод выполняет транзакцию с получением результата
     *
     * @param query запрос на jpql
     * @param <T>   ссылочный тип данных участвующего в операции объекта
     * @return возвращает true, если операция прошла успешно
     */
    <T> boolean queryAndGetBoolean(String query);

    /**
     * Метод выполняет транзакцию с получением результата
     *
     * @param query запрос на jpql
     * @param args  аргументы для составления jpql запроса
     * @param <T>   ссылочный тип данных участвующего в операции объекта
     * @return возвращает true, если операция прошла успешно
     */
    <T> boolean queryAndGetBoolean(String query, Map<String, Object> args);

    /**
     * Метод выполняет транзакцию с получением результата
     *
     * @param query  запрос на jpql
     * @param tClass класс, экземпляр которого участвует в операции
     * @param args   аргументы для составления jpql запроса
     * @param <T>    ссылочный тип данных участвующего в операции объекта
     * @return возвращает объект
     */
    <T> T queryAndGetObject(String query, Class<T> tClass, Map<String, Object> args);

    /**
     * Метод выполняет транзакционный запрос
     *
     * @param command передаваемая CRUD операция
     * @param <T>     ссылочный тип данных участвующего в операции объекта
     * @return возвращает результат выполнения транзакции
     */
    <T> T executeTransaction(Function<Session, T> command);
}
