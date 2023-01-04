package ru.job4j.cars.repository;

import ru.job4j.cars.model.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository {

    Post create(Post post);

    void update(Post post);

    void delete(int postId);

    List<Post> findAllOrderById();

    Optional<Post> findById(int postId);

    List<Post> findAllWithinLastDay();

    List<Post> findAllWithPhoto();

    List<Post> findAllByName(String carName);

    void deleteAll();
}
