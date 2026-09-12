package vn.iotstar.connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    private static final String SERVER = "localhost";
    private static final String PORT = "1433";
    private static final String DATABASE = "ExerciseWeb";
    private static final String USER = "sa";
    private static final String PASSWORD = "1504"; // đổi nếu SQL Server của bạn dùng mật khẩu khác

    public Connection getConnection() throws Exception {
        String url = "jdbc:sqlserver://" + SERVER + ":" + PORT
                + ";databaseName=" + DATABASE
                + ";encrypt=true;trustServerCertificate=true";
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        return DriverManager.getConnection(url, USER, PASSWORD);
    }
}
