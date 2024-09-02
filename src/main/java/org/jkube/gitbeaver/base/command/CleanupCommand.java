package org.jkube.gitbeaver.base.command;

import org.jkube.gitbeaver.AbstractCommand;
import org.jkube.gitbeaver.WorkSpace;
import org.jkube.gitbeaver.util.FileUtil;
import org.jkube.gitbeaver.logging.Log;

import java.nio.file.Path;
import java.util.Map;

public class CleanupCommand extends AbstractCommand {

    private static final String FOLDER = "folder";

    public CleanupCommand() {
        super("Cleanup a folder (remove all files and sub-folders, the folder itself is kept))");
        commandlineVariant("CLEANUP "+ FOLDER, "Cleanup the specified folder");
        commandlineVariant("CLEANUP", "Cleanup the complete workspace");
        argument(FOLDER, "Path of folder (relative to workspace) to be cleaned");
    }

    @Override
    public void execute(Map<String, String> variables, WorkSpace workSpace, Map<String, String> arguments) {
        Path absolute = workSpace.getAbsolutePath(arguments.get(FOLDER));
        Log.log("Cleaning directory "+absolute);
        FileUtil.createIfNotExists(absolute);
        FileUtil.clear(absolute);
    }

}
