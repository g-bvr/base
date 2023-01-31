package org.jkube.gitbeaver.base.command;

import org.jkube.gitbeaver.AbstractCommand;
import org.jkube.gitbeaver.GitBeaver;
import org.jkube.gitbeaver.SimpleCommand;
import org.jkube.gitbeaver.WorkSpace;

import java.util.List;
import java.util.Map;

public class GitSimulateCloneCommand extends SimpleCommand {

    private static final String FOLDER = "folder";

    public GitSimulateCloneCommand() {
        super("GIT SIMULATE CLONE", "specifies a folder in current workspace from which git operations are simulated");
        argument(FOLDER, "folder from which clone operations are simulated (all simulated git repositories must be subfolders of this)");
    }

    @Override
    public void execute(WorkSpace workSpace, Map<String, String> arguments) {
        GitBeaver.gitCloner().doSimulatedCloning(arguments.get(FOLDER));
    }
}
