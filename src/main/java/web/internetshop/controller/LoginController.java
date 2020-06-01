package web.internetshop.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import web.internetshop.exception.AuthenticationException;
import web.internetshop.lib.Injector;
import web.internetshop.model.User;
import web.internetshop.service.security.AuthenticationService;

public class LoginController extends HttpServlet {
    private static final Injector INJECTOR = Injector.getInstance("web.internetshop");
    private AuthenticationService authenticationService
            = (AuthenticationService) INJECTOR.getInstance(AuthenticationService.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/login.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String login = request.getParameter("login");
        String pwd = request.getParameter("pwd");
        try {
            User user = authenticationService.login(login, pwd);
            HttpSession session = request.getSession();
            session.setAttribute("user_id", user.getId());
        } catch (AuthenticationException e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/login.jsp")
                    .forward(request,response);
            return;
        }
        response.sendRedirect(request.getContextPath() + "/");
    }
}
