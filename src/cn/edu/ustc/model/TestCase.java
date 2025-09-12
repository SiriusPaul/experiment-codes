package cn.edu.ustc.model;

/**
 * @author SiriusPaul
 * @version V1.0
 * @CreateDate 2025/5/17
 * @Description 测试用例类,用于存储测试用例的信息,封装了测试用例的基本信息
 */
public class TestCase {
    private int id;
    private String problemId;
    private String name;
    private String inputData;
    private String expectedOutput;

    public TestCase() {}

    public TestCase(int id, String problemId, String name, String inputData, String expectedOutput) {
        this.id = id;
        this.problemId = problemId;
        this.name = name;
        this.inputData = inputData;
        this.expectedOutput = expectedOutput;
    }

    // 将输入数据转换为int数组
    public int[] getInputArray() {
        if (inputData == null || inputData.isEmpty()) {
            return new int[0];
        }

        String[] parts = inputData.split(",");
        int[] result = new int[parts.length];

        for (int i = 0; i < parts.length; i++) {
            result[i] = Integer.parseInt(parts[i].trim());
        }

        return result;
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getProblemId() { return problemId; }
    public void setProblemId(String problemId) { this.problemId = problemId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getInputData() { return inputData; }
    public void setInputData(String inputData) { this.inputData = inputData; }

    public String getExpectedOutput() { return expectedOutput; }
    public void setExpectedOutput(String expectedOutput) { this.expectedOutput = expectedOutput; }

    // 重写toString方法，方便打印测试用例信息
    @Override
    public String toString() {
        return name;
    }
}
