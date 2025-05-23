package cn.edu.ustc.service;

import cn.edu.ustc.model.ProblemInfo;
import cn.edu.ustc.util.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author SiriusPaul
 * @version V1.0
 * @CreateDate 2025/5/22
 * @Description
 */
public class ProblemInfoService {
    public ProblemInfo getProblemInfo(String problemId) throws SQLException {
        String sql = "SELECT title, description FROM problems WHERE id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, problemId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new ProblemInfo(
//                            problemId,
                            rs.getString("title"),
                            rs.getString("description")                                                );
                }
            }
        }

        return null;
    }
}
