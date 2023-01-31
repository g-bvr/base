package org.jkube.gitbeaver.base.command;

import org.jkube.gitbeaver.AbstractCommand;
import org.jkube.gitbeaver.WorkSpace;
import org.jkube.gitbeaver.util.FileUtil;
import org.jkube.logging.Log;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import static org.jkube.gitbeaver.CommandParser.REST;

public class SetCommand extends AbstractCommand {

    private static final String VARIABLE = "variable";

    public SetCommand() {
        super("Set a variable to a value");
        commandline("SET "+VARIABLE+" *");
        argument(VARIABLE, "name of the variable to be set");
        argument(REST, "value that the variable shall be set to");
    }

    @Override
    public void execute(Map<String, String> variables, WorkSpace workSpace, Map<String, String> arguments) {
        variables.put(arguments.get(VARIABLE), arguments.get(REST));
    }

}
