package cn.edu.ustc.gui.strategy;

import cn.edu.ustc.gui.ProblemPanel;
import cn.edu.ustc.gui.factory.UIFactory;
import cn.edu.ustc.model.StringMatchResult;
import cn.edu.ustc.model.StringMatchTestCase;
import cn.edu.ustc.model.TestCase;
import cn.edu.ustc.service.TestCaseService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

/**
 * @author SiriusPaul
 * @version V1.0
 * @CreateDate 2025/5/21
 * @Description 字符串匹配问题策略
 */
public class RunStringMatchStrategy implements RunProblemStrategy {
    private final ProblemPanel panel;
    private final TestCaseService testCaseService;
    private final javafx.scene.control.TextField textField;
    private final javafx.scene.control.TextField patternField;
    private final VBox controlsContainer;
    private final CheckBox useCustomTestCheckbox;

    public RunStringMatchStrategy(ProblemPanel panel, TestCaseService testCaseService) {
        this.panel = panel;
        this.testCaseService = testCaseService;

        // 使用UIFactory创建美化后的组件
        textField = UIFactory.createTextField(300, 25, "输入文本");
        patternField = UIFactory.createTextField(150, 25, "输入模式串");

        // 使用面板提供的复选框
        useCustomTestCheckbox = panel.getUseCustomTestCheckbox();
        useCustomTestCheckbox.setOnAction(e ->
                panel.getTestCaseSelector().setDisable(useCustomTestCheckbox.isSelected()));

        // 使用UIFactory创建标准化的布局
        HBox inputBox = UIFactory.createHBox(10, new Insets(5, 10, 5, 10), Pos.CENTER_LEFT,
                UIFactory.createLabel("文本:"), textField,
                UIFactory.createLabel("模式:"), patternField);

        // 包装在VBox中以便整体控制
        controlsContainer = UIFactory.createVBox(5, new Insets(10), useCustomTestCheckbox, inputBox);
    }

    @Override
    public void loadTestCases(String problemId) {
        try {
            var testCases = testCaseService.getTestCases(problemId);
            panel.getTestCaseSelector().getItems().clear();
            panel.getTestCaseSelector().getItems().addAll(testCases);

            if (!testCases.isEmpty()) {
                panel.getTestCaseSelector().setValue(testCases.getFirst());
                loadTestCaseInputs();
            }
        } catch (Exception e) {
            panel.showAlert("加载测试用例失败: " + e.getMessage());
        }
    }

    @Override
    public void loadTestCaseInputs() {
        TestCase testCase = panel.getTestCaseSelector().getValue();
        if (testCase instanceof StringMatchTestCase stringMatchCase) {
            textField.setText(stringMatchCase.getText());
            patternField.setText(stringMatchCase.getPattern());
        }
    }

    @Override
    public List<Node> createProblemControls() {
        return List.of(controlsContainer);
    }

    @Override
    public void prepareUI(boolean visible) {
        controlsContainer.setVisible(visible);
        useCustomTestCheckbox.setVisible(visible);
        panel.getTestCaseSelector().setVisible(visible);

        // 为测试用例选择器添加事件处理
        panel.getTestCaseSelector().setOnAction(e -> loadTestCaseInputs());
    }

    @Override
    public void execute() throws Exception {
        String text = textField.getText();
        String pattern = patternField.getText();

        if (text.isEmpty() || pattern.isEmpty()) {
            panel.getResultArea().setText("请输入有效的文本和模式串");
            return;
        }

        panel.compileAndRunCode(text, pattern, "calc",
                String.class, String.class,
                "cn.edu.ustc.model.StringMatchResult");
    }

    // 显示字符串匹配结果
    public void displayResult(String text, String pattern, StringMatchResult result) {
        StringBuilder sb = new StringBuilder("字符串匹配结果：\n\n");
        sb.append("文本: ").append(text).append("\n");
        sb.append("模式: ").append(pattern).append("\n\n");
        sb.append(result.toString());

        // 只有在非自定义测试模式才验证结果
        TestCase testCase = panel.getTestCaseSelector().getValue();
        if (testCase != null && !useCustomTestCheckbox.isSelected()) {
            String expectedOutput = testCase.getExpectedOutput();
            if (expectedOutput != null && !expectedOutput.isEmpty()) {
                sb.append("\n\n期望结果: ").append(expectedOutput);
                String positionsStr = result.getPositions().toString();

                String extractedExpected = expectedOutput;
                if (expectedOutput.contains("[")) {
                    extractedExpected = expectedOutput.substring(expectedOutput.indexOf("["));
                }

                boolean matches = positionsStr.equals(extractedExpected);
                sb.append(matches ? "\n√ 结果匹配" : "\n× 结果不匹配");
            }
        }

        panel.getResultArea().setText(sb.toString());
    }

    // Getters for testing
    public javafx.scene.control.TextField getTextField() {
        return textField;
    }

    public javafx.scene.control.TextField getPatternField() {
        return patternField;
    }
}