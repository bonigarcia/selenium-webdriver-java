package io.github.bonigarcia.webdriver.testng;

import com.kazurayam.unittest.TestOutputOrganizer;

public class TestOutputOrganizerFactory {

    public static TestOutputOrganizer create(Class clazz) {
        return new TestOutputOrganizer.Builder(clazz)
                .subDirPath(clazz.getName())
                // e.g, "io.github.bonigarcia.webdriver.testng.ch04.screenshots.WebElementScreenshotJUnit4Test"
                .build();
    }
}
