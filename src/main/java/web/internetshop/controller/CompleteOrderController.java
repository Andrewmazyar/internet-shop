package web.internetshop.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import web.internetshop.lib.Injector;
import web.internetshop.model.Order;
import web.internetshop.model.ShoppingCart;
import web.internetshop.service.OrderService;
import web.internetshop.service.ShoppingCartService;

public class CompleteOrderController extends HttpServlet {
    private static final String USER_ID = "user_id";
    private static final Injector INJECTOR = Injector.getInstance("web.internetshop");
    private ShoppingCartService shoppingCartService
            = (ShoppingCartService) INJECTOR.getInstance(ShoppingCartService.class);
    private OrderService orderService
            = (OrderService) INJECTOR.getInstance(OrderService.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute(USER_ID);
        ShoppingCart shoppingCart = shoppingCartService.getByUserId(userId);
        Order order = orderService.completeOrder(shoppingCart.getProducts(),
                shoppingCart.getUserId());
        shoppingCartService.clear(shoppingCart);

        response.sendRedirect(request.getContextPath() + "/order?id=" + order.getOrderId());
    }
}
