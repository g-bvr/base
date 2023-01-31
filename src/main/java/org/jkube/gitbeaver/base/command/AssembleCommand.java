package org.jkube.gitbeaver.base.command;

import org.jkube.gitbeaver.AbstractCommand;
import org.jkube.gitbeaver.WorkSpace;
import org.jkube.gitbeaver.util.FileUtil;
import org.jkube.util.Expect;

import java.io.File;
import java.util.Map;

public class AssembleCommand extends AbstractCommand {

    private static final String SOURCE = "source";
    private static final String TARGET = "target";

    public AssembleCommand() {
        super( "Append the contant of a file to another file");
        commandline("ASSEMBLE "+SOURCE+" TO "+TARGET);
        argument(SOURCE, "Path of file (relative to workspace) from which text lines are taken");
        argument(TARGET, "Path of file (relative to workspace) to which text lines are appended");
    }

    @Override
    public void execute(Map<String, String> variables, WorkSpace workSpace, Map<String, String> arguments) {
        File source = workSpace.getAbsolutePath(arguments.get(SOURCE)).toFile();
        File target = workSpace.getAbsolutePath(arguments.get(TARGET)).toFile();
        Expect.isFalse(source.isDirectory()).elseFail("Source file is directory: "+source);
        Expect.isFalse(target.isDirectory()).elseFail("Target file is directory: "+target);
        Expect.isTrue(source.exists()).elseFail("Source file does not exist: "+source);
        FileUtil.append(FileUtil.readLines(source.toPath()), target);
    }


}
