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
package io.github.bonigarcia.webdriver.testng.ch04.history;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import org.testng.annotations.*;
import io.github.bonigarcia.webdriver.HelperClass.TestSetup;

public class HistoryNGTest extends TestSetup {

    @Test
    public void testNavigationHistory() {
        String baseUrl = "https://bonigarcia.dev/selenium-webdriver-java/";
        String firstPageUrl = baseUrl + "navigation1.html";
        String secondPageUrl = baseUrl + "navigation2.html";
        String thirdPageUrl = baseUrl + "navigation3.html";

        navigateTo(firstPageUrl);
        navigateTo(secondPageUrl);
        navigateTo(thirdPageUrl);
        navigateBack();
        navigateForward();
        refreshPage();

        assertThat(driver.getCurrentUrl()).isEqualTo(thirdPageUrl);
    }

    private void navigateTo(String url) {
        driver.navigate().to(url);
    }

    private void navigateBack() {
        driver.navigate().back();
    }

    private void navigateForward() {
        driver.navigate().forward();
    }

    private void refreshPage() {
        driver.navigate().refresh();
    }

}
