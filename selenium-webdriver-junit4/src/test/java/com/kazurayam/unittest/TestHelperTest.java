package com.kazurayam.unittest;

import io.github.bonigarcia.webdriver.junit4.ch02.helloworld.HelloWorldChromeJUnit4Test;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

public class TestHelperTest {

    private static final Logger log = LoggerFactory.getLogger(TestHelperTest.class);

    @Test
    public void test_resolveOutput() {
        Path p = new TestHelper(HelloWorldChromeJUnit4Test.class)
                .resolveOutput("bar.json");
        assertThat(p.getParent()).exists();
        assertThat(p.getParent().getFileName().toString())
                .isEqualTo("test-output");
        assertThat(p.toAbsolutePath()
                .getParent()   // expect the test-output directory
                .getParent()   // expect the selenium-webdriver-junit4 directory
                .getFileName().toString())
                .isEqualTo("selenium-webdriver-junit4");
    }
}
