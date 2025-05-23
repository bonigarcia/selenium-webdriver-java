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
package io.github.bonigarcia.webdriver.jupiter.ch05.cdp;

import static java.lang.invoke.MethodHandles.lookup;
import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

import java.time.Duration;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v135.network.Network;
import org.openqa.selenium.devtools.v135.network.model.ConnectionType;
import org.slf4j.Logger;

import io.github.bonigarcia.wdm.WebDriverManager;

class EmulateNetworkConditionsJupiterTest {

    static final Logger log = getLogger(lookup().lookupClass());

    WebDriver driver;

    DevTools devTools;

    @BeforeEach
    void setup() {
        driver = WebDriverManager.chromedriver().create();
        devTools = ((ChromeDriver) driver).getDevTools();
        devTools.createSession();
    }

    @AfterEach
    void teardown() {
        devTools.close();
        driver.quit();
    }

    @Test
    void testEmulateNetworkConditions() {
        devTools.send(Network.enable(Optional.empty(), Optional.empty(),
                Optional.empty()));
        devTools.send(Network.emulateNetworkConditions(false, 100, 50 * 1024,
                50 * 1024, Optional.of(ConnectionType.CELLULAR3G),
                Optional.empty(), Optional.empty(), Optional.empty()));

        long initMillis = System.currentTimeMillis();
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/");
        Duration elapsed = Duration
                .ofMillis(System.currentTimeMillis() - initMillis);
        log.debug("The page took {} ms to be loaded", elapsed.toMillis());

        assertThat(driver.getTitle()).contains("Selenium WebDriver");
    }
}
