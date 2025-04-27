package cn.edu.ustc.gui;

import cn.edu.ustc.model.ProblemType;
import javafx.scene.layout.BorderPane;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * @author SiriusPaul
 * @version V1.0
 * @CreateDate 2025/3/28
 * @Description
 */
public class MainView extends BorderPane {
    private final ComboBox<ProblemType> problemSelector;
    private final ProblemPanel problemPanel;

    public MainView() {
        // 创建顶部的问题选择器
        problemSelector = new ComboBox<>();
        problemSelector.getItems().addAll(ProblemType.values());
        problemSelector.setValue(ProblemType.MAX_SUBARRAY);

        VBox topPanel = new VBox(10);
        topPanel.setPadding(new Insets(10));
        topPanel.getChildren().addAll(
                new Label("选择问题:"),
                problemSelector
        );

        // 创建中心的问题面板
        problemPanel = new ProblemPanel();

        // 监听问题选择变化
        problemSelector.setOnAction(e -> {
            ProblemType selectedProblem = problemSelector.getValue();
            problemPanel.switchProblem(selectedProblem);
        });

        // 设置布局
        setTop(topPanel);
        setCenter(problemPanel);

        // 初始化默认问题
        problemPanel.switchProblem(ProblemType.MAX_SUBARRAY);
    }
}
