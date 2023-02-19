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
import ru.job4j.cars.service.body.BodyService;
import ru.job4j.cars.service.car.CarService;
import ru.job4j.cars.service.engine.EngineService;
import ru.job4j.cars.service.post.PostService;

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

    /** Сервис, обеспечивающий доступ к хранилищу объявлений */
    private final PostService postService;

    /** Сервис, обеспечивающий доступ к хранилищу кузовов */
    private final BodyService bodyService;

    /** Сервис, обеспечивающий доступ к хранилищу автомобилей */
    private final CarService carService;

    /** Сервис, обеспечивающий доступ к хранилищу двигателей */
    private final EngineService engineService;

    /**
     * Метод обрабатывает GET-запрос к основной странице - post/allPosts
     * @param model модель
     * @param session сессия
     * @return возвращает страницу со списком всех объявлений
     */
    @GetMapping("")
    public String getAllPosts(Model model, HttpSession session) {
        List<Post> posts = postService.findAllOrderById();
        model.addAttribute("allPosts", posts);
        addAttributeUser(model, session);
        return "post/allPosts";
    }

    /**
     * Метод обрабатывает GET-запрос к странице - post/withingLastDay
     * @param model модель
     * @param session сессия
     * @return возвращает страницу со списком объявлений за последние сутки
     */
    @GetMapping("/withingLastDay")
    public String getPostsWithinLastDay(Model model, HttpSession session) {
        List<Post> posts = postService.findAllWithinLastDay();
        model.addAttribute("postsWithinLastDay", posts);
        addAttributeUser(model, session);
        return "post/withinLastDayPosts";
    }

    /**
     * Метод обрабатывает GET-запрос к странице - post/withPhotoPosts
     * @param model модель
     * @param session сессия
     * @return возвращает страницу со списком объявлений с фотографией
     */
    @GetMapping("/withPhoto")
    public String getPostsWithPhoto(Model model, HttpSession session) {
        List<Post> posts = postService.findAllWithPhoto();
        model.addAttribute("postsWithPhoto", posts);
        addAttributeUser(model, session);
        return "post/withPhotoPosts";
    }

    /**
     * Метод обрабатывает GET-запрос на получение фотографии объявления
     * @param postId ID объявления
     * @return возвращает фото объявления
     */
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

    /**
     * Метод обрабатывает GET-запрос на получение формы для создания объявления
     * @param model модель
     * @return возвращает форму для создания объявления
     */
    @GetMapping("formAddPost")
    public String addPost(Model model) {
        model.addAttribute("post",
                new Post(0, "Текст",
                new User("Имя", "Логин", "Пароль"),
                        new Car("Марка", "Модель",
                        new Body("Кузов"),
                        new Engine("Двигатель"))));
        model.addAttribute("bodies", bodyService.findAll());
        return "post/addPost";
    }

    /**
     * Метод обрабатывает POST-запрос на создание нового объявления
     * @param post объявление
     * @param car автомобиль
     * @param engine двигатель
     * @param file фото
     * @param httpSession сессия
     * @return возвращает страницу со всеми объявлениями
     * @throws IOException
     */
    @PostMapping("/createPost")
    public String createPost(@ModelAttribute Post post,
                             @ModelAttribute Car car,
                             @ModelAttribute Engine engine,
                             @RequestParam("file") MultipartFile file,
                             HttpSession httpSession) throws IOException {
        post.setUser(getAttributeUser(httpSession));
        postService.create(post, car, engine, file);
        return "redirect:/posts";
    }

    /**
     * Метод обрабатывает POST-запрос на редактирование нового объявления
     * @param post объявление
     * @param car автомобиль
     * @param engine двигатель
     * @param file фото
     * @return возвращает страницу со списком всех объявлений
     * @throws IOException
     */
    @PostMapping("/updatePost")
    public String updatePost(@ModelAttribute Post post,
                             @ModelAttribute Car car,
                             @ModelAttribute Engine engine,
                             @RequestParam("file") MultipartFile file) throws IOException {
        postService.update(post, car, engine, file);
        return "redirect:/posts";
    }

    /**
     * Метод обрабатывает GET-запрос на получение подробной информации по объявлению
     * @param model модель
     * @param id ID объявления
     * @param httpSession сессия
     * @return возвращает страницу с подробной информацией по объявлению или ошибку 404
     */
    @GetMapping("/postDescription/{postId}")
    public String getPostDescription(Model model, @PathVariable("postId") int id, HttpSession httpSession) {
        Optional<Post> post = postService.findById(id);
        if (post.isPresent()) {
            httpSession.setAttribute("postId", id);
            model.addAttribute("post", post.get());
            addAttributeUser(model, httpSession);
            return "post/postDescription";
        } else {
            return "redirect:/404";
        }
    }

    /**
     * Метод обрабатывает POST-запрос на закрытие объявления
     * @param post объявление
     * @return возвращает страницу со списком всех объявлений
     */
    @PostMapping("/completePost")
    public String completePost(@ModelAttribute Post post) {
        boolean result = postService.complete(post.getId());
        if (!result) {
            return "redirect:/posts/fail";
        }
        return "redirect:/posts";
    }

    /**
     * Метод обрабатывает GET-запрос на получение страницы с формой для редактирования объявления
     * @param model модель
     * @param id ID объявления
     * @return возвращает страницу с формой для редактирования объявления
     */
    @GetMapping("formModifyPost/{id}")
    public String modifyPost(Model model, @PathVariable("id") int id) {
        model.addAttribute("post", postService.findById(id));
        model.addAttribute("bodies", bodyService.findAll());
        return "post/modifyPost";
    }

    /**
     * Метод обрабатывает POST-запрос на удаление объявления
     * @param post объявление
     * @return возвращает страницу со списком всех объявлений
     */
    @PostMapping("/deletePost")
    public String deletePost(@ModelAttribute Post post) {
        Post postInDb = postService.findById(post.getId()).orElseThrow();
        postService.delete(postInDb);
        return "redirect:/posts";
    }

    /**
     * Метод создает объект истории цен по объявлению
     * @param post объявление
     * @return возвращает объект истории цен по объявлению
     */

}
