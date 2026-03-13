package com.staybnb.tests;

import com.staybnb.pages.LoginPage;
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
        loginPage.login("heko@gmail.com", "heko0109");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.urlToBe("https://qa-playground.nixdev.co/t/automation-adel"));
        assertEquals("https://qa-playground.nixdev.co/t/automation-adel", driver.getCurrentUrl());

        String jwt = loginPage.getStaybnbToken();
        assertNotNull(jwt, "JWT token was not retrieved after successful login.");
    }

    @Test
    public void testInvalidCredentials() {
        loginPage.navigateTo();
        loginPage.login("wronguser@gmail.com", "WrongPassword123!");

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
