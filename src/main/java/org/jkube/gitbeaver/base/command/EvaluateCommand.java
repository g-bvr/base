package org.jkube.gitbeaver.base.command;

import org.jkube.application.Application;
import org.jkube.gitbeaver.AbstractCommand;
import org.jkube.gitbeaver.WorkSpace;
import org.jkube.util.Expect;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Map;

import static org.jkube.gitbeaver.CommandParser.REST;

public class EvaluateCommand extends AbstractCommand {

    private final String CURRENT_DAY_OF_WEEK = "CurrentDayOfWeek";
    private final String CURRENT_HOUR_OF_DAY = "CurrentHourOfDay";

    private final String TIME_ZONE = "CET";

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
        if (split.length == 1) {
            return evaluateFunction(split[0]);
        }
        Expect.equal(split.length, 3).elseFail("expected 3 elements");
        Expect.equal(split[1], "==").elseFail("only == implemented so far");
        return split[0].equals(split[2]);
    }

    private Object evaluateFunction(String function) {
        Expect.isTrue(function.endsWith("()")).elseFail("only no-arg functions implemented so far");
        String name = function.substring(0, function.length() - 2);
        return switch(name) {
            case CURRENT_DAY_OF_WEEK -> currentDayOfWeek();
            case CURRENT_HOUR_OF_DAY -> currentHourOfDay();
            default -> Application.fail("Undefined function: " + name);
        };
    }

    private String currentDayOfWeek() {
        return DayOfWeek.from(now()).toString().substring(0, 3).toLowerCase();
    }

    private int currentHourOfDay() {
        return now().getHour();
    }

    private LocalTime now() {
        return LocalTime.now(ZoneId.of(TIME_ZONE));
    }

}
