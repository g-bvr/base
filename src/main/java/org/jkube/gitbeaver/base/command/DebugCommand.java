package org.jkube.gitbeaver.base.command;

import org.jkube.gitbeaver.AbstractCommand;
import org.jkube.gitbeaver.GitBeaver;
import org.jkube.gitbeaver.WorkSpace;
import org.jkube.logging.Log;

import java.util.Map;
import java.util.regex.Pattern;

public class DebugCommand extends AbstractCommand {

    private static final String DEFAULT_OPTIONS = "VS";
    private final Pattern OPTIONS_PATTERN = Pattern.compile("[SLNVCW]+");
    private static final String OPTION_SUBSTITUTED_LINE = "S";
    private static final String OPTION_LINE = "L";
    private static final String OPTION_LINE_NUM = "N";
    private static final String OPTION_VARIABLES = "V";
    private static final String OPTION_CALL_STACK = "C";
    private static final String OPTION_WORKSPACE = "W";

    private static final String OPTIONS = "options";

    public DebugCommand() {
        super("Print information for the purpose of debugging");
        commandlineVariant("DEBUG "+OPTIONS, "Provide a debug log with specified options");
        commandlineVariant("DEBUG", "Provide a debug log with default options ("+DEFAULT_OPTIONS+")");
        argument(OPTIONS, """
            A string consisting of capital letters with the following meanings:
            L logs the line that was executed previous to the DEBUG command (without variable substitution)
            S logs the line that was executed previous to the DEBUG command (after variable substitution)
            V logs all existing variables and their values
            W logs the path of the current workspace
            C logs the current call stack
            """);
    }

    @Override
    public void execute(Map<String, String> variables, WorkSpace workSpace, Map<String, String> arguments) {
        String options = arguments.get(OPTIONS);
        options = (options == null) ? DEFAULT_OPTIONS : options.toUpperCase();
        if (!OPTIONS_PATTERN.matcher(options).matches()) {
            Log.error("Illegal option string: "+options);
        }
        if (options.contains(OPTION_VARIABLES)) {
            logVariables(variables);
        }
        if (options.contains(OPTION_LINE)) {
            logLine("Previous code line (before variable substitution):", GitBeaver.scriptExecutor().getPreviousLine());
        }
        if (options.contains(OPTION_SUBSTITUTED_LINE)) {
            logLine("Previous code line (afteer variable substitution):", GitBeaver.scriptExecutor().getPreviousSubstitutedLine());
        }
        if (options.contains(OPTION_WORKSPACE)) {
            logLine("Current workspace: ", workSpace.toString());
        }
        if (options.contains(OPTION_CALL_STACK)) {
            logLine("Call stack: ", "not implemented, yet, sorry");
        }
    }

    private void logVariables(Map<String, String> variables) {
        StringBuilder sb = new StringBuilder();
        sb.append("Variables:");
        variables.forEach((k,v) -> sb.append("\n   ").append(k).append(": ").append(v));
        Log.log(sb.toString());
    }

    private void logLine(String info, String line) {
        Log.log(
                info + ":\n"
                + "   " + line
        );
    }

}
