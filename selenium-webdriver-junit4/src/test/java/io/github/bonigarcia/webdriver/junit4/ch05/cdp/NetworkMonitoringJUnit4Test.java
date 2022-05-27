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
import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v102.network.Network;
import org.openqa.selenium.devtools.v102.network.model.Headers;
import org.slf4j.Logger;

import io.github.bonigarcia.wdm.WebDriverManager;

public class NetworkMonitoringJUnit4Test {

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
    public void testNetworkMonitoring() {
        devTools.send(Network.enable(Optional.empty(), Optional.empty(),
                Optional.empty()));

        devTools.addListener(Network.requestWillBeSent(), request -> {
            log.debug("Request {}", request.getRequestId());
            log.debug("\t Method: {}", request.getRequest().getMethod());
            log.debug("\t URL: {}", request.getRequest().getUrl());
            logHeaders(request.getRequest().getHeaders());
        });

        devTools.addListener(Network.responseReceived(), response -> {
            log.debug("Response {}", response.getRequestId());
            log.debug("\t URL: {}", response.getResponse().getUrl());
            log.debug("\t Status: {}", response.getResponse().getStatus());
            logHeaders(response.getResponse().getHeaders());
        });

        driver.get("https://bonigarcia.dev/selenium-webdriver-java/");
        assertThat(driver.getTitle()).contains("Selenium WebDriver");
    }

    void logHeaders(Headers headers) {
        log.debug("\t Headers:");
        headers.toJson().forEach((k, v) -> log.debug("\t\t{}:{}", k, v));
    }

}
