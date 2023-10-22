package ru.job4j.cars.util;

import org.springframework.ui.Model;
import ru.job4j.cars.model.User;

import javax.servlet.http.HttpSession;

/**
 * Утилитный класс для добавления в модель атрибута - пользователь
 *
 * @author Denis Kalchenko
 */
public final class UserAttributeTool {

    private UserAttributeTool() {
        throw new UnsupportedOperationException("This is utility class and it cannot be instantiated");
    }

    /**
     * Метод добавляет атрибут - пользователя в модель
     *
     * @param model       модель
     * @param httpSession сессия
     */
    public static void addAttributeUser(Model model, HttpSession httpSession) {
        User user = getAttributeUser(httpSession);
        if (user == null) {
            user = new User();
            user.setLogin("Гость");
        }
        model.addAttribute("user", user);
    }

    /**
     * Метод получает атрибут - пользователя
     *
     * @param httpSession сессия
     * @return возвращает пользователя
     */
    public static User getAttributeUser(HttpSession httpSession) {
        return (User) httpSession.getAttribute("user");
    }
}
