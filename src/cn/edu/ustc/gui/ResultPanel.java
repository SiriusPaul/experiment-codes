package cn.edu.ustc.gui;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * @author SiriusPaul
 * @version V1.0
 * @CreateDate 2025/3/28
 * @Description
 */

public class ResultPanel extends VBox {

    private final Label titleLabel;
    private final TextArea resultArea;

    public ResultPanel() {
        setPadding(new Insets(10));
        setSpacing(10);

        titleLabel = new Label("执行结果");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 14));

        resultArea = new TextArea();
        resultArea.setEditable(false);
        resultArea.setPrefRowCount(5);

        getChildren().addAll(titleLabel, resultArea);
    }

    public void showResult(String... lines) {
        titleLabel.setTextFill(Color.GREEN);
        titleLabel.setText("执行成功");

        StringBuilder sb = new StringBuilder();
        for (String line : lines) {
            sb.append(line).append("\n");
        }
        resultArea.setText(sb.toString());
    }

    public void showError(String message) {
        titleLabel.setTextFill(Color.RED);
        titleLabel.setText("执行失败");
        resultArea.setText(message);
    }

    public void showMessage(String message) {
        titleLabel.setTextFill(Color.BLUE);
        titleLabel.setText("信息");
        resultArea.setText(message);
    }
}