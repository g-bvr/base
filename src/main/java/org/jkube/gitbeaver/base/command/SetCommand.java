package org.jkube.gitbeaver.base.command;

import org.jkube.gitbeaver.AbstractCommand;
import org.jkube.gitbeaver.WorkSpace;
import org.jkube.gitbeaver.util.FileUtil;
import org.jkube.logging.Log;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

/**
 * Usage:
 *    FOR var IN path DO script
 *    FOR var IN path MATCHING pattern DO script
 */
public class SetCommand extends AbstractCommand {

    public SetCommand() {
        super(2, 2, "set");
    }

    @Override
    public void execute(Map<String, String> variables, WorkSpace workSpace, List<String> arguments) {
        variables.put(arguments.get(0), arguments.get(1));
    }

}
