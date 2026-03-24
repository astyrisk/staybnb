package com.staybnb.tests;

import com.staybnb.pages.LoginPage;
import com.staybnb.utils.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class LoginTest extends BaseTest {
    private LoginPage loginPage;

    @BeforeEach
    public void setup() {
        loginPage = new LoginPage(driver);
    }

    @Test
    public void testSuccessfulLogin() {
        loginPage.navigateTo();
        loginPage.login(Constants.VALID_EMAIL, Constants.VALID_PASSWORD);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.urlToBe(Constants.HOME_URL));
        assertEquals(Constants.HOME_URL, driver.getCurrentUrl());

        String jwt = loginPage.getStaybnbToken();
        assertNotNull(jwt, "JWT token was not retrieved after successful login.");
    }

    @Test
    public void testInvalidCredentials() {
        loginPage.navigateTo();
        loginPage.login(Constants.INVALID_EMAIL, Constants.INVALID_PASSWORD);

        String errorText = loginPage.getGlobalErrorMessageText();
        assertTrue(errorText.toLowerCase().contains("invalid") || errorText.toLowerCase().contains("unauthorized"),
                "Expected an 'invalid credentials' or 'unauthorized' error message.");
    }

    @Test
    public void testBlankFieldsValidation() {
        loginPage.navigateTo();
        loginPage.clickLoginButton();
        assertTrue(loginPage.isInlineErrorDisplayed("Email and password are required"));
    }
}
