package mate.academy.internetshop.controller;

import mate.academy.internetshop.lib.Injector;
import mate.academy.internetshop.model.ShoppingCart;
import mate.academy.internetshop.service.ProductService;
import mate.academy.internetshop.service.ShoppingCartService;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddProductToShoppingCartController extends HttpServlet {
    private static Injector injector = Injector.getInstance("mate.academy.internetshop");
    ShoppingCartService shoppingCartService
            = (ShoppingCartService) injector.getInstance(ShoppingCartService.class);
    ProductService productService = (ProductService) injector.getInstance(ProductService.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userId = request.getParameter("user_id");
        String productId = request.getParameter("product_id");
        ShoppingCart shoppingCart = shoppingCartService.getByUserId(Long.valueOf(userId));
        shoppingCartService.addProduct(shoppingCart, productService.get(Long.valueOf(productId)));
        response.sendRedirect(request.getContextPath() + "/products/listProduct");
    }
}
