package org.jkube.gitbeaver.base.command;

import org.jkube.gitbeaver.AbstractCommand;
import org.jkube.gitbeaver.GitBeaver;
import org.jkube.gitbeaver.WorkSpace;

import java.util.List;
import java.util.Map;

public class StepEndCommand extends AbstractCommand {

    public StepEndCommand() {
        super("Marks the end of a step block (for structured logging)");
        commandline("STEP END");
    }

    @Override
    public void execute(Map<String, String> variables, WorkSpace workSpace, Map<String, String> arguments) {
        GitBeaver.getApplicationLogger(variables).closeStep();
    }
}
