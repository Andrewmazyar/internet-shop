package web.internetshop.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import web.internetshop.lib.Injector;
import web.internetshop.model.Product;
import web.internetshop.service.ProductService;

public class GetAllProductsForAdminController extends HttpServlet {
    private static final Injector INJECTOR = Injector.getInstance("web.internetshop");
    private ProductService productService
            = (ProductService) INJECTOR.getInstance(ProductService.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Product> allProducts = productService.getAll();

        request.setAttribute("products", allProducts);
        request.getRequestDispatcher("/WEB-INF/views/products/listProductAdmin.jsp")
                .forward(request, response);
    }
}
