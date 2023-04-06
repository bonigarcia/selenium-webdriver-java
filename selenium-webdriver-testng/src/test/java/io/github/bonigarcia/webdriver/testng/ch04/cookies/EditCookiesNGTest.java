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
package io.github.bonigarcia.webdriver.testng.ch04.cookies;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import org.openqa.selenium.WebDriver.Options;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.testng.annotations.*;
import io.github.bonigarcia.webdriver.HelperClass.Constants;
import io.github.bonigarcia.webdriver.HelperClass.TestSetup;

public class EditCookiesNGTest extends TestSetup {

    @Test
    public void testEditCookie() {
        driver.get(
                "https://bonigarcia.dev/selenium-webdriver-java/cookies.html");

        Options options = driver.manage();
        Cookie username = options.getCookieNamed(Constants.COOKIE_USERNAME);
        Cookie editedCookie = new Cookie(username.getName(), Constants.EDIT_USERNAME);
        options.addCookie(editedCookie);

        Cookie readCookie = options.getCookieNamed(username.getName());
        assertThat(editedCookie).isEqualTo(readCookie);

        driver.findElement(By.id("refresh-cookies")).click();
    }

}
