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
package io.github.bonigarcia.webdriver.junit4.ch05.bidi;

import static java.lang.invoke.MethodHandles.lookup;
import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.devtools.events.CdpEventTypes;
import org.openqa.selenium.devtools.events.DomMutationEvent;
import org.openqa.selenium.logging.HasLogEvents;
import org.slf4j.Logger;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DomMutationEdgeJUnit4Test {

    static final Logger log = getLogger(lookup().lookupClass());

    WebDriver driver;

    @Before
    public void setup() {
        driver = WebDriverManager.edgedriver().create();
    }

    @After
    public void teardown() throws InterruptedException {
        // FIXME: pause for manual browser inspection
        Thread.sleep(Duration.ofSeconds(3).toMillis());

        driver.quit();
    }

    @Test
    public void testDomMutation() throws InterruptedException {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/");

        HasLogEvents logger = (HasLogEvents) driver;
        JavascriptExecutor js = (JavascriptExecutor) driver;

        AtomicReference<DomMutationEvent> seen = new AtomicReference<>();
        CountDownLatch latch = new CountDownLatch(1);
        logger.onLogEvent(CdpEventTypes.domMutation(mutation -> {
            seen.set(mutation);
            latch.countDown();
        }));

        WebElement img = driver.findElement(By.tagName("img"));
        String newSrc = "img/award.png";
        String script = String.format("arguments[0].src = '%s';", newSrc);
        js.executeScript(script, img);

        assertThat(latch.await(10, TimeUnit.SECONDS)).isTrue();
        assertThat(seen.get().getElement().getDomProperty("src"))
                .endsWith(newSrc);
    }

}
