package gitbeaver.command;

import org.jkube.gitbeaver.Command;
import org.jkube.gitbeaver.ScriptExecutor;
import org.jkube.gitbeaver.WorkSpace;
import org.jkube.logging.Log;

import java.util.Map;

public class LogCommand implements Command {

    private final String text;
    public LogCommand(String text) {
        this.text = text;
    }

    @Override
    public void execute(Map<String, String> variables, ScriptExecutor scriptExecutor, WorkSpace workSpace) {
        Log.log(text);
    }
}
