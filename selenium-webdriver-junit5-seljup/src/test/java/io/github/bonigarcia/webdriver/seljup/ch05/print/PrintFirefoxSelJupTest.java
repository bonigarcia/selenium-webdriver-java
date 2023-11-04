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
package io.github.bonigarcia.webdriver.seljup.ch05.print;

import com.kazurayam.unittest.TestOutputOrganizer;
import io.github.bonigarcia.seljup.SeleniumJupiter;
import io.github.bonigarcia.webdriver.seljup.TestOutputOrganizerFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.Pdf;
import org.openqa.selenium.PrintsPage;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.print.PrintOptions;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SeleniumJupiter.class)
class PrintFirefoxSelJupTest {

    static TestOutputOrganizer too;

    @BeforeAll
    static void setupClass() {
        too = TestOutputOrganizerFactory.create(PrintFirefoxSelJupTest.class);
    }

    @Test
    void testPrint(FirefoxDriver driver) throws IOException {
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
