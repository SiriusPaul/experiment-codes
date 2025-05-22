package cn.edu.ustc.gui.strategy;

import cn.edu.ustc.model.TestCase;
import javafx.scene.Node;
import java.util.List;
/**
 * @author SiriusPaul
 * @version V1.0
 * @CreateDate 2025/5/21
 * @Description
 */
public interface ProblemStrategy {
    // 加载测试用例
    void loadTestCases(String problemId);

    // 创建问题特定的UI控件
    List<Node> createProblemControls();

    // 执行算法
    void execute() throws Exception;

    // 准备UI组件
    void prepareUI(boolean visible);

    // 获取测试用例
    void loadTestCaseInputs();

    // 显示基本结果的默认方法
    default void displayBasicResult(Object input, Object result, StringBuilder sb) {
        sb.append("执行结果：\n\n");
        sb.append("输入: ").append(input).append("\n");
        sb.append("结果: ").append(result).append("\n");
    }

    // 单独的结果验证方法
    default void verifyResult(Object result, TestCase testCase, StringBuilder sb) {
        if (testCase != null) {
            String expected = testCase.getExpectedOutput();
            if (expected != null && !expected.isEmpty()) {
                sb.append("\n期望结果: ").append(expected);
                boolean matches = String.valueOf(result).equals(expected);
                sb.append(matches ? "\n√ 结果匹配" : "\n× 结果不匹配");
            }
        }
    }

//    // 通用结果显示
//    void displayResult(Object input, Object result);
}
