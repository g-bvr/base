package org.jkube.gitbeaver.base.command;

import org.jkube.gitbeaver.SimpleCommand;
import org.jkube.gitbeaver.WorkSpace;
import org.jkube.gitbeaver.util.FileUtil;
import org.jkube.util.Expect;

import java.io.File;
import java.util.List;
import java.util.Map;

public class AssembleCommand extends SimpleCommand {

    public AssembleCommand() {
        super(2, "assemble");
    }

    @Override
    public void execute(WorkSpace workSpace, List<String> arguments) {
        String sourceFilename = arguments.get(0);
        String targetFilename = arguments.get(1);
        File source = workSpace.getAbsolutePath(sourceFilename).toFile();
        File target = workSpace.getAbsolutePath(targetFilename).toFile();
        Expect.isFalse(source.isDirectory()).elseFail("Source file is directory: "+source);
        Expect.isFalse(target.isDirectory()).elseFail("Target file is directory: "+target);
        Expect.isTrue(source.exists()).elseFail("Source file does not exist: "+source);
        FileUtil.append(FileUtil.readLines(source.toPath()), target);
    }


}
