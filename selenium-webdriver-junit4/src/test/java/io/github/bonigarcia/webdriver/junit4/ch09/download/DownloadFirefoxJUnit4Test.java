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
package io.github.bonigarcia.webdriver.junit4.ch09.download;

import java.io.File;
import java.time.Duration;

import com.kazurayam.unittest.TestOutputOrganizer;
import io.github.bonigarcia.webdriver.junit4.TestOutputOrganizerFactory;
import org.awaitility.Awaitility;
import org.awaitility.core.ConditionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DownloadFirefoxJUnit4Test {

    static TestOutputOrganizer too;

    WebDriver driver;

    File targetFolder;

    @BeforeClass
    public static void setupClass() {
        too = TestOutputOrganizerFactory.create(DownloadFirefoxJUnit4Test.class);
    }

    @Before
    public void setup() {
        FirefoxOptions options = new FirefoxOptions();
        targetFolder = too.resolveOutput("dummy").getParent().toFile();
        options.addPreference("browser.download.dir",
                targetFolder.getAbsolutePath());
        options.addPreference("browser.download.folderList", 2);
        options.addPreference("browser.helperApps.neverAsk.saveToDisk",
                "image/png, application/pdf");
        options.addPreference("pdfjs.disabled", true);

        driver = WebDriverManager.firefoxdriver().capabilities(options)
                .create();
    }

    @After
    public void teardown() {
        driver.quit();
    }

    @Test
    public void testDownloadFirefox() {
        driver.get(
                "https://bonigarcia.dev/selenium-webdriver-java/download.html");

        driver.findElement(By.xpath("(//a)[2]")).click();
        driver.findElement(By.xpath("(//a)[3]")).click();

        ConditionFactory await = Awaitility.await()
                .atMost(Duration.ofSeconds(5));
        File wdmLogo = new File(targetFolder, "webdrivermanager.png");
        await.until(() -> wdmLogo.exists());

        File wdmDoc = new File(targetFolder, "webdrivermanager.pdf");
        await.until(() -> wdmDoc.exists());
    }

}
