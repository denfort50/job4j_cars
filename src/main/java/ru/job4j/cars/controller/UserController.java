package ru.job4j.cars.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.cars.model.User;
import ru.job4j.cars.service.user.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Optional;

import static ru.job4j.cars.util.UserAttributeTool.addAttributeUser;

/**
 * Класс представляет собой контроллер для взаимодействия хранилища пользователей с представлениями
 * @author Denis Kalchenko
 * @version 1.0
 */
@Controller
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    /** Сервис, обеспечивающий доступ к хранилищу пользователей */
    private final UserService userService;

    /**
     * Метод обрабатывает GET-запрос на получение страницы для авторизации
     * @param model модель
     * @param session сессия
     * @param fail результат авторизации
     * @return возвращает страницу для авторизации
     */
    @GetMapping("/loginPage")
    public String loginPage(Model model, HttpSession session,
                            @RequestParam(name = "fail", required = false) Boolean fail) {
        model.addAttribute("fail", fail != null);
        addAttributeUser(model, session);
        return "user/loginPage";
    }

    /**
     * Метод обрабатывает POST-запрос на авторизацию пользователя
     * @param user пользователь
     * @param req запрос
     * @return возвращает страницу со списком всех объявлений, либо ошибку с информацией, что логин или пароль неверны
     */
    @PostMapping("/login")
    public String login(@ModelAttribute User user, HttpServletRequest req) {
        Optional<User> userDB = userService.findByLoginAndPassword(user.getLogin(), user.getPassword());
        if (userDB.isEmpty()) {
            return "redirect:/users/loginPage?fail=true";
        }
        HttpSession session = req.getSession();
        session.setAttribute("user", userDB.get());
        return "redirect:/posts";
    }

    /**
     * Метод обрабатывает GET-запрос на выход из аккаунта
     * @param session сессия
     * @return возвращает
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/posts";
    }

    /**
     * Метод обрабатывает GET-запрос на получение формы с регистрацией
     * @param model модель
     * @param session сессия
     * @return возвращает страницу со списком всех объявлений
     */
    @GetMapping("/addUser")
    public String addUser(Model model, HttpSession session) {
        model.addAttribute("newUser", new User(0, "Имя", "Логин", "Пароль", new ArrayList<>(), new ArrayList<>()));
        addAttributeUser(model, session);
        return "user/addUser";
    }

    /**
     * Метод обрабатывает POST-запрос на регистрацию пользователя
     * @param user пользователь
     * @return возвращает страницу с информацией с успешной / неуспешной регистрацией
     */
    @PostMapping("/registration")
    public String registration(@ModelAttribute User user) {
        Optional<User> regUser = userService.create(user);
        if (regUser.isEmpty()) {
            return "redirect:/users/fail";
        }
        return "redirect:/users/success";
    }

    /**
     * Метод обрабатывает GET-запрос на получение страницы с ошибкой регистрации
     * @param model модель
     * @param session сессия
     * @return возвращает страницу с ошибкой регистрации
     */
    @GetMapping("/fail")
    public String fail(Model model, HttpSession session) {
        addAttributeUser(model, session);
        return "error/registrationFail";
    }

    /**
     * Метод обрабатывает GET-запрос на получение страницы с успешной регистрацией
     * @param model модель
     * @param session сессия
     * @return возвращает страницу с успешной регистрацией
     */
    @GetMapping("/success")
    public String success(Model model, HttpSession session) {
        addAttributeUser(model, session);
        return "user/registrationSuccess";
    }
}
