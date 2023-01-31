package org.jkube.gitbeaver.base.command;

import org.jkube.gitbeaver.AbstractCommand;
import org.jkube.gitbeaver.GitBeaver;
import org.jkube.gitbeaver.ScriptExecutor;
import org.jkube.gitbeaver.WorkSpace;

import java.net.URL;
import java.util.List;
import java.util.Map;

import static org.jkube.gitbeaver.CommandParser.REST;
import static org.jkube.logging.Log.onException;

public class ReturnCommand extends AbstractCommand {

    public ReturnCommand() {
        super("Return a value from an executed script back to the calling script. The returned value is accesible in the calling script via the variable "+ScriptExecutor.RETURN_VALUE_VARIABLE);
        commandline("RETURN *");
        argument(REST, "String to returned");
    }

    @Override
    public void execute(Map<String, String> variables, WorkSpace workSpace, Map<String, String> arguments) {
        variables.put(ScriptExecutor.RETURN_VALUE_VARIABLE, arguments.get(REST));
    }

}
