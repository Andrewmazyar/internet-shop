package mate.academy.internetshop.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internetshop.lib.Injector;
import mate.academy.internetshop.model.Product;
import mate.academy.internetshop.service.ProductService;

public class ProductController extends HttpServlet {
    private static Injector injector = Injector.getInstance("mate.academy.internetshop");
    ProductService productService = (ProductService) injector.getInstance(ProductService.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Product> allProducts = productService.getAll();

        request.setAttribute("products", allProducts);
        request.getRequestDispatcher("/WEB-INF/views/products/listProduct.jsp")
                .forward(request, response);
    }

}
