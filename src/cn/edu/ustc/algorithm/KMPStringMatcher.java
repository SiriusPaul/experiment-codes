package cn.edu.ustc.algorithm;

import cn.edu.ustc.model.StringMatchResult;

import java.util.ArrayList;
import java.util.List;

/**
 * @author SiriusPaul
 * @version V1.0
 * @CreateDate 2025/5/18
 * @Description
 */
public class KMPStringMatcher {
    /**
     * 使用KMP算法查找模式串在文本串中的所有出现位置
     *
     * @param text    文本串
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

        // 构建部分匹配表(next数组)
        int[] next = buildNext(pattern);

        // KMP搜索
        // 文本串指针
        int i = 0;
        // 模式串指针
        int j = 0;

        while (i < n) {
            if (j == -1 || text.charAt(i) == pattern.charAt(j)) {
                i++;
                j++;
            } else {
                j = next[j];
            }

            // 找到一个匹配
            if (j == m) {
                positions.add(i - j);
                // 继续查找下一个匹配
                j = next[j - 1];
            }
        }

        return new StringMatchResult(positions);
    }


    // 构建KMP算法的部分匹配表(next数组)
    private int[] buildNext(String pattern) {
        int m = pattern.length();
        int[] next = new int[m + 1];

        next[0] = -1;
        int i = 0, j = -1;

        while (i < m) {
            if (j == -1 || pattern.charAt(i) == pattern.charAt(j)) {
                i++;
                j++;
                next[i] = j;
            } else {
                j = next[j];
            }
        }

        return next;
    }
}
