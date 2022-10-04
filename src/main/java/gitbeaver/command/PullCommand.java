package gitbeaver.command;

import org.jkube.gitbeaver.Command;
import org.jkube.gitbeaver.ScriptExecutor;
import org.jkube.gitbeaver.WorkSpace;
import org.jkube.gitbeaver.util.FileUtil;
import org.jkube.logging.Log;
import org.jkube.util.Expect;

import java.nio.file.Path;
import java.util.Map;

public class PullCommand implements Command {

    private static final String FILE = "file:";
    private static final String GIT = "git:";
    private final boolean simulated;
    private final String source;
    private final String target;

    public PullCommand(String args) {
        String[] split = args.trim().split(" ");
        Expect.equal(split.length, 2).elseFail("PULL must have exactly 2 arguments");
        this.simulated = split[0].startsWith(FILE);
        Expect.isTrue(simulated || split[0].startsWith(GIT)).elseFail("argument of PULL must start with "+GIT+" or "+FILE);
        this.source = split[0].substring((simulated ? FILE : GIT).length());
        this.target = split[1];
    }

    @Override
    public void execute(Map<String, String> variables, ScriptExecutor scriptExecutor, WorkSpace workSpace) {
        Path targetPath = workSpace.getAbsolutePath(target);
        boolean update = targetPath.toFile().exists();
        Log.log((update ? "Pulling" : "Cloning")+" from "+source);
        if (simulated) {
            Path sourcePath = workSpace.getAbsolutePath(source);
            if (update) {
                FileUtil.delete(targetPath);
            }
            FileUtil.copyTree(sourcePath, targetPath);
        }
        Log.log("Git operation done");
    }
}
