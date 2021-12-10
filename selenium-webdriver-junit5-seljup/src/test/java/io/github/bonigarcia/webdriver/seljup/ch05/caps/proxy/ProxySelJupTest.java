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
package io.github.bonigarcia.webdriver.seljup.ch05.caps.proxy;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.seljup.Options;
import io.github.bonigarcia.seljup.SeleniumJupiter;

@Disabled("Proxy not available")
@ExtendWith(SeleniumJupiter.class)
class ProxySelJupTest {

    @Options
    ChromeOptions options = new ChromeOptions();

    @BeforeEach
    void setup() {
        Proxy proxy = new Proxy();
        String proxyStr = "proxy:port";
        proxy.setHttpProxy(proxyStr);
        proxy.setSslProxy(proxyStr);

        options.setAcceptInsecureCerts(true);
        options.setProxy(proxy);
        // The previous line is equivalent to:
        // options.setCapability(CapabilityType.PROXY, proxy);
    }

    @Test
    void testProxy(ChromeDriver driver) {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/");
        assertThat(driver.getTitle()).contains("Selenium WebDriver");
    }

}
