package cn.edu.ustc.service;

import cn.edu.ustc.util.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author SiriusPaul
 * @version V1.0
 * @CreateDate 2025/5/17
 * @Description 根据题目id从数据库中获取算法代码，算法名称，类名等信息
 */
public class AlgorithmCodeService {
    // 获取算法名称列表
    public List<String> getAlgorithmNames(String problemId) throws SQLException {
        String sql = "SELECT algorithm_name FROM algorithm_code WHERE problem_id = ?";
        List<String> names = new ArrayList<>();

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, problemId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                names.add(rs.getString("algorithm_name"));
            }
        }

        return names;
    }

    // 获取算法代码
    public String getCode(String problemId, String algorithmName) throws SQLException {
        String sql = "SELECT source_code FROM algorithm_code WHERE problem_id = ? AND algorithm_name = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, problemId);
            stmt.setString(2, algorithmName);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("source_code");
            }
        }

        return null;
    }

    // 获取类名
    public String getClassName(String problemId, String algorithmName) throws SQLException {
        String sql = "SELECT class_name FROM algorithm_code WHERE problem_id = ? AND algorithm_name = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, problemId);
            stmt.setString(2, algorithmName);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("class_name");
            }
        }

        return algorithmName.replaceAll("\\s+", "") + "Algorithm";
    }
}
