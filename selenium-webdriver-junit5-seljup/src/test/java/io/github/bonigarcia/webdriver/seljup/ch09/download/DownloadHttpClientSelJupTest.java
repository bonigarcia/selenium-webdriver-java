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
package io.github.bonigarcia.webdriver.seljup.ch09.download;

import com.kazurayam.unittest.TestOutputOrganizer;
import io.github.bonigarcia.seljup.SeleniumJupiter;
import io.github.bonigarcia.webdriver.seljup.TestOutputOrganizerFactory;
import org.apache.commons.io.FileUtils;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpUriRequestBase;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.HttpException;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SeleniumJupiter.class)
class DownloadHttpClientSelJupTest {

    static TestOutputOrganizer too;

    Path targetFolder;

    @BeforeAll
    static void setupClass() {
        too = TestOutputOrganizerFactory.create(DownloadHttpClientSelJupTest.class);
    }

    @BeforeEach
    public void setup() {
        targetFolder = too.getOutputSubDirectory();
    }

    @Test
    void testDownloadHttpClient(ChromeDriver driver) throws IOException {
        driver.get(
                "https://bonigarcia.dev/selenium-webdriver-java/download.html");

        WebElement pngLink = driver.findElement(By.xpath("(//a)[2]"));
        File pngFile = targetFolder.resolve("webdrivermanager.png").toFile();
        download(pngLink.getAttribute("href"), pngFile);
        assertThat(pngFile).exists();

        WebElement pdfLink = driver.findElement(By.xpath("(//a)[3]"));
        File pdfFile = targetFolder.resolve("webdrivermanager.pdf").toFile();
        download(pdfLink.getAttribute("href"), pdfFile);
        assertThat(pdfFile).exists();
    }

    void download(String link, File destination) throws IOException {
        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
            HttpUriRequestBase request = new HttpGet(link);

            client.execute(request, new HttpClientResponseHandler<String>() {
                @Override
                public String handleResponse(ClassicHttpResponse response)
                        throws HttpException, IOException {
                    FileUtils.copyInputStreamToFile(
                            response.getEntity().getContent(), destination);
                    return null;
                }
            });
        }
    }
}
