IF DB_ID(N'ExerciseWeb') IS NULL
BEGIN
    CREATE DATABASE ExerciseWeb;
END
GO

USE ExerciseWeb;
GO

IF OBJECT_ID(N'dbo.Users', N'U') IS NOT NULL DROP TABLE dbo.Users;
GO
IF OBJECT_ID(N'dbo.Category', N'U') IS NOT NULL DROP TABLE dbo.Category;
GO

CREATE TABLE dbo.Users (
    id INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    username NVARCHAR(100) NOT NULL UNIQUE,
    [password] NVARCHAR(100) NOT NULL,
    fullname NVARCHAR(255) NULL,
    roleid INT NOT NULL DEFAULT 3
);
GO

INSERT INTO dbo.Users(username, [password], fullname, roleid)
VALUES
(N'admin', N'123', N'Administrator', 1),
(N'user', N'123', N'Người dùng', 3);
GO

CREATE TABLE dbo.Category (
    cate_id INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    cate_name NVARCHAR(255) NOT NULL,
    icons NVARCHAR(500) NULL
);
GO

INSERT INTO dbo.Category(cate_name, icons)
VALUES
(N'Máy ảnh', N'https://images.unsplash.com/photo-1502920917128-1aa500764cbd?w=400'),
(N'Ống kính', N'https://images.unsplash.com/photo-1606980707986-49a75ee1cdd2?w=400'),
(N'Phụ kiện', N'https://images.unsplash.com/photo-1452780212940-6f5c0d14d848?w=400');
GO
