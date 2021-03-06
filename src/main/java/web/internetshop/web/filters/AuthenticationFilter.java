package web.internetshop.web.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import web.internetshop.lib.Injector;
import web.internetshop.service.UserService;

public class AuthenticationFilter implements Filter {
    private static final String USER_ID = "user_id";
    private static final Injector INJECTOR = Injector.getInstance("web.internetshop");
    private UserService userService = (UserService) INJECTOR.getInstance(UserService.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpServletRequest request = (HttpServletRequest) req;
        String url = request.getServletPath();
        if (url.equals("/login") || url.equals("/registration")
                || url.equals("/") || url.equals("/injectData")) {
            filterChain.doFilter(request, response);
            return;
        }
        Long userId = (Long) request.getSession().getAttribute(USER_ID);
        if (userId == null || userService.get(userId) == null) {
            response.sendRedirect("/login");
            return;
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
