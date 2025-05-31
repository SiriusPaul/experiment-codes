package cn.edu.ustc.service;

import cn.edu.ustc.dao.AlgorithmCodeDAO;
import cn.edu.ustc.dao.impl.AlgorithmCodeDAOImpl;
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
 * @CreateDate 2025/5/17
 * @Description 根据题目id从数据库中获取算法代码，算法名称，类名等信息
 */
public class AlgorithmCodeService {
    private final AlgorithmCodeDAO algorithmCodeDAO;

    public AlgorithmCodeService() {
        this.algorithmCodeDAO = new AlgorithmCodeDAOImpl();
    }

    // 用于测试或依赖注入
    public AlgorithmCodeService(AlgorithmCodeDAO algorithmCodeDAO) {
        this.algorithmCodeDAO = algorithmCodeDAO;
    }

    // 获取算法名称列表
    public List<String> getAlgorithmNames(String problemId) {
        try {
            return algorithmCodeDAO.findAlgorithmNamesByProblemId(problemId);
        } catch (SQLException ex) {
            throw GlobalExceptionHandler.convertToRuntime(ex, "获取算法名称列表失败: " + problemId);
        }
    }

    // 获取算法代码
    public String getCode(String problemId, String algorithmName) {
        try {
            return algorithmCodeDAO.findCodeByProblemIdAndAlgorithmName(problemId, algorithmName);
        } catch (SQLException ex) {
            throw GlobalExceptionHandler.convertToRuntime(ex, "获取算法代码失败: " + problemId + ", " + algorithmName);
        }
    }

    // 获取类名
    public String getClassName(String problemId, String algorithmName) {
        try {
            return algorithmCodeDAO.findClassNameByProblemIdAndAlgorithmName(problemId, algorithmName);
        } catch (SQLException ex) {
            throw GlobalExceptionHandler.convertToRuntime(ex, "获取类名失败: " + problemId + ", " + algorithmName);
        }
    }
}
