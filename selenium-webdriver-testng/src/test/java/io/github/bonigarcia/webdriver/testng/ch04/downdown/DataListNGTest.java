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
package io.github.bonigarcia.webdriver.testng.ch04.downdown;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.*;
import io.github.bonigarcia.webdriver.HelperClass.TestSetup;

public class DataListNGTest extends TestSetup {

    @FindBy(xpath = "//datalist/option[2]")
    WebElement option;

    @Test
    public void testDataList() {
        driver.get(
                "https://bonigarcia.dev/selenium-webdriver-java/web-form.html");

        WebElement dataList = driver.findElement(By.name("my-datalist"));
        dataList.click();
        String optionValue = option.getAttribute("value");
        dataList.sendKeys(optionValue);

        assertThat(optionValue).isEqualTo("New York");
    }

}
