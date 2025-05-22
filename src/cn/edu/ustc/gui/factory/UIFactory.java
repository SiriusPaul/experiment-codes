package cn.edu.ustc.gui.factory;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * @author SiriusPaul
 * @version V1.0
 * @CreateDate 2025/5/21
 * @Description
 */
public class UIFactory {
    public static <T> ComboBox<T> createComboBox(String prompt, double width) {
        ComboBox<T> comboBox = new ComboBox<>();
        comboBox.setPromptText(prompt);
        comboBox.setPrefWidth(width);
        return comboBox;
    }

    public static TextField createTextField(double width, double height, String prompt) {
        TextField textField = new TextField();
        textField.setPrefWidth(width);
        textField.setPrefHeight(height);
        if (prompt != null) textField.setPromptText(prompt);
        return textField;
    }

    public static TextArea createTextArea(String style, double height, boolean editable) {
        TextArea textArea = new TextArea();
        if (style != null) textArea.setStyle(style);
        textArea.setEditable(editable);
        textArea.setPrefHeight(height);
        return textArea;
    }

    public static Button createButton(String text) {
        return new Button(text);
    }

    public static HBox createHBox(double spacing, Insets padding, Pos alignment, Node... children) {
        HBox hBox = new HBox(spacing, children);
        if (padding != null) hBox.setPadding(padding);
        if (alignment != null) hBox.setAlignment(alignment);
        return hBox;
    }

    public static VBox createVBox(double spacing, Insets padding, Node... children) {
        VBox vBox = new VBox(spacing, children);
        if (padding != null) vBox.setPadding(padding);
        return vBox;
    }
}
