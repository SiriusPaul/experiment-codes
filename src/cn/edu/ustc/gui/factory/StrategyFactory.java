package cn.edu.ustc.gui.factory;

import cn.edu.ustc.gui.ProblemPanel;
import cn.edu.ustc.gui.strategy.RunDefaultProblemStrategy;
import cn.edu.ustc.gui.strategy.RunMaxSubArrayStrategy;
import cn.edu.ustc.gui.strategy.RunProblemStrategy;
import cn.edu.ustc.gui.strategy.RunStringMatchStrategy;
import cn.edu.ustc.model.ProblemType;
import cn.edu.ustc.gui.strategy.RunQueensStrategy;
import cn.edu.ustc.service.TestCaseService;

/**
 * @author SiriusPaul
 * @version V1.0
 * @CreateDate 2025/5/21
 * @Description
 */
public class StrategyFactory {
    public static RunProblemStrategy createStrategy(ProblemType problemType,
                                                    ProblemPanel panel,
                                                    TestCaseService testCaseService) {
        if ("max-subarray".equals(problemType.getId()) ||
                "最大子数组问题".equals(problemType.getDisplayName())) {
            return new RunMaxSubArrayStrategy(panel, testCaseService);
        } else if ("string-match".equals(problemType.getId()) ||
                "字符串匹配问题".equals(problemType.getDisplayName())) {
            return new RunStringMatchStrategy(panel, testCaseService);
        } else if ("queens".equals(problemType.getId()) ||
                "八皇后问题".equals(problemType.getDisplayName())) {
            return new RunQueensStrategy(panel, testCaseService);
        }

        // 默认策略
        return new RunDefaultProblemStrategy(panel, testCaseService);
//        return null;
    }
}
