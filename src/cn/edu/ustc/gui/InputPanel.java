package cn.edu.ustc.gui;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

import java.util.function.Consumer;
/**
 * @author SiriusPaul
 * @version V1.0
 * @CreateDate 2025/3/28
 * @Description
 */

public class InputPanel extends VBox {

    private final TextArea inputArea;
    private final Consumer<int[]> onExecute;

    public InputPanel(Consumer<int[]> onExecute) {
        this.onExecute = onExecute;

        setPadding(new Insets(10));
        setSpacing(10);

        Label label = new Label("请输入数组（格式：10: 1, 2, -3, 4, 5, -6, 7, 8, -9, 10）:");

        inputArea = new TextArea();
        inputArea.setPrefRowCount(5);

        Button executeButton = new Button("运行算法");
        executeButton.setOnAction(e -> parseAndExecute());

        getChildren().addAll(label, inputArea, executeButton);
    }

    private void parseAndExecute() {
        String input = inputArea.getText().trim();

        try {
            // 解析格式如 "10: 1, 2, -3, 4, 5, -6, 7, 8, -9, 10"
            String[] parts = input.split(":", 2);
            if (parts.length != 2) {
                throw new IllegalArgumentException("输入格式错误，应为'数量: 数值1, 数值2, ...'");
            }

            String[] numberStrings = parts[1].split(",");
            int[] numbers = new int[numberStrings.length];

            for (int i = 0; i < numberStrings.length; i++) {
                numbers[i] = Integer.parseInt(numberStrings[i].trim());
            }

            onExecute.accept(numbers);
        } catch (Exception e) {
            // 输入解析错误时的处理
            onExecute.accept(new int[0]);
        }
    }
}