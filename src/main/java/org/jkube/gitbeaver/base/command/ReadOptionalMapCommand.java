package org.jkube.gitbeaver.base.command;

import org.jkube.gitbeaver.AbstractCommand;
import org.jkube.gitbeaver.WorkSpace;
import org.jkube.gitbeaver.util.FileUtil;
import org.jkube.gitbeaver.logging.Log;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class ReadOptionalMapCommand extends AbstractCommand {

    private static final String FILE = "file";
    public static final String SPLIT_REGEX = " *[:=] *";

    public ReadOptionalMapCommand() {
        super("Read a key value map from a file into variables");
        commandline("READ OPTIONAL MAP FROM "+FILE);
        argument(FILE, "path to the file in current workspace, do nothing if the file does not exist");
    }

    @Override
    public void execute(Map<String, String> variables, WorkSpace workSpace, Map<String, String> arguments) {
        Path path = workSpace.getAbsolutePath(arguments.get(FILE));
        if (path.toFile().exists()) {
            List<String> lines = FileUtil.readLines(path);
            Log.log("Read {} key-value pairs from {}", lines.size(), path);
            lines.forEach(line -> addVariable(line, variables));
        } else {
            Log.log("Optional map does not exist: {}",  path);
        }
    }

    private void addVariable(String line, Map<String, String> variables) {
        String[] split = line.trim().split(SPLIT_REGEX, 2);
        variables.put(split[0], split.length == 2 ? split[1] : "");
    }
}
