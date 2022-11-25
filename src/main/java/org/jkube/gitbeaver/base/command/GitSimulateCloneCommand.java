package org.jkube.gitbeaver.base.command;

import org.jkube.gitbeaver.AbstractCommand;
import org.jkube.gitbeaver.GitBeaver;
import org.jkube.gitbeaver.SimpleCommand;
import org.jkube.gitbeaver.WorkSpace;

import java.util.List;
import java.util.Map;

public class GitSimulateCloneCommand extends SimpleCommand {

    public GitSimulateCloneCommand() {
        super(1,"git", "simulate", "clone");
    }

    @Override
    public void execute(WorkSpace workSpace, List<String> arguments) {
        GitBeaver.gitCloner().doSimulatedCloning(arguments.get(0));
    }
}
