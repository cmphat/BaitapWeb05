package vn.iotstar.controller.admin;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.iotstar.entity.Category;
import vn.iotstar.entity.Product;
import vn.iotstar.service.ICategoryService;
import vn.iotstar.service.IProductService;

@Controller
@RequestMapping("/admin")
public class ProductAdminController {

    @Autowired
    private IProductService productService;

    @Autowired
    private ICategoryService categoryService;

    @GetMapping("/products")
    public String listProducts(Model model) {
        List<Product> products = productService.findAll();
        model.addAttribute("products", products);
        return "product/admin-list";
    }

    @GetMapping("/product/add")
    public String showAddProductForm(Model model) {
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        return "product/add";
    }

    @PostMapping("/product/insert")
    public String insertProduct(
            @RequestParam("productName") String productName,
            @RequestParam("price") String priceStr,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "image", required = false) String image,
            @RequestParam(value = "status", required = false, defaultValue = "1") int status,
            @RequestParam("categoryId") int categoryId,
            Model model) {

        String trimmedName = productName != null ? productName.trim() : "";
        if (trimmedName.isEmpty()) {
            model.addAttribute("alertMsg", "Tên sản phẩm không được để trống.");
            preserveForm(model, trimmedName, priceStr, description, image, status, categoryId);
            return "product/add";
        }

        double price;
        try {
            price = Double.parseDouble(priceStr);
            if (price <= 0) {
                model.addAttribute("alertMsg", "Giá sản phẩm phải lớn hơn 0.");
                preserveForm(model, trimmedName, priceStr, description, image, status, categoryId);
                return "product/add";
            }
        } catch (Exception e) {
            model.addAttribute("alertMsg", "Giá sản phẩm không hợp lệ.");
            preserveForm(model, trimmedName, priceStr, description, image, status, categoryId);
            return "product/add";
        }

        Category category = categoryService.findById(categoryId);
        if (category == null) {
            model.addAttribute("alertMsg", "Danh mục đã chọn không tồn tại.");
            preserveForm(model, trimmedName, priceStr, description, image, status, categoryId);
            return "product/add";
        }

        Product product = new Product();
        product.setProductName(trimmedName);
        product.setPrice(price);
        product.setDescription(description != null ? description.trim() : "");
        product.setImage(image != null ? image.trim() : "");
        product.setStatus(status);
        product.setCategory(category);

        productService.insert(product);
        return "redirect:/admin/products";
    }

    @GetMapping("/product/edit")
    public String showEditProductForm(@RequestParam("id") int id, Model model) {
        Product product = productService.findById(id);
        if (product == null) {
            return "redirect:/admin/products";
        }

        List<Category> categories = categoryService.findAll();
        model.addAttribute("product", product);
        model.addAttribute("categories", categories);
        return "product/edit";
    }

    @PostMapping("/product/update")
    public String updateProduct(
            @RequestParam("productId") int productId,
            @RequestParam("productName") String productName,
            @RequestParam("price") String priceStr,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "image", required = false) String image,
            @RequestParam(value = "status", required = false, defaultValue = "1") int status,
            @RequestParam("categoryId") int categoryId,
            Model model) {

        Product product = productService.findById(productId);
        if (product == null) {
            return "redirect:/admin/products";
        }

        String trimmedName = productName != null ? productName.trim() : "";
        if (trimmedName.isEmpty()) {
            model.addAttribute("alertMsg", "Tên sản phẩm không được để trống.");
            preserveEditForm(model, product, trimmedName, priceStr, description, image, status, categoryId);
            return "product/edit";
        }

        double price;
        try {
            price = Double.parseDouble(priceStr);
            if (price <= 0) {
                model.addAttribute("alertMsg", "Giá sản phẩm phải lớn hơn 0.");
                preserveEditForm(model, product, trimmedName, priceStr, description, image, status, categoryId);
                return "product/edit";
            }
        } catch (Exception e) {
            model.addAttribute("alertMsg", "Giá sản phẩm không hợp lệ.");
            preserveEditForm(model, product, trimmedName, priceStr, description, image, status, categoryId);
            return "product/edit";
        }

        Category category = categoryService.findById(categoryId);
        if (category == null) {
            model.addAttribute("alertMsg", "Danh mục đã chọn không tồn tại.");
            preserveEditForm(model, product, trimmedName, priceStr, description, image, status, categoryId);
            return "product/edit";
        }

        product.setProductName(trimmedName);
        product.setPrice(price);
        product.setDescription(description != null ? description.trim() : "");
        product.setImage(image != null ? image.trim() : "");
        product.setStatus(status);
        product.setCategory(category);

        productService.update(product);
        return "redirect:/admin/products";
    }

    @GetMapping("/product/delete")
    public String deleteProduct(@RequestParam("id") int id) {
        productService.delete(id);
        return "redirect:/admin/products";
    }

    private void preserveForm(Model model, String productName, String price, String description, String image, int status, int categoryId) {
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("productName", productName);
        model.addAttribute("price", price);
        model.addAttribute("description", description);
        model.addAttribute("image", image);
        model.addAttribute("status", status);
        model.addAttribute("selectedCategoryId", String.valueOf(categoryId));
    }

    private void preserveEditForm(Model model, Product product, String productName, String price, String description, String image, int status, int categoryId) {
        model.addAttribute("categories", categoryService.findAll());
        product.setProductName(productName);
        try {
            if (price != null) product.setPrice(Double.parseDouble(price));
        } catch (Exception ignored) {}
        product.setDescription(description);
        product.setImage(image);
        product.setStatus(status);
        model.addAttribute("product", product);
        model.addAttribute("selectedCategoryId", String.valueOf(categoryId));
    }
}
