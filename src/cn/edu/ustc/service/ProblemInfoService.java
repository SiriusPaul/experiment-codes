package cn.edu.ustc.service;

import cn.edu.ustc.dao.ProblemInfoDAO;
import cn.edu.ustc.dao.impl.ProblemInfoDAOImpl;
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
 * @CreateDate 2025/5/22
 * @Description
 */
public class ProblemInfoService {
    private final ProblemInfoDAO problemInfoDAO;

    public ProblemInfoService() {
        this.problemInfoDAO = new ProblemInfoDAOImpl();
    }

    // 用于测试或依赖注入
    public ProblemInfoService(ProblemInfoDAO problemInfoDAO) {
        this.problemInfoDAO = problemInfoDAO;
    }

    public ProblemInfo getProblemInfo(String problemId) {
        try {
            return problemInfoDAO.findById(problemId);
        } catch (SQLException ex) {
            throw GlobalExceptionHandler.convertToRuntime(ex, "获取问题信息失败: " + problemId);
        }
    }
}
