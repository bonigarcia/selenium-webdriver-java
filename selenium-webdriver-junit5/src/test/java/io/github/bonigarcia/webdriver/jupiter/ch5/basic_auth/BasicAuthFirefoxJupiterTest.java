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
package io.github.bonigarcia.webdriver.jupiter.ch5.basic_auth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.HasAuthentication;
import org.openqa.selenium.UsernameAndPassword;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import io.github.bonigarcia.wdm.WebDriverManager;

class BasicAuthFirefoxJupiterTest {

    WebDriver driver;

    @BeforeEach
    void setup() {
        driver = WebDriverManager.firefoxdriver().create();
    }

    @AfterEach
    void teardown() {
        driver.quit();
    }

    @Test
    void testBasicAuth() {
        assertThatThrownBy(() -> {
            HasAuthentication auth = (HasAuthentication) driver;
            auth.register(() -> new UsernameAndPassword("guest", "guest"));

            driver.get("https://bonigarcia.dev/selenium-webdriver-java/");
            driver.findElement(By.linkText("Basic authentication")).click();

            WebElement body = driver.findElement(By.tagName("body"));
            assertThat(body.getText()).contains("Your browser made it!");
        }).isInstanceOf(ClassCastException.class);
    }

}
