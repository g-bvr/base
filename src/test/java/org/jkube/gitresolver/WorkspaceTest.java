package org.jkube.gitresolver;

import org.jkube.gitbeaver.WorkSpace;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WorkspaceTest {
    @Test
    void testPath() {
        WorkSpace w = new WorkSpace(null);
        assertEquals("git/test.com/workspace/repo/main-1.2.3/test", w.getRelativePathInWorkDir("git:test.com/workspace/repo:main-1.2.3/test"));
        assertEquals("git/user/workspace/repo", w.getRelativePathInWorkDir("git:user@workspace/repo"));
        assertEquals("http/test.com/test", w.getRelativePathInWorkDir("http://test.com/test"));
        assertEquals("http/test.com/test/a=1/b=2", w.getRelativePathInWorkDir("http://test.com/test?a=1&b=2"));
    }
}
