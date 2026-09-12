package vn.iotstar.controller;

import java.util.List;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import vn.iotstar.entity.Product;
import vn.iotstar.service.IProductService;

@Controller
public class HomeController {

    @Autowired
    private IProductService productService;

    @GetMapping({"/", "/home"})
    public String home(HttpSession session, Model model) {
        if (session == null || session.getAttribute("account") == null) {
            return "redirect:/login";
        }

        List<Product> latestProducts = productService.findLatest(10);
        model.addAttribute("latestProducts", latestProducts);

        return "home";
    }
}
