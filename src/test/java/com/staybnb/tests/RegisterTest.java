package com.staybnb.tests;

import com.staybnb.config.TestConfig;
import com.staybnb.pages.RegisterPage;
import com.staybnb.data.Constants;
import com.staybnb.assertions.ErrorMessages;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RegisterTest extends BaseTest {
    private RegisterPage registerPage;

    @BeforeEach
    public void setup() {
        registerPage = new RegisterPage(driver);
        registerPage.navigateTo();
    }

    @Test
    public void testSuccessfulRegistration() {
        String uniqueEmail = "testuser_" + System.currentTimeMillis() + "@gmail.com";

        registerPage.registerAndWaitForUrl(
                TestConfig.TEST_FIRST_NAME,
                TestConfig.TEST_LAST_NAME,
                uniqueEmail,
                TestConfig.TEST_PASSWORD,
                Constants.HOME_URL
        );
        assertEquals(Constants.HOME_URL, driver.getCurrentUrl());
    }

    @Test
    public void testRegistrationWithExistingEmail() {
        registerPage.submitRegistration(
                TestConfig.TEST_FIRST_NAME,
                TestConfig.TEST_LAST_NAME,
                TestConfig.TEST_USER_EMAIL,
                TestConfig.TEST_PASSWORD
        );

        String error = registerPage.getGlobalErrorMessageText();
        assertTrue(error.toLowerCase().contains("exists") || error.toLowerCase().contains("already"), 
                ErrorMessages.EXPECTED_EMAIL_ALREADY_EXISTS);
    }

    @Test
    public void testRegistrationBlankFields() {
        registerPage.clickRegister();
        assertTrue(registerPage.isInlineErrorDisplayed(ErrorMessages.REQUIRED));
    }
}
