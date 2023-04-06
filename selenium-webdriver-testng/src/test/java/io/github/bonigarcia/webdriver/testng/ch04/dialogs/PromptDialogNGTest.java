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
package io.github.bonigarcia.webdriver.testng.ch04.dialogs;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import io.github.bonigarcia.webdriver.HelperClass.Constants;
import io.github.bonigarcia.webdriver.HelperClass.TestSetup;

public class PromptDialogNGTest extends TestSetup {

    @Test
    public void testPromptDialog() {
        driver.get(
                "https://bonigarcia.dev/selenium-webdriver-java/dialog-boxes.html");
        clickPromptButton();
        Alert prompt = driver.switchTo().alert();
        prompt.sendKeys(Constants.NAME);
        assertThat(prompt.getText()).isEqualTo("Please enter your name");
        prompt.accept();
    }

    @Test
    public void testPromptDialog2() {
        driver.get(
                "https://bonigarcia.dev/selenium-webdriver-java/dialog-boxes.html");
        clickPromptButton();
        Alert prompt = driver.switchTo().alert();
        prompt.sendKeys(Constants.NAME);
        assertThat(prompt.getText()).isEqualTo("Please enter your name");
        prompt.accept();
    }

    private void clickPromptButton() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement promptButton = driver.findElement(By.id("my-prompt"));
        promptButton.click();
        wait.until(ExpectedConditions.alertIsPresent());
    }

}
