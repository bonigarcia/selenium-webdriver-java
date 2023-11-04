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

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v118.network.Network;
import org.openqa.selenium.devtools.v118.network.model.Cookie;
import org.openqa.selenium.devtools.v118.storage.Storage;
import org.slf4j.Logger;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ManageCookiesJUnit4Test {

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
    public void teardown() throws InterruptedException {
        // FIXME: pause for manual browser inspection
        Thread.sleep(Duration.ofSeconds(3).toMillis());

        devTools.close();
        driver.quit();
    }

    @Test
    public void testManageCookies() {
        devTools.send(Network.enable(Optional.empty(), Optional.empty(),
                Optional.empty()));
        driver.get(
                "https://bonigarcia.dev/selenium-webdriver-java/cookies.html");

        // Read cookies
        List<Cookie> cookies = devTools
                .send(Storage.getCookies(Optional.empty()));
        cookies.forEach(cookie -> log.debug("{}={}", cookie.getName(),
                cookie.getValue()));
        List<String> cookieName = cookies.stream()
                .map(cookie -> cookie.getName()).sorted()
                .collect(Collectors.toList());
        Set<org.openqa.selenium.Cookie> seleniumCookie = driver.manage()
                .getCookies();
        List<String> selCookieName = seleniumCookie.stream()
                .map(selCookie -> selCookie.getName()).sorted()
                .collect(Collectors.toList());
        assertThat(cookieName).isEqualTo(selCookieName);

        // Clear cookies
        devTools.send(Network.clearBrowserCookies());
        List<Cookie> cookiesAfterClearing = devTools
                .send(Storage.getCookies(Optional.empty()));
        assertThat(cookiesAfterClearing).isEmpty();

        driver.findElement(By.id("refresh-cookies")).click();
    }
}
