package org.jkube.gitbeaver.base.command;

import org.jkube.gitbeaver.AbstractCommand;
import org.jkube.gitbeaver.GitBeaver;
import org.jkube.gitbeaver.WorkSpace;
import org.jkube.gitbeaver.applicationlog.StepState;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StepStateCommand extends AbstractCommand {

    private static final String STATE = "state";

    public StepStateCommand() {
        super("Set the state of the current step");
        commandline("STEP STATE "+STATE);
        List<String> values = Arrays.stream(StepState.values()).map(StepState::toString).collect(Collectors.toList());
        argument(STATE, "the new state assigned to the current step. Possible values: "+String.join(", ", values));
    }

    @Override
    public void execute(Map<String, String> variables, WorkSpace workSpace, Map<String, String> arguments) {
        GitBeaver.getApplicationLogger(variables).setStepState(StepState.valueOf(arguments.get(STATE)));
    }
}
