package org.jkube.gitbeaver.base.command;

import org.jkube.gitbeaver.AbstractCommand;
import org.jkube.gitbeaver.GitBeaver;
import org.jkube.gitbeaver.WorkSpace;
import org.jkube.gitbeaver.interfaces.Command;
import org.jkube.gitbeaver.util.FileUtil;
import org.jkube.logging.Log;
import org.jkube.util.Expect;

import java.io.File;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.jkube.gitbeaver.CommandParser.REST;

public class ForCommand extends AbstractCommand {

    public enum ForType {
        FILE, FOLDER, LINE
    }

    private static final String FOLDERVAR = "foldervar";
    private static final String FILEVAR = "filevar";
    private static final String LINEVAR = "linevar";
    private static final String FILE = "file";
    private static final String FOLDER = "folder";
    private static final String PATTERN = "pattern";
    private static final String SCRIPT = "script";

    public ForCommand() {
        super("Execute a command for multiple items");
        commandlineVariant("FOR FILE "+FILEVAR+" IN "+FOLDER+" DO "+SCRIPT, "Execute the command for all files in specified folder");
        commandlineVariant("FOR SUBFOLDER "+FOLDERVAR+" IN "+FOLDER+" DO "+SCRIPT, "Execute the command for all subfolders of specified folder");
        commandlineVariant("FOR LINE "+LINEVAR+" IN "+FILE+" DO "+SCRIPT, "Execute the command for all lines of specified text file");
        commandlineVariant("FOR FILE "+FILEVAR+" IN "+FOLDER+" MATCHING "+PATTERN+" DO "+SCRIPT, "Execute the command for all files in specified folder matching specified pattern");
        commandlineVariant("FOR SUBFOLDER "+FOLDERVAR+" IN "+FOLDER+" MATCHING "+PATTERN+" DO "+SCRIPT, "Execute the command for all subfolders of specified folder matching specified pattern");
        commandlineVariant("FOR LINE "+LINEVAR+" IN "+FILE+" MATCHING "+PATTERN+" DO "+SCRIPT, "Execute the command for all lines of specified text file matching specified pattern");
        argument(FOLDERVAR, "the variable into the folder item (its path relative to the workspace) name shall be written");
        argument(FILEVAR, "the variable into the file item (its path relative to the workspace) shall be written");
        argument(LINEVAR, "the variable into the line item shall be written");
        argument(FILE, "the file from which lines shall be read");
        argument(FOLDER, "the folder in which files/subfolders shall be listed");
        argument(PATTERN, "a regex expression by which the found items are filtered, the wildcard character # is resolved to [0-9]+ i.e. matches numbers");
        argument(SCRIPT, "the script to be executed for each valid item");
    }

    @Override
    public void execute(Map<String, String> variables, WorkSpace workSpace, Map<String, String> arguments) {
        String variable;
        String filename;
        ForType type;
        if (arguments.containsKey(LINEVAR)) {
            variable = arguments.get(LINEVAR);
            filename = arguments.get(FILE);
            type = ForType.LINE;
        } else if (arguments.containsKey(FOLDERVAR)) {
            variable = arguments.get(FOLDERVAR);
            filename = arguments.get(FOLDER);
            type = ForType.FOLDER;
        } else {
            variable = arguments.get(FILEVAR);
            filename = arguments.get(FOLDER);
            type = ForType.FILE;
        }
        String pattern = arguments.get(PATTERN);
        if (pattern == null) {
            pattern = ".*";
        }
        Pattern regex = createRegex(pattern);
        File file = workSpace.getAbsolutePath(filename).toFile();
        Expect.isTrue(file.exists()).elseFail("File does not exist: "+file);
        List<String> items = switch (type) {
            case LINE -> readLines(file, regex);
            case FILE -> readItemsInFolder(file, regex, false, workSpace);
            case FOLDER -> readItemsInFolder(file, regex, true, workSpace);
        };
        Map<String, String> parsedArgs = new HashMap<>();
        String script = arguments.get(SCRIPT);
        Expect.notNull(script).elseFail("");
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

    private List<String> readLines(File file, Pattern regex) {
        return FileUtil.readLines(file.toPath())
                .stream()
                .filter(item -> regex.matcher(item).matches())
                .collect(Collectors.toList());
    }

    private List<String> readItemsInFolder(File file, Pattern regex, boolean isDirectory, WorkSpace workSpace) {
        List<String> items = new ArrayList<>();
        File[] files = file.listFiles();
        if (files != null) {
            for (File f : files) {
                String relative = f.toPath().getFileName().toString();
                if ((f.isDirectory() == isDirectory) && regex.matcher(relative).matches()) {
                    Log.log("Using file {}", relative);
                    items.add(workSpace.getRelativePath(f.toPath()).toString());
                } else {
                    Log.log("Skipping file {}", relative);
                }
            }
        }
        Collections.sort(items);
        return items;
    }

    private Pattern createRegex(String pattern) {
        return Pattern.compile(pattern
                .replaceAll("#", "[0-9]+"));
    }

}
