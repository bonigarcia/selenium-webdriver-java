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
package io.github.bonigarcia.webdriver.jupiter.ch04.javascript;

import static java.lang.invoke.MethodHandles.lookup;
import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.slf4j.Logger;

import io.github.bonigarcia.wdm.WebDriverManager;

class ColorPickerJupiterTest {

    static final Logger log = getLogger(lookup().lookupClass());

    WebDriver driver;

    @BeforeEach
    void setup() {
        driver = WebDriverManager.chromedriver().create();
    }

    @AfterEach
    void teardown() throws InterruptedException {
        // FIXME: pause for manual browser inspection
        Thread.sleep(Duration.ofSeconds(3).toMillis());

        driver.quit();
    }

    @Test
    void testColorPicker() {
        driver.get(
                "https://bonigarcia.dev/selenium-webdriver-java/web-form.html");
        JavascriptExecutor js = (JavascriptExecutor) driver;

        WebElement colorPicker = driver.findElement(By.name("my-colors"));
        String initColor = colorPicker.getDomProperty("value");
        log.debug("The initial color is {}", initColor);

        Color red = new Color(255, 0, 0, 1);
        String script = String.format(
                "arguments[0].setAttribute('value', '%s');", red.asHex());
        js.executeScript(script, colorPicker);

        String finalColor = colorPicker.getDomProperty("value");
        log.debug("The final color is {}", finalColor);
        assertThat(finalColor).isNotEqualTo(initColor);
        assertThat(Color.fromString(finalColor)).isEqualTo(red);
    }

}
