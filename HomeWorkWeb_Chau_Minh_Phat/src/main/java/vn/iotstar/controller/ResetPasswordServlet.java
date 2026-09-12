package vn.iotstar.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import vn.iotstar.model.User;
import vn.iotstar.service.UserService;
import vn.iotstar.service.impl.UserServiceImpl;

@WebServlet(urlPatterns = {"/reset-password"})
public class ResetPasswordServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Boolean verified = (Boolean) session.getAttribute("RESET_VERIFIED");
        
        if (verified == null || !verified) {
            resp.sendRedirect(req.getContextPath() + "/forgot-password");
            return;
        }
        req.getRequestDispatcher("/views/reset-password.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();
        
        Boolean verified = (Boolean) session.getAttribute("RESET_VERIFIED");
        if (verified == null || !verified) {
            resp.sendRedirect(req.getContextPath() + "/forgot-password");
            return;
        }
        
        String email = (String) session.getAttribute("resetEmail");
        String newPassword = req.getParameter("newPassword");
        String confirmPassword = req.getParameter("confirmPassword");

        if (newPassword == null || confirmPassword == null || newPassword.isBlank()) {
            req.setAttribute("alertMsg", "Vui lòng nhập đầy đủ mật khẩu mới và xác nhận mật khẩu.");
            req.getRequestDispatcher("/views/reset-password.jsp").forward(req, resp);
            return;
        }

        if (newPassword.length() < 6) {
            req.setAttribute("alertMsg", "Mật khẩu mới phải có ít nhất 6 ký tự.");
            req.getRequestDispatcher("/views/reset-password.jsp").forward(req, resp);
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            req.setAttribute("alertMsg", "Mật khẩu xác nhận không khớp.");
            req.getRequestDispatcher("/views/reset-password.jsp").forward(req, resp);
            return;
        }

        User user = userService.findByEmail(email);
        if (user != null) {
            user.setPassword(newPassword); // Plain text as required by current state
            user.setOtp(null);
            user.setOtpExpiry(null);
            userService.update(user);
        }

        session.removeAttribute("RESET_VERIFIED");
        session.removeAttribute("resetEmail");

        resp.sendRedirect(req.getContextPath() + "/login?reset=1");
    }
}
