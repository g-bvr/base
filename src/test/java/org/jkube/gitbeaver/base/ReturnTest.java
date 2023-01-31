package org.jkube.gitbeaver.base;


import org.jkube.gitbeaver.util.test.GitBeaverAbstractTest;
import org.jkube.gitbeaver.util.test.TestUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

public class ReturnTest extends GitBeaverAbstractTest {

    private static final String TEST_PATH = "src/test/resources/return_test";

    @BeforeAll
    static void beforeAllTests() { TestUtil.beforeTests(); }

    @BeforeEach
    void beforeEachTest() { TestUtil.beforeEachTest(); }

    @Test
    void returnTest() {
        runTest(Path.of(TEST_PATH));
    }

    @AfterEach
    void afterEachTest() { TestUtil.assertNoFailures(); }


}
