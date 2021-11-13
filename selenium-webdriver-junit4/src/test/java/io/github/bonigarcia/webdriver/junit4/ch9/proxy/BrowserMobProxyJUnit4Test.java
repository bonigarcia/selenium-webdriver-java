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
package io.github.bonigarcia.webdriver.junit4.ch9.proxy;

import static java.lang.invoke.MethodHandles.lookup;
import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.slf4j.Logger;

import io.github.bonigarcia.wdm.WebDriverManager;
import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.core.har.HarEntry;

public class BrowserMobProxyJUnit4Test {

    static final Logger log = getLogger(lookup().lookupClass());

    WebDriver driver;

    BrowserMobProxy browserMobProxy;

    @Before
    public void setup() {
        browserMobProxy = new BrowserMobProxyServer();
        browserMobProxy.start();
        browserMobProxy.newHar();

        Proxy seleniumProxy = ClientUtil.createSeleniumProxy(browserMobProxy);
        FirefoxOptions options = new FirefoxOptions();
        options.setProxy(seleniumProxy);
        options.setAcceptInsecureCerts(true);

        driver = WebDriverManager.firefoxdriver().capabilities(options)
                .create();
    }

    @After
    public void teardown() {
        browserMobProxy.stop();
        driver.quit();
    }

    @Test
    public void testBrowserMobProxy() {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/");

        List<HarEntry> logEntries = browserMobProxy.getHar().getLog()
                .getEntries();
        logEntries.forEach(logEntry -> {
            log.debug("Request: {} - Response: {}",
                    logEntry.getRequest().getUrl(),
                    logEntry.getResponse().getStatus());
            assertThat(logEntry.getResponse().getStatus()).isEqualTo(200);
        });
    }

}
