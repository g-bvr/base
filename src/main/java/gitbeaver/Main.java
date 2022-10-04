package gitbeaver;

import org.jkube.application.Application;
import org.jkube.logging.Log;

public class Main {
    public static void main(String[] args) {
        Application.setFailureHandler((message, code) -> {
            Log.error("Critical failure: {}, terminating VM with exit code {}", message, code);
            System.exit(code);
        });
        Log.log("Running gitresolver v0.0");
        new GitResolver(args).run();
        Log.log("Done.");
    }
}