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
package io.github.bonigarcia.webdriver.testng.ch03.locators;

import static org.assertj.core.api.Assertions.assertThat;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ByXPathNGTest {

    WebDriver driver;

    @BeforeClass
    public void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeMethod
    public void setup() {
        driver = new ChromeDriver();
    }

    @AfterMethod
    public void teardown() {
        driver.quit();
    }

    @Test
    public void testByXPathBasic() {
        driver.get(
                "https://bonigarcia.dev/selenium-webdriver-java/web-form.html");

        WebElement hidden = driver
                .findElement(By.xpath("//input[@type='hidden']"));
        assertThat(hidden.isDisplayed()).isFalse();
    }

    @Test
    public void testByXPathAdvanced() {
        driver.get(
                "https://bonigarcia.dev/selenium-webdriver-java/web-form.html");

        WebElement radio1 = driver
                .findElement(By.xpath("//*[@type='radio' and @checked]"));
        assertThat(radio1.getDomProperty("id")).isEqualTo("my-radio-1");
        assertThat(radio1.isSelected()).isTrue();

        WebElement radio2 = driver
                .findElement(By.xpath("//*[@type='radio' and not(@checked)]"));
        assertThat(radio2.getDomProperty("id")).isEqualTo("my-radio-2");
        assertThat(radio2.isSelected()).isFalse();
    }

}
