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

@WebServlet(urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        
        String username = req.getParameter("username");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String fullname = req.getParameter("fullname");
        String phone = req.getParameter("phone");

        req.setAttribute("username", username);
        req.setAttribute("email", email);
        req.setAttribute("fullname", fullname);
        req.setAttribute("phone", phone);
        
        if (username == null || username.trim().isEmpty() ||
            email == null || email.trim().isEmpty() ||
            password == null || password.trim().isEmpty() ||
            fullname == null || fullname.trim().isEmpty()) {
            req.setAttribute("alertMsg", "Vui lòng nhập đầy đủ các thông tin bắt buộc (*).");
            req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
            return;
        }

        username = username.trim();
        email = email.trim();
        fullname = fullname.trim();
        if (phone != null) phone = phone.trim();

        if (username.length() < 3) {
            req.setAttribute("alertMsg", "Tên đăng nhập phải có ít nhất 3 ký tự.");
            req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
            return;
        }

        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            req.setAttribute("alertMsg", "Email không đúng định dạng.");
            req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
            return;
        }

        if (password.length() < 6) {
            req.setAttribute("alertMsg", "Mật khẩu phải có ít nhất 6 ký tự.");
            req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
            return;
        }

        if (phone != null && !phone.isEmpty() && !phone.matches("^0[0-9]{9,10}$")) {
            req.setAttribute("alertMsg", "Số điện thoại không đúng định dạng (bắt đầu bằng 0 và gồm 10-11 số).");
            req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
            return;
        }

        // Validate username and email uniqueness
        if (userService.get(username) != null) {
            req.setAttribute("alertMsg", "Tên đăng nhập đã tồn tại.");
            req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
            return;
        }
        
        if (userService.findByEmail(email) != null) {
            req.setAttribute("alertMsg", "Email đã tồn tại.");
            req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
            return;
        }

        String otp = OtpUtil.generateOtp();
        LocalDateTime otpExpiry = LocalDateTime.now().plusMinutes(5);

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password); // Note: plain text per current Phase requirements
        user.setFullname(fullname);
        user.setPhone(phone);
        user.setRoleid(2); // default user role
        user.setActive(false);
        user.setOtp(otp);
        user.setOtpExpiry(otpExpiry);

        userService.insert(user);
        
        System.out.println("[REGISTER] invoking EmailUtil.sendOtp");
        boolean mailSent = EmailUtil.sendOtp(email, otp, "ACTIVATE");
        System.out.println("[REGISTER] sendOtp result=" + mailSent);
        
        if (mailSent) {
            HttpSession session = req.getSession();
            session.setAttribute("verifyEmail", email);
            resp.sendRedirect(req.getContextPath() + "/verify-otp");
        } else {
            req.setAttribute("alertMsg", "Đăng ký thành công nhưng gửi email OTP thất bại. Vui lòng thử lại sau.");
            req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
        }
    }
}
