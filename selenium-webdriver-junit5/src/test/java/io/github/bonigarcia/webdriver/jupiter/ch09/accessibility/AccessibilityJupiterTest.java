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
package io.github.bonigarcia.webdriver.jupiter.ch09.accessibility;

import static java.lang.invoke.MethodHandles.lookup;
import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

import java.nio.file.Path;
import java.util.List;

import com.kazurayam.unittest.TestOutputOrganizer;
import io.github.bonigarcia.webdriver.jupiter.TestOutputOrganizerFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;

import com.deque.html.axecore.results.Results;
import com.deque.html.axecore.results.Rule;
import com.deque.html.axecore.selenium.AxeBuilder;
import com.deque.html.axecore.selenium.AxeReporter;

import io.github.bonigarcia.wdm.WebDriverManager;

class AccessibilityJupiterTest {

    final Logger log = getLogger(lookup().lookupClass());

    static TestOutputOrganizer too;

    WebDriver driver;

    @BeforeAll
    static void setupClass() {
        too = TestOutputOrganizerFactory.create(AccessibilityJupiterTest.class);
    }

    @BeforeEach
    void setup() {
        driver = WebDriverManager.chromedriver().create();
    }

    @AfterEach
    void teardown() {
        driver.quit();
    }

    @Test
    void testAccessibility() {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/");
        assertThat(driver.getTitle()).contains("Selenium WebDriver");

        Results result = new AxeBuilder().analyze(driver);
        List<Rule> violations = result.getViolations();
        violations.forEach(rule -> {
            log.debug("{}", rule.toString());
        });
        Path outputFile = too.resolveOutput("testAccessibility");
        AxeReporter.writeResultsToJsonFile(outputFile.toString(), result);
    }

}
