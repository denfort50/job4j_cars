package ru.job4j.cars.controller;

import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import ru.job4j.cars.model.*;
import ru.job4j.cars.service.BodyService;
import ru.job4j.cars.service.CarService;
import ru.job4j.cars.service.EngineService;
import ru.job4j.cars.service.PostService;

import javax.servlet.http.HttpSession;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class PostControllerTest {

    @Test
    void getAllPosts() {
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
        PostService postService = mock(PostService.class);
        BodyService bodyService = mock(BodyService.class);
        CarService carService = mock(CarService.class);
        EngineService engineService = mock(EngineService.class);
        Post post1 = mock(Post.class);
        Post post2 = mock(Post.class);
        List<Post> posts = List.of(post1, post2);
        when(postService.findAllOrderById()).thenReturn(posts);
        PostController postController = new PostController(postService, bodyService, carService, engineService);
        String page = postController.getAllPosts(model, session);
        verify(model).addAttribute("allPosts", posts);
        assertThat(page).isEqualTo("post/allPosts");
    }

    @Test
    void getPostsWithinLastDay() {
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
        PostService postService = mock(PostService.class);
        BodyService bodyService = mock(BodyService.class);
        CarService carService = mock(CarService.class);
        EngineService engineService = mock(EngineService.class);
        Post post1 = mock(Post.class);
        Post post2 = mock(Post.class);
        List<Post> posts = List.of(post1, post2);
        when(postService.findAllWithinLastDay()).thenReturn(posts);
        PostController postController = new PostController(postService, bodyService, carService, engineService);
        String page = postController.getPostsWithinLastDay(model, session);
        verify(model).addAttribute("postsWithinLastDay", posts);
        assertThat(page).isEqualTo("post/withinLastDayPosts");
    }

    @Test
    void getPostsWithPhoto() {
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
        PostService postService = mock(PostService.class);
        BodyService bodyService = mock(BodyService.class);
        CarService carService = mock(CarService.class);
        EngineService engineService = mock(EngineService.class);
        Post post1 = mock(Post.class);
        Post post2 = mock(Post.class);
        List<Post> posts = List.of(post1, post2);
        when(postService.findAllWithPhoto()).thenReturn(posts);
        PostController postController = new PostController(postService, bodyService, carService, engineService);
        String page = postController.getPostsWithPhoto(model, session);
        verify(model).addAttribute("postsWithPhoto", posts);
        assertThat(page).isEqualTo("post/withPhotoPosts");
    }

}