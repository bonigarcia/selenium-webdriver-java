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
package io.github.bonigarcia.webdriver.seljup.ch10.mobile;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.chromium.options.ChromiumOptions;
import io.appium.java_client.remote.AutomationName;
import io.github.bonigarcia.seljup.DriverCapabilities;
import io.github.bonigarcia.seljup.EnabledIfDriverUrlOnline;
import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.Platform;

import static org.assertj.core.api.Assertions.assertThat;

@EnabledIfDriverUrlOnline("http://localhost:4723")
@ExtendWith(SeleniumJupiter.class)
class AppiumSelJupTest {

    @DriverCapabilities
    ChromiumOptions options = new ChromiumOptions();


    @BeforeEach
    void setup() {
        options.setPlatformName(Platform.ANDROID.name())
                .setAutomationName(AutomationName.ANDROID_UIAUTOMATOR2);
        options.setCapability("deviceName",
                "Nexus 5 API 30");
    }

    @Test
    void test(AppiumDriver driver) {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/");
        assertThat(driver.getTitle()).contains("Selenium WebDriver");
    }

}
