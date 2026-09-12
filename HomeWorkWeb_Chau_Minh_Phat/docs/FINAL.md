# Final Report - Báo cáo Tổng kết Project Bài Tập

## 1. Tổng quan Project
Dự án là một ứng dụng Web Java thuần được nâng cấp từ phiên bản có sẵn. Project đã triển khai đầy đủ các nghiệp vụ quản lý Category, Product với giao diện Public và Admin. Đồng thời tích hợp tính năng xác thực tài khoản qua OTP gửi qua email.

## 2. Công nghệ sử dụng
- **Ngôn ngữ**: Java 26
- **Server**: Apache Tomcat 10.1
- **Servlet/JSP**: Jakarta Servlet 6.0, JSP 3.1, JSTL Jakarta
- **ORM Framework**: JPA / Hibernate ORM 6.6.x
- **Cơ sở dữ liệu**: SQL Server 2022 (Database: `ExerciseWeb`)
- **Gửi Email**: Jakarta Mail
- **Build Tool**: Maven WAR

## 3. Kiến trúc
- **Mô hình MVC**: Servlet đóng vai trò Controller, JSP đóng vai trò View, các lớp Java entity và DAO đóng vai trò Model.
- **DAO Pattern**: Các thao tác database được tách ra interface DAO và implementation DAO bằng JPA.
- **Service Layer**: Nằm giữa Controller và DAO để xử lý nghiệp vụ.
- **Dependency**: Loại bỏ hoàn toàn `javax.*`, thay bằng `jakarta.*`. Xoá code JDBC cũ.

## 4. User flow
1. Khách hàng truy cập `/home` hoặc `/product`.
2. Có thể xem danh sách sản phẩm (6 sp/trang) và chi tiết sản phẩm.
3. Người dùng đăng nhập ở `/login`. Nếu chưa có tài khoản có thể qua `/register`.
4. Sau khi login bằng tài khoản có quyền Admin, user có thể truy cập `/admin/categories` và `/admin/products`.

## 5. OTP activation flow
- Người dùng nhập thông tin tại trang Register.
- Hệ thống sinh OTP 6 số (dùng `SecureRandom`), gán expiry = current + 5 phút, lưu vào Entity `User`.
- Tài khoản mới tạo có `active = 0`.
- Hệ thống gửi email chứa mã OTP (gửi qua tài khoản Gmail cấu hình bằng App Password qua biến môi trường).
- Người dùng chuyển đến trang `/verify-otp`. Nhập OTP đúng và còn hạn -> Cập nhật `active = 1`, xoá OTP.

## 6. Forgot password flow
- Người dùng quên mật khẩu vào `/forgot-password`, nhập email hoặc username.
- Hệ thống kiểm tra tài khoản, sinh OTP mới (lưu vào database).
- Gửi mã OTP vào email đã đăng ký.
- Người dùng chuyển sang `/forgot-password/verify` nhập OTP. Nếu đúng, lưu quyền truy cập tạm thời vào session và chuyển sang `/reset-password`.
- Tại trang Reset Password, người dùng nhập mật khẩu mới, cập nhật thành công và tự động xoá quyền đổi mật khẩu.

## 7. Category JPA
- Category map với table `Categories` qua JPA. Đã được chuyển đổi hoàn toàn từ JDBC cũ sang JPA, với đầy đủ các nghiệp vụ CRUD qua EntityManager.

## 8. Product JPA
- Product map với table `Products` qua JPA, gồm các thuộc tính `productId`, `productName`, `price`, `description`, `image`, `status`, `createdAt`.

## 9. Category 1-N Product
- `Product` sử dụng `@ManyToOne` với `@JoinColumn(name="CategoryId")`.
- `Category` sử dụng `@OneToMany(mappedBy="category")`.
- Đảm bảo tính toàn vẹn dữ liệu khi fetch hoặc liên kết.

## 10. Product CRUD
- Hỗ trợ xem, thêm, sửa, xóa (CRUD) cho Product qua controller `ProductAdminController` mapping `/admin/products` và `/admin/product/*`. 

## 11. Latest 10
- Trang chủ `/home` hiển thị tối đa 10 sản phẩm mới nhất thông qua hàm `findLatest(10)` của `IProductService`.

## 12. Pagination 6/page
- Tại trang `/product`, sản phẩm được phân trang, tối đa 6 sản phẩm mỗi trang. Sử dụng `.setFirstResult()` và `.setMaxResults()` của JPA. Tính toán trang hợp lý, có nút Next, Previous.

## 13. Product detail
- Bấm "Xem chi tiết" từ Home hoặc trang Danh sách sẽ mở trang `/product/detail?id=X` hiển thị chi tiết đầy đủ thông tin sản phẩm.

## 14. Database setup
- Server: `localhost:1433`. User: `sa`, Password: `1504`.
- Database: `ExerciseWeb`.
- Bật tuỳ chọn `hbm2ddl.auto=update` trong `persistence.xml` để Hibernate tự sinh/cập nhật bảng.

## 15. Email setup
- Sử dụng Gmail SMTP qua thư viện Jakarta Mail.
- Không hardcode mật khẩu, ứng dụng đọc cấu hình qua Environment Variables:
  - `EMAIL_USERNAME`: Địa chỉ email thực tế.
  - `EMAIL_PASSWORD`: App password (16 ký tự).

## 16. URL demo
- Public: `/home`, `/product`, `/product/detail`
- Auth: `/login`, `/register`, `/verify-otp`, `/forgot-password`, `/reset-password`
- Admin: `/admin/categories`, `/admin/products`

## 19. SiteMesh Decorator 3 & Bootstrap 5 (Bài tập 4)
- Áp dụng `org.sitemesh:sitemesh:3.3.0-RC1` (chuẩn Jakarta Servlet 6 / Tomcat 10.1).
- Định nghĩa layout thống nhất tại `/decorators/main.jsp` sử dụng Bootstrap 5.3.3.
- Navbar hiển thị linh hoạt theo trạng thái session: Khách (Home, Products, Login, Register) / Đã đăng nhập (Home, Products, Profile, Logout, Admin Dropdown nếu có quyền).
- Tự động exclude tài nguyên tĩnh (`/css/*`, `/js/*`, `/images/*`, `/uploads/*`).

## 20. User Profile & Multipart Upload (Bài tập 4)
- **Entity**: User được cấu hình `@Entity`, `@Table(name="Users")`, bổ sung trường `images`.
- **JPA Profile Update**: Phương thức `updateProfile` trong `UserDaoImpl` quản lý transaction chặt chẽ qua `EntityManager` (`begin`, `merge`, `commit`, `rollback`, `close`).
- **Multipart Upload**: `ProfileServlet` sử dụng `@MultipartConfig`, kiểm tra giới hạn 5MB, lọc MIME type (`image/jpeg`, `image/png`, `image/webp`), tạo tên file an toàn bằng UUID chống trùng lặp và path traversal.
- **Session Sync**: Sau khi cập nhật thành công, session `account` được làm mới với đối tượng user từ database.

## 21. Form Validation (Bài tập 4)
- Triển khai đồng thời Client-side (Bootstrap `needs-validation`, HTML5 constraint) và Server-side validation.
- Áp dụng cho: Register, Login, Verify OTP, Forgot Password, Reset Password, Category CRUD, Product CRUD, User Profile.
- Không để mất dữ liệu người dùng khi có lỗi; hiển thị thông báo lỗi bằng Bootstrap Alert rõ ràng, không làm lộ stacktrace hệ thống.

