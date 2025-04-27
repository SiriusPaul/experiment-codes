package cn.edu.ustc.gui;

import cn.edu.ustc.model.Context;
import cn.edu.ustc.service.Strategy;
import cn.edu.ustc.algorithm.EnumerationMaxSubArray;
import cn.edu.ustc.algorithm.DynamicProgrammingMaxSubArray;
import cn.edu.ustc.model.MaxSubArrayResult;
import cn.edu.ustc.model.ProblemType;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 * @author SiriusPaul
 * @version V1.0
 * @CreateDate 2025/3/28
 * @Description
 */

public class ProblemPanel extends BorderPane {
    private final CodePanel codePanel;
    private final ResultPanel resultPanel;
    private ProblemType currentProblem;
    private final ComboBox<String> algorithmSelector;

    public ProblemPanel() {
        // 创建算法选择器
        algorithmSelector = new ComboBox<>();
        algorithmSelector.getItems().addAll("枚举法", "动态规划法");
        algorithmSelector.setValue("枚举法");

        // 创建输入面板和算法选择器的容器
        VBox inputContainer = new VBox(10);
        inputContainer.setPadding(new Insets(10));

        // 输入面板
        InputPanel inputPanel = new InputPanel(this::executeAlgorithm);

        // 添加算法选择器和输入面板到容器
        inputContainer.getChildren().addAll(
                new Label("选择算法:"),
                algorithmSelector,
                inputPanel
        );

        // 创建Tab面板
        TabPane tabPane = new TabPane();

        // 添加输入面板Tab
        Tab inputTab = new Tab("数据输入", inputContainer);
        inputTab.setClosable(false);

        // 代码面板
        codePanel = new CodePanel();
        Tab codeTab = new Tab("源代码查看", codePanel);
        codeTab.setClosable(false);

        tabPane.getTabs().addAll(inputTab, codeTab);

        // 结果面板
        resultPanel = new ResultPanel();

        // 设置布局
        setPadding(new Insets(10));
        setCenter(tabPane);
        setBottom(resultPanel);
    }

    public void switchProblem(ProblemType problemType) {
        this.currentProblem = problemType;

        switch (problemType) {
            case MAX_SUBARRAY:
                // 从配置文件加载算法
                codePanel.loadAlgorithmsForProblem("MAX_SUBARRAY");
                break;
            case PROBLEM_TWO:
                // 如果有配置，可以加载
                codePanel.loadAlgorithmsForProblem("PROBLEM_TWO");
                resultPanel.showMessage("此问题暂未实现");
                break;
            case PROBLEM_THREE:
                codePanel.clearCode();
                resultPanel.showMessage("此问题暂未实现");
                break;
        }
    }

    public void executeAlgorithm(int[] data) {
        if (currentProblem == ProblemType.MAX_SUBARRAY) {
            try {
                // 根据选择设置策略
                String selectedAlgorithm = algorithmSelector.getValue();
                Strategy strategy;

                if ("动态规划法".equals(selectedAlgorithm)) {
                    strategy = new DynamicProgrammingMaxSubArray();
                } else {
                    // 默认使用枚举法
                    strategy = new EnumerationMaxSubArray();
                }

                // 创建上下文并执行算法
                Context context = new Context();
                context.setStrategy(strategy);
                MaxSubArrayResult result = context.executeStrategy(data);

                // 显示结果
                resultPanel.showResult(
                        "最大子数组和: " + result.getMaxSum(),
                        "开始索引: " + result.getStartIndex(),
                        "结束索引: " + result.getEndIndex()
                );
            } catch (Exception e) {
                resultPanel.showError("计算错误: " + e.getMessage());
            }
        } else {
            resultPanel.showMessage("此问题暂未实现");
        }
    }
}