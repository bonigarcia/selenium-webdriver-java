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
package io.github.bonigarcia.webdriver.junit4.ch8.parameterized;

import static java.lang.invoke.MethodHandles.lookup;
import static org.assertj.core.api.Assertions.assertThat;
import static org.openqa.selenium.PageLoadStrategy.EAGER;
import static org.openqa.selenium.PageLoadStrategy.NONE;
import static org.openqa.selenium.PageLoadStrategy.NORMAL;
import static org.slf4j.LoggerFactory.getLogger;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;

import io.github.bonigarcia.wdm.WebDriverManager;

@RunWith(Parameterized.class)
public class PameterizedPageLoadChromeJUnit4Test {

    static final Logger log = getLogger(lookup().lookupClass());

    WebDriver driver;

    @Parameter(0)
    public PageLoadStrategy pageLoadStrategy;

    @Parameters(name = "{index}: browser={0}")
    public static Collection<Object[]> data() {
        return Arrays
                .asList(new Object[][] { { EAGER }, { NONE }, { NORMAL } });
    }

    @Before
    public void setup() {
        ChromeOptions options = new ChromeOptions();
        options.setPageLoadStrategy(pageLoadStrategy);
        driver = WebDriverManager.chromedriver().capabilities(options).create();
    }

    @After
    public void teardown() {
        driver.quit();
    }

    @Test
    public void testPameterizedPageLoad() {
        long initMillis = System.currentTimeMillis();
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/");
        Duration elapsed = Duration
                .ofMillis(System.currentTimeMillis() - initMillis);

        Capabilities capabilities = ((RemoteWebDriver) driver)
                .getCapabilities();
        Object pageLoad = capabilities
                .getCapability(CapabilityType.PAGE_LOAD_STRATEGY);
        String browserName = capabilities.getBrowserName();
        log.debug(
                "The page took {} ms to be loaded using a '{}' strategy in {}",
                elapsed.toMillis(), pageLoad, browserName);

        assertThat(pageLoad).isEqualTo(pageLoadStrategy.toString());
    }

}
