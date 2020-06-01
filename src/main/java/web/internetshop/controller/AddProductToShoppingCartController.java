package web.internetshop.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import web.internetshop.lib.Injector;
import web.internetshop.model.ShoppingCart;
import web.internetshop.service.ProductService;
import web.internetshop.service.ShoppingCartService;

public class AddProductToShoppingCartController extends HttpServlet {
    private static final String USER_ID = "user_id";
    private static final Injector INJECTOR = Injector.getInstance("web.internetshop");
    private ShoppingCartService shoppingCartService
            = (ShoppingCartService) INJECTOR.getInstance(ShoppingCartService.class);
    private ProductService productService
            = (ProductService) INJECTOR.getInstance(ProductService.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String productId = request.getParameter("product_id");
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute(USER_ID);
        ShoppingCart shoppingCart = shoppingCartService.getByUserId(userId);
        shoppingCartService.addProduct(shoppingCart, productService.get(Long.parseLong(productId)));
        response.sendRedirect(request.getContextPath() + "/products/listProduct");
    }
}
