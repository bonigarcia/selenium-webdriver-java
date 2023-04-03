package io.github.bonigarcia.webdriver.testng.ch04.dialogs;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestSetup {

    public WebDriver driver;

    @BeforeClass
    public void initializeWebDriverManager() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeMethod
    public void initializeWebDriver() {
        driver = WebDriverManager.chromedriver().create();
    }

    @AfterMethod
    public void closeWebDriver() {
        try {
            Thread.sleep(Duration.ofSeconds(3).toMillis());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.quit();
    }

    @AfterClass
    public void teardownClass() {
        WebDriverManager.chromedriver().clearCache();
    }
}
