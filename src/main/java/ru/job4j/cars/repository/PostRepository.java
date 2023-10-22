package ru.job4j.cars.repository;

import ru.job4j.cars.model.Post;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс описывает методы взаимодействия с хранилищем объявлений
 *
 * @author Denis Kalchenko
 */
public interface PostRepository {

    /**
     * Метод сохраняет объявления
     *
     * @param post объявление
     * @return возвращает объявление
     */
    Post create(Post post);

    /**
     * Метод обновляет объявления
     *
     * @param post объявление
     */
    void update(Post post);

    /**
     * Метод удаляет объявление
     *
     * @param post объявление
     */
    void delete(Post post);

    /**
     * Метод закрывает объявление
     *
     * @param id идентификатор объявления
     * @return возвращает true, если объявление удалось закрыть
     */
    boolean complete(int id);

    /**
     * Метод находит все объявления
     *
     * @return возвращает список всех объявлений
     */
    List<Post> findAllOrderById();

    /**
     * Метод находит объявление по ID
     *
     * @param postId идентификатор объявления
     * @return возвращает объявление
     */
    Optional<Post> findById(int postId);

    /**
     * Метод находит объявления за последние сутки
     *
     * @return возвращает список объявлений за последние сутки
     */
    List<Post> findAllWithinLastDay();

    /**
     * Метод находит объявления с фотографией
     *
     * @return возвращает список объявлений с фотографией
     */
    List<Post> findAllWithPhoto();

    /**
     * Метод находит объявления по названию бренда автомобиля
     *
     * @param carName бренда автомобиля
     * @return возвращает список объявлений указанного бренда
     */
    List<Post> findAllByName(String carName);

    /**
     * Метод удаляет все объявления
     */
    void deleteAll();
}
