package org.jkube.gitbeaver.base;

import org.jkube.gitbeaver.AbstractCommand;
import org.jkube.gitbeaver.SimpleCommand;
import org.jkube.gitbeaver.WorkSpace;
import org.jkube.gitbeaver.util.FileUtil;
import org.jkube.logging.Log;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import static org.jkube.logging.Log.onException;

/**
 * Usage:
 *    FOR var IN path DO script
 *    FOR var IN path MATCHING pattern DO script
 */
public class SettingsCommand extends AbstractCommand {

    protected SettingsCommand() {
        super(1, 1, "settings");
    }

    @Override
    public void execute(Map<String, String> variables, WorkSpace workSpace, List<String> arguments) {
        Path path = workSpace.getAbsolutePath(arguments.get(0));
        List<String> lines = onException(() -> Files.readAllLines(path)).fail("could not load lines of file " + path);
        Log.log("Read {} settings from {}", lines.size(), path);
        lines.forEach(line -> addVariable(line, variables));
    }

    private void addVariable(String line, Map<String, String> variables) {
        String[] split = line.trim().split(" ", 2);
        variables.put(split[0], split.length == 2 ? split[1] : "");
    }
}
