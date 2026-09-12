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

@WebServlet(urlPatterns = {"/verify-otp"})
public class VerifyOtpServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String email = (String) session.getAttribute("verifyEmail");
        
        if (email == null) {
            resp.sendRedirect(req.getContextPath() + "/register");
            return;
        }
        
        req.getRequestDispatcher("/views/verify-otp.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        
        HttpSession session = req.getSession();
        String email = (String) session.getAttribute("verifyEmail");
        String inputOtp = req.getParameter("otp");
        
        if (email == null) {
            resp.sendRedirect(req.getContextPath() + "/register");
            return;
        }

        if (inputOtp == null || inputOtp.isBlank() || !inputOtp.trim().matches("^\\d{6}$")) {
            req.setAttribute("alertMsg", "Mã OTP phải gồm đúng 6 chữ số.");
            req.getRequestDispatcher("/views/verify-otp.jsp").forward(req, resp);
            return;
        }
        inputOtp = inputOtp.trim();

        User user = userService.findByEmail(email);
        if (user == null || user.isActive()) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        if (user.getOtp() == null || !user.getOtp().equals(inputOtp)) {
            req.setAttribute("alertMsg", "Mã OTP không đúng.");
            req.getRequestDispatcher("/views/verify-otp.jsp").forward(req, resp);
            return;
        }

        if (user.getOtpExpiry() == null || LocalDateTime.now().isAfter(user.getOtpExpiry())) {
            req.setAttribute("alertMsg", "Mã OTP đã hết hạn.");
            req.getRequestDispatcher("/views/verify-otp.jsp").forward(req, resp);
            return;
        }

        user.setActive(true);
        user.setOtp(null);
        user.setOtpExpiry(null);
        
        userService.update(user);
        session.removeAttribute("verifyEmail");
        
        resp.sendRedirect(req.getContextPath() + "/login?activated=1");
    }
}
