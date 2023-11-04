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
package io.github.bonigarcia.webdriver.junit4.ch05.print;

import com.kazurayam.unittest.TestOutputOrganizer;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.webdriver.junit4.TestOutputOrganizerFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.Pdf;
import org.openqa.selenium.PrintsPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.print.PrintOptions;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThat;

public class PrintEdgeJUnit4Test {

    static TestOutputOrganizer too;

    WebDriver driver;

    @BeforeClass
    public static void setupClass() {
        too = TestOutputOrganizerFactory.create(PrintEdgeJUnit4Test.class);
    }

    @Before
    public void setup() {
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--headless");

        driver = WebDriverManager.edgedriver().capabilities(options).create();
    }

    @After
    public void teardown() {
        driver.quit();
    }

    @Test
    public void testPrint() throws IOException {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/");
        PrintsPage pg = (PrintsPage) driver;
        PrintOptions printOptions = new PrintOptions();
        Pdf pdf = pg.print(printOptions);

        String pdfBase64 = pdf.getContent();
        assertThat(pdfBase64).contains("JVBER");

        byte[] decodedImg = Base64.getDecoder()
                .decode(pdfBase64.getBytes(StandardCharsets.UTF_8));
        Path destinationFile = too.resolveOutput("my-pdf.pdf");
        Files.write(destinationFile, decodedImg);
    }

}
