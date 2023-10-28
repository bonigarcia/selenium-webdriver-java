package com.kazurayam.unittest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class TestHelper {

    private static final Logger log = LoggerFactory.getLogger(TestHelper.class);

    public static final String DEFAULT_OUTPUT_DIRNAME = "test-output";

    private Path projectDir;

    private String outputDirName;

    public TestHelper(Class clazz) {
        projectDir = ProjectDirectoryResolver.getProjectDirViaClasspath(clazz);
        outputDirName = DEFAULT_OUTPUT_DIRNAME;
    }

    public void setOutputDirName(String outputDirName) {
        Objects.requireNonNull(outputDirName);
        this.outputDirName = outputDirName;
    }

    private Path getOutputDir() {
        return projectDir.resolve(outputDirName);
    }

    /**
     * returns the Path of a file that a test class write its output into.
     * the Path will be under the "test-output" directory.
     * The "test-output" will be silently created under
     * the "selenium-webdriver-java/selenium-webdriver-junit4" directory if not yet exists.
     *
     * @param fileName e.g. "extentReport.html" -> "selenium-webdriver-java/selenium-webdriver-junit4/test-output/extentReport.html"
     *                 will be returned
     *
     *                 e.g. "foo/bar.txt" ->  "selenium-webdriver-java/selenium-webdriver-junit4/test-output/foo/bar.txt"
     *                 will be returned, the "foo" directory will be silently created
     */
    public Path resolveOutput(String fileName) {
        Path outFile = getOutputDir().resolve(fileName);
        Path parentDir = outFile.getParent();
        if (!Files.exists(parentDir)) {
            try {
                Files.createDirectories(parentDir);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return outFile;
    }
}
