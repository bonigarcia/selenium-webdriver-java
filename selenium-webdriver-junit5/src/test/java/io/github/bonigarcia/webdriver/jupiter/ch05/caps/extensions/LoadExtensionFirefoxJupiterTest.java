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
package io.github.bonigarcia.webdriver.jupiter.ch05.caps.extensions;

import static io.github.bonigarcia.wdm.WebDriverManager.zipFolder;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

class LoadExtensionFirefoxJupiterTest {

    WebDriver driver;

    Path zippedExtension;

    @BeforeEach
    void setup() throws URISyntaxException {
        Path extensionFolder = Paths
                .get(ClassLoader.getSystemResource("web-extension").toURI());
        zippedExtension = zipFolder(extensionFolder);

        driver = WebDriverManager.firefoxdriver().create();
        ((FirefoxDriver) driver).installExtension(zippedExtension, true);
    }

    @AfterEach
    void teardown() throws InterruptedException, IOException {
        // FIXME: pause for manual browser inspection
        Thread.sleep(Duration.ofSeconds(3).toMillis());

        Files.delete(zippedExtension);

        driver.quit();
    }

    @Test
    void testExtensions() {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/");

        WebElement h1 = driver.findElement(By.tagName("h1"));
        assertThat(h1.getText())
                .isNotEqualTo("Hands-On Selenium WebDriver with Java");
    }

}
