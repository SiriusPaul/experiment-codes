package cn.edu.ustc.model;

/**
 * @author SiriusPaul
 * @version V1.0
 * @CreateDate 2025/3/28
 * @Description 问题类型枚举类，定义了可视化工具中支持的所有问题类型及其对应的ID和显示名称
 */

public enum ProblemType {
    MAX_SUBARRAY("max-subarray", "最大子数组问题"),
    PROBLEM_TWO("queens","八皇后问题"),
    PROBLEM_THREE("2","问题三（未实现）");


    private final String id;
    private final String displayName;

    ProblemType(String id, String displayName) {
        this.id = id;
        this.displayName = displayName;
    }

    public String getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}