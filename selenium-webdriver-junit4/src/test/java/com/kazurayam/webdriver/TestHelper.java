package com.kazurayam.webdriver;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
public class TestHelper {

    private static Logger log = LoggerFactory.getLogger(TestHelper.class);

    public static final Path testOutputDir =
            getProjectDirViaClasspath().resolve("test-output");

    //private TestHelper() {}

    /**
     * returns the Path of file that a test class write into.
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
    public static Path resolveOutput(String fileName) {
        Path parentDir = testOutputDir.resolve(fileName).getParent();
        if (!Files.exists(parentDir)) {
            try {
                Files.createDirectories(parentDir);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return testOutputDir.resolve(fileName);
    }

    @Test
    public void test_resolveOutput() {
        Path p = TestHelper.resolveOutput("bar.json");
        assertThat(p.getParent()).exists();
        assertThat(p.getParent().getFileName().toString())
                .isEqualTo("test-output");
        assertThat(p.toAbsolutePath()
                .getParent()   // expect the test-output directory
                .getParent()   // expect the selenium-webdriver-junit4 directory
                .getParent()   // expect the selenium-webdriver-java directory
                .getFileName().toString())
                .isEqualTo("selenium-webdriver-java");
    }

    //-----------------------------------------------------------------
    /**
     * returns the project directory in which the class binary of
     * `com.kazurayam.webdriver.TestHelper` is found on disk.
     * The same value will be returned in either case where the class
     * was built by Gradle and Maven.
     *
     * e.g, "/Users/somebody/selenium-webdriver-java/selenium-webdriver-junit4/"
     *
     * The point is, the System property "user.dir" will return
     * "/Users/somebody/selenium-webdriver-java".
     * This does not include the subproject directory
     * "selenium-webdriver-junit4", therefore it is not what we want.
     *
     * When the TestHelper class is built using Gradle, the class will be stored
     * in the directory "selenium-webdriver-java/selenium-webdriver-junit4/build".
     * When the TestHelper class is built using Maven, the class will be stored
     * in the directory "selenium-webdriver-java/selenium-webdriver-junit5/target".
     * So this method look up the file name "build" or "target" in the code source
     * of the TestHelper class.
     * Then we can get the Path value of the project directory properly.
     */
    public static Path getProjectDirViaClasspath() {
        Path codeSourcePath = getCodeSourceAsPath();
        // e.g. "/Users/myname/oreilly/selenium-webdriver-java/selenium-webdriver-junit4/build/classes/java/test/com/kazurayam/webdriver/TestHelper.class"

        Iterator<Path> iter = codeSourcePath.iterator();
        List<Path> nameElements = new ArrayList<>();
        while (iter.hasNext()) {
            Path p = iter.next();
            nameElements.add(p);
        }
        int tail = -1;
        for (int i = nameElements.size() - 1; i >= 0; i--) {
            Path p = nameElements.get(i);
            if (p.getFileName().toString().equals("build") ||  // Gradle
                    p.getFileName().toString().equals("target")    // Maven
            ) {
                tail = i;
            }
        }
        // The nameElements will be, for example
        // ["Users","myname","oreilly","selenium-webdriver-java","selenium-webdriver-junit4","build","classes","java","test","com","kazurayam","webdriver","TestHelper.class"]
        //                                                                                    ^-- tail                                                      ^-- nameElements.size()-1

        // build the project dir to return as the result
        Path w = Paths.get("/");
        for (int i = 0; i < tail; i++) {
            w = w.resolve(nameElements.get(i));
        }
        return w;   // e.g, /Users/myname/oreilly/selenium-webdriver-java/selenium-webdriver-junit
    }

    @Test
    public void test_getProjectDirViaClasspath() {
        Path p = getProjectDirViaClasspath();
        assertThat(p).isNotNull().exists();
        assertThat(p.getFileName().toString())
                .isEqualTo("selenium-webdriver-junit4");
    }


    //-----------------------------------------------------------------
    /**
     * returns the Path in which the class binary of
     * `com.kazurayam.webdriver.TestHelper` is found on disk.
     *
     * e.g, "/Users/somebody/selenium-webdriver-java/selenium-webdriver-junit4/build/classes/java/test/" when built by Gradle
     * e.g, "/Users/somebody/selenium-webdriver-java/selenium-webdriver-junit4/target/test-classes/" when built by Maven
     *
     */
    public static Path getCodeSourceAsPath() {
        return getCodeSourceAsPath(TestHelper.class);
    }
    public static Path getCodeSourceAsPath(Class clazz) {
        CodeSource codeSource = clazz.getProtectionDomain().getCodeSource();
        URL url = codeSource.getLocation();
        try {
            Path path = Paths.get(url.toURI());
            log.info("The code source : " + path);
            return path;
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void test_getCodeSourceAsPath() {
        Path p = TestHelper.getCodeSourceAsPath();
        log.info("[testGetCodeSourceAsPath] p = " + p);
        assertThat(p).isNotNull().exists();
    }
}
