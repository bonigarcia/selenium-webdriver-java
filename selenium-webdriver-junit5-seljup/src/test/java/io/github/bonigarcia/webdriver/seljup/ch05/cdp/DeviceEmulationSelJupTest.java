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
package io.github.bonigarcia.webdriver.seljup.ch05.cdp;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v131.network.Network;

import io.github.bonigarcia.seljup.SeleniumJupiter;

@ExtendWith(SeleniumJupiter.class)
class DeviceEmulationSelJupTest {

    @AfterEach
    void teardown() throws InterruptedException {
        // FIXME: pause for manual browser inspection
        Thread.sleep(Duration.ofSeconds(3).toMillis());
    }

    @Test
    void testDeviceEmulation(ChromeDriver driver, DevTools devTools) {
        // 1. Override user agent (Apple iPhone 6)
        String userAgent = "Mozilla/5.0 (iPhone; CPU iPhone OS 8_0 like Mac OS X) "
                + "AppleWebKit/600.1.3 (KHTML, like Gecko) "
                + "Version/8.0 Mobile/12A4345d Safari/600.1.4";
        devTools.send(Network.setUserAgentOverride(userAgent, Optional.empty(),
                Optional.empty(), Optional.empty()));

        // 2. Emulate device dimension
        Map<String, Object> deviceMetrics = new HashMap<>();
        deviceMetrics.put("width", 375);
        deviceMetrics.put("height", 667);
        deviceMetrics.put("mobile", true);
        deviceMetrics.put("deviceScaleFactor", 2);
        ((ChromeDriver) driver).executeCdpCommand(
                "Emulation.setDeviceMetricsOverride", deviceMetrics);

        driver.get("https://bonigarcia.dev/selenium-webdriver-java/");
        assertThat(driver.getTitle()).contains("Selenium WebDriver");
    }
}
