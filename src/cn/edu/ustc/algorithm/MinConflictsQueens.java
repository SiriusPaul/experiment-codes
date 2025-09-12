package cn.edu.ustc.algorithm;

import cn.edu.ustc.model.QueensSolutionResult;
import cn.edu.ustc.service.QueensStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author SiriusPaul
 * @version V1.0
 * @CreateDate 2025/5/17
 * @Description 这个实现使用最小冲突启发式算法来解决八皇后问题：
 * 从棋盘的随机初始状态开始
 * 迭代地选择一个有冲突的皇后，并将其移动到冲突最少的位置
 * 重复这个过程直到找到解或达到最大步数限制
 * 支持多次随机开始以增加找到解的概率
 * 最小冲突算法是一种局部搜索算法，对于大规模的N皇后问题效率通常比回溯法高，但它不保证能找到所有解决方案。
 */

public class MinConflictsQueens implements QueensStrategy {
    private final int maxSteps = 1000;  // 最大尝试步数
    private final Random random = new Random();

    @Override
    public QueensSolutionResult solve(int boardSize) {
        List<int[]> solutions = new ArrayList<>();

        // 多次尝试，增加找到解的概率
        for (int attempt = 0; attempt < 10; attempt++) {
            int[] solution = minConflictsSolve(boardSize);
            if (solution != null) {
                solutions.add(solution);
                break;  // 找到一个解即可
            }
        }

        return new QueensSolutionResult(solutions);
    }

    private int[] minConflictsSolve(int boardSize) {
        // 初始化随机状态
        int[] board = getInitialState(boardSize);

        for (int step = 0; step < maxSteps; step++) {
            // 检查是否找到解
            if (isComplete(board)) {
                return board;
            }

            // 选择一个有冲突的皇后
            int row = selectQueenWithConflict(board);

            // 将皇后移动到冲突最少的位置
            int minConflictsCol = getMinConflictsPosition(board, row);
            board[row] = minConflictsCol;
        }

        // 达到最大步数仍未找到解，返回null
        return null;
    }

    private int[] getInitialState(int boardSize) {
        int[] board = new int[boardSize];
        for (int i = 0; i < boardSize; i++) {
            board[i] = random.nextInt(boardSize);
        }
        return board;
    }

    private boolean isComplete(int[] board) {
        for (int row = 0; row < board.length; row++) {
            if (countConflicts(board, row, board[row]) > 0) {
                return false;
            }
        }
        return true;
    }

    private int selectQueenWithConflict(int[] board) {
        List<Integer> conflictRows = new ArrayList<>();

        for (int row = 0; row < board.length; row++) {
            if (countConflicts(board, row, board[row]) > 0) {
                conflictRows.add(row);
            }
        }

        if (conflictRows.isEmpty()) {
            // 如果没有冲突，随机选择一行
            return random.nextInt(board.length);
        } else {
            // 从有冲突的行中随机选择
            return conflictRows.get(random.nextInt(conflictRows.size()));
        }
    }

    private int getMinConflictsPosition(int[] board, int row) {
        int boardSize = board.length;
        int minConflicts = boardSize;
        List<Integer> minConflictPositions = new ArrayList<>();

        // 寻找冲突最少的位置
        for (int col = 0; col < boardSize; col++) {
            int conflicts = countConflicts(board, row, col);

            if (conflicts < minConflicts) {
                minConflicts = conflicts;
                minConflictPositions.clear();
                minConflictPositions.add(col);
            } else if (conflicts == minConflicts) {
                minConflictPositions.add(col);
            }
        }

        // 在冲突最少的位置中随机选择一个
        return minConflictPositions.get(random.nextInt(minConflictPositions.size()));
    }

    private int countConflicts(int[] board, int row, int col) {
        int conflicts = 0;

        for (int i = 0; i < board.length; i++) {
            if (i == row) continue;  // 跳过与自身的比较

            // 检查皇后是否相互攻击
            if (board[i] == col ||  // 同列
                    Math.abs(board[i] - col) == Math.abs(i - row)) {  // 对角线
                conflicts++;
            }
        }

        return conflicts;
    }
}
