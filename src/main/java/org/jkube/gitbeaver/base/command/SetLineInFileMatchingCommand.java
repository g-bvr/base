package org.jkube.gitbeaver.base.command;

import org.jkube.gitbeaver.AbstractCommand;
import org.jkube.gitbeaver.WorkSpace;
import org.jkube.gitbeaver.util.FileUtil;
import org.jkube.logging.Log;
import org.jkube.util.Expect;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SetLineInFileMatchingCommand extends AbstractCommand {

    private static final String REGEX = "regex";
    private static final String FILE = "file";
    private static final String VARIABLE = "variable";

    public SetLineInFileMatchingCommand() {
        super("Set a variable to a value according to a line matching a regex with a group");
        commandline("SET "+VARIABLE+" FROM LINE IN " +FILE+" MATCHING "+REGEX);
        argument(VARIABLE, "name of the variable to be set");
        argument(REGEX, "regular expression with a group (in round brackets)");
        argument(FILE, "text file in which regex is searched");
    }

    @Override
    public void execute(Map<String, String> variables, WorkSpace workSpace, Map<String, String> arguments) {
        String found = null;
        Pattern pattern = Pattern.compile(arguments.get(REGEX));
        for (String line : FileUtil.readLines(workSpace.getAbsolutePath(arguments.get(FILE)))) {
            Matcher matcher = pattern.matcher(line);
            if (matcher.matches()) {
                Expect.equal(matcher.groupCount(), 1).elseFail("exactly one group expected, found "+matcher.groupCount());
                String extracted = matcher.group(1);
                if ((found != null) && !extracted.matches(found)) {
                    Log.warn("There are multiple matches with different content: {} vs. {}", extracted, found);
                }
                found = extracted;
            }
        }
        if (found != null) {
            variables.put(arguments.get(VARIABLE), found);
        }
    }

}
