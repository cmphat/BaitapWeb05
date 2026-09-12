package vn.iotstar.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import vn.iotstar.model.User;

@Component
public class AdminSecurityInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("account") : null;

        // If not logged in -> redirect to /login
        if (user == null) {
            String contextPath = request.getContextPath();
            response.sendRedirect((contextPath.isEmpty() ? "" : contextPath) + "/login?error=unauthorized");
            return false;
        }

        // If logged in but roleid != 1 (not admin) -> HTTP 403 Forbidden
        if (user.getRoleid() != 1) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Bạn không có quyền truy cập khu vực quản trị Admin.");
            return false;
        }

        return true;
    }
}
