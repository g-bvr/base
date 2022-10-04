package gitbeaver.plugin;

import org.jkube.logging.Log;
import org.jkube.util.Expect;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import static org.jkube.logging.Log.exception;
import static org.jkube.logging.Log.onException;

public class PluginCompiler {

    private static final String JAVA_EXTENSION = ".java";
    private final Path sourcePath;
    private final Path targetPath;
    private final String pluginClass;

    private final JavaCompiler compiler;

    public PluginCompiler(Path sourcePath, Path targetPath, String pluginClass) {
        this.sourcePath = sourcePath.toAbsolutePath();
        this.targetPath = targetPath.toAbsolutePath();
        this.pluginClass = pluginClass;
        this.compiler = ToolProvider.getSystemJavaCompiler();
    }

    public Plugin compileAndInstantiate() {
        //createTarget();
        compile();
        return instantiate();
    }

    private void createTarget() {
        if (!targetPath.toFile().exists()) {
            Log.log("Creating target directory "+targetPath);
            Expect.isTrue(targetPath.toFile().mkdirs())
                    .elseFail("Could not create target directory "+targetPath);
        }
    }

    private void compile() {
        try (Stream<Path> walk = Files.walk(sourcePath)) {
            walk.filter(Files::isRegularFile)
                    .filter(this::isJavaFile)
                    .forEach(this::compile);
        } catch (IOException e) {
            Log.exception(e);
        }
    }

    private boolean isJavaFile(Path path) {
        return path.toString().endsWith(JAVA_EXTENSION);
    }

    private void compile(Path path) {
        Log.log("Compiling "+path);
        compiler.run(null, null, null, "-d", targetPath.toString(), path.toString());
    }


}
