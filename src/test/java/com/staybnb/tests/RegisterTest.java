package com.staybnb.tests;

import com.staybnb.pages.RegisterPage;
import com.staybnb.utils.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class RegisterTest extends BaseTest {
    private RegisterPage registerPage;

    @BeforeEach
    public void setup() {
        registerPage = new RegisterPage(driver);
    }

    @Test
    public void testSuccessfulRegistration() {
        String uniqueEmail = "testuser_" + System.currentTimeMillis() + "@gmail.com";
        registerPage.navigateTo();
        registerPage.fillCompleteRegistration(Constants.TEST_USER_FIRST_NAME, Constants.TEST_USER_LAST_NAME, uniqueEmail, Constants.DEFAULT_PASSWORD);
        registerPage.clickRegister();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Constants.MEDIUM_WAIT));
        wait.until(ExpectedConditions.urlToBe(Constants.HOME_URL));
        assertEquals(Constants.HOME_URL, driver.getCurrentUrl());
    }

    @Test
    public void testRegistrationWithExistingEmail() {
        registerPage.navigateTo();
        registerPage.fillCompleteRegistration("Existing", "User", Constants.VALID_EMAIL, Constants.DEFAULT_PASSWORD);
        registerPage.clickRegister();

        String error = registerPage.getGlobalErrorMessageText();
        assertTrue(error.toLowerCase().contains("exists") || error.toLowerCase().contains("already"), 
                "Expected 'email already exists' error message.");
    }

    @Test
    public void testRegistrationBlankFields() {
        registerPage.navigateTo();
        registerPage.clickRegister();

        assertTrue(registerPage.isInlineErrorDisplayed("required"));
    }
}
