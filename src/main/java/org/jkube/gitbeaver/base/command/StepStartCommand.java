package org.jkube.gitbeaver.base.command;

import org.jkube.gitbeaver.AbstractCommand;
import org.jkube.gitbeaver.GitBeaver;
import org.jkube.gitbeaver.WorkSpace;

import java.util.Map;

public class StepStartCommand extends AbstractCommand {

    private static final String TITLE = "title";

    public StepStartCommand() {
        super("Marks the start of a step block (for structured logging)");
        commandline("STEP START "+TITLE);
        argument(TITLE, "the name of the step (used as title of the step section in the log)");
    }

    @Override
    public void execute(Map<String, String> variables, WorkSpace workSpace, Map<String, String> arguments) {
        GitBeaver.getApplicationLogger(variables).createStep(arguments.get(TITLE));
    }
}
