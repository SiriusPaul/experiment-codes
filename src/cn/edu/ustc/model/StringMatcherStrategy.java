package cn.edu.ustc.model;

/**
 * @author SiriusPaul
 * @version V1.0
 * @CreateDate 2025/5/23
 * @Description
 */
public interface StringMatcherStrategy {
    StringMatchResult calc(String text, String pattern);
}
