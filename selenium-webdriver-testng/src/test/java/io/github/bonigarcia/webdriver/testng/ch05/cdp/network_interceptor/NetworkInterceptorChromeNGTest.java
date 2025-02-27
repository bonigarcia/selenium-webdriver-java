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
package io.github.bonigarcia.webdriver.testng.ch05.cdp.network_interceptor;

import static java.lang.invoke.MethodHandles.lookup;
import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.devtools.NetworkInterceptor;
import org.openqa.selenium.remote.http.Contents;
import org.openqa.selenium.remote.http.HttpResponse;
import org.openqa.selenium.remote.http.Route;
import org.slf4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class NetworkInterceptorChromeNGTest {

    static final Logger log = getLogger(lookup().lookupClass());

    WebDriver driver;

    @BeforeMethod
    public void setup() {
        driver = WebDriverManager.chromedriver().create();
    }

    @AfterMethod
    public void teardown() throws InterruptedException {
        // FIXME: pause for manual browser inspection
        Thread.sleep(Duration.ofSeconds(3).toMillis());

        driver.quit();
    }

    @Test
    public void testNetworkInterceptor() throws Exception {
        Path img = Paths
                .get(ClassLoader.getSystemResource("tools.png").toURI());
        byte[] bytes = Files.readAllBytes(img);

        try (NetworkInterceptor interceptor = new NetworkInterceptor(driver,
                Route.matching(req -> req.getUri().endsWith(".png"))
                        .to(() -> req -> new HttpResponse()
                                .setContent(Contents.bytes(bytes))))) {
            driver.get("https://bonigarcia.dev/selenium-webdriver-java/");

            int width = Integer.parseInt(driver.findElement(By.tagName("img"))
                    .getDomProperty("width"));
            assertThat(width).isGreaterThan(80);
        }
    }

}
