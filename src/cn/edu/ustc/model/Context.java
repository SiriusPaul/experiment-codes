package cn.edu.ustc.model;

import cn.edu.ustc.service.Strategy;

/**
 * @author SiriusPaul
 * @version V1.0
 * @CreateDate 2025/3/14
 * @Description
 */
public class Context {
    private Strategy strategy;
    public Context() {
    }
    public Context(Strategy strategy) {
        this.strategy = strategy;
    }
    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }
    public MaxSubArrayResult executeStrategy(int[] arr) {
        return strategy.calc(arr);
    }
}
