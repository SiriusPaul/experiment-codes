package cn.edu.ustc.dao.impl;

import cn.edu.ustc.dao.AlgorithmCodeDAO;
import cn.edu.ustc.exception.GlobalExceptionHandler;
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
 * @CreateDate 2025/5/31
 * @Description
 */
public class AlgorithmCodeDAOImpl implements AlgorithmCodeDAO {
    @Override
    public List<String> findAlgorithmNamesByProblemId(String problemId) throws SQLException {
        String sql = "SELECT algorithm_name FROM algorithm_code WHERE problem_id = ?";
        List<String> names = new ArrayList<>();

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, problemId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                names.add(rs.getString("algorithm_name"));
            }
        } catch (SQLException ex) {
            GlobalExceptionHandler.handleException(ex, "查询算法名称列表时");
            throw ex;
        }

        return names;
    }

    @Override
    public String findCodeByProblemIdAndAlgorithmName(String problemId, String algorithmName) throws SQLException {
        String sql = "SELECT source_code FROM algorithm_code WHERE problem_id = ? AND algorithm_name = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, problemId);
            stmt.setString(2, algorithmName);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("source_code");
            }
        } catch (SQLException ex) {
            GlobalExceptionHandler.handleException(ex, "查询算法代码时");
            throw ex;
        }

        return null;
    }

    @Override
    public String findClassNameByProblemIdAndAlgorithmName(String problemId, String algorithmName) throws SQLException {
        String sql = "SELECT class_name FROM algorithm_code WHERE problem_id = ? AND algorithm_name = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, problemId);
            stmt.setString(2, algorithmName);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("class_name");
            }
        } catch (SQLException ex) {
            GlobalExceptionHandler.handleException(ex, "查询类名时");
            throw ex;
        }

        return algorithmName.replaceAll("\\s+", "") + "Algorithm";
    }
}
