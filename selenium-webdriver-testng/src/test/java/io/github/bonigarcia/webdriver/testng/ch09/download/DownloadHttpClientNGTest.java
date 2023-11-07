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
package io.github.bonigarcia.webdriver.testng.ch09.download;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;

import com.kazurayam.unittest.TestOutputOrganizer;
import io.github.bonigarcia.webdriver.testng.TestOutputOrganizerFactory;
import org.apache.commons.io.FileUtils;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpUriRequestBase;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.HttpException;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import java.nio.file.Path;

public class DownloadHttpClientNGTest {

    static TestOutputOrganizer too;

    WebDriver driver;

    Path targetFolder;

    @BeforeClass
    static void setupClass() {
        too = TestOutputOrganizerFactory.create(DownloadHttpClientNGTest.class);
    }

    @BeforeMethod
    public void setup() {
        targetFolder = too.getOutputSubDirectory();
        driver = WebDriverManager.chromedriver().create();
    }

    @AfterMethod
    public void teardown() {
        driver.quit();
    }

    @Test
    public void testDownloadHttpClient() throws IOException {
        driver.get(
                "https://bonigarcia.dev/selenium-webdriver-java/download.html");

        WebElement pngLink = driver.findElement(By.xpath("(//a)[2]"));
        File pngFile = targetFolder.resolve("webdrivermanager.png").toFile();
        download(pngLink.getAttribute("href"), pngFile);
        assertThat(pngFile).exists();

        WebElement pdfLink = driver.findElement(By.xpath("(//a)[3]"));
        File pdfFile = targetFolder.resolve( "webdrivermanager.pdf").toFile();
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
