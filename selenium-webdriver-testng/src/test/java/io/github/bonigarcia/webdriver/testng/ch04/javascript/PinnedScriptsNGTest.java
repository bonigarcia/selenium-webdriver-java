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

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import java.util.Set;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.ScriptKey;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class PinnedScriptsNGTest {

    WebDriver driver;

    @BeforeMethod
    public void setup() {
        driver = WebDriverManager.chromedriver().create();
    }

    @AfterMethod
    public void teardown() throws InterruptedException {
        // FIXME: pause for manual browser inspection
        Thread.sleep(Duration.ofSeconds(3).toMillis());

        driver.quit();
    }

    @Test
    public void testPinnedScripts() {
        String initPage = "https://bonigarcia.dev/selenium-webdriver-java/";
        driver.get(initPage);
        JavascriptExecutor js = (JavascriptExecutor) driver;

        ScriptKey linkKey = js
                .pin("return document.getElementsByTagName('a')[2];");
        ScriptKey firstArgKey = js.pin("return arguments[0];");

        Set<ScriptKey> pinnedScripts = js.getPinnedScripts();
        assertThat(pinnedScripts).hasSize(2);

        WebElement formLink = (WebElement) js.executeScript(linkKey);
        formLink.click();
        assertThat(driver.getCurrentUrl()).isNotEqualTo(initPage);

        String message = "Hello world!";
        String executeScript = (String) js.executeScript(firstArgKey, message);
        assertThat(executeScript).isEqualTo(message);

        js.unpin(linkKey);
        assertThat(js.getPinnedScripts()).hasSize(1);
    }

}
