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
package io.github.bonigarcia.webdriver.testng.ch03.locators_relative;

import static java.lang.invoke.MethodHandles.lookup;
import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.locators.RelativeLocator;
import org.slf4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DatePickerNGTest {

    static final Logger log = getLogger(lookup().lookupClass());

    WebDriver driver;

    @BeforeMethod
    public void setup() {
        driver = WebDriverManager.chromedriver().create();
    }

    @AfterMethod
    public void teardown() {
        driver.quit();
    }

    @Test
    public void testDatePicker() {
        driver.get(
                "https://bonigarcia.dev/selenium-webdriver-java/web-form.html");

        // Get the current date from the system clock
        LocalDate today = LocalDate.now();
        int currentYear = today.getYear();
        int currentDay = today.getDayOfMonth();

        // Click on the date picker to open the calendar
        WebElement datePicker = driver.findElement(By.name("my-date"));
        datePicker.click();

        // Click on the current month by searching by text
        WebElement monthElement = driver.findElement(By.xpath(
                String.format("//th[contains(text(),'%d')]", currentYear)));
        monthElement.click();

        // Get the element to look for with relative locator
        WebElement yearElement = driver.findElement(By.xpath(
                String.format("//th[@class='datepicker-switch' and text()='%d']", currentYear)));

        // Click on the left arrow using relative locators
        WebElement arrowLeft = driver.findElement(
                RelativeLocator.with(By.tagName("th")).toLeftOf(yearElement));
        arrowLeft.click();

        // Click on the current month of that year
        WebElement monthPastYear = driver.findElement(RelativeLocator
                .with(By.cssSelector("span[class$=focused]")).below(arrowLeft));
        monthPastYear.click();

        // Click on the present day in that month
        WebElement dayElement = driver.findElement(By.xpath(String.format(
                "//td[@class='day' and contains(text(),'%d')]", currentDay)));
        dayElement.click();

        // Get the final date on the input text
        String oneYearBack = datePicker.getDomProperty("value");
        log.debug("Final date in date picker: {}", oneYearBack);

        // Assert that the expected date is equal to the one selected in the
        // date picker
        LocalDate previousYear = today.minusYears(1);
        DateTimeFormatter dateFormat = DateTimeFormatter
                .ofPattern("MM/dd/yyyy");
        String expectedDate = previousYear.format(dateFormat);
        log.debug("Expected date: {}", expectedDate);

        assertThat(oneYearBack).isEqualTo(expectedDate);
    }

}
