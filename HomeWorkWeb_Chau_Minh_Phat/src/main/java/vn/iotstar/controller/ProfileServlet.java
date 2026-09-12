package vn.iotstar.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import vn.iotstar.model.User;
import vn.iotstar.service.UserService;
import vn.iotstar.service.impl.UserServiceImpl;

@WebServlet(urlPatterns = {"/profile"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,       // 1 MB buffer
        maxFileSize = 5 * 1024 * 1024,        // 5 MB max per file
        maxRequestSize = 10 * 1024 * 1024     // 10 MB max request size
)
public class ProfileServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final UserService userService = new UserServiceImpl();

    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList("jpg", "jpeg", "png", "webp");
    private static final List<String> ALLOWED_MIME_TYPES = Arrays.asList("image/jpeg", "image/png", "image/webp");
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5 MB

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        User sessionUser = (User) session.getAttribute("account");
        User currentUser = userService.findById(sessionUser.getId());
        if (currentUser == null) {
            currentUser = sessionUser;
        }

        req.setAttribute("user", currentUser);
        req.getRequestDispatcher("/views/profile.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        User sessionUser = (User) session.getAttribute("account");
        User currentUser = userService.findById(sessionUser.getId());
        if (currentUser == null) {
            currentUser = sessionUser;
        }

        String fullname = req.getParameter("fullname");
        String phone = req.getParameter("phone");

        // Validate server-side
        if (fullname == null || fullname.trim().isEmpty()) {
            req.setAttribute("alertMsg", "Họ và tên không được để trống.");
            req.setAttribute("user", currentUser);
            req.getRequestDispatcher("/views/profile.jsp").forward(req, resp);
            return;
        }

        if (phone != null && !phone.trim().isEmpty()) {
            String cleanPhone = phone.trim();
            if (!cleanPhone.matches("^0[0-9]{9,10}$")) {
                req.setAttribute("alertMsg", "Số điện thoại không đúng định dạng (bắt đầu bằng 0 và gồm 10-11 chữ số).");
                currentUser.setFullname(fullname.trim());
                currentUser.setPhone(cleanPhone);
                req.setAttribute("user", currentUser);
                req.getRequestDispatcher("/views/profile.jsp").forward(req, resp);
                return;
            }
        }

        String newImagePath = currentUser.getImages();

        // Handle multipart avatar upload
        try {
            Part filePart = req.getPart("image");
            if (filePart != null && filePart.getSize() > 0) {
                // Check size limit
                if (filePart.getSize() > MAX_FILE_SIZE) {
                    req.setAttribute("alertMsg", "Kích thước ảnh vượt quá giới hạn 5MB.");
                    req.setAttribute("user", currentUser);
                    req.getRequestDispatcher("/views/profile.jsp").forward(req, resp);
                    return;
                }

                // Get and sanitize submitted file name to prevent path traversal
                String submittedName = filePart.getSubmittedFileName();
                if (submittedName == null || submittedName.trim().isEmpty()) {
                    submittedName = "avatar";
                }
                String safeFileName = Paths.get(submittedName).getFileName().toString();

                // Extract and validate extension
                String ext = "";
                int dotIdx = safeFileName.lastIndexOf('.');
                if (dotIdx > 0 && dotIdx < safeFileName.length() - 1) {
                    ext = safeFileName.substring(dotIdx + 1).toLowerCase();
                }

                if (!ALLOWED_EXTENSIONS.contains(ext)) {
                    req.setAttribute("alertMsg", "Định dạng file không được hỗ trợ. Chỉ chấp nhận JPG, JPEG, PNG, WEBP.");
                    req.setAttribute("user", currentUser);
                    req.getRequestDispatcher("/views/profile.jsp").forward(req, resp);
                    return;
                }

                // Validate MIME type
                String mimeType = filePart.getContentType();
                if (mimeType == null || !ALLOWED_MIME_TYPES.contains(mimeType.toLowerCase())) {
                    req.setAttribute("alertMsg", "Loại MIME không hợp lệ (" + mimeType + "). Chỉ chấp nhận file hình ảnh.");
                    req.setAttribute("user", currentUser);
                    req.getRequestDispatcher("/views/profile.jsp").forward(req, resp);
                    return;
                }

                // Safe relative upload directory inside web context
                String uploadSubDir = "/uploads/profile";
                String realPath = req.getServletContext().getRealPath(uploadSubDir);
                File uploadDir = new File(realPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }

                // Generate unique filename using UUID to prevent collisions & path traversal
                String uniqueFileName = UUID.randomUUID().toString() + "." + ext;
                File targetFile = new File(uploadDir, uniqueFileName);

                // Anti-path traversal canonical check
                if (!targetFile.getCanonicalPath().startsWith(uploadDir.getCanonicalPath())) {
                    req.setAttribute("alertMsg", "Phát hiện tên file không an toàn.");
                    req.setAttribute("user", currentUser);
                    req.getRequestDispatcher("/views/profile.jsp").forward(req, resp);
                    return;
                }

                // Write file to disk
                filePart.write(targetFile.getAbsolutePath());
                newImagePath = uploadSubDir + "/" + uniqueFileName;
            }
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("alertMsg", "Lỗi trong quá trình tải ảnh lên: " + e.getMessage());
            req.setAttribute("user", currentUser);
            req.getRequestDispatcher("/views/profile.jsp").forward(req, resp);
            return;
        }

        // Update user profile using JPA transaction
        try {
            currentUser.setFullname(fullname.trim());
            currentUser.setPhone(phone != null ? phone.trim() : null);
            currentUser.setImages(newImagePath);

            userService.updateProfile(currentUser);

            // Refresh user in session
            User refreshedUser = userService.findById(currentUser.getId());
            if (refreshedUser == null) {
                refreshedUser = currentUser;
            }
            session.setAttribute("account", refreshedUser);

            req.setAttribute("user", refreshedUser);
            req.setAttribute("successMsg", "Cập nhật hồ sơ cá nhân thành công!");
            req.getRequestDispatcher("/views/profile.jsp").forward(req, resp);

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("alertMsg", "Cập nhật thất bại. Lỗi hệ thống: " + e.getMessage());
            req.setAttribute("user", currentUser);
            req.getRequestDispatcher("/views/profile.jsp").forward(req, resp);
        }
    }
}
