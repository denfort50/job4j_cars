package ru.job4j.cars.repository.classes;

import lombok.AllArgsConstructor;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.repository.interfaces.CrudRepository;
import ru.job4j.cars.repository.interfaces.PostRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class HibernatePostRepository implements PostRepository {

    private final CrudRepository crudRepository;

    private static final String FIND_ALL_WITHIN_LAST_DAY = "FROM Post p WHERE p.created BETWEEN :fNowMinusOneDay AND :fNow";
    private static final String FIND_ALL_WITH_PHOTO = "FROM Post p WHERE p.photo IS NOT NULL";
    private static final String FIND_ALL_BY_BRAND = "FROM Post p JOIN FETCH p.car c WHERE c.brand = :fBrand";

    @Override
    public List<Post> findAllWithinLastDay() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nowMinusOneDay = LocalDateTime.now().minusDays(1);
        return crudRepository.queryAndGetList(FIND_ALL_WITHIN_LAST_DAY, Post.class, Map.of("fNowMinusOneDay", nowMinusOneDay, "fNow", now));
    }

    @Override
    public List<Post> findAllWithPhoto() {
        return crudRepository.queryAndGetList(FIND_ALL_WITH_PHOTO, Post.class);
    }

    @Override
    public List<Post> findAllByBrand(String brand) {
        return crudRepository.queryAndGetList(FIND_ALL_BY_BRAND, Post.class, Map.of("fBrand", brand));
    }
}
