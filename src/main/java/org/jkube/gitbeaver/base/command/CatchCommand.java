package org.jkube.gitbeaver.base.command;

import org.jkube.gitbeaver.AbstractCommand;
import org.jkube.gitbeaver.FailureHandler;
import org.jkube.gitbeaver.GitBeaver;
import org.jkube.gitbeaver.WorkSpace;
import org.jkube.gitbeaver.util.FileUtil;
import org.jkube.gitbeaver.logging.Log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import static org.jkube.gitbeaver.CommandParser.REST;

public class CatchCommand extends AbstractCommand {

    private static final String SCRIPT = "script";

    public CatchCommand() {
        super("Catch exceptions when executing a command, stacktrace and message will be written to files 'stacktrace.txt' and 'exception.txt' in current working directory");
        commandline("CATCH IN "+SCRIPT+" DO "+REST);
        argument(SCRIPT, "the script to be executed when an exception occurs");
        argument(REST, "the command to be tried (from which occuring exceptions will be caught)");
    }

    @Override
    public void execute(Map<String, String> variables, WorkSpace workSpace, Map<String, String> arguments) {
        String script = arguments.get(SCRIPT);
        String command = arguments.get(REST);
        try {
            FailureHandler.NUM_CATCHING_BLOCKS.incrementAndGet();
            Map<String, String> parsedArgs = new HashMap<>();
            GitBeaver.commandParser()
                    .parseCommand(arguments.get(REST), parsedArgs)
                    .execute(variables, workSpace, parsedArgs);
        } catch (Throwable t) {
            Log.warn("Exception was caught: "+t.getMessage());
            FileUtil.store(workSpace.getAbsolutePath("exception.txt"), t.getMessage());
            String stacktrace = getStackTrace(t);
            FileUtil.store(workSpace.getAbsolutePath("stacktrace.txt"), stacktrace);
            GitBeaver.getApplicationLogger(variables).createSubConsole().error(stacktrace);
            GitBeaver.scriptExecutor().executeSharingVariables(script, null, variables, workSpace, workSpace);
        } finally {
            FailureHandler.NUM_CATCHING_BLOCKS.decrementAndGet();
        }
    }

    private String getStackTrace(Throwable t) {
        StringWriter stringWriter = new StringWriter();
        t.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }

}
