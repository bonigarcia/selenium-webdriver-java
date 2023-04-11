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

import static java.lang.invoke.MethodHandles.lookup;
import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class GatherLogsHeadlessChromeNGTest {

    static final Logger log = getLogger(lookup().lookupClass());

    WebDriverManager wdm = WebDriverManager.chromedriver()
            .capabilities(new ChromeOptions().addArguments("--headless=new"))
            .watch();
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
    public void testGatherLogsHeadlessChrome() {
        driver.get(
                "https://bonigarcia.dev/selenium-webdriver-java/console-logs.html");

        List<Map<String, Object>> logMessages = wdm.getLogs();

        assertThat(logMessages).hasSize(5);

        logMessages.forEach(map -> log.debug("[{}] [{}] {}",
                map.get("datetime"),
                String.format("%1$-14s",
                        map.get("source").toString().toUpperCase() + "."
                                + map.get("type").toString().toUpperCase()),
                map.get("message")));
    }

}
