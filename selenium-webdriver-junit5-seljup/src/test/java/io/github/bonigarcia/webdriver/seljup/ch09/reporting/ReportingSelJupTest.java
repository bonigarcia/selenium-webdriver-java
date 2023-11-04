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
package io.github.bonigarcia.webdriver.seljup.ch09.reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.kazurayam.unittest.TestOutputOrganizer;
import io.github.bonigarcia.seljup.SeleniumJupiter;
import io.github.bonigarcia.webdriver.seljup.TestOutputOrganizerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SeleniumJupiter.class)
class ReportingSelJupTest {

    static TestOutputOrganizer too;

    static ExtentReports reports;

    @BeforeAll
    static void setupClass() {
        too = TestOutputOrganizerFactory.create(ReportingSelJupTest.class);
        reports = new ExtentReports();
        ExtentSparkReporter htmlReporter =
                new ExtentSparkReporter(too.resolveOutput("extentReport.html").toFile());
        reports.attachReporter(htmlReporter);
    }

    @BeforeEach
    void setup(TestInfo testInfo) {
        reports.createTest(testInfo.getDisplayName());
    }

    @AfterAll
    static void teardownClass() {
        reports.flush();
    }

    @Test
    void testReporting1(ChromeDriver driver) {
        checkIndex(driver);
    }

    @Test
    void testReporting2(ChromeDriver driver) {
        checkIndex(driver);
    }

    void checkIndex(WebDriver driver) {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/");
        assertThat(driver.getTitle()).contains("Selenium WebDriver");
    }

}
