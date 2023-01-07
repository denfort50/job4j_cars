package ru.job4j.cars.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.cars.model.*;
import ru.job4j.cars.service.BodyService;
import ru.job4j.cars.service.PostService;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

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

    @GetMapping("")
    public String getAllPosts(Model model, HttpSession session) {
        List<Post> posts = postService.findAllOrderById();
        model.addAttribute("allPosts", posts);
        addAttributeUser(model, session);
        return "allPosts";
    }

    @GetMapping("formAddPost")
    public String addPost(Model model) {
        model.addAttribute("post",
                new Post(0, "Текст", Timestamp.valueOf(LocalDateTime.now()), false,
                new Car("Марка", "Модель",
                        new Body("Кузов"),
                        new Engine("Двигатель"))));
        model.addAttribute("bodies", bodyService.findAll());
        return "addPost";
    }

    @PostMapping("/createPost")
    public String createPost(@ModelAttribute Post post, @RequestParam("file") MultipartFile file,
                             HttpSession httpSession) throws IOException {
        post.setPhoto(file.getBytes());
        post.setUser(getAttributeUser(httpSession));
        post.getCar().setBody(bodyService.findById(post.getCar().getBody().getId()));
        postService.create(post);
        return "redirect:/allPosts";
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
    public String updatePost(@ModelAttribute Post post) {
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
