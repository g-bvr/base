package org.jkube.gitbeaver.base;


import org.jkube.gitbeaver.util.test.GitBeaverAbstractTest;
import org.jkube.gitbeaver.util.test.TestUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

public class ForTest extends GitBeaverAbstractTest {

    private static final String TEST_PATH_PREFIX = "src/test/resources/for_test_";

    @BeforeAll
    static void beforeAllTests() { TestUtil.beforeTests(); }

    @BeforeEach
    void beforeEachTest() { TestUtil.beforeEachTest(); }

    @Test
    void forTest1() { runTest(Path.of(TEST_PATH_PREFIX+1)); }

    @Test
    void forTest2() { runTest(Path.of(TEST_PATH_PREFIX+2)); }

    @Test
    void forTest3() { runTest(Path.of(TEST_PATH_PREFIX+3)); }
    @Test
    void forTest4() { runTest(Path.of(TEST_PATH_PREFIX+4)); }

    @Test
    void forTest5() { runTest(Path.of(TEST_PATH_PREFIX+5)); }

    @Test
    void forTest6() { runTest(Path.of(TEST_PATH_PREFIX+6)); }

    @Test
    void forTest7() { runTest(Path.of(TEST_PATH_PREFIX+7)); }

    @Test
    void forTest8() { runTest(Path.of(TEST_PATH_PREFIX+8)); }

    @Test
    void forTest9() { runTest(Path.of(TEST_PATH_PREFIX+9)); }

    @Test
    void forTest10() { runTest(Path.of(TEST_PATH_PREFIX+10)); }

    @AfterEach
    void afterEachTest() { TestUtil.assertNoFailures(); }


}
