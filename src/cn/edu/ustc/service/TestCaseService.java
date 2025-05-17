package cn.edu.ustc.service;

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
 * @CreateDate 2025/5/17
 * @Description 测试用例服务类,用于从数据库中获取测试用例信息,并封装为TestCase对象
 */
public class TestCaseService {
    public List<TestCase> getTestCases(String problemId) throws SQLException {
        String sql = "SELECT id, problem_id, name, input_data, expected_output FROM test_cases WHERE problem_id = ?";
        List<TestCase> testCases = new ArrayList<>();

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // 设置查询参数
            stmt.setString(1, problemId);
            ResultSet rs = stmt.executeQuery();

            // 遍历结果集，将每个测试用例添加到列表中
            while (rs.next()) {
                TestCase testCase = new TestCase();
                testCase.setId(rs.getInt("id"));
                testCase.setProblemId(rs.getString("problem_id"));
                testCase.setName(rs.getString("name"));
                testCase.setInputData(rs.getString("input_data"));
                testCase.setExpectedOutput(rs.getString("expected_output"));
                testCases.add(testCase);
            }
        }

        return testCases;
    }
}
