package gitbeaver;

import java.nio.file.Path;

public interface DataProvider {
    boolean provideFile(String resourceString, Path target);
}
