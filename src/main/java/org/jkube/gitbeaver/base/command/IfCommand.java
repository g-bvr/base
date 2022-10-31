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
    private static final String FALSE = "false";

    public IfCommand() {
        super(3, 7, "if");
    }

    @Override
    public void execute(Map<String, String> variables, WorkSpace workSpace, List<String> arguments) {
        String value = arguments.get(0);
        expectArg(1, "then", arguments);
        String thenScript = arguments.get(2);
        String elseScript;
        if (arguments.size() > 3) {
            expectNumArgs(5, arguments);
            expectArg(3, "else", arguments);
            elseScript = arguments.get(4);
        } else {
            elseScript = null;
        }
        if (value.equals("0") || value.equalsIgnoreCase(NO) || value.equalsIgnoreCase(FALSE)) {
            if (elseScript != null) {
                GitBeaver.scriptExecutor().execute(elseScript, null, variables, workSpace, workSpace);
            }
        } else {
            GitBeaver.scriptExecutor().execute(thenScript, null, variables, workSpace, workSpace);
        }
    }

}
