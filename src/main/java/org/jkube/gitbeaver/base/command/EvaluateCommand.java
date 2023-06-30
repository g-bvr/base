package org.jkube.gitbeaver.base.command;

import org.jkube.gitbeaver.AbstractCommand;
import org.jkube.gitbeaver.GitBeaver;
import org.jkube.gitbeaver.WorkSpace;
import org.jkube.util.Expect;

import java.util.HashMap;
import java.util.Map;

import static org.jkube.gitbeaver.CommandParser.REST;

public class EvaluateCommand extends AbstractCommand {

    private static final String VARIABLE = "variable";

    public EvaluateCommand() {
        super("Evaluate some numeric or string expression");
        commandline("EVALUATE "+VARIABLE+" = "+REST);
        argument(VARIABLE, "the variable that shall be set to a evaluated value");
        argument(REST, "an expression consisting of operations or functions");
    }

    @Override
    public void execute(Map<String, String> variables, WorkSpace workSpace, Map<String, String> arguments) {
        variables.put(arguments.get(VARIABLE), evaluate(arguments.get(REST)).toString());

    }

    private Object evaluate(String expression) {
        String[] split = expression.split(" ");
        Expect.equal(split.length, 3).elseFail("expected 3 elements");
        Expect.equal(split[1], "==").elseFail("only == implemented so far");
        return split[0].equals(split[2]);
    }

}
