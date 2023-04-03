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
import org.testng.annotations.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import io.github.bonigarcia.webdriver.HelperClass.Constants;

public class SelectNGTest {
    @Test
    public void testSelectOptionByVisibleText() {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/web-form.html");

        Select select = new Select(driver.findElement(By.name("my-select")));
        select.selectByVisibleText(Constants.THREE);

        assertThat(select.getFirstSelectedOption().getText()).isEqualTo(Constants.THREE);
    }

}
