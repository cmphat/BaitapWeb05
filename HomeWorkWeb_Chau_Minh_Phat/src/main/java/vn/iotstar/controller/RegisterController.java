package vn.iotstar.controller;

import java.time.LocalDateTime;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.iotstar.model.User;
import vn.iotstar.service.UserService;
import vn.iotstar.util.EmailUtil;
import vn.iotstar.util.OtpUtil;

@Controller
public class RegisterController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegisterForm() {
        return "register";
    }

    @PostMapping("/register")
    public String register(
            @RequestParam("username") String username,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("fullname") String fullname,
            @RequestParam(value = "phone", required = false) String phone,
            HttpSession session,
            Model model) {

        model.addAttribute("username", username);
        model.addAttribute("email", email);
        model.addAttribute("fullname", fullname);
        model.addAttribute("phone", phone);

        if (username == null || username.trim().isEmpty() ||
            email == null || email.trim().isEmpty() ||
            password == null || password.trim().isEmpty() ||
            fullname == null || fullname.trim().isEmpty()) {
            model.addAttribute("alertMsg", "Vui lòng nhập đầy đủ các thông tin bắt buộc (*).");
            return "register";
        }

        String trimmedUsername = username.trim();
        String trimmedEmail = email.trim();
        String trimmedFullname = fullname.trim();
        String trimmedPhone = (phone != null) ? phone.trim() : "";

        if (trimmedUsername.length() < 3) {
            model.addAttribute("alertMsg", "Tên đăng nhập phải có ít nhất 3 ký tự.");
            return "register";
        }

        if (!trimmedEmail.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            model.addAttribute("alertMsg", "Email không đúng định dạng.");
            return "register";
        }

        if (password.length() < 6) {
            model.addAttribute("alertMsg", "Mật khẩu phải có ít nhất 6 ký tự.");
            return "register";
        }

        if (!trimmedPhone.isEmpty() && !trimmedPhone.matches("^0[0-9]{9,10}$")) {
            model.addAttribute("alertMsg", "Số điện thoại không đúng định dạng (bắt đầu bằng 0 và gồm 10-11 số).");
            return "register";
        }

        if (userService.existsByUsername(trimmedUsername)) {
            model.addAttribute("alertMsg", "Tên đăng nhập đã tồn tại.");
            return "register";
        }

        if (userService.existsByEmail(trimmedEmail)) {
            model.addAttribute("alertMsg", "Email đã tồn tại.");
            return "register";
        }

        String otp = OtpUtil.generateOtp();
        LocalDateTime otpExpiry = LocalDateTime.now().plusMinutes(5);

        User user = new User();
        user.setUsername(trimmedUsername);
        user.setEmail(trimmedEmail);
        user.setPassword(password);
        user.setFullname(trimmedFullname);
        user.setPhone(trimmedPhone);
        user.setRoleid(3); // Default user role
        user.setActive(false);
        user.setOtp(otp);
        user.setOtpExpiry(otpExpiry);

        userService.insert(user);

        boolean mailSent = EmailUtil.sendOtp(trimmedEmail, otp, "ACTIVATE");
        if (mailSent) {
            session.setAttribute("verifyEmail", trimmedEmail);
            return "redirect:/verify-otp";
        } else {
            model.addAttribute("alertMsg", "Đăng ký thành công nhưng gửi email OTP thất bại. Vui lòng kiểm tra cấu hình mail.");
            return "register";
        }
    }
}
