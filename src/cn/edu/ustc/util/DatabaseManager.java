package cn.edu.ustc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author SiriusPaul
 * @version V1.0
 * @CreateDate 2025/5/17
 * @Description 数据库连接管理类
 */
public class DatabaseManager {
    private static final String URL = "jdbc:mysql://localhost:3306/experiment?useSSL=false";
    private static final String USER = "root";
    private static final String PASSWORD = "chenyq665";

    private static Connection connection;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL驱动加载失败", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        return connection;
    }

    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("关闭数据库连接失败: " + e.getMessage());
        }
    }
}
