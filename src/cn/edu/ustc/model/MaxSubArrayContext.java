package cn.edu.ustc.model;

/**
 * @author SiriusPaul
 * @version V1.0
 * @CreateDate 2025/3/14
 * @Description
 */
public class MaxSubArrayContext {
    private MaxSubArrayStrategy strategy;
    public MaxSubArrayContext() {
    }
    public MaxSubArrayContext(MaxSubArrayStrategy strategy) {
        this.strategy = strategy;
    }
    public void setStrategy(MaxSubArrayStrategy strategy) {
        this.strategy = strategy;
    }
    public MaxSubArrayResult executeStrategy(int[] arr) {
        return strategy.calc(arr);
    }
}
