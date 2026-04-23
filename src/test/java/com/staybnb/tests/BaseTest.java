package com.staybnb.tests;

import com.staybnb.config.AppConstants;
import com.staybnb.config.DriverFactory;
import com.staybnb.config.TestConfig;
import com.staybnb.extensions.ScreenshotOnFailureExtension;
import com.staybnb.pages.LoginPage;
import com.staybnb.pages.RegisterPage;
import io.qameta.allure.junit5.AllureJunit5;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.UUID;

@ExtendWith({AllureJunit5.class, ScreenshotOnFailureExtension.class})
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

    protected WebDriverWait getWait() {
        return new WebDriverWait(driver, Duration.ofSeconds(AppConstants.MEDIUM_WAIT));
    }

    protected void waitForUrlContains(String text) {
        getWait().until(ExpectedConditions.urlContains(text));
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
        registerPage.navigateViaNavbar();
        String uniqueEmail = emailPrefix + "_" + UUID.randomUUID().toString().replace("-", "") + "@gmail.com";
        registerPage.registerAndWaitForUrl(
                TestConfig.TEST_FIRST_NAME,
                TestConfig.TEST_LAST_NAME,
                uniqueEmail,
                TestConfig.TEST_PASSWORD,
                AppConstants.HOME_URL
        );
        return uniqueEmail;
    }

    protected void loginAsTestUserAndLandOnHome(LoginPage loginPage) {
        loginAsUserAndLandOnHome(loginPage);
    }

    protected void loginAsUserAndLandOnHome(LoginPage loginPage) {
        driver.get(AppConstants.HOME_URL);
        loginPage.navbar().clickLoginAndWaitForRedirect();
        loginPage.loginAndExpectSuccess(TestConfig.TEST_USER_EMAIL, TestConfig.TEST_PASSWORD);
    }

}
