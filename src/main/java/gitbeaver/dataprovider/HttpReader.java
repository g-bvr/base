package gitbeaver.dataprovider;

import org.jkube.gitbeaver.DataProvider;

import java.nio.file.Path;

public class HttpReader implements DataProvider {
    @Override
    public boolean provideFile(String resourceString, Path target) {
        return false;
    }
}
