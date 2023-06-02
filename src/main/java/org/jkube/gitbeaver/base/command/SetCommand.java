package org.jkube.gitbeaver.base.command;

import org.jkube.gitbeaver.AbstractCommand;
import org.jkube.gitbeaver.WorkSpace;

import java.util.Map;

import static org.jkube.gitbeaver.CommandParser.REST;

public class SetCommand extends AbstractCommand {

    private static final String VARIABLE = "variable";

    public SetCommand() {
        super("Set a variable to a value");
        commandlineVariant("SET "+VARIABLE+" *", "set variable to a value");
        commandlineVariant("SET "+VARIABLE, "set variable to empty string");
        argument(VARIABLE, "name of the variable to be set");
        argument(REST, "value that the variable shall be set to");
    }

    @Override
    public void execute(Map<String, String> variables, WorkSpace workSpace, Map<String, String> arguments) {
        String value = arguments.get(REST);
        if (value == null) {
            value = "";
        }
        variables.put(arguments.get(VARIABLE), value);
    }

}
