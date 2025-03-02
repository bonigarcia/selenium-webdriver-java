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
package io.github.bonigarcia.webdriver.jupiter.ch04.window;

import static java.lang.invoke.MethodHandles.lookup;
import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Window;
import org.slf4j.Logger;

import io.github.bonigarcia.wdm.WebDriverManager;

class WindowJupiterTest {

    static final Logger log = getLogger(lookup().lookupClass());

    WebDriver driver;

    @BeforeEach
    void setup() {
        driver = WebDriverManager.chromedriver().create();
    }

    @AfterEach
    void teardown() throws InterruptedException {
        // FIXME: pause for manual browser inspection
        Thread.sleep(Duration.ofSeconds(3).toMillis());

        driver.quit();
    }

    @Disabled
    @Test
    void testWindow() {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/");
        Window window = driver.manage().window();

        Point initialPosition = window.getPosition();
        Dimension initialSize = window.getSize();
        log.debug("Initial window: position {} -- size {}", initialPosition,
                initialSize);

        window.maximize();

        Point maximizedPosition = window.getPosition();
        Dimension maximizedSize = window.getSize();
        log.debug("Maximized window: position {} -- size {}", maximizedPosition,
                maximizedSize);

        assertThat(initialPosition).isNotEqualTo(maximizedPosition);
        assertThat(initialSize).isNotEqualTo(maximizedSize);
    }

}
