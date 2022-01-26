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
package io.github.bonigarcia.webdriver.junit4.ch05.caps.usermedia;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import java.util.regex.Pattern;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class UserMediaFirefoxJUnit4Test {

    WebDriver driver;

    @Before
    public void setup() {
        FirefoxOptions options = new FirefoxOptions();
        options.addPreference("media.navigator.permission.disabled", true);
        options.addPreference("media.navigator.streams.fake", true);

        driver = WebDriverManager.firefoxdriver().capabilities(options)
                .create();
    }

    @After
    public void teardown() throws InterruptedException {
        // FIXME: pause for manual browser inspection
        Thread.sleep(Duration.ofSeconds(3).toMillis());

        driver.quit();
    }

    @Test
    public void testUserMedia() {
        driver.get(
                "https://bonigarcia.dev/selenium-webdriver-java/get-user-media.html");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.findElement(By.id("start")).click();
        By videoDevice = By.id("video-device");
        Pattern nonEmptyString = Pattern.compile(".+");
        wait.until(ExpectedConditions.textMatches(videoDevice, nonEmptyString));
        assertThat(driver.findElement(videoDevice).getText()).isNotEmpty();
    }

}
