package com.staybnb.pages;

import com.staybnb.locators.Locators;
import com.staybnb.config.Constants;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegisterPage extends AuthPage {
    private By firstNameField = Locators.Register.FIRST_NAME_FIELD;
    private By lastNameField = Locators.Register.LAST_NAME_FIELD;
    private By confirmPasswordField = Locators.Register.CONFIRM_PASSWORD_FIELD;
    private By loginLink = Locators.Register.LOGIN_LINK;

    public RegisterPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected String getPageUrl() {
        return Constants.REGISTER_URL;
    }

    public void enterFirstName(String firstName) {
        type(firstNameField, firstName);
    }

    public void enterLastName(String lastName) {
        type(lastNameField, lastName);
    }

    public void enterEmail(String email) {
        type(emailField, email);
    }

    public void enterPassword(String password) {
        type(passwordField, password);
    }

    public void enterConfirmPassword(String confirmPassword) {
        type(confirmPasswordField, confirmPassword);
    }

    public void clickRegister() {
        clickPrimarySubmit();
    }

    public void clickLoginLink() {
        click(loginLink);
    }

    public void fillCompleteRegistration(String fName, String lName, String email, String pass) {
        enterFirstName(fName);
        enterLastName(lName);
        enterEmail(email);
        enterPassword(pass);
        enterConfirmPassword(pass);
    }

    public void submitRegistration(String fName, String lName, String email, String pass) {
        fillCompleteRegistration(fName, lName, email, pass);
        clickRegister();
    }

    public void registerAndWaitForUrl(String fName, String lName, String email, String pass, String expectedUrl) {
        submitRegistration(fName, lName, email, pass);
        waitForUrlToBe(expectedUrl);
    }
}
