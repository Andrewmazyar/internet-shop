package mate.academy.internetshop.controller;

import mate.academy.internetshop.lib.Injector;
import mate.academy.internetshop.model.ShoppingCart;
import mate.academy.internetshop.service.ShoppingCartService;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ShoppingCartController extends HttpServlet {
    private static final Long USER_ID = 1L;

    private static Injector injector = Injector.getInstance("mate.academy.internetshop");
    ShoppingCartService shoppingCartService
            = (ShoppingCartService) injector.getInstance(ShoppingCartService.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ShoppingCart shoppingCart = shoppingCartService.getByUserId(USER_ID);
        request.setAttribute("products", shoppingCart.getProducts());
        request.getRequestDispatcher("/WEB-INF/views/shoppingCart/shoppingCart.jsp")
                .forward(request, response);
    }
}
