package com.staybnb.tests;

import com.staybnb.config.AppConstants;
import com.staybnb.config.DriverFactory;
import com.staybnb.config.TestConfig;
import com.staybnb.extensions.ScreenshotOnFailureExtension;
import com.staybnb.pages.LoginPage;
import com.staybnb.pages.RegisterPage;
import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;

@ExtendWith({AllureJunit5.class, ScreenshotOnFailureExtension.class})
public class BaseTest {
    protected WebDriver driver;

    @BeforeEach
    public void commonSetup() {
        driver = DriverFactory.createDriver();
    }

    protected void registerNewUser() {
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.navigateViaNavbar();

        String uniqueEmail = "registerTestUser_" + System.currentTimeMillis() + "@gmail.com";

        registerPage.registerAndWaitForUrl(
            TestConfig.TEST_FIRST_NAME,
            TestConfig.TEST_LAST_NAME,
            uniqueEmail,
            TestConfig.TEST_PASSWORD,
            AppConstants.HOME_URL
        );
    }

    protected LoginPage loginAsUser() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateViaNavbar();
        loginPage.loginAndExpectSuccess(TestConfig.TEST_USER_EMAIL, TestConfig.TEST_PASSWORD);

        return loginPage;
    }
}
