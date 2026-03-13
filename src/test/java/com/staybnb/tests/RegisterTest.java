package com.staybnb.tests;

import com.staybnb.pages.RegisterPage;
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
        registerPage.fillCompleteRegistration("Test", "User", uniqueEmail, "password123");
        registerPage.clickRegister();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlToBe("https://qa-playground.nixdev.co/t/automation-adel"));
        assertEquals("https://qa-playground.nixdev.co/t/automation-adel", driver.getCurrentUrl());
    }

    @Test
    public void testRegistrationWithExistingEmail() {
        registerPage.navigateTo();
        registerPage.fillCompleteRegistration("Existing", "User", "heko@gmail.com", "password123");
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
