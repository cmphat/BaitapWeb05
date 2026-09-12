package vn.iotstar.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.iotstar.entity.Category;
import vn.iotstar.service.ICategoryService;

@Controller
@RequestMapping("/admin/categories")
public class AdminCategoryController {

    @Autowired
    private ICategoryService categoryService;

    @GetMapping
    public String listCategories(
            @RequestParam(name = "keyword", required = false, defaultValue = "") String keyword,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "5") int size,
            Model model) {

        if (page < 0) {
            page = 0;
        }
        if (size <= 0) {
            size = 5;
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by("categoryid").descending());
        Page<Category> categoryPage;

        String cleanKeyword = keyword != null ? keyword.trim() : "";
        if (!cleanKeyword.isEmpty()) {
            categoryPage = categoryService.searchByName(cleanKeyword, pageable);
        } else {
            categoryPage = categoryService.findAll(pageable);
        }

        model.addAttribute("categoryPage", categoryPage);
        model.addAttribute("categories", categoryPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", categoryPage.getTotalPages());
        model.addAttribute("totalElements", categoryPage.getTotalElements());
        model.addAttribute("keyword", cleanKeyword);
        model.addAttribute("pageSize", size);

        return "admin/category/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        Category category = new Category();
        category.setStatus(1);
        model.addAttribute("category", category);
        model.addAttribute("isEdit", false);
        return "admin/category/form";
    }

    @PostMapping("/create")
    public String createCategory(
            @RequestParam("categoryname") String categoryname,
            @RequestParam(value = "images", required = false) String images,
            @RequestParam(value = "status", required = false, defaultValue = "1") int status,
            Model model,
            RedirectAttributes redirectAttributes) {

        String trimmedName = categoryname != null ? categoryname.trim() : "";
        String trimmedImages = images != null ? images.trim() : "";

        if (trimmedName.isEmpty()) {
            model.addAttribute("errorMessage", "Tên danh mục không được để trống.");
            Category category = new Category(trimmedName, trimmedImages, status);
            model.addAttribute("category", category);
            model.addAttribute("isEdit", false);
            return "admin/category/form";
        }

        if (categoryService.existsByName(trimmedName)) {
            model.addAttribute("errorMessage", "Tên danh mục đã tồn tại trên hệ thống.");
            Category category = new Category(trimmedName, trimmedImages, status);
            model.addAttribute("category", category);
            model.addAttribute("isEdit", false);
            return "admin/category/form";
        }

        Category newCategory = new Category();
        newCategory.setCategoryname(trimmedName);
        newCategory.setImages(trimmedImages);
        newCategory.setStatus(status);

        categoryService.insert(newCategory);
        redirectAttributes.addFlashAttribute("successMessage", "Thêm danh mục mới thành công!");
        return "redirect:/admin/categories";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") int id, Model model, RedirectAttributes redirectAttributes) {
        Category category = categoryService.findById(id);
        if (category == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy danh mục có mã: " + id);
            return "redirect:/admin/categories";
        }

        model.addAttribute("category", category);
        model.addAttribute("isEdit", true);
        return "admin/category/form";
    }

    @PostMapping("/edit/{id}")
    public String updateCategory(
            @PathVariable("id") int id,
            @RequestParam("categoryname") String categoryname,
            @RequestParam(value = "images", required = false) String images,
            @RequestParam(value = "status", required = false, defaultValue = "1") int status,
            Model model,
            RedirectAttributes redirectAttributes) {

        Category existing = categoryService.findById(id);
        if (existing == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy danh mục để cập nhật.");
            return "redirect:/admin/categories";
        }

        String trimmedName = categoryname != null ? categoryname.trim() : "";
        String trimmedImages = images != null ? images.trim() : "";

        if (trimmedName.isEmpty()) {
            model.addAttribute("errorMessage", "Tên danh mục không được để trống.");
            existing.setCategoryname(trimmedName);
            existing.setImages(trimmedImages);
            existing.setStatus(status);
            model.addAttribute("category", existing);
            model.addAttribute("isEdit", true);
            return "admin/category/form";
        }

        if (categoryService.existsByNameExceptId(trimmedName, id)) {
            model.addAttribute("errorMessage", "Tên danh mục đã trùng với một danh mục khác.");
            existing.setCategoryname(trimmedName);
            existing.setImages(trimmedImages);
            existing.setStatus(status);
            model.addAttribute("category", existing);
            model.addAttribute("isEdit", true);
            return "admin/category/form";
        }

        existing.setCategoryname(trimmedName);
        existing.setImages(trimmedImages);
        existing.setStatus(status);

        categoryService.update(existing);
        redirectAttributes.addFlashAttribute("successMessage", "Cập nhật danh mục thành công!");
        return "redirect:/admin/categories";
    }

    @PostMapping("/delete/{id}")
    public String deleteCategoryPost(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        return processDelete(id, redirectAttributes);
    }

    @GetMapping("/delete/{id}")
    public String deleteCategoryGet(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        return processDelete(id, redirectAttributes);
    }

    private String processDelete(int id, RedirectAttributes redirectAttributes) {
        Category category = categoryService.findById(id);
        if (category == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Danh mục không tồn tại.");
            return "redirect:/admin/categories";
        }

        long productCount = categoryService.countProductsByCategory(id);
        if (productCount > 0) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Không thể xóa danh mục '" + category.getCategoryname() + "' vì đang có " + productCount + " sản phẩm thuộc danh mục này.");
            return "redirect:/admin/categories";
        }

        categoryService.delete(id);
        redirectAttributes.addFlashAttribute("successMessage", "Xóa danh mục '" + category.getCategoryname() + "' thành công!");
        return "redirect:/admin/categories";
    }
}
