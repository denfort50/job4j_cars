package ru.job4j.cars.service.post;

import org.springframework.web.multipart.MultipartFile;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.Engine;
import ru.job4j.cars.model.Post;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Интерфейс описывает методы сервиса, через который происходит взаимодействие с хранилищем объявлений
 */
public interface PostService {

    /**
     * Метод сохраняет объявления
     * @param post объявление
     * @return возвращает объявление
     */
    Post create(Post post, Car car, Engine engine, MultipartFile file) throws IOException;

    /**
     * Метод обновляет объявления
     * @param post объявление
     */
    void update(Post post, Car car, Engine engine, MultipartFile file) throws IOException;

    /**
     * Метод удаляет объявление
     * @param post объявление
     */
    void delete(Post post);

    /**
     * Метод закрывает объявление
     * @param id идентификатор объявления
     * @return возвращает true, если объявление удалось закрыть
     */
    boolean complete(int id);

    /**
     * Метод находит все объявления
     * @return возвращает список всех объявлений
     */
    List<Post> findAllOrderById();

    /**
     * Метод находит объявление по ID
     * @param postId идентификатор объявления
     * @return возвращает объявление
     */
    Optional<Post> findById(int postId);

    /**
     * Метод находит объявления за последние сутки
     * @return возвращает список объявлений за последние сутки
     */
    List<Post> findAllWithinLastDay();

    /**
     * Метод находит объявления с фотографией
     * @return возвращает список объявлений с фотографией
     */
    List<Post> findAllWithPhoto();

    /**
     * Метод находит объявления по названию бренда автомобиля
     * @param carName бренда автомобиля
     * @return возвращает список объявлений указанного бренда
     */
    List<Post> findAllByName(String carName);

    /**
     * Метод удаляет все объявления
     */
    void deleteAll();
}
