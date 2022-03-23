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
package io.github.bonigarcia.webdriver.junit4.ch05.cdp;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.JavascriptException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.events.ConsoleEvent;
import org.slf4j.Logger;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ConsoleListenerJUnit4Test {

    static final Logger log = getLogger(lookup().lookupClass());

    WebDriver driver;

    DevTools devTools;

    @Before
    public void setup() {
        driver = WebDriverManager.chromedriver().create();
        devTools = ((ChromeDriver) driver).getDevTools();
        devTools.createSession();
    }

    @After
    public void teardown() {
        devTools.close();
        driver.quit();
    }

    @Test
    public void testConsoleListener() throws Exception {
        CompletableFuture<ConsoleEvent> futureEvents = new CompletableFuture<>();
        devTools.getDomains().events()
                .addConsoleListener(futureEvents::complete);

        CompletableFuture<JavascriptException> futureJsExc = new CompletableFuture<>();
        devTools.getDomains().events()
                .addJavascriptExceptionListener(futureJsExc::complete);

        driver.get(
                "https://bonigarcia.dev/selenium-webdriver-java/console-logs.html");

        ConsoleEvent consoleEvent = futureEvents.get(5, TimeUnit.SECONDS);
        log.debug("ConsoleEvent: {} {} {}", consoleEvent.getTimestamp(),
                consoleEvent.getType(), consoleEvent.getMessages());

        JavascriptException jsException = futureJsExc.get(5, TimeUnit.SECONDS);
        log.debug("JavascriptException: {} {}", jsException.getMessage(),
                jsException.getSystemInformation());
    }
}
