package vn.iotstar.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.iotstar.entity.Product;
import vn.iotstar.service.IProductService;

@Controller
public class ProductPublicController {

    @Autowired
    private IProductService productService;

    @GetMapping("/product")
    public String listProducts(
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            Model model) {

        int pageSize = 6;
        if (page < 1) page = 1;

        long totalItems = productService.count();
        int totalPages = (int) Math.ceil(totalItems / (double) pageSize);

        if (totalPages > 0 && page > totalPages) {
            page = totalPages;
        }

        List<Product> products = productService.findAll(page, pageSize);

        model.addAttribute("products", products);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", totalItems);

        return "product/list";
    }

    @GetMapping("/product/detail")
    public String productDetail(@RequestParam(name = "id", required = false) Integer id, Model model) {
        if (id == null) {
            return "redirect:/product";
        }

        Product product = productService.findById(id);
        if (product == null) {
            return "redirect:/product";
        }

        model.addAttribute("product", product);
        return "product/detail";
    }
}
