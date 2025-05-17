package cn.edu.ustc.service;

import cn.edu.ustc.model.QueensSolutionResult;

/**
 * @author SiriusPaul
 * @version V1.0
 * @CreateDate 2025/5/17
 * @Description
 */
public interface QueensStrategy {
    QueensSolutionResult solve(int boardSize);
}
