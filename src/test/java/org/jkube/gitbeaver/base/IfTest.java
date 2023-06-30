package org.jkube.gitbeaver.base;


import org.jkube.gitbeaver.util.test.GitBeaverAbstractTest;
import org.jkube.gitbeaver.util.test.TestUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

public class IfTest extends GitBeaverAbstractTest {

    private static final String TEST_PATH_PREFIX = "src/test/resources/if_test_";

    @BeforeAll
    static void beforeAllTests() { TestUtil.beforeTests(); }

    @BeforeEach
    void beforeEachTest() { TestUtil.beforeEachTest(); }

    @Test
    void ifTest1() { runTest(Path.of(TEST_PATH_PREFIX+1)); }

    @Test
    void ifTest2() { runTest(Path.of(TEST_PATH_PREFIX+2)); }

    @Test
    void ifTest3() { runTest(Path.of(TEST_PATH_PREFIX+3)); }

    @Test
    void ifTest4() { runTest(Path.of(TEST_PATH_PREFIX+4)); }

    @Test
    void ifTest5() { runTest(Path.of(TEST_PATH_PREFIX+5)); }

    @Test
    void ifTest6() { runTest(Path.of(TEST_PATH_PREFIX+6)); }

    @Test
    void ifTest7() { runTest(Path.of(TEST_PATH_PREFIX+7)); }

    @Test
    void ifTest8() { runTest(Path.of(TEST_PATH_PREFIX+8)); }

    @Test
    void ifTest9() { runTest(Path.of(TEST_PATH_PREFIX+9)); }

    @Test
    void ifTest10() { runTest(Path.of(TEST_PATH_PREFIX+10)); }

    @Test
    void ifTest11() { runTest(Path.of(TEST_PATH_PREFIX+11)); }

    @Test
    void ifTest12() { runTest(Path.of(TEST_PATH_PREFIX+12)); }

    @Test
    void ifTest13() { runTest(Path.of(TEST_PATH_PREFIX+13)); }

    @Test
    void ifTest14() { runTest(Path.of(TEST_PATH_PREFIX+14)); }

    @AfterEach
    void afterEachTest() { TestUtil.assertNoFailures(); }


}
