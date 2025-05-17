package cn.edu.ustc.model;

import cn.edu.ustc.service.MaxSubArrayStrategy;

/**
 * @author SiriusPaul
 * @version V1.0
 * @CreateDate 2025/3/14
 * @Description
 */
public class Context {
    private MaxSubArrayStrategy strategy;
    public Context() {
    }
    public Context(MaxSubArrayStrategy strategy) {
        this.strategy = strategy;
    }
    public void setStrategy(MaxSubArrayStrategy strategy) {
        this.strategy = strategy;
    }
    public MaxSubArrayResult executeStrategy(int[] arr) {
        return strategy.calc(arr);
    }
}
