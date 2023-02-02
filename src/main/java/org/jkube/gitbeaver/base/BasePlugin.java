package org.jkube.gitbeaver.base;

import org.jkube.gitbeaver.base.command.*;
import org.jkube.gitbeaver.plugin.SimplePlugin;

public class BasePlugin extends SimplePlugin {

    public BasePlugin() {
        super("""
                        Collects various elementary commands (file handling, flow control, sub script execution, loops, etc.)
                        """,
                AppendCommand.class,
                AssembleCommand.class,
                CleanupCommand.class,
                CopyCommand.class,
                DebugCommand.class,
                ExecuteCommand.class,
                ForCommand.class,
                GitPullOrCloneCommand.class,
                GitSimulateCloneCommand.class,
                IfCommand.class,
                DokumentationCommand.class,
                ReturnCommand.class,
                SetCommand.class,
                ReadVariablesCommand.class,
                StepStartCommand.class,
                StepEndCommand.class,
                StepStateCommand.class
        );
    }
}
