package cn.edu.ustc.model;

/**
 * @author SiriusPaul
 * @version V1.0
 * @CreateDate 2025/5/20
 * @Description 字符串匹配测试用例
 */
public class StringMatchTestCase extends TestCase {
    private String text;
    private String pattern;

    public StringMatchTestCase() {
        super();
    }

    public StringMatchTestCase(int id, String name, String problemId,
                           String text, String pattern, String expectedOutput) {
        super(id, problemId, name, null, expectedOutput);
        this.text = text;
        this.pattern = pattern;
    }

    // 覆盖父类的getInputArray方法，确保不被误用
    @Override
    public int[] getInputArray() {
        throw new UnsupportedOperationException("字符串匹配测试用例不支持获取整数数组");
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }
}