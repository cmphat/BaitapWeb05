package vn.iotstar.controller.admin;

import java.util.regex.Pattern;
import jakarta.servlet.http.HttpSession;
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

import vn.iotstar.model.User;
import vn.iotstar.service.UserService;

@Controller
@RequestMapping("/admin/users")
public class AdminUserController {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\d{10,11}$");

    @Autowired
    private UserService userService;

    @GetMapping
    public String listUsers(
            @RequestParam(name = "keyword", required = false, defaultValue = "") String keyword,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "5") int size,
            Model model) {

        if (page < 0) page = 0;
        if (size <= 0) size = 5;

        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        String cleanKeyword = keyword != null ? keyword.trim() : "";

        Page<User> userPage;
        if (!cleanKeyword.isEmpty()) {
            userPage = userService.searchUsers(cleanKeyword, pageable);
        } else {
            userPage = userService.findAll(pageable);
        }

        model.addAttribute("userPage", userPage);
        model.addAttribute("users", userPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", userPage.getTotalPages());
        model.addAttribute("totalElements", userPage.getTotalElements());
        model.addAttribute("keyword", cleanKeyword);
        model.addAttribute("pageSize", size);

        return "admin/user/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        User user = new User();
        user.setRoleid(3);
        user.setActive(true);
        model.addAttribute("user", user);
        model.addAttribute("isEdit", false);
        return "admin/user/form";
    }

    @PostMapping("/create")
    public String createUser(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam(value = "fullname", required = false) String fullname,
            @RequestParam("email") String email,
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "roleid", required = false, defaultValue = "3") int roleid,
            @RequestParam(value = "active", required = false, defaultValue = "false") boolean active,
            @RequestParam(value = "images", required = false) String images,
            Model model,
            RedirectAttributes redirectAttributes) {

        String trimmedUsername = username != null ? username.trim() : "";
        String trimmedPassword = password != null ? password.trim() : "";
        String trimmedEmail = email != null ? email.trim() : "";
        String trimmedFullname = fullname != null ? fullname.trim() : "";
        String trimmedPhone = phone != null ? phone.trim() : "";
        String trimmedImages = images != null ? images.trim() : "";

        User formUser = new User();
        formUser.setUsername(trimmedUsername);
        formUser.setFullname(trimmedFullname);
        formUser.setEmail(trimmedEmail);
        formUser.setPhone(trimmedPhone);
        formUser.setRoleid(roleid);
        formUser.setActive(active);
        formUser.setImages(trimmedImages);

        // Validation
        if (trimmedUsername.isEmpty() || trimmedUsername.length() < 3) {
            model.addAttribute("errorMessage", "Tên đăng nhập phải từ 3 ký tự trở lên và không được để trống.");
            model.addAttribute("user", formUser);
            model.addAttribute("isEdit", false);
            return "admin/user/form";
        }

        if (trimmedPassword.isEmpty() || trimmedPassword.length() < 6) {
            model.addAttribute("errorMessage", "Mật khẩu phải từ 6 ký tự trở lên.");
            model.addAttribute("user", formUser);
            model.addAttribute("isEdit", false);
            return "admin/user/form";
        }

        if (trimmedEmail.isEmpty() || !EMAIL_PATTERN.matcher(trimmedEmail).matches()) {
            model.addAttribute("errorMessage", "Email không hợp lệ hoặc để trống.");
            model.addAttribute("user", formUser);
            model.addAttribute("isEdit", false);
            return "admin/user/form";
        }

        if (!trimmedPhone.isEmpty() && !PHONE_PATTERN.matcher(trimmedPhone).matches()) {
            model.addAttribute("errorMessage", "Số điện thoại phải gồm 10 đến 11 chữ số.");
            model.addAttribute("user", formUser);
            model.addAttribute("isEdit", false);
            return "admin/user/form";
        }

        if (userService.existsByUsername(trimmedUsername)) {
            model.addAttribute("errorMessage", "Tên đăng nhập '" + trimmedUsername + "' đã tồn tại trên hệ thống.");
            model.addAttribute("user", formUser);
            model.addAttribute("isEdit", false);
            return "admin/user/form";
        }

        if (userService.existsByEmail(trimmedEmail)) {
            model.addAttribute("errorMessage", "Email '" + trimmedEmail + "' đã được sử dụng bởi tài khoản khác.");
            model.addAttribute("user", formUser);
            model.addAttribute("isEdit", false);
            return "admin/user/form";
        }

        formUser.setPassword(trimmedPassword);
        userService.insert(formUser);

        redirectAttributes.addFlashAttribute("successMessage", "Thêm người dùng mới '" + trimmedUsername + "' thành công!");
        return "redirect:/admin/users";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") int id, Model model, RedirectAttributes redirectAttributes) {
        User user = userService.findById(id);
        if (user == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy người dùng có mã #" + id);
            return "redirect:/admin/users";
        }

        model.addAttribute("user", user);
        model.addAttribute("isEdit", true);
        return "admin/user/form";
    }

    @PostMapping("/edit/{id}")
    public String updateUser(
            @PathVariable("id") int id,
            @RequestParam(value = "fullname", required = false) String fullname,
            @RequestParam("email") String email,
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "roleid", required = false, defaultValue = "3") int roleid,
            @RequestParam(value = "active", required = false, defaultValue = "false") boolean active,
            @RequestParam(value = "images", required = false) String images,
            @RequestParam(value = "newPassword", required = false) String newPassword,
            HttpSession session,
            Model model,
            RedirectAttributes redirectAttributes) {

        User existing = userService.findById(id);
        if (existing == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy người dùng để cập nhật.");
            return "redirect:/admin/users";
        }

        String trimmedEmail = email != null ? email.trim() : "";
        String trimmedFullname = fullname != null ? fullname.trim() : "";
        String trimmedPhone = phone != null ? phone.trim() : "";
        String trimmedImages = images != null ? images.trim() : "";
        String trimmedNewPassword = newPassword != null ? newPassword.trim() : "";

        existing.setFullname(trimmedFullname);
        existing.setEmail(trimmedEmail);
        existing.setPhone(trimmedPhone);
        existing.setRoleid(roleid);
        existing.setActive(active);
        existing.setImages(trimmedImages);

        // Validation
        if (trimmedEmail.isEmpty() || !EMAIL_PATTERN.matcher(trimmedEmail).matches()) {
            model.addAttribute("errorMessage", "Email không hợp lệ hoặc để trống.");
            model.addAttribute("user", existing);
            model.addAttribute("isEdit", true);
            return "admin/user/form";
        }

        if (!trimmedPhone.isEmpty() && !PHONE_PATTERN.matcher(trimmedPhone).matches()) {
            model.addAttribute("errorMessage", "Số điện thoại phải gồm 10 đến 11 chữ số.");
            model.addAttribute("user", existing);
            model.addAttribute("isEdit", true);
            return "admin/user/form";
        }

        if (userService.existsByEmailExceptId(trimmedEmail, id)) {
            model.addAttribute("errorMessage", "Email '" + trimmedEmail + "' đã được sử dụng bởi tài khoản khác.");
            model.addAttribute("user", existing);
            model.addAttribute("isEdit", true);
            return "admin/user/form";
        }

        if (!trimmedNewPassword.isEmpty()) {
            if (trimmedNewPassword.length() < 6) {
                model.addAttribute("errorMessage", "Mật khẩu mới phải từ 6 ký tự trở lên.");
                model.addAttribute("user", existing);
                model.addAttribute("isEdit", true);
                return "admin/user/form";
            }
            existing.setPassword(trimmedNewPassword);
        }

        userService.update(existing);

        // If editing current logged in user, refresh session
        User sessionUser = (session != null) ? (User) session.getAttribute("account") : null;
        if (sessionUser != null && sessionUser.getId() == id) {
            session.setAttribute("account", existing);
        }

        redirectAttributes.addFlashAttribute("successMessage", "Cập nhật thông tin người dùng '" + existing.getUsername() + "' thành công!");
        return "redirect:/admin/users";
    }

    @PostMapping("/delete/{id}")
    public String deleteUserPost(@PathVariable("id") int id, HttpSession session, RedirectAttributes redirectAttributes) {
        return processDelete(id, session, redirectAttributes);
    }

    @GetMapping("/delete/{id}")
    public String deleteUserGet(@PathVariable("id") int id, HttpSession session, RedirectAttributes redirectAttributes) {
        return processDelete(id, session, redirectAttributes);
    }

    private String processDelete(int id, HttpSession session, RedirectAttributes redirectAttributes) {
        User targetUser = userService.findById(id);
        if (targetUser == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Người dùng không tồn tại.");
            return "redirect:/admin/users";
        }

        User currentAdmin = (session != null) ? (User) session.getAttribute("account") : null;
        if (currentAdmin != null && currentAdmin.getId() == id) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Bạn không thể tự xóa tài khoản quản trị đang đăng nhập của chính mình!");
            return "redirect:/admin/users";
        }

        userService.delete(id);
        redirectAttributes.addFlashAttribute("successMessage", "Xóa người dùng '" + targetUser.getUsername() + "' thành công!");
        return "redirect:/admin/users";
    }
}
