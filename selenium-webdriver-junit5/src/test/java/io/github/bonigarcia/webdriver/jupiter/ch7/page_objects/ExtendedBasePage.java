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
package io.github.bonigarcia.webdriver.jupiter.ch7.page_objects;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ExtendedBasePage {

    static final Logger log = getLogger(lookup().lookupClass());

    WebDriver driver;
    int timeoutSec = 5; // wait timeout (5 seconds by default)

    public ExtendedBasePage(Class<? extends WebDriver> webDriverClass,
            int timeoutSec) {
        this(webDriverClass);
        this.timeoutSec = timeoutSec;
    }

    public ExtendedBasePage(Class<? extends WebDriver> webDriverClass) {
        this.driver = WebDriverManager.getInstance(webDriverClass).create();
    }

    public boolean isDisplayed(By locator) {
        try {
            WebDriverWait wait = new WebDriverWait(driver,
                    Duration.ofSeconds(timeoutSec));
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (TimeoutException e) {
            log.warn("Timeout of {} wait for {}", timeoutSec, locator);
            return false;
        }
        return true;
    }

    public void quit() {
        if (driver != null) {
            driver.quit();
        }
    }

}
