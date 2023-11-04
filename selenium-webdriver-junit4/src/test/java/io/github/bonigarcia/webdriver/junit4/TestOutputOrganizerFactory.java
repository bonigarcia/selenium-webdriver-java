package io.github.bonigarcia.webdriver.junit4;

import com.kazurayam.unittest.TestOutputOrganizer;

import java.nio.file.Paths;

/**
 * A Factory class that creates an instance of com.kazurayam.unittest.TestHelper
 * initialized with custom values of "outputDirPath" and "subDirPath"
 */
public class TestOutputOrganizerFactory {

    public static TestOutputOrganizer create(Class clazz) {
        return new TestOutputOrganizer.Builder(clazz)
                .subDirPath(clazz.getName())
                // e.g, "io.github.bonigarcia.webdriver.junit4.ch04.screenshots.WebElementScreenshotJUnit4Test"
                .build();
    }
}