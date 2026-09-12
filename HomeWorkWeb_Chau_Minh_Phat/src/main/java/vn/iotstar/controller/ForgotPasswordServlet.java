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
import vn.iotstar.util.EmailUtil;
import vn.iotstar.util.OtpUtil;

@WebServlet(urlPatterns = {"/forgot-password"})
public class ForgotPasswordServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/views/forgot-password.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String email = req.getParameter("email");
        req.setAttribute("email", email);

        if (email == null || email.trim().isEmpty()) {
            req.setAttribute("alertMsg", "Vui lòng nhập địa chỉ email.");
            req.getRequestDispatcher("/views/forgot-password.jsp").forward(req, resp);
            return;
        }

        email = email.trim();
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            req.setAttribute("alertMsg", "Email không đúng định dạng.");
            req.getRequestDispatcher("/views/forgot-password.jsp").forward(req, resp);
            return;
        }

        User user = userService.findByEmail(email);
        if (user == null) {
            req.setAttribute("alertMsg", "Không tìm thấy tài khoản với email này.");
            req.getRequestDispatcher("/views/forgot-password.jsp").forward(req, resp);
            return;
        }

        String otp = OtpUtil.generateOtp();
        user.setOtp(otp);
        user.setOtpExpiry(LocalDateTime.now().plusMinutes(5));
        userService.update(user);
 
        System.out.println("[FORGOT_PASSWORD] invoking EmailUtil.sendOtp");
        boolean sent = EmailUtil.sendOtp(email, otp, "RESET_PASSWORD");
        System.out.println("[FORGOT_PASSWORD] sendOtp result=" + sent);
        if (sent) {
            HttpSession session = req.getSession();
            session.setAttribute("resetEmail", email);
            resp.sendRedirect(req.getContextPath() + "/forgot-password/verify");
        } else {
            req.setAttribute("alertMsg", "Gửi email thất bại. Vui lòng thử lại sau.");
            req.getRequestDispatcher("/views/forgot-password.jsp").forward(req, resp);
        }
    }
}
