package ru.job4j.cars.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.configuration.TestDataSourceConfig;
import ru.job4j.cars.model.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

class HibernatePostRepositoryTest {

    private final TestDataSourceConfig testDataSourceConfig = new TestDataSourceConfig();
    private final CrudRepository crudRepository = testDataSourceConfig.getCrudRepository();
    private final PostRepository postRepository = new HibernatePostRepository(crudRepository);
    private final UserRepository userRepository = new HibernateUserRepository(crudRepository);
    private final CarRepository carRepository = new HibernateCarRepository(crudRepository);
    private final EngineRepository engineRepository = new HibernateEngineRepository(crudRepository);
    private final BodyRepository bodyRepository = new HibernateBodyRepository(crudRepository);

    @AfterEach
    void cleanTable() {
        postRepository.deleteAll();
        userRepository.deleteAll();
        carRepository.deleteAll();
        engineRepository.deleteAll();
        bodyRepository.deleteAll();
    }

    @Test
    void whenCreateThenSuccess() {
        Engine engine = new Engine("TDI 3.0");
        engineRepository.create(engine);
        Body body = new Body("Sportback");
        bodyRepository.create(body);
        Car car = new Car("Audi", "A7", body, engine);
        carRepository.create(car);
        User user = new User("Denis", "denfort50@yandex.ru", "password");
        userRepository.create(user);
        Post post = new Post(3000000, "Продаю Audi A7", user, car);
        postRepository.create(post);
        assertThat(post.getId()).isNotEqualTo(0);
    }

    @Test
    void whenUpdateThenSuccess() {
        Engine engine = new Engine("TDI 3.0");
        engineRepository.create(engine);
        Body body = new Body("Sportback");
        bodyRepository.create(body);
        Car car = new Car("Audi", "A7", body, engine);
        carRepository.create(car);
        User user = new User("Denis", "denfort50@yandex.ru", "password");
        userRepository.create(user);
        Post post = new Post(3000000, "Продаю Audi A7", user, car);
        postRepository.create(post);
        Post updPost = new Post(4000000, "Продаю Audi A6", user, car);
        updPost.setId(post.getId());
        postRepository.update(updPost);
        assertThat(updPost.getId()).isNotEqualTo(0);
    }

    @Test
    void whenDeleteThenSuccess() {
        Engine engine = new Engine("TDI 3.0");
        engineRepository.create(engine);
        Body body = new Body("Sportback");
        bodyRepository.create(body);
        Car car = new Car("Audi", "A7", body, engine);
        carRepository.create(car);
        User user = new User("Denis", "denfort50@yandex.ru", "password");
        userRepository.create(user);
        Post post = new Post(3000000, "Продаю Audi A7", user, car);
        postRepository.create(post);
        postRepository.delete(post);
        Optional<Post> postFromDB = postRepository.findById(post.getId());
        assertThat(postFromDB).isEmpty();
    }

    @Test
    void whenFindAllThenSuccess() {
        Engine engine1 = new Engine("TDI 3.0");
        Engine engine2 = new Engine("TDI 2.0");
        engineRepository.create(engine1);
        engineRepository.create(engine2);
        Body body1 = new Body("Sportback");
        bodyRepository.create(body1);
        Body body2 = new Body("Sedan");
        bodyRepository.create(body2);
        Car car1 = new Car("Audi", "A7", body1, engine1);
        Car car2 = new Car("Audi", "A6", body2, engine2);
        carRepository.create(car1);
        carRepository.create(car2);
        User user = new User("Denis", "denfort50@yandex.ru", "password");
        userRepository.create(user);
        Post post1 = new Post(3000000, "Продаю Audi A7", user, car1);
        Post post2 = new Post(4000000, "Продаю Audi A6", user, car2);
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
        Body body1 = new Body("Sportback");
        bodyRepository.create(body1);
        Body body2 = new Body("Sedan");
        bodyRepository.create(body2);
        Car car1 = new Car("Audi", "A7", body1, engine1);
        Car car2 = new Car("Audi", "A6", body2, engine2);
        carRepository.create(car1);
        carRepository.create(car2);
        User user = new User("Denis", "denfort50@yandex.ru", "password");
        userRepository.create(user);
        Post post1 = new Post(3000000, "Продаю Audi A7", user, car1);
        Post post2 = new Post(4000000, "Продаю Audi A6", user, car2);
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
        Body body1 = new Body("Sportback");
        bodyRepository.create(body1);
        Body body2 = new Body("Sedan");
        bodyRepository.create(body2);
        Car car1 = new Car("Audi", "A7", body1, engine1);
        Car car2 = new Car("Audi", "A6", body2, engine2);
        carRepository.create(car1);
        carRepository.create(car2);
        User user = new User("Denis", "denfort50@yandex.ru", "password");
        userRepository.create(user);
        Post post1 = new Post(3000000, "Продаю Audi A7", user, car1);
        Post post2 = new Post(4000000, "Продаю Audi A6", user, car2);
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
        Body body1 = new Body("Sportback");
        bodyRepository.create(body1);
        Car car = new Car("Audi", "A7", body1, engine);
        carRepository.create(car);
        User user = new User("Denis", "denfort50@yandex.ru", "password");
        userRepository.create(user);
        Post post1 = new Post(3000000, "Продаю Audi A7", user, car);
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
        Body body1 = new Body("Sportback");
        bodyRepository.create(body1);
        Body body2 = new Body("Sedan");
        bodyRepository.create(body2);
        Body body3 = new Body("SUV");
        bodyRepository.create(body3);
        Car car1 = new Car("Audi", "A7", body1, engine1);
        Car car2 = new Car("Audi", "A6", body2, engine2);
        Car car3 = new Car("BMW", "X6", body3, engine3);
        carRepository.create(car1);
        carRepository.create(car2);
        carRepository.create(car3);
        User user = new User("Denis", "denfort50@yandex.ru", "password");
        userRepository.create(user);
        Post post1 = new Post(3000000, "Продаю Audi A7", user, car1);
        Post post2 = new Post(2500000, "Продаю Audi A6", user, car2);
        Post post3 = new Post(4000000, "Продаю BMW X6", user, car3);
        postRepository.create(post1);
        postRepository.create(post2);
        postRepository.create(post3);
        List<Post> postsFromDB = postRepository.findAllByName("BMW");
        assertThat(postsFromDB.stream().map(Post::getText).collect(Collectors.toList()))
                .contains(post3.getText())
                .doesNotContain(post2.getText(), post1.getText());
    }

}