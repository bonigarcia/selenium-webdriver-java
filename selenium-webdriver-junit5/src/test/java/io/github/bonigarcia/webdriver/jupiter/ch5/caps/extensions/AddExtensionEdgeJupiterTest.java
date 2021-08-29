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
package io.github.bonigarcia.webdriver.jupiter.ch5.caps.extensions;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.support.Color;

import io.github.bonigarcia.wdm.WebDriverManager;

class AddExtensionEdgeJupiterTest {

    WebDriver driver;

    @BeforeEach
    void setup() throws URISyntaxException {
        Path extension = Paths
                .get(ClassLoader.getSystemResource("dark-bg.crx").toURI());
        EdgeOptions options = new EdgeOptions();
        options.addExtensions(extension.toFile());

        driver = WebDriverManager.edgedriver().capabilities(options).create();
    }

    @AfterEach
    void teardown() throws InterruptedException {
        // FIXME: pause for manual browser inspection
        Thread.sleep(Duration.ofSeconds(3).toMillis());

        driver.quit();
    }

    @Test
    void testAddExtension() {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/");

        Color pageBackgroundColor = Color
                .fromString(driver.findElement(By.tagName("body"))
                        .getCssValue("background-color"));
        Color white = new Color(255, 255, 255, 1);

        assertThat(pageBackgroundColor).isNotEqualTo(white);
    }

}
