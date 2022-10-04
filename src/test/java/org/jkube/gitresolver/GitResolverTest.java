package org.jkube.gitresolver;

import org.jkube.gitbeaver.GitResolver;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GitResolverTest {

    public static final String RUN = "run=file:src/test/resources/gitresolver/";
    public static final String WORKDIR = "workdir=workdir";
    @Test
    public void helloWorldTest() {
        GitResolver gr = new GitResolver(RUN+"helloworld", WORKDIR);
        gr.run();
    }

    @Test
    public void resolveTest() {
        GitResolver gr = new GitResolver(RUN+"resolve", WORKDIR);
        gr.run();
    }

    @Test
    public void scanTreeTest() {
        GitResolver gr = new GitResolver(RUN+"scantree", WORKDIR);
        gr.run();
    }

}
