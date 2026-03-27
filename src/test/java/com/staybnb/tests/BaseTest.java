package com.staybnb.tests;

import com.staybnb.utils.Constants;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BaseTest {
    protected WebDriver driver;

    @BeforeEach
    public void commonSetup() {
        if (driver == null) {
            ChromeOptions options = new ChromeOptions();
            boolean isHeadless = "true".equals(System.getenv("GITHUB_ACTIONS")) || 
                                 System.getenv("JENKINS_URL") != null ||
                                 "true".equals(System.getProperty("headless"));
            if (isHeadless) {
                options.addArguments("--headless=new");
                options.addArguments("--disable-gpu");
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-dev-shm-usage");
            }
            driver = new ChromeDriver(options);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Constants.MEDIUM_WAIT));
            driver.manage().window().maximize();
        }
    }

    @AfterEach
    public void commonTeardown() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    protected WebDriverWait getWait(int seconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(seconds));
    }

    protected void waitForUrlContains(String text) {
        getWait(Constants.MEDIUM_WAIT).until(ExpectedConditions.urlContains(text));
    }

    protected void waitForUrlToBe(String url) {
        getWait(Constants.MEDIUM_WAIT).until(ExpectedConditions.urlToBe(url));
    }

    protected boolean isUrlContains(String text) {
        try {
            waitForUrlContains(text);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @RegisterExtension
    AfterTestExecutionCallback screenshotCallback = new AfterTestExecutionCallback() {
        @Override
        public void afterTestExecution(ExtensionContext context) throws Exception {
            if (context.getExecutionException().isPresent()) {
                captureScreenshot(context.getDisplayName());
            }
        }
    };

    private void captureScreenshot(String testName) {
        if (driver != null && driver instanceof TakesScreenshot) {
            try {
                File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
                Path destDir = Paths.get("target/screenshots");
                Files.createDirectories(destDir);
                String sanitizedName = testName.replaceAll("[^a-zA-Z0-9._-]", "_");
                Path destFile = destDir.resolve(sanitizedName + "_" + timestamp + ".png");
                Files.copy(screenshot.toPath(), destFile);
                System.out.println("Test Failed! Screenshot saved to: " + destFile.toAbsolutePath());
            } catch (IOException e) {
                System.err.println("Failed to capture screenshot: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Error during screenshot capture: " + e.getMessage());
            }
        }
    }
}
