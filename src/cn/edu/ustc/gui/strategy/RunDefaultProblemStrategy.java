package cn.edu.ustc.gui.strategy;

import cn.edu.ustc.gui.ProblemPanel;
import cn.edu.ustc.model.TestCase;
import cn.edu.ustc.service.TestCaseService;
import javafx.scene.Node;

import java.util.Collections;
import java.util.List;

/**
 * @author SiriusPaul
 * @version V1.0
 * @CreateDate 2025/5/21
 * @Description 默认问题策略，适用于未特殊处理的问题类型
 */
public class RunDefaultProblemStrategy implements RunProblemStrategy {
    private final ProblemPanel panel;
    private final TestCaseService testCaseService;

    public RunDefaultProblemStrategy(ProblemPanel panel, TestCaseService testCaseService) {
        this.panel = panel;
        this.testCaseService = testCaseService;
    }

    @Override
    public void loadTestCases(String problemId) {
        try {
            var testCases = testCaseService.getTestCases(problemId);
            panel.getTestCaseSelector().getItems().clear();
            panel.getTestCaseSelector().getItems().addAll(testCases);

            if (!testCases.isEmpty()) {
                panel.getTestCaseSelector().setValue(testCases.get(0));
            }
        } catch (Exception e) {
            panel.showAlert("加载测试用例失败: " + e.getMessage());
        }
    }

    @Override
    public List<Node> createProblemControls() {
        // 默认策略没有特殊控件
        return Collections.emptyList();
    }

    @Override
    public void prepareUI(boolean visible) {
        // 默认策略无需特殊UI控制
    }

    @Override
    public void loadTestCaseInputs() {

    }

    @Override
    public void execute() throws Exception {
        TestCase testCase = panel.getTestCaseSelector().getValue();

        if (testCase != null) {
            // 通用执行方法
            panel.compileAndRunCode(
                    testCase.getInputArray(),
                    null,
                    "solve",
                    Object[].class,
                    null,
                    null
            );
        } else {
            panel.getResultArea().setText("请选择一个测试用例");
        }
    }

    // 通用结果显示
    public void displayResult(Object input, Object result) {
        StringBuilder sb = new StringBuilder();

        // 始终显示基本结果
        displayBasicResult(input, result, sb);

        // 只有在非自定义测试模式才验证结果
        TestCase testCase = panel.getTestCaseSelector().getValue();
        if (testCase != null && !panel.getUseCustomTestCheckbox().isSelected()) {
            verifyResult(result, testCase, sb);
        }

        panel.getResultArea().setText(sb.toString());
    }
}