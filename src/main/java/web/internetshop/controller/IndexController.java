package web.internetshop.controller;

import java.io.IOException;
import java.time.LocalDate;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class IndexController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String dateTime = LocalDate.now().toString();
        request.setAttribute("time", dateTime);

        request.getRequestDispatcher("/WEB-INF/views/index.jsp").forward(request, response);
    }
}
