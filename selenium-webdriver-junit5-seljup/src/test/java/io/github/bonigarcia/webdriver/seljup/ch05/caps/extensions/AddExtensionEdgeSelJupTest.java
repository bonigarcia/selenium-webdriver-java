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
package io.github.bonigarcia.webdriver.seljup.ch05.caps.extensions;

import static org.openqa.selenium.support.ui.ExpectedConditions.attributeToBe;
import static org.openqa.selenium.support.ui.ExpectedConditions.not;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.seljup.Options;
import io.github.bonigarcia.seljup.SeleniumJupiter;

@ExtendWith(SeleniumJupiter.class)
class AddExtensionEdgeSelJupTest {

    @Options
    EdgeOptions options = new EdgeOptions();

    @BeforeEach
    void setup() throws URISyntaxException {
        Path extension = Paths.get(
                ClassLoader.getSystemResource("shade_dark_mode.crx").toURI());
        options.addExtensions(extension.toFile());
    }

    @Test
    void testAddExtension(EdgeDriver driver) {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement body = driver.findElement(By.tagName("body"));
        String whiteRgba = "rgba(255, 255, 255, 1)";
        wait.until(not(attributeToBe(body, "background-color", whiteRgba)));
    }

}
