package org.jkube.gitbeaver.base.command;

import org.jkube.gitbeaver.AbstractCommand;
import org.jkube.gitbeaver.GitBeaver;
import org.jkube.gitbeaver.WorkSpace;

import java.util.Map;

public class ExecuteSharingVariablesCommand extends AbstractCommand {

    private static final String SCRIPT = "script";
    private static final String FOLDER = "folder";

    public ExecuteSharingVariablesCommand() {
        super("Execute a script sharing the variables (variable changes become visible after return)");
        commandlineVariant("EXECUTE "+SCRIPT+" SHARING VARIABLES", "execute a script within the current workspace");
        commandlineVariant("EXECUTE "+SCRIPT+" SHARING VARIABLES IN "+ FOLDER, "execute a script (located in the current workspace) within a sub-workspace");
        commandlineVariant("EXECUTE "+SCRIPT+" IN "+ FOLDER+" SHARING VARIABLES", "execute a script (located in the current workspace) within a sub-workspace");
        argument(SCRIPT, "the script to be executed (relatve path in current workspace)");
        argument(FOLDER, "the folder (relatve path in current workspace) to be used as execution workspace of the script");
    }

    @Override
    public void execute(Map<String, String> variables, WorkSpace workSpace, Map<String, String> arguments) {
        String script = arguments.get(SCRIPT);
        String folder = arguments.get(FOLDER);
        WorkSpace executionWorkspace = folder == null ? workSpace :  workSpace.getSubWorkspace(folder);
        GitBeaver.scriptExecutor().executeSharingVariables(script, null, variables, workSpace, executionWorkspace);
    }

}
