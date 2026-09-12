-- ==========================================================
-- BÀI TẬP WEB 05: KHỞI TẠO CƠ SỞ DỮ LIỆU SQL SERVER
-- Tên cơ sở dữ liệu: ExerciseWeb
-- ==========================================================

IF DB_ID(N'ExerciseWeb') IS NULL
BEGIN
    CREATE DATABASE ExerciseWeb;
END
GO

USE ExerciseWeb;
GO

-- 1. BẢNG USERS (Người dùng & Quản trị)
IF OBJECT_ID(N'dbo.Users', N'U') IS NOT NULL DROP TABLE dbo.Users;
GO

CREATE TABLE dbo.Users (
    id INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    username NVARCHAR(100) NOT NULL UNIQUE,
    [password] NVARCHAR(100) NOT NULL,
    fullname NVARCHAR(255) NULL,
    email NVARCHAR(255) NULL,
    phone NVARCHAR(20) NULL,
    roleid INT NOT NULL DEFAULT 3, -- 1: Admin, 2: Manager, 3: User
    active BIT NOT NULL DEFAULT 1,
    otp NVARCHAR(6) NULL,
    otp_expiry DATETIME2 NULL,
    images NVARCHAR(500) NULL
);
GO

-- Thêm tài khoản mẫu:
-- 1. admin / 123 (Role 1: Admin)
-- 2. user / 123 (Role 3: User)
-- 3. manager / 123 (Role 2: Manager)
INSERT INTO dbo.Users(username, [password], fullname, email, phone, roleid, active, images)
VALUES
(N'admin', N'123', N'Quản Trị Viên Hệ Thống', N'admin@iotstar.vn', N'0901234567', 1, 1, N'https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?w=200'),
(N'user', N'123', N'Nguyễn Văn Người Dùng', N'user@iotstar.vn', N'0912345678', 3, 1, N'https://images.unsplash.com/photo-1570295999919-56ceb5ecca61?w=200'),
(N'manager', N'123', N'Trần Thị Quản Lý', N'manager@iotstar.vn', N'0923456789', 2, 1, N'https://images.unsplash.com/photo-1494790108377-be9c29b29330?w=200'),
(N'phatcm', N'123', N'Châu Minh Phát', N'phatcm@iotstar.vn', N'0934567890', 1, 1, N'https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=200'),
(N'testuser', N'123', N'Người Dùng Thử Nghiệm', N'test@iotstar.vn', N'0945678901', 3, 1, NULL),
(N'lockeduser', N'123', N'Tài Khoản Đang Khóa', N'locked@iotstar.vn', N'0956789012', 3, 0, NULL);
GO

-- 2. BẢNG CATEGORIES (Danh mục)
IF OBJECT_ID(N'dbo.Products', N'U') IS NOT NULL DROP TABLE dbo.Products;
GO
IF OBJECT_ID(N'dbo.categories', N'U') IS NOT NULL DROP TABLE dbo.categories;
GO

CREATE TABLE dbo.categories (
    CategoryId INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    CategoryName NVARCHAR(255) NOT NULL,
    Images NVARCHAR(500) NULL,
    [Status] INT NOT NULL DEFAULT 1
);
GO

-- Dữ liệu mẫu danh mục
INSERT INTO dbo.categories(CategoryName, Images, [Status])
VALUES
(N'Máy ảnh Mirrorless', N'https://images.unsplash.com/photo-1502920917128-1aa500764cbd?w=400', 1),
(N'Ống kính (Lens)', N'https://images.unsplash.com/photo-1606980707986-49a75ee1cdd2?w=400', 1),
(N'Phụ kiện nhiếp ảnh', N'https://images.unsplash.com/photo-1452780212940-6f5c0d14d848?w=400', 1),
(N'Flycam / Drone', N'https://images.unsplash.com/photo-1508614589041-895b88991e3e?w=400', 1),
(N'Thiết bị âm thanh', N'https://images.unsplash.com/photo-1590658268037-6bf12165a8df?w=400', 1),
(N'Chân máy & Gimbal', N'https://images.unsplash.com/photo-1512790182412-b19e6d62bc39?w=400', 1),
(N'Đèn Studio & Flash', N'https://images.unsplash.com/photo-1524758631624-e2822e304c36?w=400', 0);
GO

-- 3. BẢNG PRODUCTS (Sản phẩm)
CREATE TABLE dbo.Products (
    ProductId INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    ProductName NVARCHAR(255) NOT NULL,
    Price FLOAT NOT NULL,
    [Description] NVARCHAR(MAX) NULL,
    [Image] NVARCHAR(500) NULL,
    [Status] INT NOT NULL DEFAULT 1,
    CreatedAt DATETIME2 NULL DEFAULT GETDATE(),
    CategoryId INT NOT NULL CONSTRAINT FK_Products_Categories FOREIGN KEY REFERENCES dbo.categories(CategoryId)
);
GO

-- Dữ liệu mẫu sản phẩm
INSERT INTO dbo.Products(ProductName, Price, [Description], [Image], [Status], CategoryId)
VALUES
(N'Sony Alpha A7 IV', 59990000, N'Máy ảnh full-frame chuyên nghiệp', N'https://images.unsplash.com/photo-1516035069371-29a1b244cc32?w=400', 1, 1),
(N'Fujifilm X-T5', 43900000, N'Cảm biến X-Trans CMOS 5 HR 40.2MP', N'https://images.unsplash.com/photo-1502920917128-1aa500764cbd?w=400', 1, 1),
(N'Canon EOS R6 Mark II', 62500000, N'Chụp liên tiếp 40fps, quay 4K 60p', N'https://images.unsplash.com/photo-1512790182412-b19e6d62bc39?w=400', 1, 1),
(N'Sony FE 24-70mm F2.8 GM II', 49990000, N'Ống kính zoom tiêu chuẩn G Master', N'https://images.unsplash.com/photo-1606980707986-49a75ee1cdd2?w=400', 1, 2),
(N'DJI Mini 4 Pro', 19800000, N'Flycam nhỏ gọn dưới 249g quay 4K', N'https://images.unsplash.com/photo-1508614589041-895b88991e3e?w=400', 1, 4),
(N'Rode Wireless PRO', 9900000, N'Micro thu âm không dây 32-bit float', N'https://images.unsplash.com/photo-1590658268037-6bf12165a8df?w=400', 1, 5);
GO
