package ru.job4j.cars.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import org.springframework.stereotype.Component;

/**
 * Класс описывает работу авторизационного фильтра
 *
 * @author Denis Kalchenko
 */
@Component
public class AuthFilter implements Filter {

    private final Set<String> mappings =
            new HashSet<>(Set.of("posts", "loginPage", "addUser", "registration", "login", "fail", "success"));

    /**
     * Метод перебрасывает пользователя на страницу для авторизации, если пользователь ещё не авторизовался
     *
     * @param request  The request to process
     * @param response The response associated with the request
     * @param chain    Provides access to the next filter in the chain for this
     *                 filter to pass the request and response to for further
     *                 processing
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String uri = req.getRequestURI();
        if (mappingIsPresent(uri) || showPhotosWithoutLogin(uri)) {
            chain.doFilter(req, res);
            return;
        }
        if (req.getSession().getAttribute("user") == null) {
            res.sendRedirect(req.getContextPath().split("http://.[^/]+")[0] + "/users/loginPage");
            return;
        }
        chain.doFilter(req, res);
    }

    /**
     * Метод проверяет наличие адреса в списке исключений
     *
     * @param uri адрес
     * @return возвращает true/false в зависимости от результата проверки
     */
    private boolean mappingIsPresent(String uri) {
        return mappings.stream().anyMatch(uri::endsWith);
    }

    /**
     * Метод проверяет наличие требования показать фото в адресе запроса
     *
     * @param uri адрес
     * @return возвращает true, если запрашивается фото
     */
    private boolean showPhotosWithoutLogin(String uri) {
        return uri.contains("/postPhoto/");
    }

}
