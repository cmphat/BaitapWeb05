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
public class ForgotPasswordController {

    @Autowired
    private UserService userService;

    @GetMapping("/forgot-password")
    public String showForgotPasswordForm() {
        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public String sendResetOtp(
            @RequestParam("email") String email,
            HttpSession session,
            Model model) {

        String trimmedEmail = email != null ? email.trim() : "";
        model.addAttribute("email", trimmedEmail);

        if (trimmedEmail.isEmpty()) {
            model.addAttribute("alertMsg", "Vui lòng nhập địa chỉ email.");
            return "forgot-password";
        }

        if (!trimmedEmail.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            model.addAttribute("alertMsg", "Email không đúng định dạng.");
            return "forgot-password";
        }

        User user = userService.findByEmail(trimmedEmail);
        if (user == null) {
            model.addAttribute("alertMsg", "Không tìm thấy tài khoản với email này.");
            return "forgot-password";
        }

        String otp = OtpUtil.generateOtp();
        user.setOtp(otp);
        user.setOtpExpiry(LocalDateTime.now().plusMinutes(5));
        userService.update(user);

        boolean sent = EmailUtil.sendOtp(trimmedEmail, otp, "RESET_PASSWORD");
        if (sent) {
            session.setAttribute("resetEmail", trimmedEmail);
            return "redirect:/forgot-password/verify";
        } else {
            model.addAttribute("alertMsg", "Gửi email OTP thất bại. Vui lòng kiểm tra cấu hình mail.");
            return "forgot-password";
        }
    }

    @GetMapping("/forgot-password/verify")
    public String showVerifyResetOtpForm(HttpSession session) {
        if (session == null || session.getAttribute("resetEmail") == null) {
            return "redirect:/forgot-password";
        }
        return "forgot-password-verify";
    }

    @PostMapping("/forgot-password/verify")
    public String verifyResetOtp(
            @RequestParam("otp") String inputOtp,
            HttpSession session,
            Model model) {

        if (session == null || session.getAttribute("resetEmail") == null) {
            return "redirect:/forgot-password";
        }

        String email = (String) session.getAttribute("resetEmail");
        String trimmedOtp = inputOtp != null ? inputOtp.trim() : "";

        if (trimmedOtp.isEmpty() || !trimmedOtp.matches("^\\d{6}$")) {
            model.addAttribute("alertMsg", "Mã OTP phải gồm đúng 6 chữ số.");
            return "forgot-password-verify";
        }

        User user = userService.findByEmail(email);
        if (user == null || user.getOtp() == null || !user.getOtp().equals(trimmedOtp)) {
            model.addAttribute("alertMsg", "Mã OTP không đúng.");
            return "forgot-password-verify";
        }

        if (user.getOtpExpiry() == null || LocalDateTime.now().isAfter(user.getOtpExpiry())) {
            model.addAttribute("alertMsg", "Mã OTP đã hết hạn.");
            return "forgot-password-verify";
        }

        session.setAttribute("RESET_VERIFIED", true);
        return "redirect:/reset-password";
    }

    @GetMapping("/reset-password")
    public String showResetPasswordForm(HttpSession session) {
        if (session == null || !Boolean.TRUE.equals(session.getAttribute("RESET_VERIFIED"))) {
            return "redirect:/forgot-password";
        }
        return "reset-password";
    }

    @PostMapping("/reset-password")
    public String resetPassword(
            @RequestParam("newPassword") String newPassword,
            @RequestParam("confirmPassword") String confirmPassword,
            HttpSession session,
            Model model) {

        if (session == null || !Boolean.TRUE.equals(session.getAttribute("RESET_VERIFIED"))) {
            return "redirect:/forgot-password";
        }

        String email = (String) session.getAttribute("resetEmail");

        if (newPassword == null || confirmPassword == null || newPassword.trim().isEmpty()) {
            model.addAttribute("alertMsg", "Vui lòng nhập đầy đủ mật khẩu mới và xác nhận mật khẩu.");
            return "reset-password";
        }

        String trimmedNewPassword = newPassword.trim();
        if (trimmedNewPassword.length() < 6) {
            model.addAttribute("alertMsg", "Mật khẩu mới phải có ít nhất 6 ký tự.");
            return "reset-password";
        }

        if (!trimmedNewPassword.equals(confirmPassword.trim())) {
            model.addAttribute("alertMsg", "Mật khẩu xác nhận không khớp.");
            return "reset-password";
        }

        User user = userService.findByEmail(email);
        if (user != null) {
            user.setPassword(trimmedNewPassword);
            user.setOtp(null);
            user.setOtpExpiry(null);
            userService.update(user);
        }

        session.removeAttribute("RESET_VERIFIED");
        session.removeAttribute("resetEmail");

        return "redirect:/login?reset=1";
    }
}
