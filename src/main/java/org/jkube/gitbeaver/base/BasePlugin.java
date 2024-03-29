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
                CatchCommand.class,
                CheckFileExistsCommand.class,
                CleanupCommand.class,
                CopyCommand.class,
                DebugCommand.class,
                EvaluateCommand.class,
                ExecuteCommand.class,
                ExecuteSharingVariablesCommand.class,
                ForCommand.class,
                GitPullOrCloneCommand.class,
                GitSimulateCloneCommand.class,
                HttpRequestCommand.class,
                IfCommand.class,
                DokumentationCommand.class,
                ReturnCommand.class,
                ReadFileCommand.class,
                ReadOptionalMapCommand.class,
                ReadVariablesCommand.class,
                SetSubstitutingCommand.class,
                SetLineInFileMatchingCommand.class,
                SetRemovingCommand.class,
                SetCommand.class,
                SleepCommand.class,
                StepStartCommand.class,
                StepEndCommand.class,
                StepStateCommand.class
        );
    }

}
