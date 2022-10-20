package org.jkube.gitbeaver.base;

import org.jkube.gitbeaver.AbstractCommand;
import org.jkube.gitbeaver.GitBeaver;
import org.jkube.gitbeaver.SimpleCommand;
import org.jkube.gitbeaver.WorkSpace;
import org.jkube.gitbeaver.util.FileUtil;
import org.jkube.util.Expect;

import java.io.File;
import java.util.List;
import java.util.Map;

public class StepStartCommand extends AbstractCommand {

    public StepStartCommand() {
        super(1, null, "step", "start");
    }

    @Override
    public void execute(Map<String, String> variables, WorkSpace workSpace, List<String> arguments) {
        GitBeaver.getApplicationLogger(variables).createStep(String.join(" ", arguments));
    }
}
