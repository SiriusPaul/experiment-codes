package cn.edu.ustc.model;

/**
 * @author SiriusPaul
 * @version V1.0
 * @CreateDate 2025/3/28
 * @Description the entry of the application
 */

public enum ProblemType {
    MAX_SUBARRAY("最大子数组问题"),
    PROBLEM_TWO("问题二（未实现）"),
    PROBLEM_THREE("问题三（未实现）");

    private final String displayName;

    ProblemType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}