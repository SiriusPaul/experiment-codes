package cn.edu.ustc.gui;

import cn.edu.ustc.model.Context;
import cn.edu.ustc.model.TestCase;
import cn.edu.ustc.service.AlgorithmCodeService;
import cn.edu.ustc.service.Strategy;
import cn.edu.ustc.algorithm.EnumerationMaxSubArray;
import cn.edu.ustc.algorithm.DynamicProgrammingMaxSubArray;
import cn.edu.ustc.model.MaxSubArrayResult;
import cn.edu.ustc.model.ProblemType;
import cn.edu.ustc.service.TestCaseService;
import cn.edu.ustc.util.RuntimeCompiler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * @author SiriusPaul
 * @version V1.0
 * @CreateDate 2025/3/28
 * @Description 问题面板类，负责显示算法代码和测试用例，并提供编译和运行功能
 * 该类包含了所有的UI组件和服务类，以及事件处理逻辑
 * 该类的主要功能是根据当前选中的问题和算法，加载对应的代码和测试用例，并提供编译和运行功能
 * 该类的主要逻辑是：
 * 1. 从数据库中加载算法代码和测试用例
 * 2. 编译算法代码
 * 3. 运行算法代码
 * 4. 显示运行结果
 * 5. 提供编辑代码和重新加载功能
 * 6. 提供编译和运行按钮的事件处理逻辑
 * 7. 提供编辑代码和重新加载按钮的事件处理逻辑
 * 8. 提供算法选择框和测试用例选择框的事件处理逻辑
 * 9. 提供问题切换功能
 * 10. 提供编译和运行功能
 * 11. 提供编辑代码和重新加载功能
 * 12. 提供算法选择框和测试用例选择框的事件处理逻辑
 * 13. 提供问题切换功能
 * 14. 提供编译和运行功能
 * 15. 提供编辑代码和重新加载功能
 * 16. 提供算法选择框和测试用例选择框的事件处理逻辑
 */

public class ProblemPanel extends BorderPane {
    // UI组件
    private ComboBox<String> algorithmSelector;
    private ComboBox<TestCase> testCaseSelector;
    private TextArea codeArea;
    private TextArea resultArea;
    private Button runButton;
    private Button editButton;
    private Button reloadButton;

    // 服务类
    private final AlgorithmCodeService codeService = new AlgorithmCodeService();
    private final TestCaseService testCaseService = new TestCaseService();

    // 当前状态
    private String currentProblemId;
    private String currentClassName;
    private String originalCode;
    private List<TestCase> testCases;

    public ProblemPanel() {
        initComponents();
        layoutComponents();
        setupEventHandlers();
    }

    private void initComponents() {
        // 初始化所有UI组件
        algorithmSelector = new ComboBox<>();
        algorithmSelector.setPromptText("选择算法");
        algorithmSelector.setPrefWidth(150);

        testCaseSelector = new ComboBox<>();
        testCaseSelector.setPromptText("选择测试用例");
        testCaseSelector.setPrefWidth(150);

        codeArea = new TextArea();
        codeArea.setStyle("-fx-font-family: monospace;");
        codeArea.setEditable(false);
        codeArea.setPrefHeight(400);

        resultArea = new TextArea();
        resultArea.setEditable(false);
        resultArea.setPrefHeight(120);

        runButton = new Button("编译运行");
        editButton = new Button("编辑代码");
        reloadButton = new Button("重新加载");
        reloadButton.setDisable(true);
    }

    private void layoutComponents() {
        // 布局组件
        HBox controlBox = new HBox(10);
        controlBox.setPadding(new Insets(10));
        controlBox.getChildren().addAll(
                new Label("算法:"), algorithmSelector,
                new Label("测试:"), testCaseSelector,
                runButton, editButton, reloadButton
        );

        VBox centerBox = new VBox(10);
        centerBox.setPadding(new Insets(10));
        centerBox.getChildren().addAll(
                new Label("算法代码:"), codeArea,
                new Label("执行结果:"), resultArea
        );

        setTop(controlBox);
        setCenter(centerBox);
    }

    // 设置事件处理器
    private void setupEventHandlers() {
        // 设置事件处理
        algorithmSelector.setOnAction(e -> loadSelectedAlgorithmCode());

        editButton.setOnAction(e -> {
            if (!codeArea.isEditable()) {
                // 进入编辑模式
                originalCode = codeArea.getText();
                codeArea.setEditable(true);
                editButton.setText("取消编辑");
                reloadButton.setDisable(false);
                // 添加提示信息
                resultArea.setText("您现在可以编辑代码。编辑完成后，点击「编译运行」按钮编译并执行。");
            } else {
                // 退出编辑模式
                codeArea.setEditable(false);
                editButton.setText("编辑代码");
                reloadButton.setDisable(true);
            }
        });

        runButton.setOnAction(e -> compileAndRun());

        reloadButton.setOnAction(e -> {
            if (originalCode != null) {
                codeArea.setText(originalCode);
            }
        });
    }

    public void switchProblem(ProblemType problem) {
        currentProblemId = problem.getId();
        loadAlgorithms();
        loadTestCases();
    }

    private void loadAlgorithms() {
        try {
            algorithmSelector.getItems().clear();
            algorithmSelector.getItems().addAll(codeService.getAlgorithmNames(currentProblemId));
            if (!algorithmSelector.getItems().isEmpty()) {
                algorithmSelector.setValue(algorithmSelector.getItems().get(0));
                loadSelectedAlgorithmCode();
            }
        } catch (SQLException e) {
            showAlert("加载算法列表失败: " + e.getMessage());
        }
    }

    private void loadTestCases() {
        try {
            testCases = testCaseService.getTestCases(currentProblemId);
            testCaseSelector.getItems().clear();
            testCaseSelector.getItems().addAll(testCases);
            if (!testCases.isEmpty()) {
                testCaseSelector.setValue(testCases.getFirst());
            }
        } catch (SQLException e) {
            showAlert("加载测试用例失败: " + e.getMessage());
        }
    }

    private void loadSelectedAlgorithmCode() {
        String selectedAlgo = algorithmSelector.getValue();
        if (selectedAlgo == null) return;

        try {
            String code = codeService.getCode(currentProblemId, selectedAlgo);
            currentClassName = codeService.getClassName(currentProblemId, selectedAlgo);

            if (code != null) {
                codeArea.setText(code);
                originalCode = code;
            } else {
                codeArea.setText("// 未找到算法代码");
            }

            // 重置编辑状态
            codeArea.setEditable(false);
            editButton.setText("编辑代码");
            reloadButton.setDisable(true);
            resultArea.clear();
        } catch (SQLException e) {
            showAlert("加载代码失败: " + e.getMessage());
        }
    }

    // 编译和运行算法代码
    // 编译代码前先显示正在编译的消息
    private void compileAndRun() {
        try {
            String sourceCode = codeArea.getText();
            if (sourceCode == null || sourceCode.trim().isEmpty() || currentClassName == null) {
                resultArea.setText("没有可执行的代码");
                return;
            }

            // 获取测试用例
            TestCase testCase = testCaseSelector.getValue();
            if (testCase == null) {
                resultArea.setText("请选择一个测试用例");
                return;
            }

            resultArea.setText("正在编译...");

            try {
                // 添加时间戳创建唯一类名，避免类加载器缓存问题
                String uniqueClassName = currentClassName + "_" + System.currentTimeMillis();
                String fullClassName = "cn.edu.ustc.algorithm." + uniqueClassName;

                // 修改源代码中的类名以匹配唯一类名
                String modifiedSourceCode = sourceCode.replaceFirst(
                        "class\\s+" + currentClassName,
                        "class " + uniqueClassName
                );

                // 编译修改后的代码
                Class<?> compiledClass = RuntimeCompiler.compileAndLoad(fullClassName, modifiedSourceCode);

                // 执行代码
                int[] inputArray = testCase.getInputArray();
                Object instance = compiledClass.getDeclaredConstructor().newInstance();
                Method calcMethod = compiledClass.getMethod("calc", int[].class);
                Object resultObj = calcMethod.invoke(instance, (Object) inputArray);

                // 提取数值结果
                int numericResult;
                if (resultObj != null) {
                    if (resultObj instanceof Integer) {
                        numericResult = (Integer) resultObj;
                    } else {
                        // 通过反射获取maxSum字段的值
                        try {
                            java.lang.reflect.Field field = resultObj.getClass().getDeclaredField("maxSum");
                            field.setAccessible(true);
                            numericResult = (Integer) field.get(resultObj);
                            System.out.println("通过字段访问获取值: " + numericResult);
                        } catch (Exception ex) {
                            System.err.println("无法获取maxSum值: " + ex);
                            // 表示获取失败
                            numericResult = -1;
                        }
                    }
                } else {
                    numericResult = 0;
                }


                // 显示结果
                StringBuilder resultText = new StringBuilder();
                resultText.append("编译成功!\n");
                resultText.append("测试用例: ").append(testCase.getName()).append("\n");
                resultText.append("输入: ").append(Arrays.toString(inputArray)).append("\n");
                resultText.append("执行结果: ").append(numericResult);

                if (testCase.getExpectedOutput() != null && !testCase.getExpectedOutput().isEmpty()) {
                    resultText.append("\n期望结果: ").append(testCase.getExpectedOutput());
                    if (String.valueOf(numericResult).equals(testCase.getExpectedOutput())) {
                        resultText.append("\n√ 结果匹配");
                    } else {
                        resultText.append("\n× 结果不匹配");
                    }
                }

                resultArea.setText(resultText.toString());
            } catch (Exception e) {
                // 详细显示编译错误
                resultArea.setText("错误: " + e.getMessage());
                e.printStackTrace();
            }
        } catch (Exception e) {
            resultArea.setText("执行错误: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("提示");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}