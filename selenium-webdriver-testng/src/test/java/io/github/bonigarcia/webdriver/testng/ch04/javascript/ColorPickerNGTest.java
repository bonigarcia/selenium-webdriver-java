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
package io.github.bonigarcia.webdriver.testng.ch04.javascript;

import static java.lang.invoke.MethodHandles.lookup;
import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.slf4j.Logger;
import org.testng.annotations.*;
import io.github.bonigarcia.webdriver.HelperClass.TestSetup;

public class ColorPickerNGTest extends TestSetup {

    static final Logger log = getLogger(lookup().lookupClass());

    @Test
    public void testColorPicker() {
        String initialColor = getInitialColor();

        Color red = new Color(255, 0, 0, 1);
        setColor(red);

        String finalColor = getFinalColor();
        assertThat(finalColor).isNotEqualTo(initialColor);
        assertThat(Color.fromString(finalColor)).isEqualTo(red);
    }

    private WebElement getColorPicker() {
        return driver.findElement(By.name("my-colors"));
    }

    private String getInitialColor() {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/web-form.html");
        WebElement colorPicker = getColorPicker();
        String initialColor = colorPicker.getAttribute("value");
        log.debug("The initial color is {}", initialColor);
        return initialColor;
    }

    private void setColor(Color color) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement colorPicker = getColorPicker();
        String script = String.format(
                "arguments[0].setAttribute('value', '%s');", color.asHex());
        js.executeScript(script, colorPicker);
    }

    private String getFinalColor() {
        WebElement colorPicker = getColorPicker();
        String finalColor = colorPicker.getAttribute("value");
        log.debug("The final color is {}", finalColor);
        return finalColor;
    }

}
