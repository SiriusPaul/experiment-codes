package cn.edu.ustc.model;

/**
 * @author SiriusPaul
 * @version V1.0
 * @CreateDate 2025/5/23
 * @Description
 */
public class StringMatcherContext {
    private StringMatcherStrategy strategy;
    public StringMatcherContext() {
    }
    public StringMatcherContext(StringMatcherStrategy strategy) {
        this.strategy = strategy;
    }
    public void setStrategy(StringMatcherStrategy strategy) {
        this.strategy = strategy;
    }
    public StringMatchResult executeStrategy(String text, String pattern) {
        return strategy.calc(text, pattern);
    }
}
