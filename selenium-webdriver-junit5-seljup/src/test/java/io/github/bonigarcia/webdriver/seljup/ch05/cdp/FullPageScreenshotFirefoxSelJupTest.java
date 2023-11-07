/*
 * (C) Copyright 2021 Boni Garcia (https://bonigarcia.github.io/)
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
package io.github.bonigarcia.webdriver.seljup.ch05.cdp;

import com.kazurayam.unittest.TestOutputOrganizer;
import io.github.bonigarcia.seljup.SeleniumJupiter;
import io.github.bonigarcia.webdriver.seljup.TestOutputOrganizerFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SeleniumJupiter.class)
class FullPageScreenshotFirefoxSelJupTest {

    static TestOutputOrganizer too;

    @BeforeAll
    static void setupClass() {
        too = TestOutputOrganizerFactory
                .create(FullPageScreenshotFirefoxSelJupTest.class);
    }

    @Test
    void testFullPageScreenshotFirefox(FirefoxDriver driver)
            throws IOException {
        driver.get(
                "https://bonigarcia.dev/selenium-webdriver-java/long-page.html");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfNestedElementsLocatedBy(
                By.className("container"), By.tagName("p")));

        byte[] imageBytes = ((FirefoxDriver) driver)
                .getFullPageScreenshotAs(OutputType.BYTES);
        Path destination =
                too.resolveOutput("fullpage-screenshot-firefox.png");
        Files.write(destination, imageBytes);

        assertThat(destination).exists();
    }

}
