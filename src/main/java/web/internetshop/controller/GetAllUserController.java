package web.internetshop.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import web.internetshop.lib.Injector;
import web.internetshop.model.User;
import web.internetshop.service.UserService;

public class GetAllUserController extends HttpServlet {
    private static final Injector INJECTOR = Injector.getInstance("web.internetshop");
    private UserService userService = (UserService) INJECTOR.getInstance(UserService.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<User> allUsers = userService.getAll();

        request.setAttribute("users", allUsers);
        request.getRequestDispatcher("/WEB-INF/views/users/listUsers.jsp")
                .forward(request, response);
    }
}
