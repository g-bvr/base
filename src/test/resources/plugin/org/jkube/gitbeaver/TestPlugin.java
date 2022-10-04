package org.jkube.gitbeaver;

import org.jkube.gitbeaver.log.LogAggregator;
import org.jkube.gitbeaver.plugin.Plugin;
import org.jkube.logging.Log;

import java.util.Collections;
import java.util.List;

public class TestPlugin implements Plugin {
    @Override
    public List<Command> getCommands() {
        return Collections.emptyList();
    }

    @Override
    public void init(LogAggregator logAggregator) {
        Log.log("The test plugin was initialized");
    }

    @Override
    public void shutdown() {
        Log.log("The test plugin was shut down");
    }
}
