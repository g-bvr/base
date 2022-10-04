package gitbeaver.plugin;

import org.jkube.gitbeaver.Command;
import org.jkube.gitbeaver.DataProvider;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class PluginManager {

    private final List<Plugin> plugins = new ArrayList<>();
    private final List<Command> commands = new ArrayList<>();
    private final List<LogListener> logListeners = new ArrayList<>();
    private final List<DataProvider> dataProviders = new ArrayList<>();

    private FileResolver fileResolver;

    public PluginManager(Command pluginCommand, DataProvider gitProvider, FileResolver defaultFileResolver) {
        commands.add(pluginCommand);
        dataProviders.add(gitProvider);
        fileResolver = defaultFileResolver;
    }

    public void addPlugin(Path sourcePath, Path targetPath, String pluginClass) {
        Plugin plugin = new PluginCompiler(sourcePath, targetPath, pluginClass).compileAndInstantiate();
        plugins.add(plugin);
        plugin.init();
        commands.addAll(plugin.getCommands());
        logListeners.addAll(plugin.getLogListeners());
        dataProviders.addAll(plugin.getDataProviders());
        fileResolver = plugin.getFileResolver().orElse(fileResolver);
    }

    public void shutdown() {
        plugins.forEach(Plugin::shutdown);
    }

    public List<Plugin> getPlugins() {
        return plugins;
    }

    public List<Command> getCommands() {
        return commands;
    }

    public List<LogListener> getLogListeners() {
        return logListeners;
    }

    public List<DataProvider> getDataProviders() {
        return dataProviders;
    }

    public FileResolver getFileResolver() {
        return fileResolver;
    }
}
