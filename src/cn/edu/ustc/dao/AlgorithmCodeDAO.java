package cn.edu.ustc.dao;

import java.sql.SQLException;
import java.util.List;

/**
 * @author SiriusPaul
 * @version V1.0
 * @CreateDate 2025/5/31
 * @Description
 */
public interface AlgorithmCodeDAO {
    List<String> findAlgorithmNamesByProblemId(String problemId) throws SQLException;
    String findCodeByProblemIdAndAlgorithmName(String problemId, String algorithmName) throws SQLException;
    String findClassNameByProblemIdAndAlgorithmName(String problemId, String algorithmName) throws SQLException;

}
