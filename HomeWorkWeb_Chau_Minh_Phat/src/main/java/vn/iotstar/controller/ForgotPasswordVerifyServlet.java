package vn.iotstar.controller;

import java.io.IOException;
import java.time.LocalDateTime;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import vn.iotstar.model.User;
import vn.iotstar.service.UserService;
import vn.iotstar.service.impl.UserServiceImpl;

@WebServlet(urlPatterns = {"/forgot-password/verify"})
public class ForgotPasswordVerifyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (session.getAttribute("resetEmail") == null) {
            resp.sendRedirect(req.getContextPath() + "/forgot-password");
            return;
        }
        req.getRequestDispatcher("/views/forgot-password-verify.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();
        String email = (String) session.getAttribute("resetEmail");

        if (email == null) {
            resp.sendRedirect(req.getContextPath() + "/forgot-password");
            return;
        }

        String inputOtp = req.getParameter("otp");
        if (inputOtp == null || inputOtp.isBlank() || !inputOtp.trim().matches("^\\d{6}$")) {
            req.setAttribute("alertMsg", "Mã OTP phải gồm đúng 6 chữ số.");
            req.getRequestDispatcher("/views/forgot-password-verify.jsp").forward(req, resp);
            return;
        }
        inputOtp = inputOtp.trim();

        User user = userService.findByEmail(email);

        if (user == null || user.getOtp() == null || !user.getOtp().equals(inputOtp)) {
            req.setAttribute("alertMsg", "Mã OTP không đúng.");
            req.getRequestDispatcher("/views/forgot-password-verify.jsp").forward(req, resp);
            return;
        }

        if (user.getOtpExpiry() == null || LocalDateTime.now().isAfter(user.getOtpExpiry())) {
            req.setAttribute("alertMsg", "Mã OTP đã hết hạn.");
            req.getRequestDispatcher("/views/forgot-password-verify.jsp").forward(req, resp);
            return;
        }

        session.setAttribute("RESET_VERIFIED", true);
        resp.sendRedirect(req.getContextPath() + "/reset-password");
    }
}
