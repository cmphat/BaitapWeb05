USE ExerciseWeb;
GO

-- Add column images to Users table if not already exists
IF NOT EXISTS (
    SELECT 1 
    FROM sys.columns 
    WHERE object_id = OBJECT_ID(N'dbo.Users') 
      AND name = N'images'
)
BEGIN
    ALTER TABLE dbo.Users
    ADD images NVARCHAR(500) NULL;
    PRINT 'Added column images to dbo.Users';
END
ELSE
BEGIN
    PRINT 'Column images already exists in dbo.Users';
END
GO
