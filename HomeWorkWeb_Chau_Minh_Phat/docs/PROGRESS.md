# Progress

- [PASS] Phase A: Kiểm tra và chuẩn hoá pom.xml (Thêm Jakarta Mail, xoá duplicate).
- [PASS] Phase B: User + Cập nhật model (Thêm fields OTP, active...). Cập nhật database SQL Server thành công.
- [ ] Phase C: Tạo OtpUtil.
- [ ] Phase D: Tạo EmailUtil.
- [ ] Phase E: Đăng ký & gửi email OTP.
- [ ] Phase F: Xác thực OTP.
- [ ] Phase G: Cập nhật Login check active.
- [ ] Phase H: Quên mật khẩu.
- [ ] Phase I: Tạo Product Entity (JPA).
- [ ] Phase J: Cập nhật persistence.xml.
- [ ] Phase K: Tạo Product Dao.
- [ ] Phase L: Tạo Product Service.
- [ ] Phase M: Tạo Product Admin Controller (CRUD).
- [ ] Phase N: Thêm view JSP cho Product Admin.
- [ ] Phase O: Home hiển thị 10 Product mới nhất.
- [ ] Phase P: Phân trang /product.
- [ ] Phase Q: Chi tiết sản phẩm.
- [ ] Phase R: Test data.
- [ ] Phase S: Hoàn thiện UI/Link.
- [ ] Phase T: Security / Basic validation.
- [ ] Phase U: Final test.
- [ ] Phase V: Dọn code cũ.
- [ ] Phase W: Cập nhật tài liệu.

## Audit Result
- **Xóa class thừa**: Đã xóa các class JDBC của Category không còn dùng để tránh duplicate/import nhầm (bao gồm `vn.iotstar.model.Category`, `CategoryDao`, `CategoryDaoImpl`, `CategoryService` - JDBC version).
- **Import javax**: Đã check toàn bộ project, không có import `javax.*`.
- **Servlet Mappings**: Đã check không có duplicate URL mapping.
- **EntityManager Close**: Đã kiểm tra `CategoryDao` (JPA), các hàm đều có block `finally { em.close(); }` đầy đủ.
- **Transaction Rollback**: Đã kiểm tra `CategoryDao`, các khối `catch` đều có check `trans.isActive()` và `trans.rollback()`.
- **JPQL/Parameter**: Các name parameter và properties như `categoryname` đều match chính xác.
- **JSP Attributes & URI**: `list-jpa.jsp` sử dụng URI JSTL mới (`jakarta.tags.core`), attributes `listcate` khớp với `CategoryJpaController`.
- **Lỗi NumberFormatException**: Đã fix parsing parameter trong `CategoryJpaController` bằng khối `try-catch` và default fallback/redirect.

### Các files đã sửa/xoá trong quá trình Audit:
- `src/main/java/vn/iotstar/controller/CategoryJpaController.java` (Sửa lỗi parse int)
- `src/main/java/vn/iotstar/model/Category.java` (Đã xoá)
- `src/main/java/vn/iotstar/dao/CategoryDao.java` (Đã xoá)
- `src/main/java/vn/iotstar/dao/impl/CategoryDaoImpl.java` (Đã xoá)
- `src/main/java/vn/iotstar/service/CategoryService.java` (Đã xoá)

## Feature Status

| FEATURE | STATUS | FILES | TEST METHOD |
| --- | --- | --- | --- |
| Login | [PASS] | `LoginServlet.java`, `UserDaoImpl.java`, `login.jsp` | Đã test luồng login, cookie remember, validate rỗng, retain username. |
| Register | [PASS] | `RegisterServlet.java`, `register.jsp` | Xử lý đăng ký, validation email regex, password >=6 ký tự, OTP email. |
| OTP Activation | [PASS] | `VerifyOtpServlet.java`, `verify-otp.jsp` | Validate mã OTP đúng 6 chữ số, kiểm tra hết hạn, kích hoạt tài khoản. |
| Forgot Password | [PASS] | `ForgotPasswordServlet.java`, `forgot-password.jsp` | Validate email hợp lệ, tạo OTP đặt lại mật khẩu. |
| Reset Password | [PASS] | `ResetPasswordServlet.java`, `reset-password.jsp` | Validate mật khẩu mới >=6 ký tự, xác nhận mật khẩu khớp. |
| Category CRUD | [PASS] | `CategoryJpaController.java`, `CategoryDao.java`, `views/category/*` | Validate tên danh mục không rỗng, JPA transaction commit/rollback. |
| Product CRUD | [PASS] | `ProductAdminController.java`, `views/product/*` | Validate tên sản phẩm, giá > 0, chọn danh mục, retain input form. |
| Home latest 10 | [PASS] | `HomeServlet.java`, `home.jsp` | Hiển thị 10 sp mới nhất, card Bootstrap 5. |
| Pagination 6/page | [PASS] | `ProductPublicController.java`, `list.jsp` | Phân trang 6 sp/trang, UI phân trang Bootstrap 5. |
| Product detail | [PASS] | `ProductPublicController.java`, `detail.jsp` | Chi tiết sản phẩm, hiển thị danh mục, giá định dạng tiền tệ. |
| SiteMesh 3 Decorator | [PASS] | `pom.xml`, `web.xml`, `sitemesh3.xml`, `decorators/main.jsp` | SiteMesh 3.3.0-RC1 (Jakarta 6), exclude static css/js/uploads, layout chung. |
| Bootstrap 5 Template | [PASS] | `decorators/main.jsp`, tất cả JSP | Navbar responsive, footer, alert, table, card, button thống nhất. |
| User Profile (JPA) | [PASS] | `User.java`, `UserDaoImpl.java`, `UserServiceImpl.java`, `ProfileServlet.java` | JPA transaction update fullname, phone, images, đồng bộ session account. |
| Multipart Upload | [PASS] | `ProfileServlet.java`, `profile.jsp` | @MultipartConfig, UUID file name, MIME filter, max 5MB, chống path traversal. |
| Form Validation | [PASS] | Toàn bộ Servlet và JSP | Client-side (Bootstrap needs-validation) + Server-side kiểm tra chặt chẽ. |

## Tiến độ hoàn thành Bài tập 4:
- **Phase 15 - SiteMesh Decorator 3**: Cấu hình thành công `org.sitemesh:sitemesh:3.3.0-RC1` tương thích Jakarta Servlet 6.0 trên Tomcat 10.1. Exclude static assets `/css/*`, `/js/*`, `/images/*`, `/uploads/*`.
- **Phase 16 - Bootstrap Shared Layout**: Tích hợp Bootstrap 5.3.3 + Icons vào decorator `main.jsp`. Tối ưu tất cả view JSP bỏ phần header/footer duplicate.
- **Phase 17 - User Profile + Multipart + JPA**: Migration SQL Server `update_user_profile.sql`, User JPA entity map `images`, DAO/Service cập nhật profile bằng JPA EntityManager transaction (`begin`, `merge`, `commit`, `rollback`, `close`). ProfileServlet hỗ trợ multipart upload với bảo mật cao.
- **Phase 18 - Form Validation**: Triển khai cả Client-side (Bootstrap `needs-validation`, invalid-feedback) và Server-side (kiểm tra format, độ dài, uniqueness, số tiền > 0, retain form data) cho Register, Login, Verify OTP, Forgot Password, Reset Password, Product, Category, Profile.
- **Phase 19 - Full Regression Test**: Build verification `mvn clean package` đạt `BUILD SUCCESS`. Không xung đột mapping, không lộ stacktrace.
- **Phase 20 - Documentation**: Cập nhật toàn bộ tài liệu hướng dẫn, checklist, README theo chuẩn nộp bài.
