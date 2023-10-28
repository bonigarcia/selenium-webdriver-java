package com.kazurayam.unittest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class ProjectDirectoryResolver {

    private static final Logger log = LoggerFactory.getLogger(ProjectDirectoryResolver.class);

    private ProjectDirectoryResolver() {}

    /**
     * returns the Path in which the class binary of
     * `com.kazurayam.unittest.ProjectDirectoryResolver` is found on disk.
     *
     * e.g, "/Users/somebody/selenium-webdriver-java/selenium-webdriver-junit4/build/classes/java/test/" when built by Gradle
     * e.g, "/Users/somebody/selenium-webdriver-java/selenium-webdriver-junit4/target/test-classes/" when built by Maven
     *
     */
    public static Path getCodeSourceAsPath(Class clazz) {
        CodeSource codeSource = clazz.getProtectionDomain().getCodeSource();
        URL url = codeSource.getLocation();
        try {
            Path path = Paths.get(url.toURI());
            log.debug("The code source : " + path);
            return path;
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

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
    public static Path getProjectDirViaClasspath(Class clazz) {
        Path codeSourcePath = ProjectDirectoryResolver.getCodeSourceAsPath(clazz);
        // e.g. "/Users/myname/oreilly/selenium-webdriver-java/selenium-webdriver-junit4/build/classes/java/test/com/kazurayam/webdriver/TestHelper.class"
        List<String> nameElements = toNameElements(codeSourcePath);
        StringSequence ss = new StringSequence(nameElements);
        int indexOfBuildDir =
                ss.indexOfSubsequence(Arrays.asList("build", "classes", "java", "test"));
        int indexOfTargetDir =
                ss.indexOfSubsequence(Arrays.asList("target", "test-classes"));
        int boundary = -1;
        if (indexOfBuildDir > 0) {
            // project is built by Gradle
            boundary = indexOfBuildDir;
        } else if (indexOfTargetDir > 0) {
            // project is built by Maven
            boundary = indexOfTargetDir;
        } else {
            throw new IllegalStateException("unable to find the project directory via classpath");
        }
        // build the project dir to return as the result
        Path w = Paths.get("/");
        for (int i = 0; i < boundary; i++) {
            w = w.resolve(nameElements.get(i));
        }
        return w;   // e.g, /Users/myname/oreilly/selenium-webdriver-java/selenium-webdriver-junit4
    }

    private static List<String> toNameElements(Path codeSourcePath) {
        List<String> nameElements = new ArrayList<>();
        Iterator<Path> iter = codeSourcePath.iterator();
        while (iter.hasNext()) {
            Path p = iter.next();
            nameElements.add(p.getFileName().toString());
        }
        return nameElements;
    }

}
