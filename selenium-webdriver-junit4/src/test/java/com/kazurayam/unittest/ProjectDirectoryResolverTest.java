package com.kazurayam.unittest;

import io.github.bonigarcia.webdriver.junit4.ch02.helloworld.HelloWorldChromeJUnit4Test;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

public class ProjectDirectoryResolverTest {

    private static final Logger log = LoggerFactory.getLogger(ProjectDirectoryResolverTest.class);

    @Test
    public void test_getCodeSourceAsPath() {
        Path p = ProjectDirectoryResolver.getCodeSourceAsPath(HelloWorldChromeJUnit4Test.class);
        log.info("[testGetCodeSourceAsPath] p = " + p);
        assertThat(p).isNotNull().exists();
    }

    @Test
    public void test_getProjectDirViaClasspath() {
        Path p = ProjectDirectoryResolver.getProjectDirViaClasspath(HelloWorldChromeJUnit4Test.class);
        assertThat(p).isNotNull().exists();
        assertThat(p.getFileName().toString())
                .isEqualTo("selenium-webdriver-junit4");
    }

}
