package org.jkube.gitbeaver.base.command;

import org.jkube.gitbeaver.AbstractCommand;
import org.jkube.gitbeaver.GitBeaver;
import org.jkube.gitbeaver.SimpleCommand;
import org.jkube.gitbeaver.WorkSpace;
import org.jkube.gitbeaver.util.FileUtil;
import org.jkube.util.Expect;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static org.jkube.logging.Log.onException;

/**
 * Usage:
 *    FOR var IN path DO script
 *    FOR var IN path MATCHING pattern DO script
 */
public class CopyCommand extends SimpleCommand {

    public CopyCommand() {
        super(3, "copy");
    }

    @Override
    protected void execute(WorkSpace workSpace, List<String> arguments) {
        Path source = workSpace.getAbsolutePath(arguments.get(0));
        expectArg(1, "TO", arguments);
        Path target = workSpace.getAbsolutePath(arguments.get(2));
        if (target.toFile().exists() && target.toFile().isDirectory() && !source.toFile().isDirectory()) {
            target = target.resolve(source.getFileName());
        }
        FileUtil.copyTree(source, target);
    }

}
