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
package io.github.bonigarcia.webdriver.jupiter.ch3.keyboard;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.commons.lang.SystemUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import io.github.bonigarcia.wdm.WebDriverManager;

class CopyPasteJupiterTest {

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
                "https://bonigarcia.dev/selenium-webdriver-java/web-form.html");

        WebElement inputText = driver.findElement(By.name("my-text"));
        WebElement textarea = driver.findElement(By.name("my-textarea"));

        Keys ctrl = SystemUtils.IS_OS_MAC ? Keys.COMMAND : Keys.CONTROL;
        String textValue = "hello world";
        new Actions(driver).sendKeys(inputText, textValue).keyDown(ctrl)
                .sendKeys(inputText, "a").sendKeys(inputText, "c")
                .sendKeys(textarea, "v").perform();

        assertThat(inputText.getAttribute("value")).isEqualTo(textValue);
        assertThat(textarea.getAttribute("value")).isEqualTo(textValue);
    }

}
