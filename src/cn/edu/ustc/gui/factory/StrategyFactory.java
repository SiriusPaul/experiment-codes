package cn.edu.ustc.gui.factory;

import cn.edu.ustc.gui.ProblemPanel;
import cn.edu.ustc.gui.strategy.DefaultProblemStrategy;
import cn.edu.ustc.gui.strategy.MaxSubarrayStrategy;
import cn.edu.ustc.gui.strategy.ProblemStrategy;
import cn.edu.ustc.gui.strategy.StringMatchStrategy;
import cn.edu.ustc.model.ProblemType;
import cn.edu.ustc.gui.strategy.QueensStrategy;
import cn.edu.ustc.service.TestCaseService;

/**
 * @author SiriusPaul
 * @version V1.0
 * @CreateDate 2025/5/21
 * @Description
 */
public class StrategyFactory {
    public static ProblemStrategy createStrategy(ProblemType problemType,
                                                 ProblemPanel panel,
                                                 TestCaseService testCaseService) {
        if ("max-subarray".equals(problemType.getId()) ||
                "最大子数组问题".equals(problemType.getDisplayName())) {
            return new MaxSubarrayStrategy(panel, testCaseService);
        } else if ("string-match".equals(problemType.getId()) ||
                "字符串匹配问题".equals(problemType.getDisplayName())) {
            return new StringMatchStrategy(panel, testCaseService);
        } else if ("queens".equals(problemType.getId()) ||
                "八皇后问题".equals(problemType.getDisplayName())) {
            return new QueensStrategy(panel, testCaseService);
        }

        // 默认策略
        return new DefaultProblemStrategy(panel, testCaseService);
//        return null;
    }
}
