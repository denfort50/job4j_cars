package ru.job4j.cars.service.post;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.Engine;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.model.PriceHistory;
import ru.job4j.cars.repository.post.PostRepository;
import ru.job4j.cars.service.body.BodyService;
import ru.job4j.cars.service.car.CarService;
import ru.job4j.cars.service.engine.EngineService;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static ru.job4j.cars.util.UserAttributeTool.getAttributeUser;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    private final EngineService engineService;

    private final BodyService bodyService;

    private final CarService carService;

    @Override
    public Post create(Post post, Car car, Engine engine,
                       MultipartFile file, HttpSession httpSession) throws IOException {
        post.setPhoto(file.getBytes());
        post.setUser(getAttributeUser(httpSession));
        engineService.create(engine);
        car.setEngine(engine);
        post.setCar(car);
        post.getCar().setBody(bodyService.findById(car.getBody().getId()));
        PriceHistory priceHistory = createPriceHistoryObject(post);
        post.addToPriceHistoryList(priceHistory);
        return postRepository.create(post);
    }

    @Override
    public void update(Post post, Car car, Engine engine,
                       MultipartFile file) throws IOException {
        Post postInDb = findById(post.getId()).orElseThrow();
        Car carInDb = postInDb.getCar();
        Engine engineInDb = carInDb.getEngine();
        engineInDb.setIndex(engine.getIndex());
        engineService.update(engineInDb);
        carInDb.setBrand(car.getBrand());
        carInDb.setModel(car.getModel());
        carInDb.setEngine(engineInDb);
        carInDb.setBody(bodyService.findById(car.getBody().getId()));
        carService.update(carInDb);
        postInDb.setPrice(post.getPrice());
        postInDb.setText(post.getText());
        postInDb.setCar(carInDb);
        postInDb.setPhoto(file.getBytes());
        PriceHistory priceHistory = createPriceHistoryObject(postInDb);
        postInDb.addToPriceHistoryList(priceHistory);
        postRepository.update(postInDb);
    }

    @Override
    public void delete(Post post) {
        postRepository.delete(post);
    }

    @Override
    public boolean complete(int id) {
        return postRepository.complete(id);
    }

    @Override
    public List<Post> findAllOrderById() {
        return postRepository.findAllOrderById();
    }

    @Override
    public Optional<Post> findById(int postId) {
        return postRepository.findById(postId);
    }

    @Override
    public List<Post> findAllWithinLastDay() {
        return postRepository.findAllWithinLastDay();
    }

    @Override
    public List<Post> findAllWithPhoto() {
        return postRepository.findAllWithPhoto();
    }

    @Override
    public List<Post> findAllByName(String carName) {
        return postRepository.findAllByName(carName);
    }

    @Override
    public void deleteAll() {
        postRepository.deleteAll();
    }

    private PriceHistory createPriceHistoryObject(Post post) {
        PriceHistory priceHistory = new PriceHistory();
        int size = post.getPriceHistoryListSize();
        if (size == 0) {
            priceHistory.setBefore(0);
        } else {
            priceHistory.setBefore(post.getPreviousPrice());
        }
        priceHistory.setAfter(post.getPrice());
        priceHistory.setCreated(Timestamp.valueOf(LocalDateTime.now()));
        return priceHistory;
    }
}
