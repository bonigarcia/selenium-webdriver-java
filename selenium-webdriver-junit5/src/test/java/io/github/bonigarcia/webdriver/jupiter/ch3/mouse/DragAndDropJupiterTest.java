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
package io.github.bonigarcia.webdriver.jupiter.ch3.mouse;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import io.github.bonigarcia.wdm.WebDriverManager;

class DragAndDropJupiterTest {

    WebDriver driver;

    @BeforeEach
    void setupTest() {
        driver = WebDriverManager.chromedriver().create();
        driver.get(
                "https://bonigarcia.dev/selenium-webdriver-java/drag-and-drop.html");
    }

    @AfterEach
    void teardown() throws InterruptedException {
        // FIXME: this is an active wait to check the browser
        Thread.sleep(Duration.ofSeconds(3).toMillis());

        driver.quit();
    }

    @Test
    void testDragAndDrop() {
        WebElement draggable = driver.findElement(By.id("draggable"));
        WebElement target = driver.findElement(By.id("target"));

        new Actions(driver).dragAndDrop(draggable, target).build().perform();

        Point targetLocation = target.getLocation();
        Point finalLocation = draggable.getLocation();
        assertThat(finalLocation).isEqualTo(targetLocation);
    }

    @Test
    void testDragAndDropBy() {
        WebElement draggable = driver.findElement(By.id("draggable"));
        Point initLocation = draggable.getLocation();

        new Actions(driver).dragAndDropBy(draggable, 100, 0)
                .dragAndDropBy(draggable, 0, 100)
                .dragAndDropBy(draggable, -100, 0)
                .dragAndDropBy(draggable, 0, -100).build().perform();

        Point finalLocation = draggable.getLocation();
        assertThat(initLocation).isEqualTo(finalLocation);
    }

}
