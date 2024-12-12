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
package io.github.bonigarcia.webdriver.seljup.ch05.cdp;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v131.network.Network;
import org.openqa.selenium.devtools.v131.network.model.Headers;

import io.github.bonigarcia.seljup.SeleniumJupiter;

@ExtendWith(SeleniumJupiter.class)
class ExtraHeadersSelJupTest {

    @Test
    void testExtraHeaders(ChromeDriver driver, DevTools devTools) {
        devTools.send(Network.enable(Optional.empty(), Optional.empty(),
                Optional.empty()));

        String userName = "guest";
        String password = "guest";
        Map<String, Object> headers = new HashMap<>();
        String basicAuth = "Basic " + new String(Base64.getEncoder()
                .encode(String.format("%s:%s", userName, password).getBytes()));
        headers.put("Authorization", basicAuth);
        devTools.send(Network.setExtraHTTPHeaders(new Headers(headers)));

        driver.get("https://jigsaw.w3.org/HTTP/Basic/");
        String bodyText = driver.findElement(By.tagName("body")).getText();
        assertThat(bodyText).contains("Your browser made it!");
    }
}
