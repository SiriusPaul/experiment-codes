package cn.edu.ustc.gui;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * @author SiriusPaul
 * @version V1.0
 * @CreateDate 2025/3/28
 * @Description
 */

public class CodePanel extends VBox {

    private final ComboBox<String> algorithmSelector;
    private final TextArea codeArea;
    private final Map<String, AlgorithmInfo> displayNameToAlgoMap = new LinkedHashMap<>();

    // 配置文件名和路径
    private String configFileName = "config.ini";
    private String configPath = "lib";

    public static class AlgorithmInfo {
        private final String displayName;
        private final String className;

        public AlgorithmInfo(String displayName, String className) {
            this.displayName = displayName;
            this.className = className;
        }

        public String getDisplayName() { return displayName; }
        public String getClassName() { return className; }
    }

    public CodePanel() {
        setPadding(new Insets(10));
        setSpacing(10);

        // 算法选择器
        algorithmSelector = new ComboBox<>();
        algorithmSelector.setPromptText("选择算法");
        algorithmSelector.setPrefWidth(300);

        Button loadButton = new Button("加载代码");
        loadButton.setOnAction(e -> loadSelectedCode());

        HBox controlBox = new HBox(10);
        controlBox.getChildren().addAll(new Label("选择算法:"), algorithmSelector, loadButton);

        // 代码显示区域
        codeArea = new TextArea();
        codeArea.setEditable(true);
        codeArea.setWrapText(true);
        codeArea.setStyle("-fx-font-family: 'monospace';");
        VBox.setVgrow(codeArea, Priority.ALWAYS);

        getChildren().addAll(controlBox, new Label("源代码:"), codeArea);
    }


    /**
     * 从配置文件加载指定问题的算法
     */
    public void loadAlgorithmsForProblem(String problemId) {
        try {
            Properties config = loadAlgorithmConfig();
            List<AlgorithmInfo> algorithms = loadAlgorithmsFromConfig(config, problemId);

            if (algorithms.isEmpty()) {
                clearCode();
                return;
            }

            displayNameToAlgoMap.clear();
            for (AlgorithmInfo algorithm : algorithms) {
                displayNameToAlgoMap.put(algorithm.getDisplayName(), algorithm);
            }

            algorithmSelector.setItems(FXCollections.observableArrayList(displayNameToAlgoMap.keySet()));
            if (!displayNameToAlgoMap.isEmpty()) {
                algorithmSelector.setValue(displayNameToAlgoMap.keySet().iterator().next());
                loadSelectedCode();
            }
        } catch (IOException e) {
            codeArea.setText("加载算法配置失败: " + e.getMessage());
        }
    }

    /**
     * 从配置中加载特定问题的算法信息
     */
    private List<AlgorithmInfo> loadAlgorithmsFromConfig(Properties config, String problemId) {
        List<AlgorithmInfo> algorithms = new ArrayList<>();
        Map<Integer, AlgorithmInfo> orderedAlgorithms = new TreeMap<>();

        String prefix = "problem." + problemId + ".algorithm.";

        for (String key : config.stringPropertyNames()) {
            if (key.startsWith(prefix) && key.endsWith(".className")) {
                // 提取算法键和索引
                String algorithmKey = key.substring(prefix.length(), key.lastIndexOf(".className"));
                int index;
                try {
                    index = Integer.parseInt(algorithmKey);
                } catch (NumberFormatException e) {
                    index = algorithmKey.hashCode();
                }

                String baseKey = prefix + algorithmKey;
                String displayName = config.getProperty(baseKey + ".displayName");
                String className = config.getProperty(baseKey + ".className");

                if (displayName != null && className != null) {
                    orderedAlgorithms.put(index, new AlgorithmInfo(displayName, className));
                }
            }
        }

        algorithms.addAll(orderedAlgorithms.values());
        return algorithms;
    }

    /**
     * 加载算法配置文件
     */
    private Properties loadAlgorithmConfig() throws IOException {
        Properties props = new Properties();

        // 从指定的lib目录加载配置文件
        Path configFilePath = Paths.get(configPath, configFileName);
        File configFile = configFilePath.toFile();

        if (configFile.exists()) {
            // 使用InputStreamReader并指定UTF-8字符编码
            try (InputStreamReader reader = new InputStreamReader(
                    new FileInputStream(configFile), StandardCharsets.UTF_8)) {
                props.load(reader);
                return props;
            }
        }

        // 如果找不到，尝试从类路径加载
        String resourcePath = configPath + "/" + configFileName;
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(resourcePath)) {
            if (is != null) {
                props.load(is);
                return props;
            }
        }

        throw new FileNotFoundException("未找到配置文件: " + configFilePath.toAbsolutePath());
    }

    public void clearCode() {
        displayNameToAlgoMap.clear();
        algorithmSelector.getItems().clear();
        codeArea.clear();
    }

    private void loadSelectedCode() {
        String displayName = algorithmSelector.getValue();
        if (displayName == null || displayName.isEmpty()) {
            return;
        }

        AlgorithmInfo algorithm = displayNameToAlgoMap.get(displayName);
        if (algorithm == null) {
            return;
        }

        String className = algorithm.getClassName();
        String resourcePath = className.replace('.', '/') + ".java";

        try {
            String code = loadSourceCode(resourcePath);
            if (code != null) {
                codeArea.setText(code);
            } else {
                codeArea.setText("无法找到源文件: " + resourcePath);
            }
        } catch (Exception e) {
            codeArea.setText("加载源代码时出错: " + e.getMessage());
        }
    }

    private String loadSourceCode(String resourcePath) throws IOException {
        // 1. 从src目录加载
        Path srcPath = Paths.get("src", resourcePath);
        if (Files.exists(srcPath)) {
            return Files.readString(srcPath, StandardCharsets.UTF_8);
        }

        // 2. 从项目根目录的src目录加载
        Path altPath = Paths.get(".", "src", resourcePath);
        if (Files.exists(altPath)) {
            return Files.readString(altPath, StandardCharsets.UTF_8);
        }

        throw new IOException("无法找到源文件: " + resourcePath);
    }
}