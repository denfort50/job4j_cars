package ru.job4j.cars.controller;

import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import ru.job4j.cars.model.User;
import ru.job4j.cars.service.user.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

class UserControllerTest {

    @Test
    void whenLoginPageThenSuccess() {
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
        UserService userService = mock(UserService.class);
        UserController userController = new UserController(userService);
        String page = userController.loginPage(model, session, true);
        verify(model).addAttribute("fail", true);
        assertThat(page).isEqualTo("user/loginPage");
    }

    @Test
    void whenLoginThenSuccess() {
        User user = new User("Denis", "mr_bond", "password");
        HttpSession session = mock(HttpSession.class);
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        UserService userService = mock(UserService.class);
        UserController userController = new UserController(userService);
        when(userService.findByLoginAndPassword(user.getLogin(), user.getPassword())).thenReturn(Optional.of(user));
        when(httpServletRequest.getSession()).thenReturn(session);
        String page = userController.login(user, httpServletRequest);
        assertThat(page).isEqualTo("redirect:/posts");
    }

    @Test
    void whenLogoutThenSuccess() {
        HttpSession session = mock(HttpSession.class);
        UserService userService = mock(UserService.class);
        UserController userController = new UserController(userService);
        String page = userController.logout(session);
        assertThat(page).isEqualTo("redirect:/posts");
    }

    @Test
    void whenAddUserThenSuccess() {
        User user = new User(0, "Имя", "Логин", "Пароль", new ArrayList<>(), new ArrayList<>());
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
        UserService userService = mock(UserService.class);
        UserController userController = new UserController(userService);
        String page = userController.addUser(model, session);
        verify(model).addAttribute("newUser", user);
        assertThat(page).isEqualTo("user/addUser");
    }

    @Test
    void whenRegistrationThenSuccess() {
        User user = new User("Denis", "mr_bond", "password");
        UserService userService = mock(UserService.class);
        when(userService.create(user)).thenReturn(Optional.of(user));
        UserController userController = new UserController(userService);
        String page = userController.registration(user);
        assertThat(page).isEqualTo("redirect:/users/success");
    }

    @Test
    void whenRegistrationThenFail() {
        User user = new User("Denis", "mr_bond", "password");
        UserService userService = mock(UserService.class);
        when(userService.create(user)).thenReturn(Optional.empty());
        UserController userController = new UserController(userService);
        String page = userController.registration(user);
        assertThat(page).isEqualTo("redirect:/users/fail");
    }

    @Test
    void whenFail() {
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
        UserService userService = mock(UserService.class);
        UserController userController = new UserController(userService);
        String page = userController.fail(model, session);
        assertThat(page).isEqualTo("error/registrationFail");
    }

    @Test
    void whenSuccess() {
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
        UserService userService = mock(UserService.class);
        UserController userController = new UserController(userService);
        String page = userController.success(model, session);
        assertThat(page).isEqualTo("user/registrationSuccess");
    }
}