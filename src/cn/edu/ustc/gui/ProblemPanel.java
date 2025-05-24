package cn.edu.ustc.gui;

import cn.edu.ustc.gui.command.*;
import cn.edu.ustc.gui.factory.*;
import cn.edu.ustc.gui.strategy.*;
import cn.edu.ustc.model.*;
//import cn.edu.ustc.service.*;
import cn.edu.ustc.service.ProblemInfoService;
import cn.edu.ustc.util.RuntimeCompiler;


//import cn.edu.ustc.model.StringMatchTestCase;
import cn.edu.ustc.model.TestCase;
import cn.edu.ustc.service.AlgorithmCodeService;
import cn.edu.ustc.model.ProblemType;
import cn.edu.ustc.service.TestCaseService;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.lang.reflect.Method;
//import java.sql.SQLException;
//import java.util.Arrays;
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
    private CheckBox useCustomTestCheckbox;
    private HBox controlBox;
    private VBox topContainer;
    private Button infoButton;
    private final ProblemInfoService infoService = new ProblemInfoService();

    // 服务类
    private final AlgorithmCodeService codeService = new AlgorithmCodeService();
    private final TestCaseService testCaseService = new TestCaseService();

    // 当前状态
    private String currentProblemId;
    private String currentClassName;
    private String originalCode;
    //    private List<TestCase> testCases;
    private RunProblemStrategy currentStrategy;

    // 命令对象
    private final Command runCommand;
    private final Command editCommand;
    private final Command reloadCommand;

    //菜单
    private MenuBar menuBar;

//    // 新增字符串匹配输入区域
//    private TextField textField;
//    private TextField patternField;
//    private HBox stringMatchInputBox;
//
//    //自定义测试样例功能
//    private CheckBox useCustomTestCheckbox;
//    private TextField customArrayField;
//    private HBox customMaxSubarrayBox;


    public ProblemPanel() {
        // 创建命令
        runCommand = new RunCommand(this);
        editCommand = new EditCodeCommand(this);
        reloadCommand = new ReloadCodeCommand(this);

        initComponents();
        layoutComponents();
        setupEventHandlers();
    }

    // 初始化菜单
    private void initMenuBar() {
        menuBar = new MenuBar();

        //setting menu
        Menu settingMenu = new Menu("设置");
        MenuItem exitItem = new MenuItem("退出");
        exitItem.setOnAction(e -> Platform.exit());
        settingMenu.getItems().add(exitItem);

        //help menu
        Menu helpMenu = new Menu("帮助");
        MenuItem aboutItem = new MenuItem("关于");
        aboutItem.setOnAction(e -> showAboutDialog());
        helpMenu.getItems().addAll(aboutItem);

        menuBar.getMenus().addAll(settingMenu, helpMenu);
        menuBar.setStyle("-fx-background-color: " + UIFactory.PRIMARY_COLOR + ";");

    }

    private void initComponents() {
        // 使用工厂创建组件
        algorithmSelector = UIFactory.createComboBox("选择算法", 150);
        testCaseSelector = UIFactory.createComboBox("选择测试用例", 150);
        codeArea = UIFactory.createTextArea("-fx-font-family: monospace;", 300, false);
        resultArea = UIFactory.createTextArea(null, 150, false);

        // 使用不同颜色区分按钮
        runButton = UIFactory.createRunButton("编译运行");
        editButton = UIFactory.createButton("编辑代码");
        reloadButton = UIFactory.createButton("重新加载");
        reloadButton.setDisable(true);

        useCustomTestCheckbox = new CheckBox("自定义测试");
        useCustomTestCheckbox.setStyle("-fx-text-fill: " + UIFactory.PRIMARY_DARK_COLOR + ";");

        // 问题信息按钮
        infoButton = UIFactory.createInfoButton();
        infoButton.setOnAction(e -> showProblemInfo());
        //        // 初始化所有UI组件
//        algorithmSelector = new ComboBox<>();
//        algorithmSelector.setPromptText("选择算法");
//        algorithmSelector.setPrefWidth(150);
//
//        testCaseSelector = new ComboBox<>();
//        testCaseSelector.setPromptText("选择测试用例");
//        testCaseSelector.setPrefWidth(150);
//
//        codeArea = new TextArea();
//        codeArea.setStyle("-fx-font-family: monospace;");
//        codeArea.setEditable(false);
//        codeArea.setPrefHeight(300);
//
//        resultArea = new TextArea();
//        resultArea.setEditable(false);
//        resultArea.setPrefHeight(150);
//
//        runButton = new Button("编译运行");
//        editButton = new Button("编辑代码");
//        reloadButton = new Button("重新加载");
//        reloadButton.setDisable(true);
//
//        // 八皇后问题特定组件
//        boardSizeSpinner = new Spinner<>(new SpinnerValueFactory.IntegerSpinnerValueFactory(4, 12, 8));
//        boardSizeSpinner.setEditable(true);
//        boardSizeSpinner.setPrefWidth(80);
//
//        queensOptionsBox = new HBox(10);
//        queensOptionsBox.setPadding(new Insets(10));
//        queensOptionsBox.setAlignment(Pos.CENTER_LEFT);
//        queensOptionsBox.getChildren().addAll(new Label("棋盘大小:"), boardSizeSpinner);
//        queensOptionsBox.setVisible(false);
//
//        // 字符串匹配问题特定组件
//        stringMatchPanel = new StringMatchPanel();
//        stringMatchPanel.setVisible(false);
//        stringMatchPanel.setAlignment(Pos.CENTER_LEFT);
//
//        // 初始化字符串匹配相关组件
//        // 初始化字符串匹配相关组件
//        textField = new TextField();
//        textField.setPrefWidth(400);
//        textField.setPrefHeight(25);  // Set explicit height
//        patternField = new TextField();
//        patternField.setPrefHeight(25);  // Set explicit height
//
//        Button runStringMatchButton = new Button("运行匹配");
//        runStringMatchButton.setOnAction(e -> runProblem());
//
//        stringMatchInputBox = new HBox(10);
//        stringMatchInputBox.setPadding(new Insets(10));
//        stringMatchInputBox.setAlignment(Pos.CENTER_LEFT);
//        stringMatchInputBox.getChildren().addAll(
//                new Label("文本:"), textField,
//                new Label("模式:"), patternField,
//                runStringMatchButton
//        );
//        stringMatchInputBox.setVisible(false);
//
//        // 自定义测试样例组件
//        useCustomTestCheckbox = new CheckBox("自定义测试");
//        HBox customTestBox = new HBox(10);
//        customTestBox.setPadding(new Insets(0, 10, 5, 10));
//        customTestBox.setAlignment(Pos.CENTER_LEFT);
//        customTestBox.getChildren().add(useCustomTestCheckbox);
//
//        customArrayField = new TextField();
//        customArrayField.setPromptText("输入数组，例如：-2,1,-3,4,-1,2,1,-5,4");
//        customArrayField.setPrefWidth(300);
//        customArrayField.setPrefHeight(25);
//        // 自定义测试样例组件
//        useCustomTestCheckbox = new CheckBox("自定义测试");
//        customArrayField = new TextField();
//        customArrayField.setPromptText("输入数组，例如：-2,1,-3,4,-1,2,1,-5,4");
//        customArrayField.setPrefWidth(300);
//
//        //最大子数组自定义测试区域
//        customMaxSubarrayBox = new HBox(10);
//        customMaxSubarrayBox.setPadding(new Insets(5));
//        customMaxSubarrayBox.setAlignment(Pos.CENTER_LEFT);
//        customMaxSubarrayBox.getChildren().addAll(new Label("数组:"), customArrayField);
    }


    // 显示关于对话框
    private void showAboutDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("关于");
        alert.setHeaderText("算法实时运行平台");

        VBox content = new VBox(10);
        content.setPadding(new Insets(20));

        Label versionLabel = new Label("版本: 1.0.0");
        Label authorLabel = new Label("作者: SiriusPaul");
        Label descLabel = new Label("本程序旨在帮助学习和理解各种算法，提供实时的运行环境。");
        Label webssiteLabel = new Label("GitHub仓库：https://github.com/SiriusPaul/experiment-codes/tree/reflection");
        descLabel.setWrapText(true);

        content.getChildren().addAll(versionLabel, authorLabel, new Separator(), descLabel, webssiteLabel);

        alert.getDialogPane().setContent(content);
        alert.showAndWait();
    }

    private void layoutComponents() {
        VBox centerBox;
        // 初始化菜单栏
        initMenuBar();
        menuBar.setPrefWidth(Double.MAX_VALUE);
        menuBar.setMinHeight(25);
        // 确保菜单栏颜色与页面背景一致
        menuBar.setStyle(UIFactory.BASIC_COLOUR);

        // 控制区域
        controlBox = UIFactory.createHBox(10, new Insets(15, 15, 5, 15), Pos.CENTER_LEFT,
                UIFactory.createLabel("算法:"), algorithmSelector,
                UIFactory.createLabel("测试:"), testCaseSelector,
                runButton, editButton, reloadButton, infoButton);
        controlBox.setStyle("-fx-background-color: " + UIFactory.SECONDARY_COLOR + ";" +
                "-fx-border-color: #e0e0e0;" +
                "-fx-border-width: 0 0 1 0;");

        // 将菜单栏放在最上方，然后是控制区域
        topContainer = new VBox();
        topContainer.getChildren().addAll(menuBar, controlBox);

        // 应用浅灰色背景到文本区域
        codeArea.setStyle(UIFactory.TEXT_AREA_STYLE);
        resultArea.setStyle(UIFactory.TEXT_AREA_STYLE);

        // 代码和结果区域
        VBox codeBox = UIFactory.createVBox(5, new Insets(5), UIFactory.createHeaderLabel("算法代码:"), codeArea);
        codeBox.setStyle("-fx-background-color: white;" +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 3, 0, 0, 1);" +
                "-fx-background-radius: 4px;");

        VBox resultBox = UIFactory.createVBox(5, new Insets(5), UIFactory.createHeaderLabel("执行结果:"), resultArea);
        resultBox.setStyle("-fx-background-color: white;" +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 3, 0, 0, 1);" +
                "-fx-background-radius: 4px;");

        // 内容区域
        centerBox = UIFactory.createVBox(15, new Insets(15), codeBox, resultBox);
        centerBox.setStyle(UIFactory.BASIC_COLOUR);

        // 设置整体背景
        setStyle(UIFactory.BASIC_COLOUR);

        // 应用布局
        setTop(topContainer);
        setCenter(centerBox);


//        // 布局组件
//        HBox controlBox = new HBox(10);
//        controlBox.setPadding(new Insets(10));
//        controlBox.getChildren().addAll(
//                new Label("算法:"), algorithmSelector,
//                new Label("测试:"), testCaseSelector,
//                runButton, editButton, reloadButton
//        );
//
//        // 创建单独的复选框容器
//        HBox customTestBox = new HBox(10);
//        customTestBox.setPadding(new Insets(0, 10, 5, 10));
//        customTestBox.setAlignment(Pos.CENTER_LEFT);
//        customTestBox.getChildren().add(useCustomTestCheckbox);
//
//        // 确保字符串匹配输入和最大子数组输入一致的布局
//        stringMatchInputBox = new HBox(10);
//        stringMatchInputBox.setPadding(new Insets(5));
//        stringMatchInputBox.setAlignment(Pos.CENTER_LEFT);
//        stringMatchInputBox.getChildren().clear();
//        stringMatchInputBox.getChildren().addAll(
//                new Label("文本:"), textField,
//                new Label("模式:"), patternField
//        );
//
//        centerBox = new VBox(10);
//        centerBox.setPadding(new Insets(10));
//        centerBox.getChildren().addAll(
//                new Label("算法代码:"), codeArea,
//                new Label("执行结果:"), resultArea
//        );
//
//        // 重新组织布局
//        VBox topContainer = new VBox(5);
//        topContainer.getChildren().addAll(
//                controlBox,
//                // 复选框统一位置
//                customTestBox,
//                queensOptionsBox,
//                customMaxSubarrayBox,
//                stringMatchInputBox
//        );
//
//        setTop(topContainer);
//        setCenter(centerBox);
    }

//        setTop(controlBox);
//        setCenter(centerBox);

    // 将所有组件添加到主面板
//        VBox topContainer = new VBox(5);
//        topContainer.getChildren().addAll(controlBox, queensOptionsBox,
//                customMaxSubarrayBox, stringMatchInputBox);


    //显示问题信息对话框
    private void showProblemInfo() {
        if (currentProblemId == null) {
            showAlert("请先选择一个问题");
            return;
        }
        try {
            ProblemInfo info = infoService.getProblemInfo(currentProblemId);
            if (info == null) {
                showAlert("无法获取问题信息");
                return;
            }

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("问题详情");
            dialog.setHeaderText(info.getTitle());

            // 创建内容区域
            GridPane content = new GridPane();
            content.setHgap(10);
            content.setVgap(10);
            content.setPadding(new Insets(20, 150, 10, 20));

            // 添加描述
            content.add(UIFactory.createHeaderLabel("描述"), 0, 0);
            TextArea descriptionArea = UIFactory.createTextArea(null, 100, false);
            descriptionArea.setText(info.getDescription());
            descriptionArea.setWrapText(true);
            GridPane.setHgrow(descriptionArea, Priority.ALWAYS);
            content.add(descriptionArea, 0, 1);
            descriptionArea.setPrefHeight(300);

            dialog.getDialogPane().setContent(content);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
            dialog.getDialogPane().setPrefSize(600, 500);

            dialog.showAndWait();

        } catch (Exception e) {
            showAlert("加载问题信息失败: " + e.getMessage());
        }
    }

    // 设置事件处理器
    private void setupEventHandlers() {
        algorithmSelector.setOnAction(e -> loadSelectedAlgorithmCode());
        testCaseSelector.setOnAction(e -> {
            if (currentStrategy != null) {
                currentStrategy.loadTestCaseInputs();
            }
        });
        runButton.setOnAction(e -> runCommand.execute());
        editButton.setOnAction(e -> editCommand.execute());
        reloadButton.setOnAction(e -> reloadCommand.execute());
        useCustomTestCheckbox.setOnAction(e -> testCaseSelector.setDisable(useCustomTestCheckbox.isSelected()));

//        // 设置事件处理
//        algorithmSelector.setOnAction(e -> loadSelectedAlgorithmCode());
//
//        // 选择测试用例时加载对应的输入
//        testCaseSelector.setOnAction(e -> {
//            TestCase selectedCase = testCaseSelector.getValue();
//            if (selectedCase instanceof StringMatchTestCase stringMatchCase) {
//                textField.setText(stringMatchCase.getText());
//                patternField.setText(stringMatchCase.getPattern());
//            }
//        });
//
//        editButton.setOnAction(e -> {
//            if (!codeArea.isEditable()) {
//                // 进入编辑模式
//                originalCode = codeArea.getText();
//                codeArea.setEditable(true);
//                editButton.setText("取消编辑");
//                reloadButton.setDisable(false);
//                resultArea.setText("您现在可以编辑代码。编辑完成后，点击「编译运行」按钮编译并执行。");
//            } else {
//                // 退出编辑模式
//                codeArea.setEditable(false);
//                editButton.setText("编辑代码");
//                reloadButton.setDisable(true);
//            }
//        });
//
//        runButton.setOnAction(e -> runProblem());
//
//        reloadButton.setOnAction(e -> {
//            if (originalCode != null) {
//                codeArea.setText(originalCode);
//            }
//        });
//
//        useCustomTestCheckbox.setOnAction(e -> {
//            boolean isCustom = useCustomTestCheckbox.isSelected();
//            testCaseSelector.setDisable(isCustom);
//        });
    }

    public void switchProblem(ProblemType problem) {
        currentProblemId = problem.getId();

        // 使用工厂创建策略
        currentStrategy = StrategyFactory.createStrategy(problem, this, testCaseService);

        // 更新UI
        // 保留菜单栏，只清除策略相关控件
        topContainer.getChildren().removeAll(topContainer.getChildren().filtered(node -> !node.equals(menuBar) && !node.equals(controlBox)));
        // 添加策略特定的控件
        List<Node> problemControls = currentStrategy.createProblemControls();
        topContainer.getChildren().addAll(problemControls);

        // 加载算法和测试用例
        loadAlgorithms();
        currentStrategy.loadTestCases(currentProblemId);

        // 更新组件可见性
        boolean isQueens = "queens".equals(problem.getId()) || "八皇后问题".equals(problem.getDisplayName());
        testCaseSelector.setVisible(!isQueens);

        // 重置状态
        useCustomTestCheckbox.setSelected(false);
        testCaseSelector.setDisable(false);

        // 准备策略UI
        currentStrategy.prepareUI(true);
//        currentProblemId = problem.getId();
//        loadAlgorithms();
//
//        // 根据问题类型显示特定组件
//        boolean isMaxSubArrayProblem = "最大子数组问题".equals(problem.getDisplayName()) ||
//                "max-subarray".equals(problem.getId());
//
//        boolean isQueensProblem = "八皇后问题".equals(problem.getDisplayName()) ||
//                "queens".equals(problem.getId());
//
//        boolean isStringMatchProblem = "字符串匹配问题".equals(problem.getDisplayName()) ||
//                "string-match".equals(problem.getId());
//
//        // 根据问题类型显示/隐藏控件
//        queensOptionsBox.setVisible(isQueensProblem);
//        customMaxSubarrayBox.setVisible(isMaxSubArrayProblem);
//        stringMatchInputBox.setVisible(isStringMatchProblem);
//        testCaseSelector.setVisible(!isQueensProblem);
//
//        // 设置字符串匹配面板的可见性
//        if (!isQueensProblem && !isStringMatchProblem) {
//            loadTestCases();
//        } else if (isStringMatchProblem) {
//            loadStringMatchTestCases();
//        }
//        if (isStringMatchProblem) {
//            loadStringMatchTestCases();
//        } else if (!isQueensProblem) {
//            loadTestCases();
//        }
//
//
//        // 更新组件可见性
//        queensOptionsBox.setVisible(isQueensProblem);
//        customMaxSubarrayBox.setVisible(isMaxSubArrayProblem);
//        stringMatchInputBox.setVisible(isStringMatchProblem);
//        testCaseSelector.setVisible(!isQueensProblem);
//
//        // 只在相关问题时显示复选框
//        useCustomTestCheckbox.setVisible(isMaxSubArrayProblem || isStringMatchProblem);
//
//
//        // 重置状态
//        useCustomTestCheckbox.setSelected(false);
//        testCaseSelector.setDisable(false);
//
//        // 在相同位置添加复选框
//        if (isStringMatchProblem) {
//            if (!stringMatchInputBox.getChildren().contains(useCustomTestCheckbox)) {
//                stringMatchInputBox.getChildren().addFirst(useCustomTestCheckbox);
//            }
//        }
//
//        // 加载相应测试用例
//        if (isStringMatchProblem) {
//            loadStringMatchTestCases();
//        } else if (!isQueensProblem) {
//            loadTestCases();
//        }
    }

    private void loadAlgorithms() {
        try {
            algorithmSelector.getItems().clear();
            algorithmSelector.getItems().addAll(codeService.getAlgorithmNames(currentProblemId));
            if (!algorithmSelector.getItems().isEmpty()) {
                algorithmSelector.setValue(algorithmSelector.getItems().getFirst());
                loadSelectedAlgorithmCode();
            }
        } catch (Exception e) {
            showAlert("加载算法列表失败: " + e.getMessage());
        }
//        try {
//            algorithmSelector.getItems().clear();
//            algorithmSelector.getItems().addAll(codeService.getAlgorithmNames(currentProblemId));
//            if (!algorithmSelector.getItems().isEmpty()) {
//                algorithmSelector.setValue(algorithmSelector.getItems().get(0));
//                loadSelectedAlgorithmCode();
//            }
//        } catch (SQLException e) {
//            showAlert("加载算法列表失败: " + e.getMessage());
//        }
//    }
//
//    private void loadTestCases() {
//        try {
//            testCases = testCaseService.getTestCases(currentProblemId);
//            testCaseSelector.getItems().clear();
//            testCaseSelector.getItems().addAll(testCases);
//            if (!testCases.isEmpty()) {
//                testCaseSelector.setValue(testCases.getFirst());
//            }
//        } catch (SQLException e) {
//            showAlert("加载测试用例失败: " + e.getMessage());
//        }
    }

//    private void loadStringMatchTestCases() {
//        try {
//            testCases = testCaseService.getTestCases(currentProblemId);
//            testCaseSelector.getItems().clear();
//            testCaseSelector.getItems().addAll(testCases);
//            if (!testCases.isEmpty()) {
//                testCaseSelector.setValue(testCases.getFirst());
//                // 填充文本和模式串到输入框
//                StringMatchTestCase testCase = (StringMatchTestCase) testCaseSelector.getValue();
//                textField.setText(testCase.getText());
//                patternField.setText(testCase.getPattern());
//            }
//        } catch (SQLException e) {
//            showAlert("加载测试用例失败: " + e.getMessage());
//        }
//    }

    private void loadSelectedAlgorithmCode() {
        String selectedAlgo = algorithmSelector.getValue();
        if (selectedAlgo == null) {
            return;
        }

        try {
            String code = codeService.getCode(currentProblemId, selectedAlgo);
            currentClassName = codeService.getClassName(currentProblemId, selectedAlgo);
            codeArea.setText(code != null ? code : "// 未找到算法代码");
            originalCode = code;

            // 重置编辑状态
            codeArea.setEditable(false);
            editButton.setText("编辑代码");
            reloadButton.setDisable(true);
            resultArea.clear();
        } catch (Exception e) {
            showAlert("加载代码失败: " + e.getMessage());
        }
//        String selectedAlgo = algorithmSelector.getValue();
//        if (selectedAlgo == null) return;
//
//        try {
//            String code = codeService.getCode(currentProblemId, selectedAlgo);
//            currentClassName = codeService.getClassName(currentProblemId, selectedAlgo);
//
//            if (code != null) {
//                codeArea.setText(code);
//                originalCode = code;
//            } else {
//                codeArea.setText("// 未找到算法代码");
//            }
//
//            // 重置编辑状态
//            codeArea.setEditable(false);
//            editButton.setText("编辑代码");
//            reloadButton.setDisable(true);
//            resultArea.clear();
//        } catch (SQLException e) {
//            showAlert("加载代码失败: " + e.getMessage());
//        }
    }


//    private void loadTestCaseInputs() {
//        // 委托给当前策略处理
//        if (currentStrategy != null) {
//            try {
//                currentStrategy.loadTestCases(currentProblemId);
//            } catch (Exception e) {
//                showAlert("加载测试用例失败: " + e.getMessage());
//            }
//        }
//    }

    // 运行问题
    public void runProblem() {
        long startTime = System.nanoTime();
        resultArea.setText("正在编译...");

        try {
            String sourceCode = codeArea.getText();
            if (sourceCode == null || sourceCode.trim().isEmpty()) {
                resultArea.setText("没有可执行的代码");
                return;
            }

            // 委托给当前策略执行
            if (currentStrategy != null) {
                currentStrategy.execute();
            }

            // 显示执行时间
            double executionTimeMs = (System.nanoTime() - startTime) / 1_000_000.0;
            resultArea.appendText("\n\n执行时间: " + String.format("%.3f 毫秒", executionTimeMs));
        } catch (Exception e) {
            resultArea.setText("执行错误: " + e.getMessage());
        }
//        long executionStartTime = System.nanoTime();
//        resultArea.setText("正在编译...");
//
//        try {
//            String sourceCode = codeArea.getText();
//            if (sourceCode == null || sourceCode.trim().isEmpty()) {
//                resultArea.setText("没有可执行的代码");
//                return;
//            }
//
//            if (queensOptionsBox.isVisible()) {
//                runQueensProblem();
//            } else if (stringMatchInputBox.isVisible()) {
//                // 字符串匹配问题
//                String text = textField.getText();
//                String pattern = patternField.getText();
//
//                // 确保输入不为空
//                if (text.isEmpty() || pattern.isEmpty()) {
//                    resultArea.setText("请输入有效的文本和模式串");
//                    return;
//                }
//
//                compileAndRunCode(text, pattern,
//                        "calc", String.class, String.class,
//                        "cn.edu.ustc.model.StringMatchResult");
//            } else {
//                // 最大子数组问题
//                if (useCustomTestCheckbox.isSelected()) {
//                    try {
//                        String[] values = customArrayField.getText().split(",");
//                        int[] customArray = new int[values.length];
//                        for (int i = 0; i < values.length; i++) {
//                            customArray[i] = Integer.parseInt(values[i].trim());
//                        }
//                        compileAndRunCode(customArray, null,
//                                "calc", int[].class, null, null);
//                    } catch (NumberFormatException e) {
//                        resultArea.setText("无效的数组格式。请使用逗号分隔的整数。");
//                        return;
//                    }
//                } else {
//                    TestCase testCase = testCaseSelector.getValue();
//                    if (testCase == null) {
//                        resultArea.setText("请选择一个测试用例");
//                        return;
//                    }
//                    compileAndRunCode(testCase.getInputArray(), null,
//                            "calc", int[].class, null, null);
//                }
//            }
//
//            // 执行时间
//            long executionEndTime = System.nanoTime();
//            double executionTimeMs = (executionEndTime - executionStartTime) / 1_000_000.0;
//            resultArea.appendText("\n\n执行时间: " + String.format("%.3f 毫秒", executionTimeMs));
//        } catch (Exception e) {
//            resultArea.setText("执行错误: " + e.getMessage());
//            e.printStackTrace();
//        }
    }


    // 提取类名，确保处理public修饰符
//    private String extractClassName(String sourceCode) {
//        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("(public\\s+)?class\\s+(\\w+)");
//        java.util.regex.Matcher matcher = pattern.matcher(sourceCode);
//        if (matcher.find()) {
//            return matcher.group(2);
//        }
//        return currentClassName;
//    }

    // 编译并运行代码
    public void compileAndRunCode(Object arg1, Object arg2, String methodName, Class<?> arg1Type, Class<?> arg2Type, String expectedReturnType) throws Exception {
        // 创建唯一类名
        String sourceCode = codeArea.getText();
        String actualClassName = extractClassName(sourceCode);
        String uniqueClassName = actualClassName + "_" + System.currentTimeMillis();
        String fullClassName = "cn.edu.ustc.algorithm." + uniqueClassName;

        // 修改源代码中的类名
        String modifiedSourceCode = sourceCode.replaceFirst("(public\\s+)?class\\s+" + actualClassName + "\\b", "public class " + uniqueClassName);

        // 编译代码
        Class<?> compiledClass = RuntimeCompiler.compileAndLoad(fullClassName, modifiedSourceCode);

        // 执行代码
        Object instance = compiledClass.getDeclaredConstructor().newInstance();
        Method method;
        Object result;

        if (arg2Type != null) {
            method = compiledClass.getMethod(methodName, arg1Type, arg2Type);
            result = method.invoke(instance, arg1, arg2);

            if (result != null && "cn.edu.ustc.model.StringMatchResult".equals(expectedReturnType)) {
                StringMatchResult matchResult = (StringMatchResult) result;
                if (currentStrategy instanceof RunStringMatchStrategy strStrategy) {
                    strStrategy.displayResult((String) arg1, (String) arg2, matchResult);
                }
            }
        } else {
            method = compiledClass.getMethod(methodName, arg1Type);
            result = method.invoke(instance, arg1);

            if (arg1 instanceof int[] intArg && currentStrategy instanceof RunMaxSubArrayStrategy maxStrategy) {
                int numericResult = extractNumericResult(result);
                maxStrategy.displayResult(intArg, numericResult);
            }
        }

// 创建唯一类名
//        String sourceCode = codeArea.getText();
//        String actualClassName = extractClassName(sourceCode);
//        String uniqueClassName = actualClassName + "_" + System.currentTimeMillis();
//        String fullClassName = "cn.edu.ustc.algorithm." + uniqueClassName;
//
//        // 修改源代码中的类名，确保正确处理public修饰符
//        String modifiedSourceCode = sourceCode.replaceFirst(
//                "(public\\s+)?class\\s+" + actualClassName + "\\b",
//                "public class " + uniqueClassName
//        );
//
//
//        // 编译代码
//        Class<?> compiledClass = RuntimeCompiler.compileAndLoad(fullClassName, modifiedSourceCode);
//
//        // 执行代码
//        Object instance = compiledClass.getDeclaredConstructor().newInstance();
//        Method method;
//        Object result;
//
//        if (arg2Type != null) {
//            method = compiledClass.getMethod(methodName, arg1Type, arg2Type);
//            result = method.invoke(instance, arg1, arg2);
//
//            if (result != null && "cn.edu.ustc.model.StringMatchResult".equals(expectedReturnType)) {
//                cn.edu.ustc.model.StringMatchResult matchResult =
//                        (cn.edu.ustc.model.StringMatchResult) result;
//                displayStringMatchResult((String) arg1, (String) arg2, matchResult);
//            }
//        } else {
//            method = compiledClass.getMethod(methodName, arg1Type);
//            result = method.invoke(instance, arg1);
//
//            if (arg1 instanceof int[] intArg1) {
//                int numericResult = extractNumericResult(result);
//                TestCase testCase = testCaseSelector.getValue();
//                displayMaxSubarrayResult(testCase, intArg1, numericResult);
//            }
//        }
    }

//    private void runMaxSubarrayProblem(String sourceCode) {
//        // 获取测试用例
//        TestCase testCase = testCaseSelector.getValue();
//        if (testCase == null) {
//            resultArea.setText("请选择一个测试用例");
//            return;
//        }
//
//        resultArea.setText("正在编译...");
//
//        try {
//            // 添加时间戳创建唯一类名，避免类加载器缓存问题
//            String uniqueClassName = currentClassName + "_" + System.currentTimeMillis();
//            String fullClassName = "cn.edu.ustc.algorithm." + uniqueClassName;
//
//            // 修改源代码中的类名以匹配唯一类名
//            String modifiedSourceCode = sourceCode.replaceFirst(
//                    "class\\s+" + currentClassName,
//                    "class " + uniqueClassName
//            );
//
//            // 编译修改后的代码
//            Class<?> compiledClass = RuntimeCompiler.compileAndLoad(fullClassName, modifiedSourceCode);
//
//            // 执行代码
//            int[] inputArray = testCase.getInputArray();
//            Object instance = compiledClass.getDeclaredConstructor().newInstance();
//            Method calcMethod = compiledClass.getMethod("calc", int[].class);
//            Object resultObj = calcMethod.invoke(instance, (Object) inputArray);
//
//            // 提取数值结果
//            int numericResult = extractNumericResult(resultObj);
//
//            // 显示结果
//            displayMaxSubarrayResult(testCase, inputArray, numericResult);
//        } catch (Exception e) {
//            // 详细显示编译错误
//            resultArea.setText("错误: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }

    private int extractNumericResult(Object resultObj) {
        if (resultObj == null) {
            return 0;
        }
        if (resultObj instanceof Integer intResult) {
            return intResult;
        }

        try {
            java.lang.reflect.Field field = resultObj.getClass().getDeclaredField("maxSum");
            field.setAccessible(true);
            return (Integer) field.get(resultObj);
        } catch (Exception ex) {
            return -1;
        }
    }

    // 提取类名，确保处理public修饰符
    private String extractClassName(String sourceCode) {
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("(public\\s+)?class\\s+(\\w+)");
        java.util.regex.Matcher matcher = pattern.matcher(sourceCode);
        if (matcher.find()) {
            return matcher.group(2);
        }
        return currentClassName;
    }

//    private void displayMaxSubarrayResult(TestCase testCase, int[] inputArray, int result) {
//        StringBuilder resultText = new StringBuilder();
//        resultText.append("编译成功!\n");
//        resultText.append("测试用例: ").append(testCase.getName()).append("\n");
//        resultText.append("输入: ").append(Arrays.toString(inputArray)).append("\n");
//        resultText.append("执行结果: ").append(result);
//
//        if (testCase.getExpectedOutput() != null && !testCase.getExpectedOutput().isEmpty()) {
//            resultText.append("\n期望结果: ").append(testCase.getExpectedOutput());
//            if (String.valueOf(result).equals(testCase.getExpectedOutput())) {
//                resultText.append("\n√ 结果匹配");
//            } else {
//                resultText.append("\n× 结果不匹配");
//            }
//        }
//
//        resultArea.setText(resultText.toString());
//    }

//    private void runQueensProblem() {
//        String selectedAlgo = algorithmSelector.getValue();
//        if (selectedAlgo == null) {
//            resultArea.setText("请选择一个算法");
//            return;
//        }
//
//        int boardSize = boardSizeSpinner.getValue();
//        resultArea.setText("正在计算...");
//
//        try {
//            // 根据算法名称选择策略
//            cn.edu.ustc.service.QueensStrategy strategy;
//            if ("回溯法".equals(selectedAlgo)) {
//                strategy = new cn.edu.ustc.algorithm.BacktrackingQueens();
//            } else if ("最小冲突法".equals(selectedAlgo)) {
//                strategy = new cn.edu.ustc.algorithm.MinConflictsQueens();
//            } else {
//                resultArea.setText("未知算法: " + selectedAlgo);
//                return;
//            }
//
//            // 执行算法并获取结果
//            cn.edu.ustc.model.QueensSolutionResult result = strategy.solve(boardSize);
//
//            // 显示结果
//            displayQueensSolutions(result);
//        } catch (Exception e) {
//            resultArea.setText("计算错误: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }
//
//    private void displayQueensSolutions(cn.edu.ustc.model.QueensSolutionResult result) {
//        StringBuilder output = new StringBuilder();
//        List<int[]> solutions = result.getSolutions();
//
//        output.append("找到 ").append(solutions.size()).append(" 个解决方案\n\n");
//
//        // 限制显示前5个解决方案
//        int displayCount = Math.min(10, solutions.size());
//
//        for (int i = 0; i < displayCount; i++) {
//            int[] solution = solutions.get(i);
//            output.append("方案 ").append(i + 1).append(":\n");
//
//            // 显示棋盘
//            for (int j : solution) {
//                for (int col = 0; col < solution.length; col++) {
//                    output.append(j == col ? "Q " : ". ");
//                }
//                output.append("\n");
//            }
//            output.append("\n");
//        }
//
//        if (solutions.size() > displayCount) {
//            output.append("... 还有 ").append(solutions.size() - displayCount).append(" 个解决方案未显示");
//        }
//
//        resultArea.setText(output.toString());
//    }

//    private void runStringMatching() {
//        resultArea.setText("正在运行字符串匹配算法...");
//
//        try {
//            String text = textField.getText();
//            String pattern = patternField.getText();
//
//            if (text.isEmpty() || pattern.isEmpty()) {
//                resultArea.setText("请输入有效的文本和模式串");
//                return;
//            }
//
//            String selectedAlgo = algorithmSelector.getValue();
//            String sourceCode = codeArea.getText();
//
//            // 添加时间戳创建唯一类名，避免类加载器缓存问题
//            String uniqueClassName = currentClassName + "_" + System.currentTimeMillis();
//            String fullClassName = "cn.edu.ustc.algorithm." + uniqueClassName;
//
//            // 修改源代码中的类名以匹配唯一类名
//            String modifiedSourceCode = sourceCode.replaceFirst(
//                    "class\\s+" + currentClassName,
//                    "class " + uniqueClassName
//            );
//
//            // 编译修改后的代码
//            Class<?> compiledClass = RuntimeCompiler.compileAndLoad(fullClassName, modifiedSourceCode);
//
//            // 执行代码
//            Object instance = compiledClass.getDeclaredConstructor().newInstance();
//            Method calcMethod = compiledClass.getMethod("calc", String.class, String.class);
//            Object resultObj = calcMethod.invoke(instance, text, pattern);
//
//            // 显示结果
//            if (resultObj instanceof cn.edu.ustc.model.StringMatchResult) {
//                cn.edu.ustc.model.StringMatchResult matchResult =
//                        (cn.edu.ustc.model.StringMatchResult) resultObj;
//                displayStringMatchResult(text, pattern, matchResult);
//            } else {
//                resultArea.setText("算法返回的结果类型不正确，请确保返回StringMatchResult类型");
//            }
//
//        } catch (Exception e) {
//            resultArea.setText("运行错误: " + e.getMessage() + "\n" + e);
//            e.printStackTrace();
//        }
//    }

    // 显示字符串匹配结果
//    private void displayStringMatchResult(String text, String pattern,
//                                          cn.edu.ustc.model.StringMatchResult result) {
//        StringBuilder sb = new StringBuilder();
//        sb.append("字符串匹配结果：\n\n");
//        sb.append("文本: ").append(text).append("\n");
//        sb.append("模式: ").append(pattern).append("\n\n");
//        sb.append(result.toString());
//
//        // 显示期望结果和匹配结果
//        TestCase testCase = testCaseSelector.getValue();
//        if (testCase instanceof StringMatchTestCase stringMatchCase) {
//            String expectedOutput = stringMatchCase.getExpectedOutput();
//
//            if (expectedOutput != null && !expectedOutput.isEmpty()) {
//                sb.append("\n\n期望结果: ").append(expectedOutput);
//                String positionsStr = result.getPositions().toString();
//
//                String extractedExpected = expectedOutput;
//                if (expectedOutput.contains("[")) {
//                    extractedExpected = expectedOutput.substring(expectedOutput.indexOf("["));
//                }
//
//                if (positionsStr.equals(extractedExpected)) {
//                    sb.append("\n√ 结果匹配");
//                } else {
//                    sb.append("\n× 结果不匹配");
//                }
//            }
//        }
//
//        resultArea.setText(sb.toString());
//    }

    // 切换编辑模式
    public void toggleEditMode() {
        boolean newEditState = !codeArea.isEditable();
        codeArea.setEditable(newEditState);
        editButton.setText(newEditState ? "取消编辑" : "编辑代码");
        reloadButton.setDisable(!newEditState);

        if (newEditState) {
            originalCode = codeArea.getText();
            resultArea.setText("您现在可以编辑代码。编辑完成后，点击「编译运行」按钮编译并执行。");
        }
    }


    // 重新加载原始代码
    public void reloadOriginalCode() {
        if (originalCode != null) {
            codeArea.setText(originalCode);
        }
    }


    // 显示提示框
    public void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("提示");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Getters
    public TextArea getCodeArea() {
        return codeArea;
    }

    public TextArea getResultArea() {
        return resultArea;
    }

    public ComboBox<String> getAlgorithmSelector() {
        return algorithmSelector;
    }

    public ComboBox<TestCase> getTestCaseSelector() {
        return testCaseSelector;
    }

    public CheckBox getUseCustomTestCheckbox() {
        return useCustomTestCheckbox;
    }

}