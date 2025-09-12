package cn.edu.ustc.exception;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author SiriusPaul
 * @version V1.0
 * @CreateDate 2025/5/31
 * @Description
 */
public class GlobalExceptionHandler {
    private static final Logger logger = Logger.getLogger(GlobalExceptionHandler.class.getName());

    // 处理数据库异常
    public static void handleDatabaseException(SQLException ex, String operation) {
        logger.log(Level.SEVERE, "数据库操作错误: " + operation, ex);
    }

    // 将检查异常转换为运行时异常
    public static RuntimeException convertToRuntime(Exception ex, String message) {
        logger.log(Level.SEVERE, message, ex);
        return new RuntimeException(message, ex);
    }

    // 处理通用异常
    public static void handleException(Exception ex, String operation) {
        logger.log(Level.SEVERE, "操作错误: " + operation, ex);
    }
}
