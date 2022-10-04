package org.jkube.gitbeaver;

import org.jkube.gitbeaver.plugin.Plugin;
import org.jkube.gitbeaver.plugin.PluginCompiler;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

public class TestCompiler {

    @Test
    public void compilerTest() {
        PluginCompiler compiler = new PluginCompiler(Path.of("src/test/resources/plugin"), "org.jkube.gitbeaver.TestPlugin");
        Plugin plugin = compiler.compileAndInstantiate();
        plugin.init(null);
        plugin.shutdown();
    }
}
