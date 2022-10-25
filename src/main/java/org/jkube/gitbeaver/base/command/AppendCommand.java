package org.jkube.gitbeaver.base.command;

import org.jkube.application.Application;
import org.jkube.gitbeaver.AbstractCommand;
import org.jkube.gitbeaver.WorkSpace;
import org.jkube.gitbeaver.util.FileUtil;
import org.jkube.util.Expect;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

public class AppendCommand extends AbstractCommand {

    public AppendCommand() {
        super(1, null, "append");
    }

    @Override
    public void execute(Map<String, String> variables, WorkSpace workSpace, List<String> arguments) {
        String filename = arguments.get(0);
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < arguments.size(); i++) {
            if (i > 1) {
                sb.append(" ");
            }
            sb.append(arguments.get(i));
        }
        File file = workSpace.getAbsolutePath(filename).toFile();
        Expect.isFalse(file.isDirectory()).elseFail("File is directory: "+file);
        FileUtil.append(sb.toString(), file);
    }


}
