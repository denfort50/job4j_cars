package ru.job4j.cars.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.config.TestHibernateConfig;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.Engine;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.model.User;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

class HibernatePostRepositoryTest {

    private final TestHibernateConfig testHibernateConfig = new TestHibernateConfig();
    private final CrudRepository crudRepository = testHibernateConfig.getCrudRepository();
    private final PostRepository postRepository = new HibernatePostRepository(crudRepository);
    private final UserRepository userRepository = new HibernateUserRepository(crudRepository);
    private final CarRepository carRepository = new HibernateCarRepository(crudRepository);
    private final EngineRepository engineRepository = new HibernateEngineRepository(crudRepository);

    @AfterEach
    void cleanTable() {
        postRepository.deleteAll();
        userRepository.deleteAll();
        carRepository.deleteAll();
        engineRepository.deleteAll();
    }

    @Test
    void whenCreateThenSuccess() {
        Engine engine = new Engine("TDI 3.0");
        engineRepository.create(engine);
        Car car = new Car("Audi", "A7", "Sportback", engine);
        carRepository.create(car);
        User user = new User("Denis", "password");
        userRepository.create(user);
        Post post = new Post(3000000, "Продаю Audi A7", Timestamp.valueOf(LocalDateTime.now()), false, user, car);
        postRepository.create(post);
        Post postFromDB = postRepository.findById(post.getId()).orElseThrow();
        assertThat(postFromDB.getText()).isEqualTo(post.getText());
    }

    @Test
    void whenUpdateThenSuccess() {
        Engine engine = new Engine("TDI 3.0");
        engineRepository.create(engine);
        Car car = new Car("Audi", "A7", "Sportback", engine);
        carRepository.create(car);
        User user = new User("Denis", "password");
        userRepository.create(user);
        Post post = new Post(3000000, "Продаю Audi A7", Timestamp.valueOf(LocalDateTime.now()), false, user, car);
        postRepository.create(post);
        Post updPost = new Post(4000000, "Продаю Audi A6", Timestamp.valueOf(LocalDateTime.now()), false, user, car);
        updPost.setId(post.getId());
        postRepository.update(updPost);
        Post postFromDB = postRepository.findById(updPost.getId()).orElseThrow();
        assertThat(postFromDB.getText()).isEqualTo(updPost.getText());
    }

    @Test
    void whenDeleteThenSuccess() {
        Engine engine = new Engine("TDI 3.0");
        engineRepository.create(engine);
        Car car = new Car("Audi", "A7", "Sportback", engine);
        carRepository.create(car);
        User user = new User("Denis", "password");
        userRepository.create(user);
        Post post = new Post(3000000, "Продаю Audi A7", Timestamp.valueOf(LocalDateTime.now()), false, user, car);
        postRepository.create(post);
        postRepository.delete(post.getId());
        Optional<Post> postFromDB = postRepository.findById(post.getId());
        assertThat(postFromDB).isEmpty();
    }

    @Test
    void whenFindAllThenSuccess() {
        Engine engine1 = new Engine("TDI 3.0");
        Engine engine2 = new Engine("TDI 2.0");
        engineRepository.create(engine1);
        engineRepository.create(engine2);
        Car car1 = new Car("Audi", "A7", "Sportback", engine1);
        Car car2 = new Car("Audi", "A6", "Sedan", engine2);
        carRepository.create(car1);
        carRepository.create(car2);
        User user = new User("Denis", "password");
        userRepository.create(user);
        Post post1 = new Post(3000000, "Продаю Audi A7", Timestamp.valueOf(LocalDateTime.now()), false, user, car1);
        Post post2 = new Post(4000000, "Продаю Audi A6", Timestamp.valueOf(LocalDateTime.now()), false, user, car2);
        postRepository.create(post1);
        postRepository.create(post2);
        List<Post> postsFromDB = postRepository.findAllOrderById();
        assertThat(postsFromDB.stream().map(Post::getText).collect(Collectors.toList()))
                .contains(post1.getText(), post2.getText());
    }

    @Test
    void whenDeleteAllThenSuccess() {
        Engine engine1 = new Engine("TDI 3.0");
        Engine engine2 = new Engine("TDI 2.0");
        engineRepository.create(engine1);
        engineRepository.create(engine2);
        Car car1 = new Car("Audi", "A7", "Sportback", engine1);
        Car car2 = new Car("Audi", "A6", "Sedan", engine2);
        carRepository.create(car1);
        carRepository.create(car2);
        User user = new User("Denis", "password");
        userRepository.create(user);
        Post post1 = new Post(3000000, "Продаю Audi A7", Timestamp.valueOf(LocalDateTime.now()), false, user, car1);
        Post post2 = new Post(4000000, "Продаю Audi A6", Timestamp.valueOf(LocalDateTime.now()), false, user, car2);
        postRepository.create(post1);
        postRepository.create(post2);
        postRepository.deleteAll();
        assertThat(postRepository.findAllOrderById()).isEmpty();
    }

    @Test
    void whenFindAllWithinLastDayThenSuccess() {
        Engine engine1 = new Engine("TDI 3.0");
        Engine engine2 = new Engine("TDI 2.0");
        engineRepository.create(engine1);
        engineRepository.create(engine2);
        Car car1 = new Car("Audi", "A7", "Sportback", engine1);
        Car car2 = new Car("Audi", "A6", "Sedan", engine2);
        carRepository.create(car1);
        carRepository.create(car2);
        User user = new User("Denis", "password");
        userRepository.create(user);
        Post post1 = new Post(3000000, "Продаю Audi A7",
                Timestamp.valueOf(LocalDateTime.now().minusMinutes(1)), false, user, car1);
        Post post2 = new Post(4000000, "Продаю Audi A6",
                Timestamp.valueOf(LocalDateTime.of(2022, 12, 16, 20, 55)), false, user, car2);
        postRepository.create(post1);
        postRepository.create(post2);
        List<Post> postsFromDB = postRepository.findAllWithinLastDay();
        assertThat(postsFromDB.stream().map(Post::getText).collect(Collectors.toList()))
                .contains(post1.getText());
    }

    @Test
    void whenFindAllWithPhotoThenFail() {
        Engine engine = new Engine("TDI 3.0");
        engineRepository.create(engine);
        Car car = new Car("Audi", "A7", "Sportback", engine);
        carRepository.create(car);
        User user = new User("Denis", "password");
        userRepository.create(user);
        Post post1 = new Post(3000000, "Продаю Audi A7", Timestamp.valueOf(LocalDateTime.now()), false, user, car);
        postRepository.create(post1);
        List<Post> postsFromDB = postRepository.findAllWithPhoto();
        assertThat(postsFromDB).isEmpty();
    }

    @Test
    void whenFindAllByNameThenSuccess() {
        Engine engine1 = new Engine("TDI 3.0");
        Engine engine2 = new Engine("TDI 2.0");
        Engine engine3 = new Engine("3.0d");
        engineRepository.create(engine1);
        engineRepository.create(engine2);
        engineRepository.create(engine3);
        Car car1 = new Car("Audi", "A7", "Sportback", engine1);
        Car car2 = new Car("Audi", "A6", "Sedan", engine2);
        Car car3 = new Car("BMW", "X6", "SUV", engine3);
        carRepository.create(car1);
        carRepository.create(car2);
        carRepository.create(car3);
        User user = new User("Denis", "password");
        userRepository.create(user);
        Post post1 = new Post(3000000, "Продаю Audi A7",
                Timestamp.valueOf(LocalDateTime.now()), false, user, car1);
        Post post2 = new Post(2500000, "Продаю Audi A6",
                Timestamp.valueOf(LocalDateTime.of(2022, 12, 16, 20, 55)), false, user, car2);
        Post post3 = new Post(4000000, "Продаю BMW X6",
                Timestamp.valueOf(LocalDateTime.now()), false, user, car3);
        postRepository.create(post1);
        postRepository.create(post2);
        postRepository.create(post3);
        List<Post> postsFromDB = postRepository.findAllByName("BMW X6");
        assertThat(postsFromDB.stream().map(Post::getText).collect(Collectors.toList()))
                .contains(post3.getText())
                .doesNotContain(post2.getText(), post1.getText());
    }

}