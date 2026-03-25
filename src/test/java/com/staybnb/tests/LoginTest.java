package com.staybnb.tests;

import com.staybnb.config.TestConfig;
import com.staybnb.pages.LoginPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.jupiter.api.Assertions.*;

public class LoginTest extends BaseTest {
    private LoginPage loginPage;

    @BeforeEach
    public void setup() {
        loginPage = new LoginPage(driver);
    }

    @Test
    public void testSuccessfulLoginRedirection() {
        loginPage.navigateTo(TestConfig.BASE_URL + "/login");
        loginPage.login(TestConfig.TEST_USERNAME, TestConfig.TEST_PASSWORD);

        WebDriverWait wait = getWait(5);
        wait.until(ExpectedConditions.urlContains("/"));
        assertTrue(driver.getCurrentUrl().contains("/"));
    }

    @Test
    public void testSuccessfulLoginTokenRetrieved() {
        loginPage.navigateTo(TestConfig.BASE_URL + "/login");
        loginPage.login(TestConfig.TEST_USERNAME, TestConfig.TEST_PASSWORD);

        WebDriverWait wait = getWait(5);
        wait.until(ExpectedConditions.urlContains("/"));
        String jwt = loginPage.getStaybnbToken();
        assertNotNull(jwt, "JWT token was not retrieved after successful login.");
    }

    @Test
    public void testInvalidCredentials() {
        loginPage.navigateTo(TestConfig.BASE_URL + "/login");
        loginPage.login("wronguser@gmail.com", "WrongPassword123!");

        String errorText = loginPage.getGlobalErrorMessageText();
        assertTrue(errorText.toLowerCase().contains("invalid") || errorText.toLowerCase().contains("unauthorized"),
                "Expected an 'invalid credentials' or 'unauthorized' error message.");
    }

    @Test
    public void testBlankFieldsValidation() {
        loginPage.navigateTo(TestConfig.BASE_URL + "/login");
        loginPage.clickLoginButton();
        assertTrue(loginPage.isInlineErrorDisplayed("Email and password are required"));
    }
}
