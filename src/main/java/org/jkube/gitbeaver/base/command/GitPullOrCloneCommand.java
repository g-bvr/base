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
import java.util.Map;

import static org.jkube.logging.Log.onException;

public class GitPullOrCloneCommand extends AbstractCommand {

    private static final String BASE_URL = "baseurl";
    private static final String REPOSITORY = "repository";
    private static final String TAG = "tag";

    public GitPullOrCloneCommand() {
        super("Pull a changes of a git repository in the current workspace, if it was not cloned yet, clone it from specified remote location");
        commandlineVariant("GIT PULL OR CLONE "+BASE_URL+" "+REPOSITORY, "pull or clone from default branch");
        commandlineVariant("GIT PULL OR CLONE "+BASE_URL+" "+REPOSITORY+" "+TAG, "pull already checked out branch or clone and check out specified branch or tag");
        argument(BASE_URL, "The url prefix of the repository (not including the actual repo name)");
        argument(REPOSITORY, "The name of the repository (which together with the base url constitutes the url of the repository)");
        argument(TAG, "Optional argument to specify either a branch or a tag which shall be checked out (if omitted the default branch will be used)");
    }

    @Override
    public void execute(Map<String, String> variables, WorkSpace workSpace, Map<String, String> arguments) {
        String urlString = arguments.get(BASE_URL);
        String repository = arguments.get(REPOSITORY);
        String tag = arguments.get(TAG);
        URL url = onException(() -> new URL(urlString)).fail("Could not parse url "+urlString);
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
