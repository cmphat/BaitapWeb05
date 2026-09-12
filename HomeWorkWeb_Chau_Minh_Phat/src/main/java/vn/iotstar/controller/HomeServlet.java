package vn.iotstar.controller;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import vn.iotstar.entity.Product;
import vn.iotstar.service.IProductService;
import vn.iotstar.service.impl.ProductServiceImpl;
import java.util.List;

@WebServlet(urlPatterns = {"/", "/home"})
public class HomeServlet extends HttpServlet {
    private final IProductService productService = new ProductServiceImpl();

    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            resp.sendRedirect(req.getContextPath() + "/login"); return;
        }

        List<Product> latestProducts = productService.findLatest(10);
        req.setAttribute("latestProducts", latestProducts);

        req.getRequestDispatcher("/views/home.jsp").forward(req, resp);
    }
}
