# BÀI TẬP WEB 04

Dự án Bài tập Web 04 - Phát triển ứng dụng Web Java với Jakarta Servlet, JPA/Hibernate, SiteMesh Decorator 3 và Bootstrap 5.

---

## Nội dung bài tập

### Yêu cầu 1 - SiteMesh Decorator 3
- Cấu hình SiteMesh Decorator 3 (`sitemesh:3.3.0-RC1` tương thích hoàn toàn Jakarta Servlet 6.0 và Tomcat 10.1).
- Sử dụng 01 Bootstrap Template (Bootstrap 5.3.3 kết hợp Bootstrap Icons).
- Tích hợp layout dùng chung (`/decorators/main.jsp`) với Navbar responsive, Footer và container chung cho toàn bộ các trang JSP trong hệ thống.
- Cấu hình exclude các tài nguyên tĩnh (`/css/*`, `/js/*`, `/images/*`, `/uploads/*`).

### Yêu cầu 2 - Validation Form
- Bổ sung cả **Client-side validation** (Bootstrap 5 `.needs-validation` và `.invalid-feedback`) lẫn **Server-side validation** (kiểm tra định dạng, độ dài tối thiểu, uniqueness, ràng buộc logic, số tiền > 0, retain input khi lỗi).
- Áp dụng trên toàn bộ các form:
  - Register (Họ tên, username >= 3 ký tự, email regex, mật khẩu >= 6 ký tự, số điện thoại).
  - Login (Không để trống tài khoản và mật khẩu, retain username).
  - Verify OTP (Mã OTP gồm đúng 6 chữ số, kiểm tra hết hạn 5 phút).
  - Forgot Password (Email hợp lệ và kiểm tra tồn tại).
  - Reset Password (Mật khẩu mới >= 6 ký tự, xác nhận mật khẩu trùng khớp).
  - Category (Tên danh mục không để trống, trim whitespace).
  - Product (Tên sản phẩm, giá bán > 0, chọn danh mục hợp lệ).
  - Profile (Họ tên không để trống, số điện thoại 10-11 số, avatar hợp lệ).

### Yêu cầu 3 - User Profile
- Bổ sung thông tin người dùng: `fullname`, `phone`, `images`.
- **Cập nhật Profile bằng JPA**: Sử dụng `EntityManager` với transaction quản lý rõ ràng (`begin`, `merge`, `commit`, `rollback`, `close`).
- **Upload ảnh đại diện bằng Multipart**:
  - Servlet sử dụng `@MultipartConfig`.
  - Hỗ trợ định dạng: `image/jpeg`, `image/png`, `image/webp` (đuôi `.jpg`, `.jpeg`, `.png`, `.webp`).
  - Giới hạn kích thước tệp tối đa 5MB.
  - Đặt tên file ngẫu nhiên bằng `UUID` chống trùng lặp, chống path traversal, chống upload file thực thi.
  - Lưu trữ tương đối trong thư mục `/uploads/profile/` trong webapp.
  - Giữ lại ảnh cũ nếu người dùng không chọn tải ảnh mới.
  - Tự động cập nhật lại thông tin đối tượng trong `HttpSession` (`account`).
- Giao diện Profile xây dựng bằng SiteMesh + Bootstrap với preview ảnh trực tiếp và avatar mặc định nếu chưa có ảnh.

---

## Công nghệ sử dụng

- **Ngôn ngữ**: Java 26
- **Web Layer**: Jakarta Servlet 6.0, JSP, JSTL (`jakarta.tags.core`)
- **Quản lý dự án**: Apache Maven
- **Web Server**: Apache Tomcat 10.1 (chạy cổng `8081`, context path `/Exercise`)
- **Cơ sở dữ liệu**: Microsoft SQL Server 2022 (Database: `ExerciseWeb`)
- **ORM / Persistence**: Hibernate / JPA (`persistence.xml`, `EntityManagerFactory`)
- **Layout & Decorator**: SiteMesh 3 (`org.sitemesh:sitemesh:3.3.0-RC1`)
- **Giao diện**: Bootstrap 5.3.3 & Bootstrap Icons 1.11.3
- **Mail**: Jakarta Mail 2.0.1 (gửi OTP kích hoạt và đặt lại mật khẩu)

---

## Cơ sở dữ liệu (Database)

- **Database Name**: `ExerciseWeb`
- **Tài khoản mặc định**: `sa` / `1504` (cấu hình trong `src/main/resources/META-INF/persistence.xml`).
- **Các script SQL**:
  1. `database.sql` (nếu khởi tạo mới cấu trúc ban đầu).
  2. `database/update_user_otp.sql`: Bổ sung cột `otp` và `otp_expiry`.
  3. `database/update_user_profile.sql`: Bổ sung cột `images NVARCHAR(500) NULL` cho bảng `Users`.

> **Lưu ý**: Nhờ cấu hình `hibernate.hbm2ddl.auto = update`, Hibernate cũng sẽ tự động đồng bộ cấu trúc bảng `Users`, `Category`, và `Products` khi ứng dụng khởi chạy.

---

## Cách chạy ứng dụng

### 1. Build dự án với Maven
```bash
mvn clean package -DskipTests
```
Sau khi build thành công, file WAR `Exercise.war` sẽ được tạo tại thư mục `target/`.

### 2. Cấu hình Tomcat
- **Phiên bản Tomcat**: 10.1.x
- **Port**: `8081` (cấu hình trong file `conf/server.xml` của Tomcat).
- **Context Path**: `/Exercise`
- Deploy file `Exercise.war` vào thư mục `webapps/` của Tomcat (hoặc cấu hình context trong Eclipse/IntelliJ).

### 3. Cấu hình gửi mail OTP (Gmail SMTP)
Để gửi mã OTP xác thực tài khoản và khôi phục mật khẩu, hệ thống hỗ trợ 3 cách cấu hình tự động (theo thứ tự ưu tiên):
- **Cách 1: Biến môi trường hệ thống**: `EMAIL_USERNAME` và `EMAIL_PASSWORD` (App Password 16 ký tự).
- **Cách 2: Tham số JVM / VM Arguments trong Tomcat / Eclipse**: `-DEMAIL_USERNAME=your_email@gmail.com -DEMAIL_PASSWORD=your_16_char_app_password`.
- **Cách 3: Cấu hình file nội bộ**: Copy file mẫu `src/main/resources/mail.properties.example` thành `src/main/resources/mail.properties` và điền thông tin (file này đã được đưa vào `.gitignore`, bảo đảm không bao giờ lộ lên Git).
- **Kiểm tra cấu hình nhanh qua dòng lệnh**:
  ```bash
  java -cp "target/classes;target/Exercise/WEB-INF/lib/*" vn.iotstar.util.EmailUtil
  ```

---

## Các URL kiểm thử chính

| Chức năng | Đường dẫn (URL) | Mô tả |
| --- | --- | --- |
| **Trang chủ** | `http://localhost:8081/Exercise/home` | 10 sản phẩm mới nhất |
| **Sản phẩm (Public)** | `http://localhost:8081/Exercise/product` | Danh sách sản phẩm phân trang (6 sp/trang) |
| **Đăng nhập** | `http://localhost:8081/Exercise/login` | Đăng nhập tài khoản, ghi nhớ Cookie |
| **Đăng ký** | `http://localhost:8081/Exercise/register` | Đăng ký tài khoản, gửi OTP |
| **Xác thực OTP** | `http://localhost:8081/Exercise/verify-otp` | Nhập 6 số OTP kích hoạt tài khoản |
| **Quên mật khẩu** | `http://localhost:8081/Exercise/forgot-password` | Yêu cầu OTP đặt lại mật khẩu |
| **Xác thực OTP đổi pass** | `http://localhost:8081/Exercise/forgot-password/verify` | Xác thực OTP đặt lại mật khẩu |
| **Đặt lại mật khẩu** | `http://localhost:8081/Exercise/reset-password` | Nhập mật khẩu mới |
| **Hồ sơ cá nhân** | `http://localhost:8081/Exercise/profile` | Xem & sửa họ tên, SĐT, upload avatar |
| **Admin Danh mục** | `http://localhost:8081/Exercise/admin/categories` | Quản lý Category bằng JPA |
| **Admin Sản phẩm** | `http://localhost:8081/Exercise/admin/products` | Quản lý Product bằng JPA |

---

## Lịch sử thực hiện & Quá trình commit

Repository này được tổ chức lịch sử commit theo từng giai đoạn và yêu cầu thực tế của Bài tập 4:
1. `Bài 4: Khởi tạo source từ bài tập 3`: Toàn bộ source baseline từ Bài tập 3 (Register, Login, OTP, Forgot Password, Category JPA, Product pagination).
2. `Bài 4.1: Cấu hình SiteMesh Decorator 3`: Tích hợp SiteMesh 3 Jakarta, bộ lọc `web.xml`, file `sitemesh3.xml`.
3. `Bài 4.1: Tích hợp giao diện Bootstrap bằng SiteMesh`: Layout chung Bootstrap 5, shared Navbar, Footer, container, làm sạch các trang JSP.
4. `Bài 4.3: Bổ sung thông tin Profile cho User`: Bổ sung trường `images`, JPA Entity User, script migration SQL Server.
5. `Bài 4.3: Cập nhật Profile User bằng JPA`: Xử lý DAO, Service, ProfileServlet, cập nhật session và giao diện `profile.jsp`.
6. `Bài 4.3: Thêm upload ảnh Profile bằng Multipart`: `@MultipartConfig`, kiểm tra MIME, UUID filename, max 5MB, chống path traversal.
7. `Bài 4.2: Bổ sung validation cho các Form`: Client-side và Server-side validation cho Register, Login, OTP, Forgot Password, Category, Product, Profile.
8. `Bài 4: Kiểm thử và hoàn thiện các chức năng`: Kiểm tra toàn bộ luồng nghiệp vụ, build xác thực `BUILD SUCCESS`.
9. `Bài 4: Cập nhật tài liệu và hướng dẫn chạy project`: Hoàn thiện `README.md`, `TASK.md`, `PROGRESS.md`.
