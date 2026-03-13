package com.staybnb.tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

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
            driver = new ChromeDriver();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
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
