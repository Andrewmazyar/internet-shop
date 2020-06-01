package web.internetshop.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import web.internetshop.lib.Injector;
import web.internetshop.model.ShoppingCart;
import web.internetshop.service.ShoppingCartService;

public class GetAllProductsInShoppingCartByUserController extends HttpServlet {
    private static final String USER_ID = "user_id";
    private static final Injector INJECTOR = Injector.getInstance("web.internetshop");
    private ShoppingCartService shoppingCartService
            = (ShoppingCartService) INJECTOR.getInstance(ShoppingCartService.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute(USER_ID);
        ShoppingCart shoppingCart = shoppingCartService.getByUserId(userId);
        request.setAttribute("products", shoppingCart.getProducts());
        request.getRequestDispatcher("/WEB-INF/views/shoppingCart/shoppingCart.jsp")
                .forward(request, response);
    }
}
