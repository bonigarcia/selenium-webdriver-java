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
package io.github.bonigarcia.webdriver.testng.ch05.caps.iemode;

import static java.lang.invoke.MethodHandles.lookup;
import static org.apache.commons.lang3.SystemUtils.IS_OS_WINDOWS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assumptions.assumeThat;
import static org.slf4j.LoggerFactory.getLogger;

import java.nio.file.Path;
import java.util.Optional;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.slf4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

@Ignore("The required setup to enable IE mode on Edge cannot be done in GitHub Actions")
public class IEmodeEdgeNGTest {

    static final Logger log = getLogger(lookup().lookupClass());

    WebDriver driver;

    @BeforeClass
    public static void setupClass() {
        assumeThat(IS_OS_WINDOWS).isTrue(); // IE mode is only supported on Win

        WebDriverManager.iedriver().setup();
    }

    @BeforeMethod
    public void setup() {
        Optional<Path> browserPath = WebDriverManager.edgedriver()
                .getBrowserPath();
        assumeThat(browserPath).isPresent();

        InternetExplorerOptions options = new InternetExplorerOptions();
        options.attachToEdgeChrome();
        options.withEdgeExecutablePath(browserPath.get().toString());

        driver = new InternetExplorerDriver(options);
    }

    @AfterMethod
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testIEmodeEdge() {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/");
        assertThat(driver.getTitle()).contains("Selenium WebDriver");
    }

}
