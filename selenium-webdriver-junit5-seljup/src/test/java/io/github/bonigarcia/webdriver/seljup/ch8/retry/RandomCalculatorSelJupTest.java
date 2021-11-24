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
package io.github.bonigarcia.webdriver.seljup.ch8.retry;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.artsok.RepeatedIfExceptionsTest;
import io.github.bonigarcia.seljup.SeleniumJupiter;
import io.github.bonigarcia.seljup.SingleSession;

@SingleSession
@ExtendWith(SeleniumJupiter.class)
class RandomCalculatorSelJupTest {

    @RepeatedIfExceptionsTest(repeats = 5)
    void testRandomCalculator(ChromeDriver driver) {
        driver.get(
                "https://bonigarcia.dev/selenium-webdriver-java/random-calculator.html");
        // 1 + 3
        driver.findElement(By.xpath("//span[text()='1']")).click();
        driver.findElement(By.xpath("//span[text()='+']")).click();
        driver.findElement(By.xpath("//span[text()='3']")).click();
        driver.findElement(By.xpath("//span[text()='=']")).click();

        // ... should be 4
        String result = driver.findElement(By.className("screen")).getText();
        assertThat(result).isEqualTo("4");
    }

}
