package org.jkube.gitbeaver.base.command;

import org.jkube.gitbeaver.AbstractCommand;
import org.jkube.gitbeaver.GitBeaver;
import org.jkube.gitbeaver.WorkSpace;
import org.jkube.logging.Log;
import org.jkube.util.Expect;

import java.util.List;
import java.util.Map;

/**
 * Usage:
 *    FOR var IN path DO script
 *    FOR var IN path MATCHING pattern DO script
 */
public class IfCommand extends AbstractCommand {

    private static final String NO = "no";
    private static final String ZERO = "0";
    private static final String FALSE = "false";
    private static final String THEN = "then";

    private static final String ELSE = "else";

    public IfCommand() {
        super(3, 7, "if");
    }

    @Override
    public void execute(Map<String, String> variables, WorkSpace workSpace, List<String> arguments) {
        String value = variables.get(arguments.get(0));
        if (value != null) {
            value = value.trim();
        }
        expectArg(1, THEN, arguments);
        String thenScript = arguments.get(2);
        String elseScript;
        if (arguments.size() > 3) {
            expectNumArgs(5, arguments);
            expectArg(3, ELSE, arguments);
            elseScript = arguments.get(4);
        } else {
            elseScript = null;
        }
        if ((value == null) || value.isEmpty() || value.equals(ZERO) || value.equalsIgnoreCase(NO) || value.equalsIgnoreCase(FALSE)) {
            if (elseScript != null) {
                GitBeaver.scriptExecutor().execute(elseScript, null, variables, workSpace, workSpace);
            }
        } else {
            GitBeaver.scriptExecutor().execute(thenScript, null, variables, workSpace, workSpace);
        }
    }

}
