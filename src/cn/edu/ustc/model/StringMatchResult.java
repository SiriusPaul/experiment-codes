package cn.edu.ustc.model;

import java.util.List;

/**
 * @author SiriusPaul
 * @version V1.0
 * @CreateDate 2025/5/18
 * @Description
 */
    public class StringMatchResult {
        private List<Integer> positions;

        // 匹配位置列表
        public StringMatchResult(List<Integer> positions) {
            this.positions = positions;
        }

        // 匹配位置列表和执行时间
        public List<Integer> getPositions() {
            return positions;
        }

        // 获取匹配次数
        public int getMatchCount() {
            return positions.size();
        }

        // 获取匹配位置
        @Override
        public String toString() {
            return "匹配次数: " + positions.size() +
                    "\n匹配位置: " + positions;
        }
}
