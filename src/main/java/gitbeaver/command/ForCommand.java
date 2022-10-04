package gitbeaver.command;

import org.jkube.gitbeaver.Command;
import org.jkube.gitbeaver.ScriptExecutor;
import org.jkube.gitbeaver.WorkSpace;
import org.jkube.util.Expect;

import java.io.File;
import java.nio.file.Path;
import java.util.Map;

public class ForCommand implements Command {

    private static final String FILE = "FILE";
    private static final String FOLDER = "FOLDER";

    private static final String IN = "IN";
    private final boolean forFiles;
    private final String iterationVariable;
    private final String folder;
    private final String subscript;

    public ForCommand(String args) {
        String[] split = args.trim().split(" ");
        Expect.equal(split.length, 5).elseFail("FOR must have exactly 5 arguments");
        this.forFiles = split[0].equalsIgnoreCase(FILE);
        Expect.isTrue(this.forFiles || split[0].equalsIgnoreCase(FOLDER))
                .elseFail("first argument of FOR must be "+FILE+" or "+FOLDER);
        this.iterationVariable = split[1];
        Expect.equal(IN, split[2]).elseFail("third argument of FOR must be "+IN);
        this.folder = split[3];
        this.subscript = split[4];
    }

    @Override
    public void execute(Map<String, String> variables, ScriptExecutor scriptExecutor, WorkSpace workSpace) {
        Path subscriptPath = workSpace.ensurePresent(subscript);
        File dir = workSpace.getAbsolutePath(folder).toFile();
        Expect.isTrue(dir.exists()).elseFail("No such folder: "+folder);
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (dir.isDirectory() != forFiles) {
                    String filename = file.getName();
                    variables.put(iterationVariable, folder + "/" + filename);
                    new ScriptExecutor(workSpace).execute(subscript, filename, variables);
                }
            }
        }
        variables.remove(iterationVariable);
    }

}
