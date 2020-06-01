package web.internetshop.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import web.internetshop.lib.Injector;
import web.internetshop.service.OrderService;
import web.internetshop.service.UserService;

public class GetAllOrdersController extends HttpServlet {
    private static final String USER_ID = "user_id";
    private static final Injector INJECTOR = Injector.getInstance("web.internetshop");
    private OrderService orderService
            = (OrderService) INJECTOR.getInstance(OrderService.class);
    private UserService userService = (UserService) INJECTOR.getInstance(UserService.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute(USER_ID);
        request.setAttribute("orders",
                orderService.getUserOrders(userService.get(userId)));
        request.getRequestDispatcher("/WEB-INF/views/order/orderAll.jsp")
                .forward(request, response);
    }
}
