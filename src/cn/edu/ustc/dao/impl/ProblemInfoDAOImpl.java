package cn.edu.ustc.dao.impl;

import cn.edu.ustc.dao.ProblemInfoDAO;
import cn.edu.ustc.exception.GlobalExceptionHandler;
import cn.edu.ustc.model.ProblemInfo;
import cn.edu.ustc.util.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author SiriusPaul
 * @version V1.0
 * @CreateDate 2025/5/31
 * @Description
 */
public class ProblemInfoDAOImpl implements ProblemInfoDAO {
    @Override
    public ProblemInfo findById(String problemId) throws SQLException {
        String sql = "SELECT title, description FROM problems WHERE id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, problemId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new ProblemInfo(
                            rs.getString("title"),
                            rs.getString("description")
                    );
                }
            }
        } catch (SQLException ex) {
            GlobalExceptionHandler.handleException(ex, "查询ID为" + problemId + "的问题时");
            throw ex;
        }

        return null;
    }
}
