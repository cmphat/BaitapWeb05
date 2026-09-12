# BÀI TẬP WEB 05

Dự án **Bài tập Web 05** - Xây dựng hệ thống quản trị Web đa năng bằng **Spring Boot 4**, **Spring Data JPA**, **JSP/JSTL**, **SiteMesh Decorator 3** và **Bootstrap 5**.

---

## 1. Yêu cầu bài tập

Hệ thống hoàn thiện đầy đủ các chức năng theo đề bài giảng viên:
1. **CRUD Category bằng Spring Boot 4 + JSP/JSTL** cho role ADMIN (Thêm, Xem danh sách, Chỉnh sửa, Xóa an toàn chống lỗi ràng buộc khóa ngoại).
2. **CRUD User bằng Spring Boot 4 + JSP/JSTL** cho role ADMIN (Thêm, Xem danh sách, Cập nhật thông tin, Khóa/Kích hoạt tài khoản, Phân quyền vai trò, Bảo mật mật khẩu, Chống tự xóa tài khoản đang đăng nhập).
3. **Chức năng Tìm kiếm (Search)**: Tìm kiếm danh mục theo tên; Tìm kiếm người dùng đa trường (username, họ tên, email).
4. **Chức năng Phân trang (Pagination)**: Sử dụng Spring Data `Pageable` và `Page<T>` (mặc định 5 mục/trang, điều hướng Trang trước, Trang sau, số trang, giữ nguyên từ khóa tìm kiếm khi chuyển trang).
5. **Giao diện SiteMesh Decorator 3**: Tích hợp bộ lọc SiteMesh 3 (`sitemesh:3.3.0-RC1`) tương thích Jakarta Servlet, chia sẻ Navbar responsive, Menu quản trị, Footer và container chung.
6. **Bootstrap Template**: Giao diện quản trị hiện đại, sạch sẽ, chuẩn Bootstrap 5.3.3 và Bootstrap Icons 1.11.3 (thẻ Card, bảng Table responsive, Badge trạng thái, Modal xác nhận xóa, Alert thông báo).
7. **Phân quyền Role ADMIN**: Bảo vệ toàn bộ các URL `/admin` và `/admin/**` bằng Interceptor. Chặn người dùng chưa đăng nhập (redirect `/login`), chặn người dùng không có quyền admin (trả lỗi HTTP 403 Forbidden với giao diện thông báo riêng).

---

## 2. Công nghệ sử dụng

- **Ngôn ngữ lập trình**: Java 26 (Oracle JDK 26)
- **Framework Backend**: Spring Boot 4.0.0 (Spring Framework 7.0.1, Jakarta EE 11)
- **Kiến trúc Web**: Spring MVC (`@Controller`, `@GetMapping`, `@PostMapping`, `Model`, `RedirectAttributes`)
- **Tầng dữ liệu (ORM & Persistence)**: Spring Data JPA, Hibernate 7, SQL Server JDBC (`mssql-jdbc`)
- **View Layer**: JSP (JavaServer Pages), JSTL (`jakarta.tags.core`), Jasper compiler (`tomcat-embed-jasper:11.0.14`)
- **Decorator & Layout**: SiteMesh Decorator 3 (`org.sitemesh:sitemesh:3.3.0-RC1`)
- **Giao diện Frontend**: Bootstrap 5.3.3 & Bootstrap Icons 1.11.3
- **Quản lý dự án & Build**: Apache Maven 3.9.11
- **Cơ sở dữ liệu**: Microsoft SQL Server 2022 (Database: `ExerciseWeb`)
- **Kiểm thử**: JUnit 5, Mockito, Spring Boot Test

---

## 3. Cấu trúc Project

```
BaitapWeb05/
├── .gitignore
├── README.md
└── HomeWorkWeb_Chau_Minh_Phat/
    ├── pom.xml
    ├── README.md
    ├── database/
    │   ├── init_database_web05.sql       <-- Script khởi tạo toàn bộ CSDL và dữ liệu mẫu
    │   ├── update_user_otp.sql
    │   └── update_user_profile.sql
    ├── src/
    │   ├── main/
    │   │   ├── java/vn/iotstar/
    │   │   │   ├── BaitapWeb05Application.java     <-- Main class Spring Boot 4
    │   │   │   ├── config/
    │   │   │   │   ├── SiteMeshConfig.java         <-- Cấu hình SiteMesh 3 FilterRegistrationBean
    │   │   │   │   └── WebMvcConfig.java           <-- Cấu hình Interceptor & Resource Handlers
    │   │   │   ├── controller/
    │   │   │   │   ├── admin/
    │   │   │   │   │   ├── AdminCategoryController.java  <-- CRUD, Search & Pagination Category
    │   │   │   │   │   ├── AdminDashboardController.java <-- Dashboard thống kê tổng quan
    │   │   │   │   │   ├── AdminUserController.java      <-- CRUD, Search & Pagination User
    │   │   │   │   │   └── ProductAdminController.java   <-- Quản lý sản phẩm role Admin
    │   │   │   │   ├── AuthController.java               <-- Đăng nhập, ghi nhớ cookie, đăng xuất
    │   │   │   │   ├── CustomErrorController.java        <-- Điều hướng trang lỗi 403, 404
    │   │   │   │   ├── ForgotPasswordController.java     <-- Quên mật khẩu & xác thực OTP
    │   │   │   │   ├── HomeController.java               <-- Trang chủ hiển thị 10 sp mới nhất
    │   │   │   │   ├── ProductPublicController.java      <-- Xem danh sách & chi tiết sản phẩm
    │   │   │   │   ├── ProfileController.java            <-- Hồ sơ cá nhân & upload ảnh đại diện
    │   │   │   │   ├── RegisterController.java           <-- Đăng ký tài khoản mới & gửi OTP
    │   │   │   │   └── VerifyOtpController.java          <-- Kích hoạt tài khoản bằng mã OTP
    │   │   │   ├── entity/
    │   │   │   │   ├── Category.java                 <-- Entity Category (@Table categories)
    │   │   │   │   └── Product.java                  <-- Entity Product (@Table Products)
    │   │   │   ├── model/
    │   │   │   │   └── User.java                     <-- Entity User (@Table Users)
    │   │   │   ├── interceptor/
    │   │   │   │   └── AdminSecurityInterceptor.java <-- Kiểm tra phiên & quyền roleid == 1
    │   │   │   ├── repository/
    │   │   │   │   ├── CategoryRepository.java       <-- Spring Data JPA Category
    │   │   │   │   ├── ProductRepository.java        <-- Spring Data JPA Product
    │   │   │   │   └── UserRepository.java           <-- Spring Data JPA User
    │   │   │   ├── service/
    │   │   │   │   ├── ICategoryService.java & impl/CategoryServiceImpl.java
    │   │   │   │   ├── IProductService.java & impl/ProductServiceImpl.java
    │   │   │   │   └── UserService.java & impl/UserServiceImpl.java
    │   │   │   └── util/
    │   │   │       ├── EmailUtil.java                <-- Tiện ích gửi email Gmail SMTP
    │   │   │       └── OtpUtil.java                  <-- Tiện ích sinh OTP ngẫu nhiên 6 chữ số
    │   │   ├── resources/
    │   │   │   ├── application.properties            <-- Cấu hình Spring Boot, Datasource, JPA, View
    │   │   │   └── mail.properties.example           <-- File mẫu cấu hình Gmail App Password
    │   │   └── webapp/
    │   │       ├── WEB-INF/
    │   │       │   ├── decorators/
    │   │       │   │   └── main.jsp                  <-- Layout chung SiteMesh 3 + Bootstrap 5
    │   │       │   ├── sitemesh3.xml                 <-- Cấu hình mapping & exclude SiteMesh 3
    │   │       │   └── web.xml                       <-- Cấu hình webapp Jakarta Servlet
    │   │       ├── views/
    │   │       │   ├── admin/
    │   │       │   │   ├── category/
    │   │       │   │   │   ├── list.jsp              <-- Danh sách Category, tìm kiếm, phân trang
    │   │       │   │   │   └── form.jsp              <-- Form Thêm/Sửa Category
    │   │       │   │   ├── user/
    │   │       │   │   │   ├── list.jsp              <-- Danh sách User, tìm kiếm, phân trang
    │   │       │   │   │   └── form.jsp              <-- Form Thêm/Sửa User
    │   │       │   │   └── dashboard.jsp             <-- Bảng điều khiển quản trị thống kê
    │   │       │   ├── error/
    │   │       │   │   ├── 403.jsp                   <-- Giao diện lỗi 403 Forbidden
    │   │       │   │   └── 404.jsp                   <-- Giao diện lỗi 404 Not Found
    │   │       │   ├── home.jsp, login.jsp, register.jsp, profile.jsp, ...
    │   │       │   └── product/list.jsp, detail.jsp, admin-list.jsp, ...
    │   └── test/java/vn/iotstar/
    │       ├── AdminCategoryControllerTest.java      <-- Kiểm thử CRUD, Search, Pagination Category
    │       ├── AdminSecurityInterceptorTest.java     <-- Kiểm thử phân quyền Role Admin
    │       └── AdminUserControllerTest.java          <-- Kiểm thử CRUD, Search, Pagination User
```

---

## 4. Cơ sở dữ liệu (Database)

- **Hệ quản trị CSDL**: Microsoft SQL Server 2019/2022
- **Tên cơ sở dữ liệu**: `ExerciseWeb`
- **Tài khoản kết nối mặc định**: `sa` / `1504` (có thể ghi đè linh hoạt qua biến môi trường `DB_URL`, `DB_USERNAME`, `DB_PASSWORD`).
- **File script khởi tạo CSDL**:
  Đường dẫn: `HomeWorkWeb_Chau_Minh_Phat/database/init_database_web05.sql`
  Script tạo sẵn:
  - Bảng `Users` (chứa các trường `id`, `username`, `password`, `fullname`, `email`, `phone`, `roleid`, `active`, `otp`, `otp_expiry`, `images`).
  - Bảng `categories` (chứa các trường `CategoryId`, `CategoryName`, `Images`, `Status`).
  - Bảng `Products` (chứa các trường `ProductId`, `ProductName`, `Price`, `Description`, `Image`, `Status`, `CreatedAt`, `CategoryId`).
  - Dữ liệu mẫu ban đầu cho đầy đủ các bảng.

> **Lưu ý**: Cấu hình `spring.jpa.hibernate.ddl-auto=update` trong `application.properties` sẽ tự động cập nhật và đồng bộ cấu trúc bảng khi ứng dụng chạy.

---

## 5. Cách chạy ứng dụng

### Cách 1: Chạy trực tiếp bằng Maven Spring Boot Plugin (Khuyên dùng khi dev)
Mở terminal tại thư mục `HomeWorkWeb_Chau_Minh_Phat`:
```bash
mvn spring-boot:run
```
Ứng dụng sẽ khởi động trên cổng `8081` (truy cập: `http://localhost:8081/`).

### Cách 2: Đóng gói và chạy file WAR thực thi
```bash
mvn clean package -DskipTests
java -jar target/Exercise.war
```

### Cách 3: Triển khai trên máy chủ Apache Tomcat 10.1+
Copy file `target/Exercise.war` vào thư mục `webapps/` của Tomcat 10.1+ và khởi động Tomcat.

---

## 6. Tài khoản kiểm thử (Demo Test Accounts)

| Tên đăng nhập (Username) | Mật khẩu (Password) | Họ và tên | Vai trò (Role) | Mô tả quyền hạn |
| :--- | :--- | :--- | :--- | :--- |
| **admin** | `123` | Quản Trị Viên Hệ Thống | **Quản trị viên (Role 1)** | Toàn quyền truy cập Dashboard, CRUD Category, CRUD User, CRUD Product |
| **phatcm** | `123` | Châu Minh Phát | **Quản trị viên (Role 1)** | Tài khoản Admin bổ sung |
| **manager** | `123` | Trần Thị Quản Lý | **Quản lý (Role 2)** | Không vào được `/admin`, chỉ dùng chức năng nội bộ |
| **user** | `123` | Nguyễn Văn Người Dùng | **Người dùng (Role 3)** | Bị chặn khi vào `/admin` (hiển thị trang 403 Forbidden) |

---

## 7. Các URL kiểm thử chính

| Chức năng | Đường dẫn (URL) | Mô tả |
| :--- | :--- | :--- |
| **Trang chủ** | `http://localhost:8081/home` hoặc `/` | Hiển thị 10 sản phẩm mới nhất (yêu cầu đăng nhập) |
| **Đăng nhập** | `http://localhost:8081/login` | Đăng nhập tài khoản, ghi nhớ cookie |
| **Đăng ký** | `http://localhost:8081/register` | Đăng ký thành viên, gửi mã OTP kích hoạt |
| **Xác thực OTP** | `http://localhost:8081/verify-otp` | Nhập mã 6 số kích hoạt tài khoản |
| **Quên mật khẩu** | `http://localhost:8081/forgot-password` | Yêu cầu OTP đặt lại mật khẩu |
| **Hồ sơ cá nhân** | `http://localhost:8081/profile` | Xem & sửa họ tên, SĐT, upload avatar Multipart |
| **Admin Dashboard** | `http://localhost:8081/admin` | Bảng điều khiển thống kê tổng quan (Admin only) |
| **Admin Danh mục** | `http://localhost:8081/admin/categories` | Danh sách danh mục, tìm kiếm & phân trang (Admin only) |
| **Thêm danh mục** | `http://localhost:8081/admin/categories/create` | Form thêm danh mục có validation (Admin only) |
| **Sửa danh mục** | `http://localhost:8081/admin/categories/edit/{id}` | Form sửa danh mục (Admin only) |
| **Admin Người dùng** | `http://localhost:8081/admin/users` | Danh sách người dùng, tìm kiếm & phân trang (Admin only) |
| **Thêm người dùng** | `http://localhost:8081/admin/users/create` | Form thêm người dùng có validation (Admin only) |
| **Sửa người dùng** | `http://localhost:8081/admin/users/edit/{id}` | Form cập nhật thông tin người dùng (Admin only) |
| **Admin Sản phẩm** | `http://localhost:8081/admin/products` | Quản lý sản phẩm (Admin only) |
| **Sản phẩm Public** | `http://localhost:8081/product` | Danh sách sản phẩm phân trang cho khách hàng |

---

## 8. Chi tiết chức năng Quản lý Danh mục (Category)

- **READ**: Xem danh sách toàn bộ danh mục, hiển thị mã, ảnh đại diện, tên danh mục, trạng thái hoạt động (Hoạt động / Tạm khóa).
- **CREATE**:
  - URL: `GET /admin/categories/create` & `POST /admin/categories/create`
  - Validation: Tên danh mục không để trống, trim khoảng trắng, kiểm tra chống trùng tên danh mục trong CSDL.
  - Retain dữ liệu đã nhập khi gặp lỗi.
- **UPDATE**:
  - URL: `GET /admin/categories/edit/{id}` & `POST /admin/categories/edit/{id}`
  - Tải dữ liệu hiện tại lên form, kiểm tra không để trống tên, kiểm tra trùng lặp với danh mục khác.
- **DELETE**:
  - URL: `POST /admin/categories/delete/{id}`
  - **Bảo toàn ràng buộc khóa ngoại (Foreign Key Integrity)**: Kiểm tra xem danh mục có sản phẩm (`Product`) đang liên kết hay không. Nếu có sản phẩm, hệ thống từ chối xóa và hiển thị thông báo lỗi rõ ràng: *"Không thể xóa danh mục vì đang có X sản phẩm thuộc danh mục này"*, không làm sập ứng dụng.
  - Có modal Bootstrap xác nhận trước khi thực hiện xóa.

---

## 9. Chi tiết chức năng Quản lý Người dùng (User)

- **READ**: Xem danh sách người dùng với đầy đủ thông tin: Mã, Ảnh đại diện/Avatar, Tên đăng nhập, Họ tên, Email, Số điện thoại, Badge vai trò (Quản trị viên / Quản lý / Người dùng), Badge trạng thái (Kích hoạt / Khóa).
- **CREATE**:
  - URL: `GET /admin/users/create` & `POST /admin/users/create`
  - Validation:
    * `username`: Bắt buộc, tối thiểu 3 ký tự, kiểm tra trùng lặp.
    * `password`: Bắt buộc, tối thiểu 6 ký tự.
    * `email`: Bắt buộc, kiểm tra định dạng email regex, kiểm tra trùng lặp.
    * `phone`: Kiểm tra định dạng 10-11 số (nếu có nhập).
    * `roleid`: Chọn vai trò hợp lệ (1: Admin, 2: Manager, 3: User).
    * `active`: Switch kích hoạt hoặc tạm khóa tài khoản.
- **UPDATE**:
  - URL: `GET /admin/users/edit/{id}` & `POST /admin/users/edit/{id}`
  - `username` hiển thị ở chế độ chỉ đọc (readonly) để bảo toàn định danh.
  - **Bảo mật mật khẩu**: Không hiển thị mật khẩu plaintext. Nếu admin để trống ô *Mật khẩu mới*, hệ thống tự động giữ nguyên mật khẩu cũ của người dùng. Nếu nhập mật khẩu mới (tối thiểu 6 ký tự), hệ thống mới cập nhật.
  - Cập nhật phiên làm việc (`HttpSession`) ngay lập tức nếu admin đang tự chỉnh sửa thông tin của chính mình.
- **DELETE**:
  - URL: `POST /admin/users/delete/{id}`
  - **Chống tự khóa/tự xóa**: Kiểm tra đối chiếu ID với tài khoản đang đăng nhập trong Session. Nếu admin cố tình tự xóa chính mình, hệ thống lập tức chặn lại và báo lỗi: *"Bạn không thể tự xóa tài khoản quản trị đang đăng nhập của chính mình!"*.
  - Modal Bootstrap cảnh báo và xác nhận trước khi xóa.

---

## 10. Tìm kiếm & Phân trang (Search & Pagination)

### Danh mục (Category):
- Tìm kiếm theo `categoryname` (không phân biệt hoa thường - Case-insensitive).
- Nếu từ khóa trống -> hiển thị tất cả danh mục.
- Phân trang chuẩn Spring Data `Pageable` (`Page<Category>`), mặc định 5 danh mục/trang.
- Giữ nguyên tham số tìm kiếm `keyword` khi chuyển giữa các trang (ví dụ: `/admin/categories?keyword=máy&page=1&size=5`).

### Người dùng (User):
- Tìm kiếm một từ khóa trên nhiều trường đồng thời bằng truy vấn Spring Data JPA:
  ```sql
  LOWER(u.username) LIKE %:keyword% OR LOWER(u.fullname) LIKE %:keyword% OR LOWER(u.email) LIKE %:keyword%
  ```
- Phân trang chuẩn Spring Data `Pageable` (`Page<User>`), mặc định 5 người dùng/trang.
- Thanh phân trang Bootstrap hiển thị nút *Trước*, danh sách số trang, nút *Sau*, vô hiệu hóa nút *Trước* ở trang đầu và nút *Sau* ở trang cuối.

---

## 11. SiteMesh Decorator 3 & Bootstrap Template

- Bộ lọc SiteMesh 3 được đăng ký chuẩn trong Spring Boot 4 qua `FilterRegistrationBean<ConfigurableSiteMeshFilter>`.
- Exclude các tài nguyên tĩnh: `/css/*`, `/js/*`, `/images/*`, `/assets/*`, `/uploads/*`.
- Layout dùng chung tại `/WEB-INF/decorators/main.jsp`:
  * Navbar Bootstrap 5 responsive kèm icon Bootstrap Icons.
  * Tự động nhận diện session: Menu *Quản trị Admin* chỉ hiển thị khi tài khoản có `roleid == 1`.
  * Huy hiệu (Badge) phân biệt vai trò Admin trên thanh điều hướng.
  * Footer bản quyền đồng bộ trên toàn bộ hệ thống.
  * Client-side validation `.needs-validation` tự động kích hoạt cho tất cả form HTML.

---

## 12. Kiểm thử tự động (Automated Unit Tests)

Dự án bao gồm bộ kiểm thử đơn vị tự động (Unit Tests) toàn diện:
- `AdminCategoryControllerTest`: 6 tests kiểm tra CRUD, validation tên rỗng, tìm kiếm, phân trang và chặn xóa danh mục có sản phẩm.
- `AdminUserControllerTest`: 7 tests kiểm tra CRUD, validation username/email trùng, validation mật khẩu ngắn, tìm kiếm, phân trang và chặn admin tự xóa chính mình.
- `AdminSecurityInterceptorTest`: 3 tests kiểm tra chặn truy cập chưa đăng nhập (redirect `/login`), chặn user thường (HTTP 403), và cho phép Admin hợp lệ.

**Kết quả chạy lệnh `mvn test`:**
```
[INFO] Tests run: 16, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
```
Tất cả 16/16 tests đều **PASS 100%**.
