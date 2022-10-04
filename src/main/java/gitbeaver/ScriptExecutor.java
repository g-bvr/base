package gitbeaver;

import org.jkube.logging.Log;
import org.jkube.util.Expect;

import java.util.List;
import java.util.Map;

public class ScriptExecutor {

    private final WorkSpace workSpace;
    private final CommandParser commandParser;

    public ScriptExecutor(WorkSpace workSpace) {
        this.workSpace = workSpace;
        this.commandParser = new CommandParser();
    }

    public void execute(String script, String forItem, Map<String, String> variables) {
        Log.log("Executing script "+script+(forItem == null ? "" : " for "+forItem)+" in  workdir "+workSpace.getWorkdir());
        List<String> scriptLines = workSpace.readLines(script, variables);
        Expect.notNull(scriptLines).elseFail("Script file does not exist: "+script);
        scriptLines.forEach(line -> executeLine(line, variables));
    }

    private void executeLine(String line, Map<String, String> variables) {
        Command command = commandParser.parseCommand(line);
        if (command != null) {
            command.execute(variables, this, workSpace);
        }
    }
}
