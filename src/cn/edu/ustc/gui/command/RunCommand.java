package cn.edu.ustc.gui.command;

import cn.edu.ustc.gui.ProblemPanel;

/**
 * @author SiriusPaul
 * @version V1.0
 * @CreateDate 2025/5/21
 * @Description
 */
public class RunCommand implements Command{
    private final ProblemPanel panel;

    public RunCommand(ProblemPanel panel) {
        this.panel = panel;
    }

    @Override
    public void execute() {
        panel.runProblem();
    }
}
