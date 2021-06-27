/*
 * (C) Copyright 2021 Boni Garcia (http://bonigarcia.github.io/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package io.github.bonigarcia.webdriver.testng.ch2.otherbrowsers;

import static java.lang.invoke.MethodHandles.lookup;
import static java.nio.file.Files.exists;
import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.slf4j.Logger;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class HelloWorldSafariNGTest {

    static final Logger log = getLogger(lookup().lookupClass());

    private WebDriver driver;

    @BeforeClass
    public void setupSuite() {
        // TODO: Use WebDriverManager 5 (not released yet) to get browser path
        // Optional<Path> browserPath = WebDriverManager.safaridriver().getBrowserPath();
        // browserPath.orElseThrow(() -> new SkipException("Safari not available"));

        if (!exists(getBrowserPath())) {
            throw new SkipException("Safari not available");
        }
    }

    @BeforeMethod
    public void setup() {
        driver = new SafariDriver();
    }

    @AfterTest
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void test() {
        // Exercise
        String sutUrl = "https://bonigarcia.github.io/selenium-webdriver-java/";
        driver.get(sutUrl);
        String title = driver.getTitle();
        log.debug("The title of {} is {}", sutUrl, title);

        // Verify
        assertThat(title).isEqualTo("Hands-on Selenium WebDriver with Java");
    }

    private static Path getBrowserPath() {
        return Paths.get("/Applications/Safari.app/Contents/MacOS/Safari");
    }

}
