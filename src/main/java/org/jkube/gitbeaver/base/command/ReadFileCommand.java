package org.jkube.gitbeaver.base.command;

import org.jkube.gitbeaver.AbstractCommand;
import org.jkube.gitbeaver.WorkSpace;
import org.jkube.gitbeaver.util.FileUtil;
import org.jkube.logging.Log;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class ReadFileCommand extends AbstractCommand {

    private static final String FILE = "file";
    private static final String VARIABLE = "variable";

    public ReadFileCommand() {
        super("Read content of a file into a variable");
        commandline("READ FILE "+FILE+" INTO VARIABLE "+VARIABLE);
        argument(FILE, "path to the file in current workspace");
        argument(VARIABLE, "name of the variable");
    }

    @Override
    public void execute(Map<String, String> variables, WorkSpace workSpace, Map<String, String> arguments) {
        Path path = workSpace.getAbsolutePath(arguments.get(FILE));
        List<String> lines = FileUtil.readLines(path);
        Log.log("Read {} lines from {}", lines.size(), path);
        variables.put(arguments.get(VARIABLE), String.join("\n", lines));
    }

}
