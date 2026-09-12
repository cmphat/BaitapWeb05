package vn.iotstar.controller;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.iotstar.model.User;
import vn.iotstar.service.UserService;

@Controller
public class ProfileController {

    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList("jpg", "jpeg", "png", "webp");
    private static final List<String> ALLOWED_MIME_TYPES = Arrays.asList("image/jpeg", "image/png", "image/webp");
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5 MB

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public String showProfile(HttpSession session, Model model) {
        if (session == null || session.getAttribute("account") == null) {
            return "redirect:/login";
        }

        User sessionUser = (User) session.getAttribute("account");
        User currentUser = userService.findById(sessionUser.getId());
        if (currentUser == null) {
            currentUser = sessionUser;
        }

        model.addAttribute("user", currentUser);
        return "profile";
    }

    @PostMapping("/profile")
    public String updateProfile(
            @RequestParam("fullname") String fullname,
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "avatarFile", required = false) MultipartFile avatarFile,
            HttpServletRequest request,
            HttpSession session,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (session == null || session.getAttribute("account") == null) {
            return "redirect:/login";
        }

        User sessionUser = (User) session.getAttribute("account");
        User currentUser = userService.findById(sessionUser.getId());
        if (currentUser == null) {
            return "redirect:/login";
        }

        String trimmedFullname = fullname != null ? fullname.trim() : "";
        String trimmedPhone = phone != null ? phone.trim() : "";

        if (trimmedFullname.isEmpty()) {
            model.addAttribute("alertMsg", "Họ và tên không được để trống.");
            currentUser.setPhone(trimmedPhone);
            model.addAttribute("user", currentUser);
            return "profile";
        }

        if (!trimmedPhone.isEmpty() && !trimmedPhone.matches("^\\d{10,11}$")) {
            model.addAttribute("alertMsg", "Số điện thoại phải gồm 10 đến 11 chữ số.");
            currentUser.setFullname(trimmedFullname);
            currentUser.setPhone(trimmedPhone);
            model.addAttribute("user", currentUser);
            return "profile";
        }

        String avatarRelativePath = currentUser.getImages();
        if (avatarFile != null && !avatarFile.isEmpty()) {
            if (avatarFile.getSize() > MAX_FILE_SIZE) {
                model.addAttribute("alertMsg", "Kích thước ảnh đại diện vượt quá giới hạn cho phép (tối đa 5MB).");
                currentUser.setFullname(trimmedFullname);
                currentUser.setPhone(trimmedPhone);
                model.addAttribute("user", currentUser);
                return "profile";
            }

            String originalFilename = avatarFile.getOriginalFilename();
            String extension = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase();
            }

            String contentType = avatarFile.getContentType();
            if (!ALLOWED_EXTENSIONS.contains(extension) || contentType == null || !ALLOWED_MIME_TYPES.contains(contentType.toLowerCase())) {
                model.addAttribute("alertMsg", "Định dạng tệp không hợp lệ! Chỉ chấp nhận các định dạng ảnh: .jpg, .jpeg, .png, .webp.");
                currentUser.setFullname(trimmedFullname);
                currentUser.setPhone(trimmedPhone);
                model.addAttribute("user", currentUser);
                return "profile";
            }

            try {
                String uploadDir = request.getServletContext().getRealPath("/uploads/profile");
                if (uploadDir == null) {
                    uploadDir = System.getProperty("user.dir") + File.separator + "uploads" + File.separator + "profile";
                }
                File uploadFolder = new File(uploadDir);
                if (!uploadFolder.exists()) {
                    uploadFolder.mkdirs();
                }

                String uniqueFileName = UUID.randomUUID().toString().replace("-", "") + "." + extension;
                File targetFile = new File(uploadFolder, uniqueFileName);
                avatarFile.transferTo(targetFile);

                avatarRelativePath = request.getContextPath() + "/uploads/profile/" + uniqueFileName;
            } catch (IOException e) {
                model.addAttribute("alertMsg", "Đã xảy ra lỗi khi lưu tệp tải lên: " + e.getMessage());
                currentUser.setFullname(trimmedFullname);
                currentUser.setPhone(trimmedPhone);
                model.addAttribute("user", currentUser);
                return "profile";
            }
        }

        currentUser.setFullname(trimmedFullname);
        currentUser.setPhone(trimmedPhone);
        currentUser.setImages(avatarRelativePath);

        userService.updateProfile(currentUser);
        session.setAttribute("account", currentUser);

        redirectAttributes.addFlashAttribute("successMsg", "Cập nhật hồ sơ cá nhân thành công!");
        return "redirect:/profile";
    }
}
