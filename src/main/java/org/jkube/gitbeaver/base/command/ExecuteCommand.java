package org.jkube.gitbeaver.base.command;

import org.jkube.gitbeaver.AbstractCommand;
import org.jkube.gitbeaver.GitBeaver;
import org.jkube.gitbeaver.WorkSpace;
import org.jkube.gitbeaver.util.FileUtil;
import org.jkube.logging.Log;
import org.jkube.util.Expect;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Usage:
 *    FOR var IN path DO script
 *    FOR var IN path MATCHING pattern DO script
 */
public class ExecuteCommand extends AbstractCommand {

    public ExecuteCommand() {
        super(1, 3, "execute");
    }

    @Override
    public void execute(Map<String, String> variables, WorkSpace workSpace, List<String> arguments) {
        String script = arguments.get(0);
        if (arguments.size() > 1) {
            expectArg(1, "in", arguments);
            expectNumArgs(3, arguments);
        }
        WorkSpace executionWorkspace = arguments.size() == 3
                ? workSpace.getSubWorkspace(arguments.get(2))
                : workSpace;
        GitBeaver.scriptExecutor().execute(script, null, variables, workSpace, executionWorkspace);
    }

}
