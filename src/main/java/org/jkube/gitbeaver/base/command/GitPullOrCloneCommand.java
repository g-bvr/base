package org.jkube.gitbeaver.base.command;

import org.jkube.gitbeaver.AbstractCommand;
import org.jkube.gitbeaver.GitBeaver;
import org.jkube.gitbeaver.WorkSpace;
import org.jkube.gitbeaver.applicationlog.CombinedApplicationLoggers;
import org.jkube.gitbeaver.applicationlog.DefaultLogConsole;
import org.jkube.gitbeaver.interfaces.ApplicationLogger;
import org.jkube.gitbeaver.util.ExternalProcess;

import java.net.URL;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import static org.jkube.logging.Log.onException;

/**
 * Usage: git clone or pull providerUrl repositoryName [tag]
 */
public class GitPullOrCloneCommand extends AbstractCommand {

    public GitPullOrCloneCommand() {
        super(2, 3, "git", "pull", "or", "clone");
    }

    @Override
    public void execute(Map<String, String> variables, WorkSpace workSpace, List<String> arguments) {
        URL url = onException(() -> new URL(arguments.get(0))).fail("Could not parse url "+arguments.get(0));
        String repository = arguments.get(1);
        String tag = arguments.size() == 3 ? arguments.get(2) : null;
        CombinedApplicationLoggers applicationLogger = GitBeaver.getApplicationLogger(variables);
        if (workSpace.getAbsolutePath(repository).toFile().exists()) {
            gitPull(workSpace.getAbsolutePath(repository), applicationLogger);
        } else {
            GitBeaver.gitCloner().clone(workSpace.getWorkdir(), url, repository, tag, applicationLogger);
        }
    }

    public void gitPull(Path workdir, ApplicationLogger logger) {
        new ExternalProcess()
                .command("git", "pull")
                .dir(workdir)
                .successMarker("Updated ")
                .logConsole(logger.createSubConsole())
                .logConsole(new DefaultLogConsole())
                .execute();
    }


}
