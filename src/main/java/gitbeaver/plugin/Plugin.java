package gitbeaver.plugin;

import org.jkube.gitbeaver.Command;
import org.jkube.gitbeaver.DataProvider;

import java.util.List;
import java.util.Optional;

public interface Plugin {

    void init();

    Optional<FileResolver> getFileResolver();
    List<LogListener> getLogListeners();

    List<Command> getCommands();

    List<DataProvider> getDataProviders();

    void shutdown();
}
