package cn.edu.ustc.model;

import java.util.List;

/**
 * @author SiriusPaul
 * @version V1.0
 * @CreateDate 2025/5/17
 * @Description
 */
public class QueensSolutionResult {
    // 解决方案列表（每个数组表示皇后的位置）
    private List<int[]> solutions;

    public QueensSolutionResult(List<int[]> solutions) {
        this.solutions = solutions;
    }

    public List<int[]> getSolutions() {
        return solutions;
    }

    public int getSolutionCount() {
        return solutions.size();
    }
}
