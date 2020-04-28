package mate.academy.internetshop.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internetshop.lib.Injector;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.UserService;

public class UserController extends HttpServlet {
    private static Injector injector = Injector.getInstance("mate.academy.internetshop");
    UserService userService = (UserService) injector.getInstance(UserService.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<User> allUsers = userService.getAll();

        request.setAttribute("users", allUsers);
        request.getRequestDispatcher("/WEB-INF/views/users/listUsers.jsp")
                .forward(request, response);
    }
}
