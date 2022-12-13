package ru.job4j.cars.repository.interfaces;

import ru.job4j.cars.model.Post;

import java.util.List;

public interface PostRepository {

    List<Post> findAllWithinLastDay();

    List<Post> findAllWithPhoto();

    List<Post> findAllByBrand(String carName);
}
