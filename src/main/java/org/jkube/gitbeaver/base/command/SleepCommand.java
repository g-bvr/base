package org.jkube.gitbeaver.base.command;

import org.jkube.gitbeaver.AbstractCommand;
import org.jkube.gitbeaver.SimpleCommand;
import org.jkube.gitbeaver.WorkSpace;
import org.jkube.logging.Log;

import java.util.Map;

import static org.jkube.gitbeaver.CommandParser.REST;

public class SleepCommand extends SimpleCommand {

    private static final String SECONDS = "seconds";

    public SleepCommand() {
        super("SLEEP", "Sleep (delay script execution)");
        argument(SECONDS, "time in seconds to delay further script execution");
    }

    @Override
    public void execute(WorkSpace workSpace, Map<String, String> arguments) {
        Log.interruptable(() -> Thread.sleep(Integer.parseInt(arguments.get(SECONDS))));
    }

}
