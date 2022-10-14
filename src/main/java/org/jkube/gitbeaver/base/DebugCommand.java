package org.jkube.gitbeaver.base;

import org.jkube.gitbeaver.AbstractCommand;
import org.jkube.gitbeaver.GitBeaver;
import org.jkube.gitbeaver.WorkSpace;
import org.jkube.logging.Log;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import static org.jkube.logging.Log.onException;

/**
 * Usage:
 *    FOR var IN path DO script
 *    FOR var IN path MATCHING pattern DO script
 */
public class DebugCommand extends AbstractCommand {

    private static final String DEFAULT_OPTIONS = "V";
    private static final String OPTION_SUBSTITUTED_LINE = "S";
    private static final String OPTION_LINE = "L";
    private static final String OPTION_LINE_NUM = "N";
    private static final String OPTION_VARIABLES = "V";
    private static final String OPTION_CALL_STACK = "C";
    private static final String OPTION_WORKSPACE = "W";

    protected DebugCommand() {
        super(0, 1, "debug");
    }

    @Override
    public void execute(Map<String, String> variables, WorkSpace workSpace, List<String> arguments) {
        String options = arguments.isEmpty() ? DEFAULT_OPTIONS : arguments.get(0).toUpperCase();
        if (options.contains(OPTION_VARIABLES)) {
            logVariables(variables);
        }
        if (options.contains(OPTION_LINE)) {
            logLine("Previous code line (before variable substitution):", GitBeaver.scriptExecutor().getPreviousLine());
        }
        if (options.contains(OPTION_SUBSTITUTED_LINE)) {
            logLine("Previous code line (afteer variable substitution):", GitBeaver.scriptExecutor().getPreviousSubstitutedLine());
        }
    }

    private void logVariables(Map<String, String> variables) {
        StringBuilder sb = new StringBuilder();
        sb.append("Variables:");
        variables.forEach((k,v) -> {
            sb.append("\n   ").append(k).append(": ").append(v);
        });
        Log.log(sb.toString());
    }

    private void logLine(String info, String line) {
        StringBuilder sb = new StringBuilder();
        sb.append(info).append(":\n");
        sb.append("   ").append(line);
        Log.log(sb.toString());
    }

}
