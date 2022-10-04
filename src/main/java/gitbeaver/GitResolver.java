package gitbeaver;

import org.jkube.application.Application;
import org.jkube.gitbeaver.util.FileUtil;
import org.jkube.logging.Log;

import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;

public class GitResolver {

    private static final String RUN = "run";
    private static final String WORKDIR = "workdir";
    private final Map<String, String> variables;
    private final WorkSpace workSpace;
    private final ScriptExecutor executor;

    public GitResolver(String... args) {
        variables = parseArgs(args);
        workSpace = new WorkSpace(variables.get(WORKDIR));
        if (variables.containsKey(WORKDIR)) {
            FileUtil.clear(Path.of(WORKDIR));
        }
        executor = new ScriptExecutor(workSpace);
    }

    public WorkSpace getWorkSpace() {
        return workSpace;
    }

    public ScriptExecutor getExecutor() {
        return executor;
    }

    public void run() {
        Log.log("Initial variables: "+variables);
        String script = variables.get(RUN);
        if (script == null) {
            Application.fail("Variable "+RUN+" must be set");
        }
        executor.execute(script, null, variables);
    }

    private Map<String, String> parseArgs(String[] args) {
        Map<String, String> res = new LinkedHashMap<>();
        for (String arg : args) {
            String[] split = arg.split("=");
            if (split.length != 2) {
                Application.fail("argument is not of form key=value: "+arg);
            }
            res.put(split[0].trim(), split[1].trim());
        }
        return res;
    }
}
