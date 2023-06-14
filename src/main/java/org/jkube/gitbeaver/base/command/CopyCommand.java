package org.jkube.gitbeaver.base.command;

import org.jkube.gitbeaver.AbstractCommand;
import org.jkube.gitbeaver.WorkSpace;
import org.jkube.gitbeaver.util.FileUtil;

import java.nio.file.Path;
import java.util.Map;

public class CopyCommand extends AbstractCommand {

    private static final String SOURCE = "source";
    private static final String TARGET = "target";

    public CopyCommand() {
        super( """
            Copy a file or directory to another place. It is not allowed that source is a file and target
            is an (already existing) directory. If target is an existing directory and the source is a
            file, this file will be copied into a new file in tha tsrget directory with same filename.
            If source is a directory and target is an already existing drectory, the contents of source are copied
            to the target. If the target directory does not exist, yet, it will be created (including ancestors
            if needed).
            """);
        commandline("COPY "+SOURCE+" TO "+TARGET);
        argument(SOURCE, "Path of file or directory (relative to workspace) from which text lines are taken");
        argument(TARGET, "Path of file (relative to workspace) to which text lines are appended");
    }

    @Override
    public void execute(Map<String, String> variables, WorkSpace workSpace, Map<String, String> arguments) {
        Path source = workSpace.getAbsolutePath(arguments.get(SOURCE));
        Path target = workSpace.getAbsolutePath(arguments.get(TARGET));
        if (target.toFile().exists() && target.toFile().isDirectory() && !source.toFile().isDirectory()) {
            target = target.resolve(source.getFileName());
        }
        FileUtil.deleteFileIfExists(target.toFile());
        FileUtil.copyTree(source, target);
    }

}
