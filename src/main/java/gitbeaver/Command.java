package gitbeaver;

import java.util.List;
import java.util.Map;

public interface Command {

    void execute(Map<String, String> variables, ScriptExecutor scriptExecutor, WorkSpace workSpace, List<String> arguments);
}
