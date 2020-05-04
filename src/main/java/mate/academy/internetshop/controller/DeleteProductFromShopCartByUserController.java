package mate.academy.internetshop.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mate.academy.internetshop.lib.Injector;
import mate.academy.internetshop.model.Product;
import mate.academy.internetshop.model.ShoppingCart;
import mate.academy.internetshop.service.ProductService;
import mate.academy.internetshop.service.ShoppingCartService;

public class DeleteProductFromShopCartByUserController extends HttpServlet {
    private static final String USER_ID = "user_id";
    private static final Injector INJECTOR = Injector.getInstance("mate.academy.internetshop");
    private ShoppingCartService shoppingCartService
            = (ShoppingCartService) INJECTOR.getInstance(ShoppingCartService.class);
    private ProductService productService
            = (ProductService) INJECTOR.getInstance(ProductService.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute(USER_ID);
        String productId = request.getParameter("product_id");
        ShoppingCart shoppingCart = shoppingCartService.getByUserId(userId);
        Product product = productService.get(Long.valueOf(productId));
        shoppingCartService.deleteProduct(shoppingCart, product);

        response.sendRedirect(request.getContextPath() + "/shoppingCart/shoppingCart");
    }
}
