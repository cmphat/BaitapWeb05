package vn.iotstar;

import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import vn.iotstar.controller.admin.AdminCategoryController;
import vn.iotstar.entity.Category;
import vn.iotstar.service.ICategoryService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdminCategoryControllerTest {

    @Mock
    private ICategoryService categoryService;

    @InjectMocks
    private AdminCategoryController controller;

    private Category sampleCategory;

    @BeforeEach
    public void setup() {
        sampleCategory = new Category("Máy ảnh", "camera.jpg", 1);
        sampleCategory.setCategoryid(1);
    }

    @Test
    public void testListCategoriesWithoutKeyword() {
        Page<Category> page = new PageImpl<>(Collections.singletonList(sampleCategory));
        when(categoryService.findAll(any(Pageable.class))).thenReturn(page);

        Model model = new ConcurrentModel();
        String view = controller.listCategories("", 0, 5, model);

        assertEquals("admin/category/list", view);
        assertNotNull(model.getAttribute("categories"));
        assertEquals(0, model.getAttribute("currentPage"));
    }

    @Test
    public void testListCategoriesWithKeyword() {
        Page<Category> page = new PageImpl<>(Collections.singletonList(sampleCategory));
        when(categoryService.searchByName(eq("máy"), any(Pageable.class))).thenReturn(page);

        Model model = new ConcurrentModel();
        String view = controller.listCategories("máy", 0, 5, model);

        assertEquals("admin/category/list", view);
        assertEquals("máy", model.getAttribute("keyword"));
    }

    @Test
    public void testCreateCategorySuccess() {
        when(categoryService.existsByName("Ống kính")).thenReturn(false);

        RedirectAttributesModelMap redirectAttributes = new RedirectAttributesModelMap();
        Model model = new ConcurrentModel();
        String view = controller.createCategory("Ống kính", "lens.jpg", 1, model, redirectAttributes);

        assertEquals("redirect:/admin/categories", view);
        verify(categoryService, times(1)).insert(any(Category.class));
        assertNotNull(redirectAttributes.getFlashAttributes().get("successMessage"));
    }

    @Test
    public void testCreateCategoryEmptyNameFails() {
        Model model = new ConcurrentModel();
        RedirectAttributesModelMap redirectAttributes = new RedirectAttributesModelMap();

        String view = controller.createCategory("   ", "lens.jpg", 1, model, redirectAttributes);

        assertEquals("admin/category/form", view);
        assertNotNull(model.getAttribute("errorMessage"));
        verify(categoryService, never()).insert(any(Category.class));
    }

    @Test
    public void testDeleteCategoryPreventedWhenHasProducts() {
        when(categoryService.findById(1)).thenReturn(sampleCategory);
        when(categoryService.countProductsByCategory(1)).thenReturn(5L);

        RedirectAttributesModelMap redirectAttributes = new RedirectAttributesModelMap();
        String view = controller.deleteCategoryPost(1, redirectAttributes);

        assertEquals("redirect:/admin/categories", view);
        assertNotNull(redirectAttributes.getFlashAttributes().get("errorMessage"));
        verify(categoryService, never()).delete(anyInt());
    }

    @Test
    public void testDeleteCategorySuccessWhenNoProducts() {
        when(categoryService.findById(1)).thenReturn(sampleCategory);
        when(categoryService.countProductsByCategory(1)).thenReturn(0L);

        RedirectAttributesModelMap redirectAttributes = new RedirectAttributesModelMap();
        String view = controller.deleteCategoryPost(1, redirectAttributes);

        assertEquals("redirect:/admin/categories", view);
        assertNotNull(redirectAttributes.getFlashAttributes().get("successMessage"));
        verify(categoryService, times(1)).delete(1);
    }
}
