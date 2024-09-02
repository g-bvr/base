package org.jkube.gitbeaver.base.command;

import org.jkube.gitbeaver.AbstractCommand;
import org.jkube.gitbeaver.WorkSpace;
import org.jkube.gitbeaver.util.FileUtil;
import org.jkube.gitbeaver.logging.Log;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class CheckFileExistsCommand extends AbstractCommand {

    private static final String FILE = "file";
    private static final String VARIABLE = "variable";

    public CheckFileExistsCommand() {
        super("Check if file exists and write result into a variable");
        commandline("CHECK FILE "+FILE+" EXISTS INTO VARIABLE "+VARIABLE);
        argument(FILE, "path to the file in current workspace");
        argument(VARIABLE, "name of the variable");
    }

    @Override
    public void execute(Map<String, String> variables, WorkSpace workSpace, Map<String, String> arguments) {
        boolean exists = workSpace.getAbsolutePath(arguments.get(FILE)).toFile().exists();
        variables.put(arguments.get(VARIABLE), String.valueOf(exists));
    }

}
