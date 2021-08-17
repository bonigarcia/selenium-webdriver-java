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

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

class MessageBoxesJupiterTest {

    WebDriver driver;

    @BeforeAll
    static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setup() {
        driver = new ChromeDriver();
        driver.get(
                "https://bonigarcia.dev/selenium-webdriver-java/message-boxes.html");
    }

    @AfterEach
    void teardown() {
        driver.quit();
    }

    @Test
    void testAlert() {
        driver.findElement(By.id("my-alert")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        assertThat(alert.getText()).isEqualTo("This is an alert");
        alert.accept();
    }

    @Test
    void testConfirm() {
        driver.findElement(By.id("my-confirm")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        Alert confirm = wait.until(ExpectedConditions.alertIsPresent());
        assertThat(confirm.getText()).isEqualTo("Press a button");
        confirm.dismiss();
    }

    @Test
    void testPrompt() {
        driver.findElement(By.id("my-prompt")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        Alert prompt = wait.until(ExpectedConditions.alertIsPresent());
        prompt.sendKeys("John Doe");
        assertThat(prompt.getText()).isEqualTo("Please enter your name");
        prompt.accept();
    }

    @Test
    void testModal() {
        driver.findElement(By.id("my-modal")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        WebElement close = driver
                .findElement(By.xpath("//button[text() = 'Close']"));
        assertThat(close.getTagName()).isEqualTo("button");
        wait.until(ExpectedConditions.elementToBeClickable(close));
        close.click();
    }

}
