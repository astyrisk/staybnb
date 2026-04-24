package com.staybnb.pages;

import com.staybnb.locators.Locators;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public abstract class AuthPage extends BasePage {
    protected AuthPage(WebDriver driver) {
        super(driver);
    }

    protected abstract String getPageUrl();

    public void navigateTo() {
        super.navigateTo(getPageUrl());
    }

    public void enterEmail(String email) {
        type(Locators.Auth.EMAIL_FIELD, email);
    }

    public void enterPassword(String password) {
        type(Locators.Auth.PASSWORD_FIELD, password);
    }

    public void clickPrimarySubmit() {
        click(Locators.Auth.PRIMARY_SUBMIT_BUTTON);
    }

    public boolean isInlineErrorDisplayed(String expectedErrorText) {
        List<WebElement> errors = driver.findElements(Locators.Auth.INLINE_ERROR_MESSAGES);
        String expectedLower = expectedErrorText.toLowerCase();
        return errors.stream().anyMatch(e -> e.getText().toLowerCase().contains(expectedLower));
    }

    public String getGlobalErrorMessageText() {
        return waitForElementVisible(Locators.Auth.GLOBAL_ERROR_MESSAGE).getText();
    }

    public String getInlineErrorMessageText() {
        return waitForElementVisible(Locators.Auth.INLINE_ERROR_MESSAGES).getText();
    }
}
