/*
 * (C) Copyright 2022 Boni Garcia (https://bonigarcia.github.io/)
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
package io.github.bonigarcia.webdriver.testng.ch05.monitoring;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DisableCspFirefoxNGTest {

    WebDriverManager wdm = WebDriverManager.firefoxdriver().watch()
            .disableCsp();
    WebDriver driver;

    @BeforeMethod
    public void setup() {
        driver = wdm.create();
    }

    @AfterMethod
    public void teardown() {
        driver.quit();
    }

    @Test
    public void testDisableCspFirefox() {
        driver.get("https://paypal.com/");
        List<Map<String, Object>> logMessages = wdm.getLogs();
        assertThat(logMessages).isNotNull();
    }

}
