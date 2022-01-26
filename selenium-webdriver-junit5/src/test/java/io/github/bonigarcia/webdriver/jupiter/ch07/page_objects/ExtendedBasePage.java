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
package io.github.bonigarcia.webdriver.jupiter.ch07.page_objects;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ExtendedBasePage {

    static final Logger log = getLogger(lookup().lookupClass());

    WebDriver driver;
    WebDriverWait wait;
    int timeoutSec = 5; // wait timeout (5 seconds by default)

    public ExtendedBasePage(String browser) {
        driver = WebDriverManager.getInstance(browser).create();
        wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSec));
    }

    public void setTimeoutSec(int timeoutSec) {
        this.timeoutSec = timeoutSec;
    }

    public void quit() {
        if (driver != null) {
            driver.quit();
        }
    }

    public void visit(String url) {
        driver.get(url);
    }

    public WebElement find(By element) {
        return driver.findElement(element);
    }

    public void click(WebElement element) {
        element.click();
    }

    public void click(By element) {
        click(find(element));
    }

    public void type(WebElement element, String text) {
        element.sendKeys(text);
    }

    public void type(By element, String text) {
        type(find(element), text);
    }

    public boolean isDisplayed(WebElement element) {
        return isDisplayed(ExpectedConditions.visibilityOf(element));
    }

    public boolean isDisplayed(By locator) {
        return isDisplayed(
                ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public boolean isDisplayed(ExpectedCondition<?> expectedCondition) {
        try {
            wait.until(expectedCondition);
        } catch (TimeoutException e) {
            log.warn("Timeout of {} wait for element ", timeoutSec);
            return false;
        }
        return true;
    }

}
