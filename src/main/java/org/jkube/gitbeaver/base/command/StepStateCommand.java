package org.jkube.gitbeaver.base.command;

import org.jkube.gitbeaver.AbstractCommand;
import org.jkube.gitbeaver.GitBeaver;
import org.jkube.gitbeaver.WorkSpace;
import org.jkube.gitbeaver.applicationlog.StepState;

import java.util.List;
import java.util.Map;

public class StepStateCommand extends AbstractCommand {

    public StepStateCommand() {
        super(1, 1, "step", "state");
    }

    @Override
    public void execute(Map<String, String> variables, WorkSpace workSpace, List<String> arguments) {
        GitBeaver.getApplicationLogger(variables).setStepState(StepState.valueOf(arguments.get(0)));
    }
}
