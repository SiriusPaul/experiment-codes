package cn.edu.ustc.dao;

import cn.edu.ustc.model.ProblemInfo;

import java.sql.SQLException;

/**
 * @author SiriusPaul
 * @version V1.0
 * @CreateDate 2025/5/31
 * @Description
 */
public interface ProblemInfoDAO {
    ProblemInfo findById(String problemId) throws SQLException;

}
