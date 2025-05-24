package cn.edu.ustc.gui.factory;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * @author SiriusPaul
 * @version V1.0
 * @CreateDate 2025/5/21
 * @Description
 */
public class UIFactory {
    // 样式常量
    public static final String PRIMARY_COLOR = "#2196F3";
    public static final String PRIMARY_DARK_COLOR = "#1976D2";
    public static final String SECONDARY_COLOR = "#f5f5f5";
    public static final String ACCENT_COLOR = "#FF4081";
    public static final String SUCCESS_COLOR = "#4CAF50";
    public static final String WARNING_COLOR = "#FFC107";
    public static final String ERROR_COLOR = "#F44336";
    public static final String BASIC_COLOUR = "-fx-background-color: #f9f9f9;";

    public static final String BUTTON_STYLE =
            "-fx-background-color: " + PRIMARY_COLOR + ";" +
                    "-fx-text-fill: white;" +
                    "-fx-background-radius: 4px;" +
                    "-fx-font-weight: bold;" +
                    "-fx-padding: 8 15 8 15;";

    public static final String BUTTON_HOVER_STYLE =
            "-fx-background-color: derive(" + PRIMARY_COLOR + ", 20%); " +
                    "-fx-text-fill: white; -fx-cursor: hand; " +
                    "-fx-background-radius: 4px; -fx-font-weight: bold; " +
                    "-fx-padding: 8 15 8 15;";

    public static final String INFO_BUTTON_STYLE =
            "-fx-background-color: " + ACCENT_COLOR + ";" +
                    "-fx-text-fill: white;" +
                    "-fx-background-radius: 4px;" +
                    "-fx-font-weight: bold;";

    public static final String TEXT_AREA_STYLE =
            "-fx-font-family: 'Consolas', 'Courier New', monospace;" +
                    "-fx-font-size: 14px;" +
                    "-fx-border-color: #e0e0e0;" +
                    "-fx-border-radius: 4px;" +
                    "-fx-background-color: #f0f0f0;";

    public static final String COMBO_BOX_STYLE =
            "-fx-background-color: white;" +
                    "-fx-border-color: #e0e0e0;" +
                    "-fx-border-radius: 4px;";

    // 已有的工厂方法，增加样式应用
    public static <T> ComboBox<T> createComboBox(String prompt, double width) {
        ComboBox<T> comboBox = new ComboBox<>();
        comboBox.setPromptText(prompt);
        comboBox.setPrefWidth(width);
        comboBox.setStyle(COMBO_BOX_STYLE);
        return comboBox;
    }

    public static TextField createTextField(double width, double height, String prompt) {
        TextField textField = new TextField();
        textField.setPrefWidth(width);
        textField.setPrefHeight(height);
        textField.setStyle("-fx-border-color: #e0e0e0; -fx-border-radius: 4px;");
        if (prompt != null) textField.setPromptText(prompt);
        return textField;
    }

    public static TextArea createTextArea(String additionalStyle, double height, boolean editable) {
        TextArea textArea = new TextArea();
        String style = "-fx-font-family: 'Consolas', 'Courier New', monospace;" +
                "-fx-font-size: 14px;" +
                "-fx-border-color: #e0e0e0;" +
                "-fx-border-radius: 4px;" +
                "-fx-background-radius: 4px;" +
                "-fx-padding: 10;";
        if (additionalStyle != null) {
            style += additionalStyle;
        }
        textArea.setStyle(style);
        textArea.setEditable(editable);
        textArea.setPrefHeight(height);
        return textArea;
    }

    public static Button createButton(String text) {
        Button button = new Button(text);
        button.setStyle(BUTTON_STYLE);
        button.setOnMouseEntered(e -> button.setStyle(BUTTON_HOVER_STYLE));
        button.setOnMouseExited(e -> button.setStyle(BUTTON_STYLE));
        return button;
    }

    public static Button createInfoButton() {
        Button infoButton = new Button("?");
        infoButton.setStyle(INFO_BUTTON_STYLE);
        infoButton.setOnMouseEntered(e ->
                infoButton.setStyle("-fx-background-color: derive(" + ACCENT_COLOR + ", 20%); -fx-text-fill: white; -fx-cursor: hand;"));
        infoButton.setOnMouseExited(e ->
                infoButton.setStyle(INFO_BUTTON_STYLE));
        infoButton.setPrefSize(30, 30);
        infoButton.setTooltip(new Tooltip("查看问题详情"));
        return infoButton;
    }

    public static Button createRunButton(String text) {
        String runButtonStyle =
                "-fx-background-color: " + SUCCESS_COLOR + ";" +
                        "-fx-text-fill: white;" +
                        "-fx-background-radius: 4px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 8 15 8 15;";

        Button button = new Button(text);
        button.setStyle(runButtonStyle);
        button.setOnMouseEntered(e ->
                button.setStyle("-fx-background-color: derive(" + SUCCESS_COLOR + ", 20%); " +
                        "-fx-text-fill: white; -fx-cursor: hand; " +
                        "-fx-background-radius: 4px; -fx-font-weight: bold; " +
                        "-fx-padding: 8 15 8 15;"));
        button.setOnMouseExited(e -> button.setStyle(runButtonStyle));
        return button;
    }

    public static Label createHeaderLabel(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: " + PRIMARY_DARK_COLOR + ";");
        return label;
    }

    public static Label createLabel(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-text-fill: " + PRIMARY_DARK_COLOR + ";");
        return label;
    }

    public static HBox createHBox(double spacing, Insets padding, Pos alignment, Node... children) {
        HBox hBox = new HBox(spacing, children);
        if (padding != null) {
            hBox.setPadding(padding);
        }
        if (alignment != null) {
            hBox.setAlignment(alignment);
        }
        return hBox;
    }

    public static VBox createVBox(double spacing, Insets padding, Node... children) {
        VBox vBox = new VBox(spacing, children);
        if (padding != null) {
            vBox.setPadding(padding);
        }
        return vBox;
    }

    private UIFactory() {
        throw new IllegalStateException("Utility class");
    }
}
