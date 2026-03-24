package com.staybnb.tests;

import com.staybnb.pages.LoginPage;
import com.staybnb.pages.LogoutPage;
import com.staybnb.utils.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class LogoutTest extends BaseTest {
    private LoginPage loginPage;
    private LogoutPage logoutPage;
    private final String DASHBOARD_URL = Constants.HOME_URL;

    @BeforeEach
    public void setup() {
        loginPage = new LoginPage(driver);
        logoutPage = new LogoutPage(driver);
    }

    private void loginAsValidUser() {
        loginPage.navigateTo();
        loginPage.login(Constants.VALID_EMAIL, Constants.VALID_PASSWORD);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlToBe(DASHBOARD_URL));
    }

    @Test
    public void testLogoutRedirectionToHomepage() {
        loginAsValidUser();
        logoutPage.logout();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlToBe(DASHBOARD_URL));
        assertEquals(DASHBOARD_URL, driver.getCurrentUrl(), "Should be redirected to homepage after logout.");
    }

    @Test
    public void testLogoutBnbTokenRemoval() {
        loginAsValidUser();
        String tokenBefore = logoutPage.getStaybnbToken();
        assertNotNull(tokenBefore, "JWT token should exist after login.");
        logoutPage.logout();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(d -> logoutPage.getStaybnbToken() == null);
        String tokenAfter = logoutPage.getStaybnbToken();
        assertNull(tokenAfter, "JWT token should be removed from localStorage after logout.");
    }
}
