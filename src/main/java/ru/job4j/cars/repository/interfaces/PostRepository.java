package ru.job4j.cars.repository.interfaces;

import ru.job4j.cars.model.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository {



    Post create(Post post);

    void update(Post post);

    void delete(int postId);

    List<Post> findAllOrderById();

    Optional<Post> findById(int postId);

    void deleteAll();
}
