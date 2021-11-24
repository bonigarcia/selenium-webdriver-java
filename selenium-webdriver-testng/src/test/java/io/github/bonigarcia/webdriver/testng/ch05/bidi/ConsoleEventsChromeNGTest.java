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
package io.github.bonigarcia.webdriver.testng.ch05.bidi;

import static java.lang.invoke.MethodHandles.lookup;
import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.devtools.events.CdpEventTypes;
import org.openqa.selenium.logging.HasLogEvents;
import org.slf4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ConsoleEventsChromeNGTest {

    static final Logger log = getLogger(lookup().lookupClass());

    WebDriver driver;

    @BeforeMethod
    public void setup() {
        driver = WebDriverManager.chromedriver().create();
    }

    @AfterMethod
    public void teardown() throws InterruptedException {
        // FIXME: pause for manual browser inspection
        Thread.sleep(Duration.ofSeconds(3).toMillis());

        driver.quit();
    }

    @Test
    public void testConsoleEvents() throws InterruptedException {
        HasLogEvents logger = (HasLogEvents) driver;

        CountDownLatch latch = new CountDownLatch(4);
        logger.onLogEvent(CdpEventTypes.consoleEvent(consoleEvent -> {
            log.debug("{} {}: {}", consoleEvent.getTimestamp(),
                    consoleEvent.getType(), consoleEvent.getMessages());
            latch.countDown();
        }));

        driver.get(
                "https://bonigarcia.dev/selenium-webdriver-java/console-logs.html");

        assertThat(latch.await(10, TimeUnit.SECONDS)).isTrue();
    }

}
