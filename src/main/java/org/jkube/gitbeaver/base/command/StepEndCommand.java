package org.jkube.gitbeaver.base.command;

import org.jkube.gitbeaver.AbstractCommand;
import org.jkube.gitbeaver.GitBeaver;
import org.jkube.gitbeaver.WorkSpace;

import java.util.List;
import java.util.Map;

public class StepEndCommand extends AbstractCommand {

    public StepEndCommand() {
        super(0, 0, "step", "end");
    }

    @Override
    public void execute(Map<String, String> variables, WorkSpace workSpace, List<String> arguments) {
        GitBeaver.getApplicationLogger(variables).closeStep();
    }
}
