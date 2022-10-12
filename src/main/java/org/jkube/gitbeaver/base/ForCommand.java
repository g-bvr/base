package org.jkube.gitbeaver.base;

import org.jkube.gitbeaver.AbstractCommand;
import org.jkube.gitbeaver.GitBeaver;
import org.jkube.gitbeaver.WorkSpace;
import org.jkube.util.Expect;

import java.io.File;
import java.nio.file.Files;
import java.util.*;
import java.util.regex.Pattern;

import static org.jkube.logging.Log.onException;

/**
 * Usage:
 *    FOR var IN path DO script
 *    FOR var IN path MATCHING pattern DO script
 */
public class ForCommand extends AbstractCommand {

    protected ForCommand() {
        super(5, 7, "for");
    }

    @Override
    public void execute(Map<String, String> variables, WorkSpace workSpace, List<String> arguments) {
        String variable = arguments.get(0);
        expectArg(1, "IN", arguments);
        String filename = arguments.get(2);
        int num = arguments.size();
        String pattern = "*";
        if (num == 7) {
            expectArg(3, "MATCHING", arguments);
            pattern = arguments.get(4);
        }
        expectArg(num-2, "DO", arguments);
        String script = arguments.get(num-1) + GitBeaver.BEAVER_EXTENSION;
        File file = workSpace.getAbsolutePath(filename).toFile();
        Expect.isTrue(file.exists()).elseFail("File does not exist: "+file);
        List<String> items;
        if (file.isDirectory()) {
            items = new ArrayList<>();
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    items.add(workSpace.getRelativePath(f.toPath()).toString());
                }
            }
            Collections.sort(items);
        } else {
            items = onException(() -> Files.readAllLines(file.toPath())).fail("could not load lines of file "+file);
        }
        Pattern regex = createRegex(pattern);
        for (String item : items) {
            if (regex.matcher(item).matches()) {
                variables.put(variable, item);
                GitBeaver.scriptExecutor().execute(script, item, variables, workSpace);
            }
        }
        variables.remove(variable);
    }

    private Pattern createRegex(String pattern) {
        return Pattern.compile(pattern
                .replaceAll("\\*", ".*")
                .replaceAll("#", "[0-9]+"));
    }

}
