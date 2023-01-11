package ru.job4j.cars.controller;

import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.cars.model.*;
import ru.job4j.cars.service.BodyService;
import ru.job4j.cars.service.EngineService;
import ru.job4j.cars.service.PostService;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static ru.job4j.cars.util.UserAttributeTool.addAttributeUser;
import static ru.job4j.cars.util.UserAttributeTool.getAttributeUser;

/**
 * Класс представляет собой контроллер для обработки действий с объявлениями
 * @author Denis Kalchenko
 * @version 1.0
 */
@Controller
@AllArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;
    private final BodyService bodyService;
    private final EngineService engineService;

    @GetMapping("")
    public String getAllPosts(Model model, HttpSession session) {
        List<Post> posts = postService.findAllOrderById();
        model.addAttribute("allPosts", posts);
        addAttributeUser(model, session);
        return "allPosts";
    }

    @GetMapping("/postPhoto/{postId}")
    public ResponseEntity<Resource> download(@PathVariable("postId") Integer postId) {
        Optional<Post> post = postService.findById(postId);
        return ResponseEntity.ok()
                .headers(new HttpHeaders())
                .contentLength(post.orElseThrow().getPhoto().length)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new ByteArrayResource(post.orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND)).getPhoto()));
    }

    @GetMapping("formAddPost")
    public String addPost(Model model) {
        model.addAttribute("post",
                new Post(0, "Текст",
                new Car("Марка", "Модель",
                        new Body("Кузов"),
                        new Engine("Двигатель"))));
        model.addAttribute("bodies", bodyService.findAll());
        return "addPost";
    }

    @PostMapping("/createPost")
    public String createPost(@ModelAttribute Post post, @ModelAttribute Car car,
                             @ModelAttribute Body body, @ModelAttribute Engine engine,
                             @RequestParam("file") MultipartFile file,
                             HttpSession httpSession) throws IOException {
        post.setPhoto(file.getBytes());
        post.setUser(getAttributeUser(httpSession));
        engineService.create(engine);
        car.setEngine(engine);
        post.setCar(car);
        post.getCar().setBody(bodyService.findById(post.getCar().getBody().getId()));
        postService.create(post);
        return "redirect:/posts";
    }

    @GetMapping("/postDescription/{postId}")
    public String getPostDescription(Model model, @PathVariable("postId") int id, HttpSession httpSession) {
        Optional<Post> post = postService.findById(id);
        if (post.isPresent()) {
            httpSession.setAttribute("postId", id);
            model.addAttribute("post", post.get());
            addAttributeUser(model, httpSession);
            return "postDescription";
        } else {
            return "redirect:/404";
        }
    }

    @PostMapping("/completePost")
    public String completePost(@ModelAttribute Post post) {
        boolean result = postService.complete(post.getId());
        if (!result) {
            return "redirect:/posts/fail";
        }
        return "redirect:/posts";
    }

    @GetMapping("formModifyPost/{id}")
    public String modifyPost(Model model, @PathVariable("id") int id) {
        model.addAttribute("post", postService.findById(id));
        model.addAttribute("bodies", bodyService.findAll());
        return "modifyPost";
    }

    @PostMapping("/updatePost")
    public String updatePost(@ModelAttribute Post post, @ModelAttribute Car car,
                             @ModelAttribute Body body, @ModelAttribute Engine engine,
                             @RequestParam("file") MultipartFile file) throws IOException {
        post.setPhoto(file.getBytes());
        engineService.update(engine);
        car.setEngine(engine);
        post.setCar(car);
        post.getCar().setBody(bodyService.findById(post.getCar().getBody().getId()));
        postService.update(post);
        return "redirect:/posts";
    }

    @PostMapping("/deletePost")
    public String deletePost(@ModelAttribute Post post) {
        postService.delete(post.getId());
        return "redirect:/posts";
    }
}
