package cn.edu.ustc.dao.impl;

import cn.edu.ustc.dao.TestCaseDAO;
import cn.edu.ustc.exception.GlobalExceptionHandler;
import cn.edu.ustc.model.StringMatchTestCase;
import cn.edu.ustc.model.TestCase;
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
public class TestCaseDAOImpl implements TestCaseDAO {
    @Override
    public List<TestCase> findByProblemId(String problemId) throws SQLException {
        List<TestCase> testCases = new ArrayList<>();

        try (Connection conn = DatabaseManager.getConnection()) {
            String sql;
            PreparedStatement stmt;

            if ("string-match".equals(problemId)) {
                sql = "SELECT id, name, problem_id, text, pattern, expected_output " +
                        "FROM string_match_test_cases WHERE problem_id = ?";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, problemId);

                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        StringMatchTestCase testCase = new StringMatchTestCase();
                        testCase.setId(rs.getInt("id"));
                        testCase.setName(rs.getString("name"));
                        testCase.setProblemId(rs.getString("problem_id"));
                        testCase.setExpectedOutput(rs.getString("expected_output"));
                        testCase.setText(rs.getString("text"));
                        testCase.setPattern(rs.getString("pattern"));

                        testCases.add(testCase);
                    }
                }
            } else {
                sql = "SELECT id, name, problem_id, input_data, expected_output " +
                        "FROM test_cases WHERE problem_id = ?";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, problemId);

                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        TestCase testCase = new TestCase();
                        testCase.setId(rs.getInt("id"));
                        testCase.setName(rs.getString("name"));
                        testCase.setProblemId(rs.getString("problem_id"));
                        testCase.setInputData(rs.getString("input_data"));
                        testCase.setExpectedOutput(rs.getString("expected_output"));
                        testCases.add(testCase);
                    }
                }
            }
        } catch (SQLException ex) {
            GlobalExceptionHandler.handleException(ex, "查询测试用例时");
            throw ex;
        }

        return testCases;
    }
}
