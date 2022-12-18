package org.jkube.gitbeaver.base.command;

import org.jkube.gitbeaver.SimpleCommand;
import org.jkube.gitbeaver.WorkSpace;
import org.jkube.gitbeaver.util.FileUtil;
import org.jkube.logging.Log;

import java.nio.file.Path;
import java.util.List;

/**
 * Usage: git clone providerUrl repositoryName [tag]
 */
public class CleanupCommand extends SimpleCommand {

    public CleanupCommand() {
        super(1, "cleanup");
    }

    @Override
    public void execute(WorkSpace workSpace, List<String> arguments) {
        Path absolute = workSpace.getAbsolutePath(arguments.get(0));
        Log.log("Cleaning directory "+absolute);
        FileUtil.clear(workSpace.getAbsolutePath(arguments.get(0)));
    }

}
