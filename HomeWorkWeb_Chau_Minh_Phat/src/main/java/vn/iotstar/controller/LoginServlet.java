package vn.iotstar.controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.iotstar.model.User;
import vn.iotstar.service.UserService;
import vn.iotstar.service.impl.UserServiceImpl;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private final UserService service = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("account") != null) {
            resp.sendRedirect(req.getContextPath() + "/home");
            return;
        }

        String activated = req.getParameter("activated");
        String reset = req.getParameter("reset");
        if ("1".equals(activated)) {
            req.setAttribute("successMsg", "Tài khoản đã được kích hoạt. Bạn có thể đăng nhập.");
        } else if ("1".equals(reset)) {
            req.setAttribute("successMsg", "Đổi mật khẩu thành công.");
        }

        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if ("username".equals(c.getName())) req.setAttribute("rememberedUsername", c.getValue());
            }
        }
        req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        boolean remember = "on".equals(req.getParameter("remember"));

        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            req.setAttribute("alert", "Tài khoản hoặc mật khẩu không được rỗng");
            req.setAttribute("username", username);
            req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
            return;
        }
        User user = service.login(username, password);
        if (user == null) {
            req.setAttribute("alert", "Tài khoản hoặc mật khẩu không đúng");
            req.setAttribute("username", username);
            req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
            return;
        }

        if (!user.isActive()) {
            req.setAttribute("alert", "Tài khoản chưa được kích hoạt. Vui lòng xác nhận OTP.");
            req.setAttribute("username", username);
            req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
            return;
        }

        HttpSession session = req.getSession(true);
        session.setAttribute("account", user);
        if (remember) {
            Cookie cookie = new Cookie("username", username);
            cookie.setMaxAge(30 * 60);
            cookie.setPath(req.getContextPath().isEmpty() ? "/" : req.getContextPath());
            resp.addCookie(cookie);
        }
        resp.sendRedirect(req.getContextPath() + "/home");
    }
}
