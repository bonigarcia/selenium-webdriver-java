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
package io.github.bonigarcia.webdriver.junit4.ch09.reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.kazurayam.unittest.TestOutputOrganizer;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.webdriver.junit4.TestOutputOrganizerFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.openqa.selenium.WebDriver;

import static org.assertj.core.api.Assertions.assertThat;

public class ReportingJUnit4Test {

    @Rule
    public TestName name = new TestName();

    static TestOutputOrganizer too;

    WebDriver driver;

    static ExtentReports reports;

    @BeforeClass
    public static void setupClass() {
        too = TestOutputOrganizerFactory.create(ReportingJUnit4Test.class);
        reports = new ExtentReports();
        ExtentSparkReporter htmlReporter =
                new ExtentSparkReporter(
                        too.resolveOutput("extentReport.html").toFile());
        reports.attachReporter(htmlReporter);
    }

    @Before
    public void setup() {
        reports.createTest(name.getMethodName());

        driver = WebDriverManager.chromedriver().create();
    }

    @After
    public void teardown() {
        driver.quit();
    }

    @AfterClass
    public static void teardownClass() {
        reports.flush();
    }

    @Test
    public void testReporting1() {
        checkIndex(driver);
    }

    @Test
    public void testReporting2() {
        checkIndex(driver);
    }

    void checkIndex(WebDriver driver) {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/");
        assertThat(driver.getTitle()).contains("Selenium WebDriver");
    }

}
