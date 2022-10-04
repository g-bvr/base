package gitbeaver;

import org.jkube.application.Application;
import org.jkube.gitbeaver.command.ForCommand;
import org.jkube.gitbeaver.command.LogCommand;
import org.jkube.gitbeaver.command.ResolveCommand;
import org.jkube.util.Expect;

public class CommandParser {
    private static final String COMMENT_PREFIX = "#";

    public Command parseCommand(String line) {
        String trimmed = line.trim();
        if (trimmed.isEmpty() || trimmed.startsWith(COMMENT_PREFIX)) {
            return null;
        }
        String[] split = trimmed.split(" ",2);
        Expect.equal(split.length,2).elseFail("Could not parse command: "+trimmed);
        return createCommand(split[0], split[1]);
    }

    private Command createCommand(String name, String args) {
        return switch(name.toUpperCase()) {
            case "LOG" -> new LogCommand(args);
            case "RESOLVE" -> new ResolveCommand(args);
            case "FOR" -> new ForCommand(args);
            default -> Application.fail("Unknown command: "+name);
        };
    }

}
