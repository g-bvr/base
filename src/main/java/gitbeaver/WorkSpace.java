package gitbeaver;

import org.jkube.application.Application;
import org.jkube.gitbeaver.dataprovider.FileReader;
import org.jkube.gitbeaver.dataprovider.GitReader;
import org.jkube.gitbeaver.dataprovider.HttpReader;
import org.jkube.gitbeaver.richfile.RichFile;
import org.jkube.util.Expect;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import static org.jkube.logging.Log.log;
import static org.jkube.logging.Log.warn;

public class WorkSpace {

    private static final String DEFAULT_WORKDIR = ".";

    private static final String FILE_PREFIX = "file:";
    private static final String HTTP_PREFIX = "http:";
    private static final String GIT_PREFIX = "git:";
    public static final String RESOLVED_PREFIX = "resolved/";

    private final Path workdir;

    private final FileReader fileReader;
    private final GitReader gitReader;
    private final HttpReader httpReader;


    public WorkSpace(String workdirOrNull) {
        workdir = Path.of(workdirOrNull == null ? DEFAULT_WORKDIR : workdirOrNull).toAbsolutePath().normalize();
        fileReader = new FileReader();
        gitReader = new GitReader();
        httpReader = new HttpReader();
    }

    public Path getWorkdir() {
        return workdir;
    }

    public String getRelativePathInWorkDir(String resource) {
        return resource.replaceAll("[:/?&@]+", "/");
    }

    public Path getAbsolutePath(String resource) {
        return workdir.resolve(getRelativePathInWorkDir(resource));
    }

    public Path ensurePresent(String resource) {
        if (resource.startsWith(FILE_PREFIX)) {
            return ensurePresent(FILE_PREFIX, resource, fileReader);
        }
        if (resource.startsWith(HTTP_PREFIX)) {
            return ensurePresent(HTTP_PREFIX, resource, httpReader);
        }
        if (resource.startsWith(GIT_PREFIX)) {
            return ensurePresent(GIT_PREFIX, resource, gitReader);
        }
        if (resource.startsWith(RESOLVED_PREFIX)) {
            return ensurePresent(RESOLVED_PREFIX, resource, null);
        }
        return Application.fail("Unexpected resource prefix: "+resource);
    }

    private Path ensurePresent(String prefix, String resource, DataProvider provider) {
        String resourcepath = resource.substring(prefix.length());
        Path path = getAbsolutePath(resource);
        if (!path.toFile().exists()) {
            if (provider == null) {
                return null;
            }
            createIfNotExist(path.getParent().toFile());
            log("Trying to provide "+path+" from "+resourcepath);
            if (!provider.provideFile(resourcepath, path)) {
                warn("Resource could not be provided: "+resourcepath);
                return null;
            }
        }
        return path;
    }

    public List<String> readLines(String resource, Map<String, String> variables) {
        Path path = ensurePresent(resource);
        return path == null ? null : new RichFile(workdir, path).resolve(variables);
    }

    public void createIfNotExist(File dir) {
       if (!dir.exists()) {
           log("Creating directory "+dir);
           Expect.isTrue(dir.mkdirs()).elseFail("Could not create directoy "+dir);
       }
    }

}
