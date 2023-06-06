package org.jkube.gitbeaver.base.command;

import org.jkube.gitbeaver.AbstractCommand;
import org.jkube.gitbeaver.WorkSpace;
import org.jkube.gitbeaver.util.FileUtil;
import org.jkube.logging.Log;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class ReadVariablesCommand extends AbstractCommand {

    private static final String FILE = "file";
    public static final String SPLIT_REGEX = "[ :=]+";

    public ReadVariablesCommand() {
        super("Read multiple variable from a file");
        commandline("READ VARIABLES FROM "+FILE);
        argument(FILE, "path to the file in current workspace");
    }

    @Override
    public void execute(Map<String, String> variables, WorkSpace workSpace, Map<String, String> arguments) {
        Path path = workSpace.getAbsolutePath(arguments.get(FILE));
        List<String> lines = FileUtil.readLines(path);
        Log.log("Read {} settings from {}", lines.size(), path);
        lines.forEach(line -> addVariable(line, variables));
    }

    private void addVariable(String line, Map<String, String> variables) {
        String[] split = line.trim().split(SPLIT_REGEX, 2);
        variables.put(split[0], split.length == 2 ? split[1] : "");
    }
}
