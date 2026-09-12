BÀI TẬP SERVLET - SESSION/COOKIE + CRUD CATEGORY (SQL SERVER)
Họ và tên: Châu Minh Phát
MSSV: 24110294

Công nghệ:
- Java 17
- Maven WAR
- Tomcat 10.1
- Jakarta Servlet 6
- JSP/JSTL Jakarta
- JDBC SQL Server
- MVC + 3 tầng: Controller -> Service -> DAO -> Database

Nội dung:
1. Login bằng tài khoản trong SQL Server.
2. Session lưu account sau đăng nhập.
3. Cookie nhớ username trong 30 phút khi chọn "Nhớ tài khoản".
4. Logout hủy Session.
5. CRUD Category: list, add, edit, delete.
6. DAO/Service tách riêng theo kiến trúc 3 tầng.

Database:
- Chạy file database.sql bằng SQL Server Management Studio.
- Database: ExerciseWeb
- Account test: admin / 123

Kết nối SQL Server:
File: src/main/java/vn/iotstar/connection/DBConnection.java
Mặc định:
  server = localhost
  port = 1433
  database = ExerciseWeb
  user = sa
  password = 1504
Nếu máy dùng mật khẩu khác thì đổi PASSWORD.

Chạy:
1. Import project Existing Maven Project trong Eclipse.
2. Maven -> Update Project.
3. Run As -> Run on Server -> Tomcat 10.1.
4. Mở http://localhost:8080/Exercise/login
