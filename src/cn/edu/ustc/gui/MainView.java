package cn.edu.ustc.gui;

import cn.edu.ustc.model.ProblemType;
import javafx.scene.layout.BorderPane;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Priority;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * @author SiriusPaul
 * @version V1.0
 * @CreateDate 2025/3/28
 * @Description 主视图类，负责显示问题选择器和问题面板
 */
public class MainView extends BorderPane {
    private final ComboBox<ProblemType> problemSelector;
    private final ProblemPanel problemPanel;

    public MainView() {
        // 创建顶部的问题选择器
        problemSelector = new ComboBox<>();
        problemSelector.getItems().addAll(ProblemType.values());
        problemSelector.setValue(ProblemType.MAX_SUBARRAY);

        Button aboutButton = new Button("关于作者");
        aboutButton.setOnAction(e -> showAboutAuthor());

        // 创建顶部布局
        HBox topControls = new HBox(10);
        topControls.getChildren().addAll(new Label("选择问题:"), problemSelector);

        HBox rightControls = new HBox();
        rightControls.getChildren().add(aboutButton);
        HBox.setHgrow(rightControls, Priority.ALWAYS);
        rightControls.setStyle("-fx-alignment: center-right;");

        HBox topBar = new HBox(10);
        topBar.getChildren().addAll(topControls, rightControls);
        HBox.setHgrow(rightControls, Priority.ALWAYS);

        VBox topPanel = new VBox(10);
        topPanel.setPadding(new Insets(10));
        topPanel.getChildren().add(topBar);

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

    private void showAboutAuthor() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("关于作者");
        alert.setHeaderText("算法可视化系统");
        alert.setContentText("作者：SiriusPaul\n版本：V1.0\n创建日期：2025/3/28\n" +
                "本系统用于展示和比较不同算法的实现与性能\nhttps://github.com/SiriusPaul/experiment-codes");
        alert.showAndWait();
    }
}