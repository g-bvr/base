package org.jkube.gitbeaver.base.command;

import org.jkube.gitbeaver.AbstractCommand;
import org.jkube.gitbeaver.GitBeaver;
import org.jkube.gitbeaver.WorkSpace;

import java.util.HashMap;
import java.util.Map;

import static org.jkube.gitbeaver.CommandParser.REST;

public class IfCommand extends AbstractCommand {

    private static final String CONDITION = "condition";
    private static final String NOT_CONDITION = "notcondition";
    private static final String SCRIPT1 = "script1";
    private static final String SCRIPT2 = "script2";
    private static final String VARIABLE = "variable";

    private static final String VALUE1 = "value1";
    private static final String VALUE2 = "value2";

    private static final String NO = "no";
    private static final String ZERO = "0";
    private static final String FALSE = "false";

    public IfCommand() {
        super("Branching statements, depending on a boolean condition");
        commandlineVariant("IF "+CONDITION+" EXECUTE "+SCRIPT1, "Execute a script if condition is met");
        commandlineVariant("IF NOT "+NOT_CONDITION+" EXECUTE "+SCRIPT2, "Execute a script if condition is not met");
        commandlineVariant("IF "+CONDITION+" EXECUTE "+SCRIPT1+" ELSE "+SCRIPT2, "Execute one of two scripts depending on condition");
        commandlineVariant("IF "+CONDITION+" SET "+VARIABLE+" TO "+VALUE1+" ELSE "+VALUE2, "Set variable to one of two values depending on condition");
        commandlineVariant("IF "+CONDITION+" THEN "+REST, "Execute a command if condition is met");
        commandlineVariant("IF NOT "+NOT_CONDITION+" THEN "+REST, "Execute a command if condition is not met");
        argument(CONDITION, "a variable that holds the condition value. The 'ELSE' action is taken if the variable does not exist, is empty, or is one out of "+NO+", "+ZERO+", "+FALSE);
        argument(NOT_CONDITION, "a variable that holds the condition value. The action is taken if the variable does not exist, is empty, or is one out of "+NO+", "+ZERO+", "+FALSE);
        argument(SCRIPT1, "the script that is to be execture if the condition is evaluated to true");
        argument(SCRIPT2, "the script that is to be execture if the condition is is evaluated to false");
        argument(VALUE1, "the value that is used if the condition is evaluated to true");
        argument(VALUE2, "the value that is used if the condition is evaluated to false");
        argument(VARIABLE, "the variable that shall be set to a conditional value");
        argument(REST, "the command to be executed if condition is evaluated to true");
    }

    @Override
    public void execute(Map<String, String> variables, WorkSpace workSpace, Map<String, String> arguments) {
        String value = variables.get(arguments.containsKey(CONDITION) ? arguments.get(CONDITION) : arguments.get(NOT_CONDITION));
        if (value != null) {
            value = value.trim();
        }
        boolean conditionFalse = (value == null) || value.isEmpty() || value.equals(ZERO) || value.equalsIgnoreCase(NO) || value.equalsIgnoreCase(FALSE);
        System.out.println("Here: "+value+" --> "+conditionFalse+" "+arguments.containsKey(NOT_CONDITION));
        if ((arguments.containsKey(SCRIPT1) || arguments.containsKey(SCRIPT2))) {
            System.out.println("AAAAAA");
            if (conditionFalse) {
                System.out.println("Script2 "+SCRIPT2);
                executeScript(arguments.get(SCRIPT2), variables, workSpace);
            } else {
                System.out.println("Script1 "+SCRIPT1);
                executeScript(arguments.get(SCRIPT1), variables, workSpace);
            }
        } else if (arguments.containsKey(VARIABLE)) {
            System.out.println("BBBBB");
            variables.put(arguments.get(VARIABLE), arguments.get(conditionFalse ? VALUE2 : VALUE1));
        } else if (conditionFalse == arguments.containsKey(NOT_CONDITION)) {
            System.out.println("=====================");
            Map<String, String> parsedArgs = new HashMap<>();
            GitBeaver.commandParser()
                    .parseCommand(arguments.get(REST), parsedArgs)
                    .execute(variables, workSpace, parsedArgs);
        }
    }

    private void executeScript(String script, Map<String, String> variables, WorkSpace workSpace) {
        if (script != null) {
            GitBeaver.scriptExecutor().executeNotSharingVariables(script, null, variables, workSpace, workSpace);
        }
    }

}
