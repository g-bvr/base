package org.jkube.gitbeaver.base.command;

import org.jkube.gitbeaver.AbstractCommand;
import org.jkube.gitbeaver.GitBeaver;
import org.jkube.gitbeaver.WorkSpace;
import org.jkube.gitbeaver.util.FileUtil;
import org.jkube.logging.Log;
import org.jkube.util.Expect;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Usage:
 *    FOR var IN path DO script
 *    FOR var IN path MATCHING pattern DO script
 */
public class ForCommand extends AbstractCommand {

    public ForCommand() {
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
        Pattern regex = createRegex(pattern);
        expectArg(num-2, "DO", arguments);
        String script = arguments.get(num-1);
        File file = workSpace.getAbsolutePath(filename).toFile();
        Expect.isTrue(file.exists()).elseFail("File does not exist: "+file);
        List<String> items;
        if (file.isDirectory()) {
            items = new ArrayList<>();
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    if (regex.matcher(f.toPath().getFileName().toString()).matches()) {
                        Log.log("Using file {}", f);
                        items.add(workSpace.getRelativePath(f.toPath()).toString());
                    } else {
                        Log.log("Skipping file {}", f);
                    }
                }
            }
            Collections.sort(items);
        } else {
            items = FileUtil.readLines(file.toPath())
                    .stream()
                    .filter(item -> regex.matcher(item).matches())
                    .collect(Collectors.toList());
        }
        String prevValue = variables.get(variable);
        for (String item : items) {
            variables.put(variable, item);
            GitBeaver.scriptExecutor().execute(script, item, variables, workSpace);
        }
        if (prevValue == null) {
            variables.remove(variable);
        } else {
            variables.put(variable, prevValue);
        }
    }

    private Pattern createRegex(String pattern) {
        return Pattern.compile(pattern
                .replaceAll("\\*", ".*")
                .replaceAll("#", "[0-9]+"));
    }

}
