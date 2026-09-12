# Task Checklist

## Phase 1: Phân tích project
- [x] Đọc cấu trúc thư mục.
- [x] Xác định các file dùng JDBC (UserDaoImpl, CategoryDaoImpl cũ).
- [x] Xác định các file dùng JPA (Category, CategoryJpaController).
- [x] Xác định servlet mappings.
- [x] Đánh giá rủi ro conflict.

## Phase 2: Lập TASK.md
- [x] Khởi tạo tài liệu TASK.md.

## Phase 3: Cấu hình
- [x] Cập nhật pom.xml thêm Jakarta Mail nếu chưa có.
- [ ] Cập nhật persistence.xml thêm Entity User và Product.

## Phase 4: User registration + OTP
- [x] Cập nhật Model/Entity User sang JPA.
- [x] Bổ sung các field OTP, active.
- [x] Viết EmailUtil.
- [x] Xây dựng RegisterServlet (GET, POST).
- [x] Xây dựng VerifyOtpServlet (GET, POST).
- [x] Tạo giao diện register.jsp, verify-otp.jsp.

## Phase 5: Forgot Password
- [x] Xây dựng ForgotPasswordServlet.
- [x] Xây dựng ResetPasswordServlet.
- [x] Tạo giao diện forgot-password.jsp, reset-password.jsp.

## Phase 6: Product Entity & Category Relationship
- [x] Tạo Product Entity (ID, name, price, description, image, status, createdAt).
- [x] Ánh xạ @ManyToOne từ Product sang Category.
- [x] Ánh xạ @OneToMany từ Category sang Product.

## Phase 7: DAO & Service Product
- [x] Tạo IProductDao, ProductDao (JPA).
- [x] Tạo IProductService, ProductServiceImpl.


## Phase 8: CRUD Product
- [x] Tạo ProductController (admin).
- [x] Xây dựng JSP list, add, edit cho Product admin.

## Phase 9: Trang chủ 10 sản phẩm mới nhất
- [x] Cập nhật HomeServlet lấy 10 products mới nhất.
- [x] Cập nhật home.jsp.

## Phase 10: Product Pagination (6/page)
- [x] Xây dựng /product xử lý logic phân trang.
- [x] Hiển thị JSP kèm Previous, 1, 2, ..., Next.

## Phase 11: Product detail
- [x] Xây dựng chức năng hiển thị chi tiết sản phẩm.
- [x] Gắn link từ Home và trang Product.

## Phase 12: Test toàn bộ
- [ ] Test các trường hợp register, login, OTP, CRUD, v.v.

## Phase 13: Dọn code cũ
- [x] Xoá hoặc vô hiệu hoá các file Category JDBC cũ không còn sử dụng.
- [x] Dọn dẹp URL/Servlet bị trùng nếu có.

## Phase 14: Docs
- [x] Cập nhật PROGRESS.md, FINAL.md.
- [x] Cập nhật README.md.

## Phase 15: SiteMesh Decorator 3
- [x] Bổ sung dependency SiteMesh tương thích Jakarta Servlet 6 / Tomcat 10.1.
- [x] Cấu hình SiteMeshFilter trong web.xml.
- [x] Cấu hình sitemesh3.xml định nghĩa decorator và exclude static resources.
- [x] Tạo decorator Bootstrap tối thiểu /decorators/main.jsp.
- [x] Kiểm tra Maven build package thành công.

## Phase 16: Bootstrap Shared Layout
- [x] Chuẩn hóa layout chung cho toàn bộ các trang JSP bằng SiteMesh + Bootstrap 5.
- [x] Loại bỏ duplicate navbar/footer/head trong các JSP.
- [x] Tích hợp Bootstrap components (navbar, container, card, table, pagination, btn, alert).

## Phase 17: User Profile + Multipart + JPA
- [x] Bổ sung trường images vào User entity và database migration.
- [x] Cập nhật DAO & Service với JPA transaction cho User profile update.
- [x] Xây dựng ProfileServlet (/profile GET & POST multipart).
- [x] Tạo view profile.jsp với upload avatar an toàn (UUID, MIME check, max size).
- [x] Cập nhật session sau khi update profile.

## Phase 18: Form Validation
- [x] Client-side validation cho tất cả form (Register, Login, Forgot Password, Profile, Product, Category).
- [x] Server-side validation trong các Servlet tương ứng.
- [x] Giữ lại form data và hiển thị Bootstrap alert khi validation thất bại.

## Phase 19: Full Regression Test
- [x] Test toàn bộ authentication flow (Login, Register, OTP, Forgot Password, Reset Password).
- [x] Test Category & Product CRUD, pagination 6/page, Home 10 latest, Product detail.
- [x] Test Profile view, update fullname, phone, upload avatar hợp lệ và không hợp lệ.
- [x] Test SiteMesh decorator và exclude static resources.
- [x] Kiểm tra JPA transaction rollback và đóng EntityManager.

## Phase 20: Documentation
- [x] Cập nhật docs/TASK.md, docs/PROGRESS.md.
- [x] Cập nhật README.md chi tiết về kiến trúc, cấu hình, SiteMesh, JPA, Profile và validation.
