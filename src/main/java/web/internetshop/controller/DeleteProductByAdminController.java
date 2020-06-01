package web.internetshop.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import web.internetshop.lib.Injector;
import web.internetshop.service.ProductService;

public class DeleteProductByAdminController extends HttpServlet {
    private static final Injector INJECTOR = Injector.getInstance("web.internetshop");
    private ProductService productService
            = (ProductService) INJECTOR.getInstance(ProductService.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("pruduct_id");
        productService.delete(Long.valueOf(id));
        response.sendRedirect(request.getContextPath() + "/products/listProductAdmin");
    }
}
