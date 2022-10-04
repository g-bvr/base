package gitbeaver.dataprovider;

import org.jkube.gitbeaver.DataProvider;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.jkube.logging.Log.onException;

public class FileReader implements DataProvider {
    @Override
    public boolean provideFile(String resourceString, Path target) {
        Path source = Path.of(resourceString);
        if (!source.toFile().exists()) {
            return false;
        }
        onException(() -> Files.copy(source, target)).fail("Could not copy "+source+" to "+target);
        return true;
    }
}
