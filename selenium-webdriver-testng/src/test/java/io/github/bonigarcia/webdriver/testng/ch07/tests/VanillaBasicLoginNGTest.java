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
package io.github.bonigarcia.webdriver.testng.ch07.tests;

import static org.assertj.core.api.Assertions.assertThat;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.webdriver.HelperClass.TestSetup;

public class VanillaBasicLoginNGTest extends TestSetup {

    @Test
    public void testVanillaBasicLoginSuccess() {
        login("user", "user");
        assertThat(driver.findElement(By.id("success")).isDisplayed()).isTrue();
    }

    @Test
    public void testVanillaBasicLoginFailure() {
        login("bad-user", "bad-password");
        assertThat(driver.findElement(By.id("invalid")).isDisplayed()).isTrue();
    }

    private void login(String username, String password) {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/login-form.html");
        driver.findElement(By.id("username")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.cssSelector("button")).click();
    }

}
