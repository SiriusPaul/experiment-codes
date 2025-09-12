package cn.edu.ustc.service;

import cn.edu.ustc.dao.TestCaseDAO;
import cn.edu.ustc.dao.impl.TestCaseDAOImpl;
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
 * @CreateDate 2025/5/17
 * @Description 测试用例服务类,用于从数据库中获取测试用例信息,并封装为TestCase对象
 */
public class TestCaseService {
    private final TestCaseDAO testCaseDAO;

    public TestCaseService() {
        this.testCaseDAO = new TestCaseDAOImpl();
    }

    // 用于测试或依赖注入
    public TestCaseService(TestCaseDAO testCaseDAO) {
        this.testCaseDAO = testCaseDAO;
    }

    public List<TestCase> getTestCases(String problemId) {
        try {
            return testCaseDAO.findByProblemId(problemId);
        } catch (SQLException ex) {
            throw GlobalExceptionHandler.convertToRuntime(ex, "获取测试用例失败: " + problemId);
        }
    }
}