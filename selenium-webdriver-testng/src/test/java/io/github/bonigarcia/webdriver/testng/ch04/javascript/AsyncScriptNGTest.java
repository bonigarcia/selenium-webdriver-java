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
package io.github.bonigarcia.webdriver.testng.ch04.javascript;

import static java.lang.invoke.MethodHandles.lookup;
import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;
import org.testng.annotations.*;
import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;

import io.github.bonigarcia.webdriver.HelperClass.TestSetup;

public class AsyncScriptNGTest extends TestSetup {

    static final Logger log = getLogger(lookup().lookupClass());

    @Test
    public void testAsyncScript() {
        String url = "https://bonigarcia.dev/selenium-webdriver-java/";
        driver.get(url);
        JavascriptExecutor js = (JavascriptExecutor) driver;

        Duration pause = Duration.ofSeconds(2);
        String script = "const callback = arguments[arguments.length - 1];"
                + "window.setTimeout(callback, " + pause.toMillis() + ");";

        long start = System.currentTimeMillis();
        js.executeAsyncScript(script);
        Duration elapsed = Duration.ofMillis(System.currentTimeMillis() - start);

        assertThat(elapsed).isGreaterThanOrEqualTo(pause);
        log.debug("Script execution time: {} ms", elapsed.toMillis());
    }

}
