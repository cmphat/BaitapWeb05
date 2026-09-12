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
import org.springframework.mock.web.MockHttpSession;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import vn.iotstar.controller.admin.AdminUserController;
import vn.iotstar.model.User;
import vn.iotstar.service.UserService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdminUserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private AdminUserController controller;

    private User sampleUser;
    private User adminUser;

    @BeforeEach
    public void setup() {
        sampleUser = new User(2, "john_doe", "password123", "John Doe");
        sampleUser.setEmail("john@example.com");
        sampleUser.setRoleid(3);
        sampleUser.setActive(true);

        adminUser = new User(1, "admin", "123456", "Administrator");
        adminUser.setEmail("admin@example.com");
        adminUser.setRoleid(1);
        adminUser.setActive(true);
    }

    @Test
    public void testListUsersWithoutKeyword() {
        Page<User> page = new PageImpl<>(Collections.singletonList(sampleUser));
        when(userService.findAll(any(Pageable.class))).thenReturn(page);

        Model model = new ConcurrentModel();
        String view = controller.listUsers("", 0, 5, model);

        assertEquals("admin/user/list", view);
        assertNotNull(model.getAttribute("users"));
        assertEquals(0, model.getAttribute("currentPage"));
    }

    @Test
    public void testListUsersWithKeyword() {
        Page<User> page = new PageImpl<>(Collections.singletonList(sampleUser));
        when(userService.searchUsers(eq("john"), any(Pageable.class))).thenReturn(page);

        Model model = new ConcurrentModel();
        String view = controller.listUsers("john", 0, 5, model);

        assertEquals("admin/user/list", view);
        assertEquals("john", model.getAttribute("keyword"));
    }

    @Test
    public void testCreateUserSuccess() {
        when(userService.existsByUsername("newuser")).thenReturn(false);
        when(userService.existsByEmail("newuser@example.com")).thenReturn(false);

        Model model = new ConcurrentModel();
        RedirectAttributesModelMap redirectAttributes = new RedirectAttributesModelMap();

        String view = controller.createUser("newuser", "securePass123", "New User",
                "newuser@example.com", "0901234567", 3, true, "", model, redirectAttributes);

        assertEquals("redirect:/admin/users", view);
        verify(userService, times(1)).insert(any(User.class));
        assertNotNull(redirectAttributes.getFlashAttributes().get("successMessage"));
    }

    @Test
    public void testCreateUserShortPasswordFails() {
        Model model = new ConcurrentModel();
        RedirectAttributesModelMap redirectAttributes = new RedirectAttributesModelMap();

        String view = controller.createUser("newuser", "123", "New User",
                "newuser@example.com", "0901234567", 3, true, "", model, redirectAttributes);

        assertEquals("admin/user/form", view);
        assertNotNull(model.getAttribute("errorMessage"));
        verify(userService, never()).insert(any(User.class));
    }

    @Test
    public void testCreateUserDuplicateUsernameFails() {
        when(userService.existsByUsername("existingUser")).thenReturn(true);

        Model model = new ConcurrentModel();
        RedirectAttributesModelMap redirectAttributes = new RedirectAttributesModelMap();

        String view = controller.createUser("existingUser", "securePass123", "User",
                "test@example.com", "0901234567", 3, true, "", model, redirectAttributes);

        assertEquals("admin/user/form", view);
        assertTrue(model.getAttribute("errorMessage").toString().contains("đã tồn tại"));
        verify(userService, never()).insert(any(User.class));
    }

    @Test
    public void testSelfDeletePrevented() {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("account", adminUser);

        when(userService.findById(1)).thenReturn(adminUser);

        RedirectAttributesModelMap redirectAttributes = new RedirectAttributesModelMap();
        String view = controller.deleteUserPost(1, session, redirectAttributes);

        assertEquals("redirect:/admin/users", view);
        assertTrue(redirectAttributes.getFlashAttributes().get("errorMessage").toString().contains("tự xóa tài khoản"));
        verify(userService, never()).delete(anyInt());
    }

    @Test
    public void testDeleteOtherUserSuccess() {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("account", adminUser);

        when(userService.findById(2)).thenReturn(sampleUser);

        RedirectAttributesModelMap redirectAttributes = new RedirectAttributesModelMap();
        String view = controller.deleteUserPost(2, session, redirectAttributes);

        assertEquals("redirect:/admin/users", view);
        assertNotNull(redirectAttributes.getFlashAttributes().get("successMessage"));
        verify(userService, times(1)).delete(2);
    }
}
