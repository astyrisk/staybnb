package com.staybnb.pages;

import com.staybnb.locators.Locators;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public abstract class AuthPage extends BasePage {
    protected final By emailField = Locators.Auth.EMAIL_FIELD;
    protected final By passwordField = Locators.Auth.PASSWORD_FIELD;
    protected final By primarySubmitButton = Locators.Auth.PRIMARY_SUBMIT_BUTTON;

    protected final By inlineErrorMessages = Locators.Auth.INLINE_ERROR_MESSAGES;
    protected final By globalErrorMessage = Locators.Auth.GLOBAL_ERROR_MESSAGE;

    protected AuthPage(WebDriver driver) {
        super(driver);
    }

    protected abstract String getPageUrl();

    public void load() {
        navigateTo(getPageUrl());
    }

    public void navigateTo() {
        load();
    }

    public void enterEmail(String email) {
        type(emailField, email);
    }

    public void enterPassword(String password) {
        type(passwordField, password);
    }

    protected void clickPrimarySubmit() {
        click(primarySubmitButton);
    }

    public boolean isInlineErrorDisplayed(String expectedErrorText) {
        List<WebElement> errors = driver.findElements(inlineErrorMessages);
        String expectedLower = expectedErrorText.toLowerCase();
        return errors.stream().anyMatch(e -> e.getText().toLowerCase().contains(expectedLower));
    }

    public String getGlobalErrorMessageText() {
        return waitForElementVisible(globalErrorMessage).getText();
    }
}
