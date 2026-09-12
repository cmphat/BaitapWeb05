package vn.iotstar.controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import vn.iotstar.entity.Category;
import vn.iotstar.entity.Product;
import vn.iotstar.service.ICategoryService;
import vn.iotstar.service.IProductService;
import vn.iotstar.service.impl.CategoryServiceImpl;
import vn.iotstar.service.impl.ProductServiceImpl;

@WebServlet(urlPatterns = {
        "/admin/products",
        "/admin/product/add",
        "/admin/product/insert",
        "/admin/product/edit",
        "/admin/product/update",
        "/admin/product/delete"
})
public class ProductAdminController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final IProductService productService = new ProductServiceImpl();
    private final ICategoryService categoryService = new CategoryServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getRequestURI();

        if (url.contains("/admin/products")) {
            List<Product> products = productService.findAll();
            req.setAttribute("products", products);
            req.getRequestDispatcher("/views/product/admin-list.jsp").forward(req, resp);
        } else if (url.contains("/admin/product/add")) {
            List<Category> categories = categoryService.findAll();
            req.setAttribute("categories", categories);
            req.getRequestDispatcher("/views/product/add.jsp").forward(req, resp);
        } else if (url.contains("/admin/product/edit")) {
            String idParam = req.getParameter("id");
            if (idParam == null) {
                resp.sendRedirect(req.getContextPath() + "/admin/products");
                return;
            }
            int id = Integer.parseInt(idParam);
            Product product = productService.findById(id);
            if (product == null) {
                resp.sendRedirect(req.getContextPath() + "/admin/products");
                return;
            }
            List<Category> categories = categoryService.findAll();
            req.setAttribute("product", product);
            req.setAttribute("categories", categories);
            req.getRequestDispatcher("/views/product/edit.jsp").forward(req, resp);
        } else if (url.contains("/admin/product/delete")) {
            String idParam = req.getParameter("id");
            if (idParam != null) {
                int id = Integer.parseInt(idParam);
                productService.delete(id);
            }
            resp.sendRedirect(req.getContextPath() + "/admin/products");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String url = req.getRequestURI();

        if (url.contains("/admin/product/insert")) {
            String productName = req.getParameter("productName");
            String priceStr = req.getParameter("price");
            String description = req.getParameter("description");
            String image = req.getParameter("image");
            String statusStr = req.getParameter("status");
            String categoryIdStr = req.getParameter("categoryId");

            if (productName == null || productName.trim().isEmpty()) {
                req.setAttribute("alertMsg", "Tên sản phẩm không được để trống.");
                preserveProductForm(req, productName, priceStr, description, image, statusStr, categoryIdStr);
                req.getRequestDispatcher("/views/product/add.jsp").forward(req, resp);
                return;
            }

            double price = 0;
            try {
                price = Double.parseDouble(priceStr);
                if (price <= 0) {
                    req.setAttribute("alertMsg", "Giá sản phẩm phải lớn hơn 0.");
                    preserveProductForm(req, productName, priceStr, description, image, statusStr, categoryIdStr);
                    req.getRequestDispatcher("/views/product/add.jsp").forward(req, resp);
                    return;
                }
            } catch (Exception e) {
                req.setAttribute("alertMsg", "Giá sản phẩm không hợp lệ.");
                preserveProductForm(req, productName, priceStr, description, image, statusStr, categoryIdStr);
                req.getRequestDispatcher("/views/product/add.jsp").forward(req, resp);
                return;
            }

            int categoryId = 0;
            try {
                categoryId = Integer.parseInt(categoryIdStr);
            } catch (Exception e) {
                req.setAttribute("alertMsg", "Vui lòng chọn danh mục hợp lệ.");
                preserveProductForm(req, productName, priceStr, description, image, statusStr, categoryIdStr);
                req.getRequestDispatcher("/views/product/add.jsp").forward(req, resp);
                return;
            }

            Category category = categoryService.findById(categoryId);
            if (category == null) {
                req.setAttribute("alertMsg", "Danh mục đã chọn không tồn tại.");
                preserveProductForm(req, productName, priceStr, description, image, statusStr, categoryIdStr);
                req.getRequestDispatcher("/views/product/add.jsp").forward(req, resp);
                return;
            }

            int status = 1;
            try {
                if (statusStr != null) {
                    status = Integer.parseInt(statusStr);
                }
            } catch (Exception e) {
                status = 1;
            }

            Product product = new Product();
            product.setProductName(productName.trim());
            product.setPrice(price);
            product.setDescription(description != null ? description.trim() : "");
            product.setImage(image != null ? image.trim() : "");
            product.setStatus(status);
            product.setCategory(category);

            productService.insert(product);
            resp.sendRedirect(req.getContextPath() + "/admin/products");
        } else if (url.contains("/admin/product/update")) {
            int productId = 0;
            try {
                productId = Integer.parseInt(req.getParameter("productId"));
            } catch (Exception e) {
                resp.sendRedirect(req.getContextPath() + "/admin/products");
                return;
            }

            String productName = req.getParameter("productName");
            String priceStr = req.getParameter("price");
            String description = req.getParameter("description");
            String image = req.getParameter("image");
            String statusStr = req.getParameter("status");
            String categoryIdStr = req.getParameter("categoryId");

            Product product = productService.findById(productId);
            if (product == null) {
                resp.sendRedirect(req.getContextPath() + "/admin/products");
                return;
            }

            if (productName == null || productName.trim().isEmpty()) {
                req.setAttribute("alertMsg", "Tên sản phẩm không được để trống.");
                preserveProductEditForm(req, product, productName, priceStr, description, image, statusStr, categoryIdStr);
                req.getRequestDispatcher("/views/product/edit.jsp").forward(req, resp);
                return;
            }

            double price = 0;
            try {
                price = Double.parseDouble(priceStr);
                if (price <= 0) {
                    req.setAttribute("alertMsg", "Giá sản phẩm phải lớn hơn 0.");
                    preserveProductEditForm(req, product, productName, priceStr, description, image, statusStr, categoryIdStr);
                    req.getRequestDispatcher("/views/product/edit.jsp").forward(req, resp);
                    return;
                }
            } catch (Exception e) {
                req.setAttribute("alertMsg", "Giá sản phẩm không hợp lệ.");
                preserveProductEditForm(req, product, productName, priceStr, description, image, statusStr, categoryIdStr);
                req.getRequestDispatcher("/views/product/edit.jsp").forward(req, resp);
                return;
            }

            int categoryId = 0;
            try {
                categoryId = Integer.parseInt(categoryIdStr);
            } catch (Exception e) {
                req.setAttribute("alertMsg", "Vui lòng chọn danh mục hợp lệ.");
                preserveProductEditForm(req, product, productName, priceStr, description, image, statusStr, categoryIdStr);
                req.getRequestDispatcher("/views/product/edit.jsp").forward(req, resp);
                return;
            }

            Category category = categoryService.findById(categoryId);
            if (category == null) {
                req.setAttribute("alertMsg", "Danh mục đã chọn không tồn tại.");
                preserveProductEditForm(req, product, productName, priceStr, description, image, statusStr, categoryIdStr);
                req.getRequestDispatcher("/views/product/edit.jsp").forward(req, resp);
                return;
            }

            int status = 1;
            try {
                if (statusStr != null) {
                    status = Integer.parseInt(statusStr);
                }
            } catch (Exception e) {
                status = 1;
            }

            product.setProductName(productName.trim());
            product.setPrice(price);
            product.setDescription(description != null ? description.trim() : "");
            product.setImage(image != null ? image.trim() : "");
            product.setStatus(status);
            product.setCategory(category);

            productService.update(product);
            resp.sendRedirect(req.getContextPath() + "/admin/products");
        }
    }

    private void preserveProductForm(HttpServletRequest req, String productName, String price, String description, String image, String status, String categoryId) {
        List<Category> categories = categoryService.findAll();
        req.setAttribute("categories", categories);
        req.setAttribute("productName", productName);
        req.setAttribute("price", price);
        req.setAttribute("description", description);
        req.setAttribute("image", image);
        req.setAttribute("status", status);
        req.setAttribute("selectedCategoryId", categoryId);
    }

    private void preserveProductEditForm(HttpServletRequest req, Product originalProduct, String productName, String price, String description, String image, String status, String categoryId) {
        List<Category> categories = categoryService.findAll();
        req.setAttribute("categories", categories);
        originalProduct.setProductName(productName);
        try {
            if (price != null) originalProduct.setPrice(Double.parseDouble(price));
        } catch (Exception ignored) {}
        originalProduct.setDescription(description);
        originalProduct.setImage(image);
        try {
            if (status != null) originalProduct.setStatus(Integer.parseInt(status));
        } catch (Exception ignored) {}
        req.setAttribute("product", originalProduct);
        req.setAttribute("selectedCategoryId", categoryId);
    }
}
