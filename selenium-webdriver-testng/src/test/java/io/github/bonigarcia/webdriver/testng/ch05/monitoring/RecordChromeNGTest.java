/*
 * (C) Copyright 2022 Boni Garcia (https://bonigarcia.github.io/)
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
package io.github.bonigarcia.webdriver.testng.ch05.monitoring;

import static java.lang.invoke.MethodHandles.lookup;
import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

import java.nio.file.Path;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

@Ignore("--load-extension has been removed in Chrome +137")
public class RecordChromeNGTest {

    static final Logger log = getLogger(lookup().lookupClass());

    WebDriver driver;
    WebDriverManager wdm;

    @BeforeMethod
    public void setup() {
        wdm = WebDriverManager.chromedriver().watch();
        driver = wdm.create();
    }

    @AfterMethod
    public void teardown() {
        driver.quit();
    }

    @Test
    public void testRecordChrome() {
        driver.get(
                "https://bonigarcia.dev/selenium-webdriver-java/slow-calculator.html");

        wdm.startRecording();

        // 1 + 3
        driver.findElement(By.xpath("//span[text()='1']")).click();
        driver.findElement(By.xpath("//span[text()='+']")).click();
        driver.findElement(By.xpath("//span[text()='3']")).click();
        driver.findElement(By.xpath("//span[text()='=']")).click();

        // ... should be 4, wait for it
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.textToBe(By.className("screen"), "4"));

        wdm.stopRecording();

        Path recordingPath = wdm.getRecordingPath();
        assertThat(recordingPath).exists();

        log.debug("Recording available at {}", recordingPath);
    }

}