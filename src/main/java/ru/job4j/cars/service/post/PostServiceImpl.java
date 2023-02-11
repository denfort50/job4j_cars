package ru.job4j.cars.service.post;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.repository.post.PostRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Override
    public Post create(Post post) {
        return postRepository.create(post);
    }

    @Override
    public void update(Post post) {
        postRepository.update(post);
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
}
