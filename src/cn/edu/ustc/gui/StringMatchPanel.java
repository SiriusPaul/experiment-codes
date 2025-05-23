package cn.edu.ustc.gui;

import cn.edu.ustc.algorithm.BoyerMooreStringMatcher;
import cn.edu.ustc.algorithm.BruteForceStringMatcher;
import cn.edu.ustc.algorithm.KMPStringMatcher;
import cn.edu.ustc.model.StringMatchResult;
import cn.edu.ustc.util.AlgorithmTimer;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
/**
 * @author SiriusPaul
 * @version V1.0
 * @CreateDate 2025/5/19
 * @Description
 */
public class StringMatchPanel extends VBox {
    private TextField textField;
    private TextField patternField;
    private TextArea resultArea;

    public StringMatchPanel() {
        setPadding(new Insets(10));
        setSpacing(10);

        // 创建输入区域
        GridPane inputGrid = new GridPane();
        inputGrid.setHgap(10);
        inputGrid.setVgap(10);

        inputGrid.add(new Label("文本串:"), 0, 0);
        textField = new TextField();
        textField.setPrefWidth(400);
        inputGrid.add(textField, 1, 0);

        inputGrid.add(new Label("模式串:"), 0, 1);
        patternField = new TextField();
        inputGrid.add(patternField, 1, 1);

        Button runButton = new Button("运行匹配算法");
        runButton.setOnAction(e -> runMatching());
        inputGrid.add(runButton, 1, 2);

        // 结果显示区域
        resultArea = new TextArea();
        resultArea.setEditable(false);
        resultArea.setPrefHeight(300);

        getChildren().addAll(inputGrid, resultArea);
    }

    private void runMatching() {
        String text = textField.getText();
        String pattern = patternField.getText();

        if (text.isEmpty() || pattern.isEmpty()) {
            resultArea.setText("请输入有效的文本和模式串");
            return;
        }

        StringBuilder results = new StringBuilder("字符串匹配结果：\n\n");

        // 运行枚举算法
        BruteForceStringMatcher enumMatcher = new BruteForceStringMatcher();
        AlgorithmTimer.TimingResult<StringMatchResult> enumResult =
                AlgorithmTimer.measure(() -> enumMatcher.calc(text, pattern));

        results.append("【枚举暴力匹配算法】\n");
        results.append(enumResult.result().toString()).append("\n");
        results.append(String.format("执行时间: %.3f 毫秒\n\n", enumResult.getExecutionTimeMillis()));

        // 运行KMP算法
        KMPStringMatcher kmpMatcher = new KMPStringMatcher();
        AlgorithmTimer.TimingResult<StringMatchResult> kmpResult =
                AlgorithmTimer.measure(() -> kmpMatcher.calc(text, pattern));

        results.append("【KMP算法】\n");
        results.append(kmpResult.result().toString()).append("\n");
        results.append(String.format("执行时间: %.3f 毫秒\n\n", kmpResult.getExecutionTimeMillis()));

        // 运行Boyer-Moore算法
        BoyerMooreStringMatcher bmMatcher = new BoyerMooreStringMatcher();
        AlgorithmTimer.TimingResult<StringMatchResult> bmResult =
                AlgorithmTimer.measure(() -> bmMatcher.calc(text, pattern));

        results.append("【Boyer-Moore算法】\n");
        results.append(bmResult.result().toString()).append("\n");
        results.append(String.format("执行时间: %.3f 毫秒", bmResult.getExecutionTimeMillis()));

        resultArea.setText(results.toString());
    }
}
