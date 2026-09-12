USE ExerciseWeb;
GO

-- Add new columns for OTP and registration
ALTER TABLE dbo.Users
ADD email NVARCHAR(255) NULL,
    phone NVARCHAR(20) NULL,
    active BIT NOT NULL DEFAULT 0,
    otp NVARCHAR(6) NULL,
    otp_expiry DATETIME2 NULL;
GO

-- Active current users so they can still login
UPDATE dbo.Users
SET active = 1
WHERE username IN ('admin', 'user');
GO
