package cn.edu.ustc.dao;

import cn.edu.ustc.model.TestCase;

import java.sql.SQLException;
import java.util.List;

/**
 * @author SiriusPaul
 * @version V1.0
 * @CreateDate 2025/5/31
 * @Description
 */
public interface TestCaseDAO {
    List<TestCase> findByProblemId(String problemId) throws SQLException;
}
