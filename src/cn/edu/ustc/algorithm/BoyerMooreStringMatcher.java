package cn.edu.ustc.algorithm;

import cn.edu.ustc.model.StringMatchResult;
import cn.edu.ustc.model.StringMatcherStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author SiriusPaul
 * @version V1.0
 * @CreateDate 2025/5/18
 * @Description
 */
public class BoyerMooreStringMatcher implements StringMatcherStrategy {
    /**
     * 使用Boyer-Moore算法查找模式串在文本串中的所有出现位置
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
        // 计算文本串和模式串的长度
        int n = text.length();
        int m = pattern.length();

        Map<Character, Integer> badCharTable = buildBadCharTable(pattern);

        // Boyer-Moore搜索过程
        // 从文本串的开头开始匹配
        int i = 0;
        while (i <= n - m) {
            // 从模式串的最后一个字符开始比较
            // 如果匹配失败，根据坏字符规则计算移动距离
            int j = m - 1;
            // 从右向左比较字符
            while (j >= 0 && pattern.charAt(j) == text.charAt(i + j)) {
                j--;
            }

            //如果匹配成功，记录匹配位置并向右移动一位继续查找下一个匹配
            if (j < 0) {
                positions.add(i);
                i++;  // 向右移动一位继续查找下一个匹配
            } else {
                // 计算坏字符规则下的移动距离
                int shift = j - badCharTable.getOrDefault(text.charAt(i + j), -1);
                // 至少移动1位
                i += Math.max(1, shift);
            }
        }

        return new StringMatchResult(positions);
    }

    /**
     * 构建坏字符规则表
     */
    private Map<Character, Integer> buildBadCharTable(String pattern) {
        Map<Character, Integer> table = new HashMap<>();
        int m = pattern.length();

        for (int i = 0; i < m; i++) {
            table.put(pattern.charAt(i), i);
        }

        return table;
    }
}
