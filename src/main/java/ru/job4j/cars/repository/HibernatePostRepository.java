package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import ru.job4j.cars.model.Post;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
public class HibernatePostRepository implements PostRepository {

    private final CrudRepository crudRepository;

    private static final String FIND_ALL_WITHIN_LAST_DAY = "FROM Post p WHERE p.created BETWEEN :fNowMinusOneDay AND :fNow";
    private static final String FIND_ALL_WITH_PHOTO = "FROM Post p WHERE p.photo IS NOT NULL";
    private static final String FIND_ALL_BY_NAME = "FROM Post p JOIN FETCH p.car c WHERE c.name = :fName";
    private static final String DELETE = "DELETE Post p WHERE p.id = :fId";
    private static final String FIND_ALL_ORDER_BY_ID = "FROM Post p ORDER BY p.id";
    private static final String FIND_BY_ID = "FROM Post p WHERE p.id = :fId";
    private static final String DELETE_ALL = "DELETE Post";

    @Override
    public Post create(Post post) {
        crudRepository.run(session -> session.save(post));
        return post;
    }

    @Override
    public void update(Post post) {
        crudRepository.run(session -> session.merge(post));
    }

    @Override
    public void delete(int postId) {
        crudRepository.queryAndGetBoolean(DELETE, Map.of("fId", postId));
    }

    @Override
    public List<Post> findAllOrderById() {
        return crudRepository.queryAndGetList(FIND_ALL_ORDER_BY_ID, Post.class);
    }

    @Override
    public Optional<Post> findById(int postId) {
        return crudRepository.queryAndGetOptional(FIND_BY_ID, Post.class, Map.of("fId", postId));
    }

    @Override
    public List<Post> findAllWithinLastDay() {
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        Timestamp nowMinusOneDay = Timestamp.valueOf(LocalDateTime.now().minusDays(1));
        return crudRepository.queryAndGetList(FIND_ALL_WITHIN_LAST_DAY, Post.class, Map.of("fNowMinusOneDay", nowMinusOneDay, "fNow", now));
    }

    @Override
    public List<Post> findAllWithPhoto() {
        return crudRepository.queryAndGetList(FIND_ALL_WITH_PHOTO, Post.class);
    }

    @Override
    public List<Post> findAllByName(String name) {
        return crudRepository.queryAndGetList(FIND_ALL_BY_NAME, Post.class, Map.of("fName", name));
    }

    public void deleteAll() {
        crudRepository.run(DELETE_ALL);
    }
}
