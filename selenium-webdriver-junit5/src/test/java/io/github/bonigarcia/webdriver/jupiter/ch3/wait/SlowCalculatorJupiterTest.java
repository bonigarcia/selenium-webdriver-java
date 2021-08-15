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
package io.github.bonigarcia.webdriver.jupiter.ch3.wait;

import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

class SlowCalculatorJupiterTest {

    WebDriver driver;

    @BeforeEach
    void setupTest() {
        driver = WebDriverManager.chromedriver().create();
    }

    @AfterEach
    void teardown() {
        driver.quit();
    }

    @Test
    void test() {
        driver.get(
                "https://bonigarcia.dev/selenium-webdriver-java/slow-calculator.html");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // 1 + 2
        WebElement one = driver.findElement(By.xpath("//button[@value='1']"));
        wait.until(ExpectedConditions.elementToBeClickable(one));
        one.click();

        WebElement plus = driver.findElement(By.xpath("//button[@value='+']"));
        wait.until(ExpectedConditions.elementToBeClickable(plus));
        plus.click();

        WebElement two = driver.findElement(By.xpath("//button[@value='2']"));
        wait.until(ExpectedConditions.elementToBeClickable(two));
        two.click();

        WebElement equalTo = driver
                .findElement(By.xpath("//button[@value='=']"));
        wait.until(ExpectedConditions.elementToBeClickable(equalTo));
        equalTo.click();

        // ... should be 3
        WebElement result = driver.findElement(By.id("result"));
        wait.until(ExpectedConditions.attributeToBe(result, "value", "3"));
    }

}
