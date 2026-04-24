package com.staybnb.pages;

import com.staybnb.locators.Locators;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.stream.Collectors;

public abstract class AuthPage extends BasePage {
    protected AuthPage(WebDriver driver) {
        super(driver);
    }

    public void enterFirstName(String firstName) {
        type(Locators.Register.FIRST_NAME_FIELD, firstName);
    }

    public void enterLastName(String lastName) {
        type(Locators.Register.LAST_NAME_FIELD, lastName);
    }

    public void enterEmail(String email) {
        type(Locators.Auth.EMAIL_FIELD, email);
    }

    public void enterPassword(String password) {
        type(Locators.Auth.PASSWORD_FIELD, password);
    }

    public void enterConfirmPassword(String confirmPassword) {
        type(Locators.Register.CONFIRM_PASSWORD_FIELD, confirmPassword);
    }

    public void clickSubmit() {
        click(Locators.Auth.PRIMARY_SUBMIT_BUTTON);
    }

    public String getInlineErrorMessageText() {
        waitForElementVisible(Locators.Auth.INLINE_ERROR_MESSAGES);
        return driver.findElements(Locators.Auth.INLINE_ERROR_MESSAGES)
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.joining(" "));
    }
}
