package cn.edu.ustc.algorithm;

import cn.edu.ustc.model.StringMatchResult;
import cn.edu.ustc.model.StringMatcherStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * @author SiriusPaul
 * @version V1.0
 * @CreateDate 2025/5/18
 * @Description
 */
public class BruteForceStringMatcher implements StringMatcherStrategy {
    /**
     * 使用暴力匹配算法查找模式串在文本串中的所有出现位置
     *
     * @param text 文本串
     * @param pattern 模式串
     * @return 匹配结果，包含所有匹配位置和执行时间
     */
    public StringMatchResult calc(String text, String pattern) {
        List<Integer> positions = new ArrayList<>();

        if (text == null || pattern == null || pattern.length() > text.length()) {
            return new StringMatchResult(positions);
        }

        int n = text.length();
        int m = pattern.length();

        // 暴力匹配
        for (int i = 0; i <= n - m; i++) {
            int j;
            for (j = 0; j < m; j++) {
                if (text.charAt(i + j) != pattern.charAt(j)) {
                    break;
                }
            }

            // 找到一个匹配
            if (j == m) {
                positions.add(i);
            }
        }

        return new StringMatchResult(positions);
    }
}