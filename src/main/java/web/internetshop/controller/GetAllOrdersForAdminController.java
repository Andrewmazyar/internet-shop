package web.internetshop.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import web.internetshop.lib.Injector;
import web.internetshop.service.OrderService;

public class GetAllOrdersForAdminController extends HttpServlet {
    private static final Injector INJECTOR = Injector.getInstance("web.internetshop");
    private OrderService orderService
            = (OrderService) INJECTOR.getInstance(OrderService.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("orders", orderService.getAll());
        request.getRequestDispatcher("/WEB-INF/views/order/allOrders.jsp")
                .forward(request, response);
    }
}
