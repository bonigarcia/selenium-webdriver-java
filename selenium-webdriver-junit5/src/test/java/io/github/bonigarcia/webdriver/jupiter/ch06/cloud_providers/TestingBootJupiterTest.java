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
package io.github.bonigarcia.webdriver.jupiter.ch06.cloud_providers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assumptions.assumeThat;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

class TestingBootJupiterTest {

    WebDriver driver;

    @BeforeEach
    void setup() throws MalformedURLException {
        String key = System.getProperty("testingBootUsername");
        String secret = System.getProperty("testingBootAccessKey");

        // An alternative way to read key and secret is using envs:
        // String key = System.getenv("TESTINGBOOT_KEY");
        // String secret = System.getenv("TESTINGBOOT_SECRET");

        assumeThat(key).isNotEmpty();
        assumeThat(secret).isNotEmpty();

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("browserName", "Chrome");
        capabilities.setCapability("version", "latest");
        capabilities.setCapability("platform", Platform.WINDOWS);
        capabilities.setCapability("name", "Testing Selenium");

        URL remoteUrl = new URL(String
                .format("http://%s:%s@hub.testingbot.com/wd/hub", key, secret));
        driver = new RemoteWebDriver(remoteUrl, capabilities);
    }

    @AfterEach
    void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void testTestingBoot() {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/");
        assertThat(driver.getTitle()).contains("Selenium WebDriver");
    }

}
