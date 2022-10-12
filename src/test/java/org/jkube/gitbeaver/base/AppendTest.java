package org.jkube.gitbeaver.base;


import org.jkube.gitbeaver.util.test.GitBeaverAbstractTest;
import org.jkube.gitbeaver.util.test.TestUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

public class AppendTest extends GitBeaverAbstractTest {

    private static final String TEST_PATH_PREFIX = "src/test/resources/append_test_";

    @BeforeAll
    static void beforeAllTests() { TestUtil.beforeTests(); }

    @BeforeEach
    void beforeEachTest() { TestUtil.beforeEachTest(); }

    @Test
    void appendTest1() {
        runTest(Path.of(TEST_PATH_PREFIX+1));
    }

    @Test
    void appendTest2() {
        runTest(Path.of(TEST_PATH_PREFIX+2));
    }

    @AfterEach
    void afterEachTest() { TestUtil.assertNoFailures(); }


}
