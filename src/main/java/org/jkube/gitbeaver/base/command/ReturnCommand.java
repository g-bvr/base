package org.jkube.gitbeaver.base.command;

import org.jkube.gitbeaver.AbstractCommand;
import org.jkube.gitbeaver.GitBeaver;
import org.jkube.gitbeaver.ScriptExecutor;
import org.jkube.gitbeaver.WorkSpace;

import java.net.URL;
import java.util.List;
import java.util.Map;

import static org.jkube.logging.Log.onException;

/**
 * Usage: git clone providerUrl repositoryName [tag]
 */
public class ReturnCommand extends AbstractCommand {

    public ReturnCommand() {
        super(0, null, "return");
    }

    @Override
    public void execute(Map<String, String> variables, WorkSpace workSpace, List<String> arguments) {
        variables.put(ScriptExecutor.RETURN_VALUE_VARIABLE, String.join(" ", arguments));
    }

}
