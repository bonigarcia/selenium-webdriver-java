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

import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import io.github.bonigarcia.wdm.WebDriverManager;

class DropDownJupiterTest {

    WebDriver driver;

    @BeforeEach
    void setupTest() {
        driver = WebDriverManager.chromedriver().create();
        driver.get(
                "https://bonigarcia.dev/selenium-webdriver-java/web-form.html");
    }

    @AfterEach
    void teardown() throws InterruptedException {
        // FIXME: active wait for manual browser inspection
        Thread.sleep(Duration.ofSeconds(3).toMillis());

        driver.quit();
    }

    @Test
    void testSelect() {
        Select select = new Select(driver.findElement(By.name("my-select")));
        String optionLabel = "Three";
        select.selectByVisibleText(optionLabel);

        assertThat(select.getFirstSelectedOption().getText())
                .isEqualTo(optionLabel);
    }

    @Test
    void testDatalist() {
        WebElement datalist = driver.findElement(By.name("my-datalist"));
        datalist.click();

        WebElement option = driver
                .findElement(By.xpath("//datalist/option[2]"));
        String optionValue = option.getAttribute("value");
        datalist.sendKeys(optionValue);

        assertThat(optionValue).isEqualTo("New York");
    }

}
