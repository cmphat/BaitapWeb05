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

@Controller
public class VerifyOtpController {

    @Autowired
    private UserService userService;

    @GetMapping("/verify-otp")
    public String showVerifyOtpForm(HttpSession session) {
        String email = (session != null) ? (String) session.getAttribute("verifyEmail") : null;
        if (email == null) {
            return "redirect:/register";
        }
        return "verify-otp";
    }

    @PostMapping("/verify-otp")
    public String verifyOtp(
            @RequestParam("otp") String inputOtp,
            HttpSession session,
            Model model) {

        String email = (session != null) ? (String) session.getAttribute("verifyEmail") : null;
        if (email == null) {
            return "redirect:/register";
        }

        String trimmedOtp = inputOtp != null ? inputOtp.trim() : "";
        if (trimmedOtp.isEmpty() || !trimmedOtp.matches("^\\d{6}$")) {
            model.addAttribute("alertMsg", "Mã OTP phải gồm đúng 6 chữ số.");
            return "verify-otp";
        }

        User user = userService.findByEmail(email);
        if (user == null || user.isActive()) {
            return "redirect:/login";
        }

        if (user.getOtp() == null || !user.getOtp().equals(trimmedOtp)) {
            model.addAttribute("alertMsg", "Mã OTP không đúng.");
            return "verify-otp";
        }

        if (user.getOtpExpiry() == null || LocalDateTime.now().isAfter(user.getOtpExpiry())) {
            model.addAttribute("alertMsg", "Mã OTP đã hết hạn.");
            return "verify-otp";
        }

        user.setActive(true);
        user.setOtp(null);
        user.setOtpExpiry(null);

        userService.update(user);
        session.removeAttribute("verifyEmail");

        return "redirect:/login?activated=1";
    }
}
