package org.jkube.gitbeaver.base.command;

import org.jkube.gitbeaver.AbstractCommand;
import org.jkube.gitbeaver.WorkSpace;

import java.util.Map;

import static org.jkube.gitbeaver.CommandParser.REST;

public class SetRemovingCommand extends AbstractCommand {

    private static final String REGEX = "regex";
    private static final String VARIABLE = "variable";

    public SetRemovingCommand() {
        super("Set a variable to a value after applying a regex substitution");
        commandline("SET "+VARIABLE+" REMOVING "+REGEX+" IN *");
        argument(VARIABLE, "name of the variable to be set");
        argument(REGEX, "regular expression to be removed");
        argument(REST, "input string");
    }

    @Override
    public void execute(Map<String, String> variables, WorkSpace workSpace, Map<String, String> arguments) {
        variables.put(arguments.get(VARIABLE), arguments.get(REST).replaceAll(arguments.get(REGEX), ""));
    }

}
