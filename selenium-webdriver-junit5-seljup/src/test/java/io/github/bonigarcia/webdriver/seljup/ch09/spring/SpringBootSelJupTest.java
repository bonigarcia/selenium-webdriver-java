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
package io.github.bonigarcia.webdriver.seljup.ch09.spring;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import io.github.bonigarcia.webdriver.SpringBootDemoApp;

@ExtendWith({ SeleniumJupiter.class, SpringExtension.class })
@SpringBootTest(classes = SpringBootDemoApp.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpringBootSelJupTest {

    @LocalServerPort
    protected int serverPort;

    @Test
    void testSpringBoot(ChromeDriver driver) {
        driver.get("http://localhost:" + serverPort);
        String bodyText = driver.findElement(By.tagName("body")).getText();
        assertThat(bodyText)
                .contains("This is a local site served by Spring-Boot");
    }

}
