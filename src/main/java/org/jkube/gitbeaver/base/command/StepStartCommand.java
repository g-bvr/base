package org.jkube.gitbeaver.base.command;

import org.jkube.gitbeaver.AbstractCommand;
import org.jkube.gitbeaver.GitBeaver;
import org.jkube.gitbeaver.WorkSpace;

import java.util.Map;

import static org.jkube.gitbeaver.CommandParser.REST;

public class StepStartCommand extends AbstractCommand {

    public StepStartCommand() {
        super("Marks the start of a step block (for structured logging)");
        commandline("STEP START *");
        argument(REST, "String to be used as title of the step section in the log");
    }

    @Override
    public void execute(Map<String, String> variables, WorkSpace workSpace, Map<String, String> arguments) {
        GitBeaver.getApplicationLogger(variables).createStep(arguments.get(REST));
    }
}
