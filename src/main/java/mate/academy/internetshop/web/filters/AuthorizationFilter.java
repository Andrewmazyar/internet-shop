package mate.academy.internetshop.web.filters;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internetshop.lib.Injector;
import mate.academy.internetshop.model.Role;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.UserService;
import org.apache.log4j.Logger;

public class AuthorizationFilter implements Filter {
    private static final Logger logger = Logger.getLogger(AuthorizationFilter.class);
    private static final String USER_ID = "user_id";
    private static final Injector INJECTOR = Injector.getInstance("mate.academy.internetshop");
    private UserService userService = (UserService) INJECTOR.getInstance(UserService.class);

    private Map<String, List<Role.RoleName>> protectedUrls = new HashMap<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        protectedUrls.put("/products/addProduct", List.of(Role.RoleName.ADMIN));
        protectedUrls.put("/users/listUsers", List.of(Role.RoleName.ADMIN));
        protectedUrls.put("/products/listProductAdmin", List.of(Role.RoleName.ADMIN));
        protectedUrls.put("/order/allOrders", List.of(Role.RoleName.ADMIN));
        protectedUrls.put("/order/orderAll", List.of(Role.RoleName.USER));
        protectedUrls.put("/order/details", List.of(Role.RoleName.USER));
        protectedUrls.put("/products/listProduct", List.of(Role.RoleName.USER));
        protectedUrls.put("/shoppingCart/shoppingCart", List.of(Role.RoleName.USER));
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String requestUrl = request.getServletPath();

        if (protectedUrls.get(requestUrl) == null) {
            filterChain.doFilter(request, response);
            return;
        }

        Long userId = (Long) request.getSession().getAttribute(USER_ID);
        if (userId == null || userService.get(userId) == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        User user = userService.get(userId);
        if (isAuthorized(user, protectedUrls.get(requestUrl))) {
            filterChain.doFilter(request, response);
        } else {
            logger.warn("User:"
                            + user.getLogin()
                            + ", user id: "
                            + user.getId()
                            + "hasn`t access");
            request.getRequestDispatcher("/WEB-INF/views/accessDenied.jsp")
                    .forward(request, response);
        }

    }

    @Override
    public void destroy() {

    }

    private boolean isAuthorized(User user, List<Role.RoleName> authorizedRoles) {
        for (Role.RoleName authorizedRole : authorizedRoles) {
            for (Role userRole : user.getRoles()) {
                if (authorizedRole.equals(userRole.getRoleName())) {
                    return true;
                }
            }
        }
        return false;
    }
}
