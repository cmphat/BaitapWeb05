package vn.iotstar.controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import vn.iotstar.entity.Product;
import vn.iotstar.service.IProductService;
import vn.iotstar.service.impl.ProductServiceImpl;

@WebServlet(urlPatterns = {"/product", "/product/detail"})
public class ProductPublicController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final IProductService productService = new ProductServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getRequestURI();

        if (url.endsWith("/product")) {
            int page = 1;
            int pageSize = 6;

            String pageParam = req.getParameter("page");
            if (pageParam != null) {
                try {
                    page = Integer.parseInt(pageParam);
                } catch (NumberFormatException e) {
                    page = 1;
                }
            }

            long totalItems = productService.count();
            int totalPages = (int) Math.ceil(totalItems / (double) pageSize);

            if (totalPages == 0) {
                page = 1;
            } else {
                if (page < 1) page = 1;
                if (page > totalPages) page = totalPages;
            }

            List<Product> products = productService.findAll(page, pageSize);

            req.setAttribute("products", products);
            req.setAttribute("currentPage", page);
            req.setAttribute("totalPages", totalPages);
            req.setAttribute("totalItems", totalItems);

            req.getRequestDispatcher("/views/product/list.jsp").forward(req, resp);
        } else if (url.endsWith("/product/detail")) {
            String idParam = req.getParameter("id");
            if (idParam == null) {
                resp.sendRedirect(req.getContextPath() + "/product");
                return;
            }
            try {
                int id = Integer.parseInt(idParam);
                Product product = productService.findById(id);
                if (product == null) {
                    resp.sendRedirect(req.getContextPath() + "/product");
                    return;
                }
                req.setAttribute("product", product);
                req.getRequestDispatcher("/views/product/detail.jsp").forward(req, resp);
            } catch (NumberFormatException e) {
                resp.sendRedirect(req.getContextPath() + "/product");
            }
        }
    }
}
