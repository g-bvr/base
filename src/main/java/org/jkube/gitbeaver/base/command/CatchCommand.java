package org.jkube.gitbeaver.base.command;

import org.jkube.gitbeaver.AbstractCommand;
import org.jkube.gitbeaver.GitBeaver;
import org.jkube.gitbeaver.WorkSpace;

import java.util.HashMap;
import java.util.Map;

import static org.jkube.gitbeaver.CommandParser.REST;

public class CatchCommand extends AbstractCommand {

    private static final String SCRIPT = "script";

    public CatchCommand() {
        super("Catch exceptions when executing a command");
        commandline("CATCH IN "+SCRIPT+" DO "+REST);
        argument(SCRIPT, "the script to be executed when an exception occurs");
        argument(REST, "the command to be tried (from which occuring exceptions will be caught)");
    }

    @Override
    public void execute(Map<String, String> variables, WorkSpace workSpace, Map<String, String> arguments) {
        String script = arguments.get(SCRIPT);
        String command = arguments.get(REST);
        try {
            Map<String, String> parsedArgs = new HashMap<>();
            GitBeaver.commandParser()
                    .parseCommand(arguments.get(REST), parsedArgs)
                    .execute(variables, workSpace, parsedArgs);
        } catch (Throwable t) {
            GitBeaver.scriptExecutor().executeNotSharingVariables(script, null, variables, workSpace, workSpace);
        }
    }

}
