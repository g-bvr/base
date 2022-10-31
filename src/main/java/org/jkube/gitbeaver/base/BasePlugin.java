package org.jkube.gitbeaver.base;

import org.jkube.gitbeaver.base.command.*;
import org.jkube.gitbeaver.plugin.SimplePlugin;

public class BasePlugin extends SimplePlugin {

    public BasePlugin() {
        super(
                AppendCommand.class,
                CopyCommand.class,
                DebugCommand.class,
                ExecuteCommand.class,
                ForCommand.class,
                IfCommand.class,
                SettingsCommand.class,
                StepStartCommand.class,
                StepEndCommand.class,
                StepStateCommand.class
        );
    }
}
