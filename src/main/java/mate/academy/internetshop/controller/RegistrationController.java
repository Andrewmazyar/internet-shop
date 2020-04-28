package mate.academy.internetshop.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internetshop.lib.Injector;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.UserService;

public class RegistrationController extends HttpServlet {
    private static Injector INJECTOR = Injector.getInstance("mate.academy.internetshop");
    private final UserService userService = (UserService) INJECTOR.getInstance(UserService.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/registration.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String login = request.getParameter("login");
        String pwd = request.getParameter("pwd");
        String pwdControl = request.getParameter("pwd-control");

        if (pwd.equals(pwdControl)) {
            userService.create(new User(name, login, pwd));
            response.sendRedirect(request.getContextPath() + "/");
        } else {
            request.setAttribute("message", "your password and repeat password are different");
            request.getRequestDispatcher("/WEB-INF/views/registration.jsp")
                    .forward(request, response);
        }
    }
}
