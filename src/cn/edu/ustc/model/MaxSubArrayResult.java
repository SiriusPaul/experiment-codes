package cn.edu.ustc.model;

/**
 * @author SiriusPaul
 * @version V1.0
 * @CreateDate 2025/3/10
 * @Description record the result of calc method
 */
public class MaxSubArrayResult {
    private final int maxSum;
    private final int startIndex;
    private final int endIndex;

    public MaxSubArrayResult(int maxSum, int startIndex, int endIndex) {
        this.maxSum = maxSum;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    public int getMaxSum() {
        return maxSum;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }
}
