package org.jkube.gitbeaver.base;

import org.jkube.gitbeaver.command.LogCommand;
import org.jkube.gitbeaver.interfaces.Command;
import org.jkube.gitbeaver.interfaces.Plugin;

import java.util.List;

public class BasePlugin implements Plugin {
    @Override
    public void init() {
    }

    @Override
    public List<Command> getCommands() {
        return List.of(
                new AppendCommand(),
                new CopyCommand(),
                new ForCommand(),
                new DebugCommand(),
                new SettingsCommand()
        );
    }

    @Override
    public void shutdown() {
    }
}
