package com.staybnb.tests;

import com.staybnb.config.DriverFactory;
import com.staybnb.config.TestConfig;
import com.staybnb.pages.LoginPage;
import com.staybnb.pages.RegisterPage;
import com.staybnb.data.Constants;
import io.qameta.allure.Allure;
import io.qameta.allure.junit5.AllureJunit5;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.ByteArrayInputStream;
import java.time.Duration;
import java.util.UUID;

@ExtendWith(AllureJunit5.class)
public class BaseTest {
    private static final Logger log = LogManager.getLogger(BaseTest.class);
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
        public void afterTestExecution(ExtensionContext context) {
            if (context.getExecutionException().isPresent()) {
                attachScreenshotToAllure(context.getDisplayName());
            }
        }
    };

    private void attachScreenshotToAllure(String testName) {
        if (driver instanceof TakesScreenshot ts) {
            try {
                byte[] screenshotBytes = ts.getScreenshotAs(OutputType.BYTES);
                Allure.addAttachment(
                        testName + " - Failure Screenshot",
                        "image/png",
                        new ByteArrayInputStream(screenshotBytes),
                        ".png"
                );
                log.info("Screenshot attached to Allure report for: {}", testName);
            } catch (Exception e) {
                log.error("Error during screenshot attachment: {}", e.getMessage());
            }
        }
    }
}
