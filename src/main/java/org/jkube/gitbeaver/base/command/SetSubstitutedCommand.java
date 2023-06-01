package org.jkube.gitbeaver.base.command;

import org.jkube.gitbeaver.AbstractCommand;
import org.jkube.gitbeaver.WorkSpace;

import java.util.Map;

import static org.jkube.gitbeaver.CommandParser.REST;

public class SetSubstitutedCommand extends AbstractCommand {

    private static final String REGEX = "regex";
    private static final String SUBSTITUTE = "substitute";
    private static final String VARIABLE = "variable";

    public SetSubstitutedCommand() {
        super("Set a variable to a value after applying a regex substitution");
        commandline("SET "+VARIABLE+" SUBSTITUTING "+REGEX+" BY "+SUBSTITUTE+" IN *");
        argument(VARIABLE, "name of the variable to be set");
        argument(REGEX, "regular expression to be replaced");
        argument(SUBSTITUTE, "string to be prelaces found places with");
        argument(REST, "input string");
    }

    @Override
    public void execute(Map<String, String> variables, WorkSpace workSpace, Map<String, String> arguments) {
        variables.put(arguments.get(VARIABLE), arguments.get(REST).replaceAll(arguments.get(REGEX), arguments.get(SUBSTITUTE)));
    }

}
