package org.jkube.gitbeaver.base;

import org.jkube.gitbeaver.command.LogCommand;
import org.jkube.gitbeaver.interfaces.Command;
import org.jkube.gitbeaver.interfaces.Plugin;
import org.jkube.gitbeaver.plugin.SimplePlugin;

import java.util.List;

public class BasePlugin extends SimplePlugin {

    public BasePlugin() {
        super(
                AppendCommand.class,
                CopyCommand.class,
                ForCommand.class,
                DebugCommand.class,
                SettingsCommand.class
        );
    }
}
