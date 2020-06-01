package web.internetshop.controller;

import java.io.IOException;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import web.internetshop.lib.Injector;
import web.internetshop.model.Role;
import web.internetshop.model.ShoppingCart;
import web.internetshop.model.User;
import web.internetshop.service.ShoppingCartService;
import web.internetshop.service.UserService;

public class RegistrationController extends HttpServlet {
    private static final Injector INJECTOR = Injector.getInstance("web.internetshop");
    private UserService userService = (UserService) INJECTOR.getInstance(UserService.class);
    private ShoppingCartService shoppingCartService
            = (ShoppingCartService) INJECTOR.getInstance(ShoppingCartService.class);

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
            User user = new User(name, login, pwd);
            user.setRoles(Set.of(Role.of("USER")));
            userService.create(user);
            ShoppingCart shoppingCartForUser
                    = new ShoppingCart(userService.findByLogin(login).get().getId());
            shoppingCartService.create(shoppingCartForUser);
            response.sendRedirect(request.getContextPath() + "/");
        } else {
            request.setAttribute("message", "your password and repeat password are different");
            request.getRequestDispatcher("/WEB-INF/views/registration.jsp")
                    .forward(request, response);
        }
    }
}
