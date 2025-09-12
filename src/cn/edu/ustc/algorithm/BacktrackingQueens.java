package cn.edu.ustc.algorithm;

import cn.edu.ustc.model.QueensSolutionResult;
import cn.edu.ustc.service.QueensStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * @author SiriusPaul
 * @version V1.0
 * @CreateDate 2025/5/17
 * @Description 回溯算法求解八皇后问题
 * 回溯算法是一种通过尝试不同的选择来解决问题的算法。
 * 它通常用于解决组合问题、排列问题和约束满足问题。
 * 回溯算法的基本思想是：从一个初始状态开始，通过尝试不同的选择来扩展当前状态，
 * 直到找到一个满足问题要求的解决方案或者确定当前状态无法继续扩展。
 * 如果当前状态无法继续扩展，回溯算法会回溯到上一个状态，尝试其他选择，
 * 直到找到一个满足要求的解决方案或者回溯到初始状态。
 * 回溯算法通常使用递归实现，通过递归调用来扩展当前状态，
 * 并在递归返回时回溯到上一个状态。
 * 回溯算法的时间复杂度通常较高，因为它需要尝试所有可能的选择。
 * 但是，回溯算法在某些问题上具有一定的优势，
 * 例如在组合问题中，可以找到所有可能的解决方案。
 */
public class BacktrackingQueens implements QueensStrategy {
    @Override
    public QueensSolutionResult solve(int boardSize) {
        List<int[]> solutions = new ArrayList<>();
        int[] queens = new int[boardSize];
        backtrack(queens, 0, solutions);
        return new QueensSolutionResult(solutions);
    }

    private void backtrack(int[] queens, int row, List<int[]> solutions) {
        int n = queens.length;

        if (row == n) {
            // 找到一个解决方案，添加到列表
            solutions.add(queens.clone());
            return;
        }

        for (int col = 0; col < n; col++) {
            queens[row] = col;
            if (isValid(queens, row)) {
                backtrack(queens, row + 1, solutions);
            }
        }
    }

    private boolean isValid(int[] queens, int row) {
        for (int i = 0; i < row; i++) {
            // 检查皇后是否可以互相攻击
            if (queens[i] == queens[row]) { // 同一列
                return false;
            }
            if (Math.abs(queens[i] - queens[row]) == Math.abs(i - row)) { // 对角线
                return false;
            }
        }
        return true;
    }
}
