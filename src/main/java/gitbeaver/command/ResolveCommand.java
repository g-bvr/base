package gitbeaver.command;

import org.jkube.gitbeaver.Command;
import org.jkube.gitbeaver.ScriptExecutor;
import org.jkube.gitbeaver.WorkSpace;
import org.jkube.gitbeaver.richfile.RichFile;
import org.jkube.util.Expect;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import static org.jkube.logging.Log.log;
import static org.jkube.logging.Log.onException;

public class ResolveCommand implements Command {

    private final String source;
    private final String target;

    public ResolveCommand(String args) {
        String[] split = args.trim().split(" ");
        Expect.equal(split.length, 2).elseFail("RESOLVE must have exactly 2 arguments");
        this.source = split[0];
        this.target = split[1];
    }

    @Override
    public void execute(Map<String, String> variables, ScriptExecutor scriptExecutor, WorkSpace workSpace) {
        Path sourcePath = workSpace.ensurePresent(source);
        Expect.notNull(sourcePath).elseFail("Cannot resolve, file does not exist: "+source);
        resolve(sourcePath, workSpace, workSpace.getAbsolutePath(target), variables);
    }

    private void resolve(Path sourcePath, WorkSpace workSpace, Path targetPath, Map<String, String> variables) {
       log("Resolving "+sourcePath+" to "+targetPath);
       workSpace.createIfNotExist(targetPath.getParent().toFile());
       onException(() -> Files.write(targetPath, new RichFile(workSpace.getWorkdir(), sourcePath).resolve(variables))).fail("Could not write resolved lines to "+targetPath);
    }
}
