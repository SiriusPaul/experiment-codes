package cn.edu.ustc.gui.strategy;

import cn.edu.ustc.gui.ProblemPanel;
import cn.edu.ustc.model.QueensSolutionResult;
import cn.edu.ustc.service.TestCaseService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.HBox;

import java.util.List;

/**
 * @author SiriusPaul
 * @version V1.0
 * @CreateDate 2025/5/21
 * @Description 八皇后问题策略
 */
public class RunQueensStrategy implements RunProblemStrategy {
    private final ProblemPanel panel;
    private final TestCaseService testCaseService;
    private final Spinner<Integer> boardSizeSpinner;
    private final HBox queensOptionsBox;

    public RunQueensStrategy(ProblemPanel panel, TestCaseService testCaseService) {
        this.panel = panel;
        this.testCaseService = testCaseService;

        // 创建棋盘大小选择器
        boardSizeSpinner = new Spinner<>(new SpinnerValueFactory.IntegerSpinnerValueFactory(4, 12, 8));
        boardSizeSpinner.setEditable(true);
        boardSizeSpinner.setPrefWidth(80);

        // 创建选项容器
        queensOptionsBox = new HBox(10);
        queensOptionsBox.setPadding(new Insets(10));
        queensOptionsBox.setAlignment(Pos.CENTER_LEFT);
        queensOptionsBox.getChildren().addAll(new Label("棋盘大小:"), boardSizeSpinner);
    }

    @Override
    public void loadTestCases(String problemId) {
        // 八皇后问题不使用数据库中的测试用例
    }

    @Override
    public List<Node> createProblemControls() {
        return List.of(queensOptionsBox);
    }

    @Override
    public void prepareUI(boolean visible) {
        queensOptionsBox.setVisible(visible);
        panel.getTestCaseSelector().setVisible(false);
    }

    @Override
        // 八皇后问题不使用测试用例输入
    public void loadTestCaseInputs() {
        // 不需要实现
    }
    

    @Override
    public void execute() throws Exception {
        int boardSize = boardSizeSpinner.getValue();
        panel.getResultArea().setText("正在计算...");

        try {
            String selectedAlgo = panel.getAlgorithmSelector().getValue();
            if (selectedAlgo == null) {
                panel.getResultArea().setText("请选择一个算法");
                return;
            }

            // 根据算法名称选择策略类
            String strategyClassName;
            if ("回溯法".equals(selectedAlgo)) {
                strategyClassName = "cn.edu.ustc.algorithm.BacktrackingQueens";
            } else if ("最小冲突法".equals(selectedAlgo)) {
                strategyClassName = "cn.edu.ustc.algorithm.MinConflictsQueens";
            } else {
                panel.getResultArea().setText("未知算法: " + selectedAlgo);
                return;
            }

            // 加载并实例化策略类
            Class<?> strategyClass = Class.forName(strategyClassName);
            Object strategy = strategyClass.getDeclaredConstructor().newInstance();

            // 调用solve方法
            Object result = strategyClass.getMethod("solve", int.class).invoke(strategy, boardSize);

            // 显示结果
            if (result instanceof QueensSolutionResult) {
                displaySolutions((QueensSolutionResult)result);
            }
        } catch (Exception e) {
            panel.getResultArea().setText("计算错误: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // 显示八皇后问题的解决方案
    private void displaySolutions(QueensSolutionResult result) {
        StringBuilder output = new StringBuilder();
        List<int[]> solutions = result.getSolutions();

        output.append("找到 ").append(solutions.size()).append(" 个解决方案\n\n");

        // 限制显示前10个解决方案
        int displayCount = Math.min(10, solutions.size());

        for (int i = 0; i < displayCount; i++) {
            int[] solution = solutions.get(i);
            output.append("方案 ").append(i + 1).append(":\n");

            // 显示棋盘
            for (int j : solution) {
                for (int col = 0; col < solution.length; col++) {
                    output.append(j == col ? "Q " : ". ");
                }
                output.append("\n");
            }
            output.append("\n");
        }

        if (solutions.size() > displayCount) {
            output.append("... 还有 ").append(solutions.size() - displayCount).append(" 个解决方案未显示");
        }

        panel.getResultArea().setText(output.toString());
    }

    // Getter for testing
    public Spinner<Integer> getBoardSizeSpinner() {
        return boardSizeSpinner;
    }
}