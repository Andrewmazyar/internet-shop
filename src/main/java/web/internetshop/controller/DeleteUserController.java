package web.internetshop.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import web.internetshop.lib.Injector;
import web.internetshop.service.UserService;

public class DeleteUserController extends HttpServlet {
    private static final Injector INJECTOR = Injector.getInstance("web.internetshop");
    private UserService userService = (UserService) INJECTOR.getInstance(UserService.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userId = request.getParameter("user_id");
        userService.delete(Long.valueOf(userId));

        response.sendRedirect(request.getContextPath() + "/users/listUsers");
    }
}
