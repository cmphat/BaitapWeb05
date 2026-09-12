package vn.iotstar.controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import vn.iotstar.entity.Category;
import vn.iotstar.service.ICategoryService;
import vn.iotstar.service.impl.CategoryServiceImpl;

@WebServlet(urlPatterns = {
        "/admin/categories",
        "/admin/category/add",
        "/admin/category/insert",
        "/admin/category/edit",
        "/admin/category/update",
        "/admin/category/delete"
})
public class CategoryJpaController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final ICategoryService cateService =
            new CategoryServiceImpl();

    @Override
    protected void doGet(
            HttpServletRequest req,
            HttpServletResponse resp)
            throws ServletException, IOException {

        String url = req.getRequestURI();

        if (url.contains("/admin/categories")) {

            List<Category> list =
                    cateService.findAll();

            req.setAttribute("listcate", list);

            req.getRequestDispatcher(
                    "/views/category/list-jpa.jsp"
            ).forward(req, resp);

        } else if (url.contains("/admin/category/add")) {

            req.getRequestDispatcher(
                    "/views/category/add-jpa.jsp"
            ).forward(req, resp);

        } else if (url.contains("/admin/category/edit")) {
            try {
                int id = Integer.parseInt(req.getParameter("id"));
                Category category = cateService.findById(id);
                req.setAttribute("cate", category);
                req.getRequestDispatcher("/views/category/edit-jpa.jsp").forward(req, resp);
            } catch (NumberFormatException e) {
                resp.sendRedirect(req.getContextPath() + "/admin/categories");
            }

        } else if (url.contains("/admin/category/delete")) {
            try {
                int id = Integer.parseInt(req.getParameter("id"));
                cateService.delete(id);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            resp.sendRedirect(req.getContextPath() + "/admin/categories");
        }
    }

    @Override
    protected void doPost(
            HttpServletRequest req,
            HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        String url = req.getRequestURI();

        if (url.contains("/admin/category/insert")) {
            String categoryname = req.getParameter("categoryname");
            String images = req.getParameter("images");
            int status = 0;
            try {
                status = Integer.parseInt(req.getParameter("status"));
            } catch (NumberFormatException e) {
                status = 1;
            }

            if (categoryname == null || categoryname.trim().isEmpty()) {
                req.setAttribute("alertMsg", "Tên danh mục không được để trống.");
                req.setAttribute("categoryname", categoryname);
                req.setAttribute("images", images);
                req.setAttribute("status", status);
                req.getRequestDispatcher("/views/category/add-jpa.jsp").forward(req, resp);
                return;
            }

            Category category = new Category();
            category.setCategoryname(categoryname.trim());
            category.setImages(images != null ? images.trim() : "");
            category.setStatus(status);

            cateService.insert(category);
            resp.sendRedirect(req.getContextPath() + "/admin/categories");
        } else if (url.contains("/admin/category/update")) {
            try {
                int id = Integer.parseInt(req.getParameter("categoryid"));
                String categoryname = req.getParameter("categoryname");
                String images = req.getParameter("images");
                int status = 0;
                try {
                    status = Integer.parseInt(req.getParameter("status"));
                } catch (NumberFormatException e) {
                    status = 1;
                }

                Category category = cateService.findById(id);
                if (category == null) {
                    resp.sendRedirect(req.getContextPath() + "/admin/categories");
                    return;
                }

                if (categoryname == null || categoryname.trim().isEmpty()) {
                    req.setAttribute("alertMsg", "Tên danh mục không được để trống.");
                    category.setImages(images != null ? images.trim() : "");
                    category.setStatus(status);
                    req.setAttribute("cate", category);
                    req.getRequestDispatcher("/views/category/edit-jpa.jsp").forward(req, resp);
                    return;
                }

                category.setCategoryname(categoryname.trim());
                category.setImages(images != null ? images.trim() : "");
                category.setStatus(status);
                cateService.update(category);

                resp.sendRedirect(req.getContextPath() + "/admin/categories");
            } catch (NumberFormatException e) {
                resp.sendRedirect(req.getContextPath() + "/admin/categories");
            }
        }
    }
}