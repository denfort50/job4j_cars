package ru.job4j.cars.repository.post;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.repository.CrudRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@Repository
public class HibernatePostRepository implements PostRepository {

    private final CrudRepository crudRepository;

    private static final String FIND_ALL_WITHIN_LAST_DAY = "FROM Post p JOIN FETCH p.car c WHERE p.created BETWEEN :fNowMinusOneDay AND :fNow";
    private static final String FIND_ALL_WITH_PHOTO = "FROM Post p JOIN FETCH p.car c WHERE p.photo IS NOT NULL";
    private static final String FIND_ALL_BY_NAME = "FROM Post p JOIN FETCH p.car c WHERE c.brand = :fBrand";
    private static final String FIND_ALL_ORDER_BY_ID = "FROM Post p JOIN FETCH p.car c ORDER BY p.id";
    private static final String FIND_BY_ID = "FROM Post p JOIN FETCH p.car JOIN FETCH p.priceHistoryList WHERE p.id = :fId";
    private static final String COMPLETE_POST = "UPDATE Post p SET p.status = :fStatus WHERE p.id = :fId";
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
    public void delete(Post post) {
        crudRepository.run(session -> session.delete(post));
    }

    public boolean complete(int id) {
        return crudRepository.queryAndGetBoolean(COMPLETE_POST, Map.of("fStatus", true, "fId", id));
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
    public List<Post> findAllByName(String brand) {
        return crudRepository.queryAndGetList(FIND_ALL_BY_NAME, Post.class, Map.of("fBrand", brand));
    }

    public void deleteAll() {
        crudRepository.run(DELETE_ALL);
    }
}
