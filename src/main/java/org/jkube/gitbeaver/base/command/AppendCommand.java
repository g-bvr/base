package org.jkube.gitbeaver.base.command;

import org.jkube.gitbeaver.AbstractCommand;
import org.jkube.gitbeaver.WorkSpace;
import org.jkube.gitbeaver.util.FileUtil;
import org.jkube.util.Expect;

import java.io.File;
import java.util.Map;

import static org.jkube.gitbeaver.CommandParser.REST;

public class AppendCommand extends AbstractCommand {

    private static final String FILE = "file";

    public AppendCommand() {
        super("Append a text to a file");
        commandlineVariant("APPEND * TO "+FILE, "Append a text line to a file");
        commandlineVariant("APPEND TO "+FILE, "Append an empty line to a file");
        argument(FILE, "Path of file (relative to workspace) to be appended");
        argument(REST, "String to be appended");
    }

    @Override
    public void execute(Map<String, String> variables, WorkSpace workSpace, Map<String, String> arguments) {
        String filename = arguments.get(FILE);
        File file = workSpace.getAbsolutePath(filename).toFile();
        Expect.isFalse(file.isDirectory()).elseFail("File is directory: "+file);
        FileUtil.append(arguments.get(REST), file);
    }

}
