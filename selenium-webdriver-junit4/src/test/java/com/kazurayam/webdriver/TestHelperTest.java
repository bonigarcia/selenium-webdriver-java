package com.kazurayam.webdriver;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

public class TestHelperTest {

    private static Logger log = LoggerFactory.getLogger(TestHelperTest.class);

    @Test
    public void test_resolveOutput() {
        Path p = TestHelper.resolveOutput("bar.json");
        assertThat(p.getParent()).exists();
        assertThat(p.getParent().getFileName().toString())
                .isEqualTo("test-output");
        assertThat(p.toAbsolutePath()
                .getParent()   // expect the test-output directory
                .getParent()   // expect the selenium-webdriver-junit4 directory
                .getFileName().toString())
                .isEqualTo("selenium-webdriver-junit4");
    }

    @Test
    public void test_getProjectDirViaClasspath() {
        Path p = TestHelper.getProjectDirViaClasspath();
        assertThat(p).isNotNull().exists();
        assertThat(p.getFileName().toString())
                .isEqualTo("selenium-webdriver-junit4");
    }

    @Test
    public void test_getCodeSourceAsPath() {
        Path p = TestHelper.getCodeSourceAsPath();
        log.info("[testGetCodeSourceAsPath] p = " + p);
        assertThat(p).isNotNull().exists();
    }

}
