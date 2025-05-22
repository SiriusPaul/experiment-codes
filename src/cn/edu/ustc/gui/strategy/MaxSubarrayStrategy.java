package cn.edu.ustc.gui.strategy;

import cn.edu.ustc.gui.ProblemPanel;
import cn.edu.ustc.model.TestCase;
import cn.edu.ustc.service.TestCaseService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Arrays;
import java.util.List;

/**
 * @author SiriusPaul
 * @version V1.0
 * @CreateDate 2025/5/21
 * @Description 最大子数组问题策略
 */
public class MaxSubarrayStrategy implements ProblemStrategy {
    private final ProblemPanel panel;
    private final TestCaseService testCaseService;
    private final TextField customArrayField;
    private final VBox controlsContainer;
    private final CheckBox useCustomTestCheckbox;

    public MaxSubarrayStrategy(ProblemPanel panel, TestCaseService testCaseService) {
        this.panel = panel;
        this.testCaseService = testCaseService;

        // 创建组件
        customArrayField = new TextField();
        customArrayField.setPromptText("输入数组，例如：-2,1,-3,4,-1,2,1,-5,4");
        customArrayField.setPrefWidth(300);

        // 使用面板提供的复选框
        useCustomTestCheckbox = panel.getUseCustomTestCheckbox();
        useCustomTestCheckbox.setOnAction(e ->
                panel.getTestCaseSelector().setDisable(useCustomTestCheckbox.isSelected()));

        // 创建标准化的布局
        HBox inputBox = new HBox(10, new Label("数组:"), customArrayField);
        inputBox.setPadding(new Insets(5, 10, 5, 10));
        inputBox.setAlignment(Pos.CENTER_LEFT);

        // 包装在VBox中以便整体控制
        controlsContainer = new VBox(5, useCustomTestCheckbox,inputBox);
        controlsContainer.setPadding(new Insets(10));
    }

    @Override
    public void loadTestCases(String problemId) {
        try {
            var testCases = testCaseService.getTestCases(problemId);
            panel.getTestCaseSelector().getItems().clear();
            panel.getTestCaseSelector().getItems().addAll(testCases);

            if (!testCases.isEmpty()) {
                panel.getTestCaseSelector().setValue(testCases.getFirst());
            }
        } catch (Exception e) {
            panel.showAlert("加载测试用例失败: " + e.getMessage());
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
    }

    @Override
    public void loadTestCaseInputs() {

    }

    @Override
    public void execute() throws Exception {
        if (useCustomTestCheckbox.isSelected()) {
            try {
                String[] values = customArrayField.getText().split(",");
                int[] customArray = Arrays.stream(values)
                        .map(String::trim)
                        .mapToInt(Integer::parseInt)
                        .toArray();

                panel.compileAndRunCode(customArray, null, "calc",
                        int[].class, null, null);
            } catch (NumberFormatException e) {
                panel.getResultArea().setText("无效的数组格式。请使用逗号分隔的整数。");
            }
        } else {
            TestCase testCase = panel.getTestCaseSelector().getValue();
            if (testCase != null) {
                panel.compileAndRunCode(testCase.getInputArray(), null, "calc",
                        int[].class, null, null);
            } else {
                panel.getResultArea().setText("请选择一个测试用例");
            }
        }
    }

    // 显示结果
    public void displayResult(int[] input, int result) {
        StringBuilder sb = new StringBuilder();

        // 显示基本结果
        sb.append("执行结果：\n\n");
        sb.append("输入: ").append(Arrays.toString(input)).append("\n");
        sb.append("结果: ").append(result).append("\n");

        // 只有在非自定义测试模式才验证结果
        TestCase testCase = panel.getTestCaseSelector().getValue();
        if (testCase != null && !useCustomTestCheckbox.isSelected()) {
            String expected = testCase.getExpectedOutput();
            if (expected != null && !expected.isEmpty()) {
                sb.append("\n期望结果: ").append(expected);
                boolean matches = String.valueOf(result).equals(expected);
                sb.append(matches ? "\n√ 结果匹配" : "\n× 结果不匹配");
            }
        }

        panel.getResultArea().setText(sb.toString());
    }

    public TextField getCustomArrayField() {
        return customArrayField;
    }
}