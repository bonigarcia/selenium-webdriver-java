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
package io.github.bonigarcia.webdriver.seljup.ch08.disabled;

import static io.github.bonigarcia.seljup.Browser.SAFARI;
import static io.github.bonigarcia.seljup.BrowserType.CHROME;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.safari.SafariDriver;

import io.github.bonigarcia.seljup.DockerBrowser;
import io.github.bonigarcia.seljup.DriverCapabilities;
import io.github.bonigarcia.seljup.EnabledIfBrowserAvailable;
import io.github.bonigarcia.seljup.EnabledIfDockerAvailable;
import io.github.bonigarcia.seljup.EnabledIfDriverUrlOnline;
import io.github.bonigarcia.seljup.SeleniumJupiter;

@ExtendWith(SeleniumJupiter.class)
class DisabledSelJupTest {

    @EnabledIfBrowserAvailable(SAFARI)
    @Test
    void testDisabled1(SafariDriver driver) {
        checkPracticeSite(driver);
    }

    @EnabledIfDockerAvailable
    @Test
    void testDisabled2(@DockerBrowser(type = CHROME) WebDriver driver) {
        checkPracticeSite(driver);
    }

    @EnabledIfDriverUrlOnline("http://localhost:4444/")
    @Test
    void testDisabled3(
            @DriverCapabilities("browserName=chrome") WebDriver driver) {
        checkPracticeSite(driver);
    }

    void checkPracticeSite(WebDriver driver) {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/");
        assertThat(driver.getTitle()).contains("Selenium WebDriver");
    }

}
