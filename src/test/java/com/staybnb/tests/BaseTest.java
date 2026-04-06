package com.staybnb.tests;

import com.staybnb.config.DriverFactory;
import com.staybnb.config.TestConfig;
import com.staybnb.pages.LoginPage;
import com.staybnb.pages.RegisterPage;
import com.staybnb.data.Constants;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
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
import java.util.UUID;

public class BaseTest {
    protected WebDriver driver;

    @BeforeEach
    public void commonSetup() {
        if (driver == null) {
            driver = DriverFactory.createDriver();
        }
    }

    @AfterEach
    public void commonTeardown() {
        DriverFactory.quitDriver();
        driver = null;
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

    protected String registerNewUserAndLandOnHome(String emailPrefix) {
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.navigateTo();
        String uniqueEmail = emailPrefix + "_" + UUID.randomUUID().toString().replace("-", "") + "@gmail.com";
        registerPage.registerAndWaitForUrl(
                TestConfig.TEST_FIRST_NAME,
                TestConfig.TEST_LAST_NAME,
                uniqueEmail,
                TestConfig.TEST_PASSWORD,
                Constants.HOME_URL
        );
        return uniqueEmail;
    }

    protected void loginAsTestUserAndLandOnHome(LoginPage loginPage) {
        loginAsUserAndLandOnHome(loginPage, TestConfig.TEST_USER_EMAIL, TestConfig.TEST_PASSWORD);
    }

    protected void loginAsUserAndLandOnHome(LoginPage loginPage, String email, String password) {
        loginPage.navigateTo();
        loginPage.loginAndExpectSuccess(email, password);
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
