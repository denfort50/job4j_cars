package ru.job4j.cars.util;

import org.springframework.ui.Model;
import ru.job4j.cars.model.User;

import javax.servlet.http.HttpSession;

public final class UserAttributeTool {

    private UserAttributeTool() {
        throw new UnsupportedOperationException("This is utility class and it cannot be instantiated");
    }

    public static void addAttributeUser(Model model, HttpSession session) {
        User user = getAttributeUser(session);
        if (user == null) {
            user = new User();
            user.setLogin("Гость");
        }
        model.addAttribute("user", user);
    }

    public static User getAttributeUser(HttpSession session) {
        return (User) session.getAttribute("user");
    }
}
