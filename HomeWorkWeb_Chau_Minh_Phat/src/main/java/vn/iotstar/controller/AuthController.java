package vn.iotstar.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.iotstar.model.User;
import vn.iotstar.service.UserService;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String showLoginForm(
            @RequestParam(name = "activated", required = false) String activated,
            @RequestParam(name = "reset", required = false) String reset,
            @RequestParam(name = "error", required = false) String error,
            HttpServletRequest request,
            HttpSession session,
            Model model) {

        if (session != null && session.getAttribute("account") != null) {
            User current = (User) session.getAttribute("account");
            if (current.getRoleid() == 1) {
                return "redirect:/admin";
            }
            return "redirect:/home";
        }

        if ("1".equals(activated)) {
            model.addAttribute("successMsg", "Tài khoản đã được kích hoạt thành công. Bạn có thể đăng nhập.");
        } else if ("1".equals(reset)) {
            model.addAttribute("successMsg", "Đổi mật khẩu thành công. Vui lòng đăng nhập bằng mật khẩu mới.");
        } else if ("unauthorized".equals(error)) {
            model.addAttribute("alert", "Vui lòng đăng nhập bằng tài khoản Quản trị viên (Admin) để tiếp tục.");
        }

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if ("username".equals(c.getName())) {
                    model.addAttribute("rememberedUsername", c.getValue());
                }
            }
        }

        return "login";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam(name = "remember", required = false) String remember,
            HttpServletRequest request,
            HttpServletResponse response,
            HttpSession session,
            Model model) {

        String trimmedUsername = username != null ? username.trim() : "";
        String trimmedPassword = password != null ? password.trim() : "";

        if (trimmedUsername.isEmpty() || trimmedPassword.isEmpty()) {
            model.addAttribute("alert", "Tài khoản hoặc mật khẩu không được rỗng.");
            model.addAttribute("username", trimmedUsername);
            return "login";
        }

        User user = userService.login(trimmedUsername, trimmedPassword);
        if (user == null) {
            model.addAttribute("alert", "Tài khoản hoặc mật khẩu không đúng.");
            model.addAttribute("username", trimmedUsername);
            return "login";
        }

        if (!user.isActive()) {
            model.addAttribute("alert", "Tài khoản chưa được kích hoạt hoặc đã bị khóa. Vui lòng liên hệ quản trị viên.");
            model.addAttribute("username", trimmedUsername);
            return "login";
        }

        session.setAttribute("account", user);

        if ("on".equals(remember)) {
            Cookie cookie = new Cookie("username", trimmedUsername);
            cookie.setMaxAge(30 * 60);
            cookie.setPath(request.getContextPath().isEmpty() ? "/" : request.getContextPath());
            response.addCookie(cookie);
        }

        if (user.getRoleid() == 1) {
            return "redirect:/admin";
        }
        return "redirect:/home";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/login";
    }
}
