package com.staybnb.pages;

import com.staybnb.locators.Locators;
import com.staybnb.config.AppConstants;
import org.openqa.selenium.WebDriver;

public class RegisterPage extends AuthPage {
    public RegisterPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected String getPageUrl() {
        return AppConstants.REGISTER_URL;
    }

    public void enterFirstName(String firstName) {
        type(Locators.Register.FIRST_NAME_FIELD, firstName);
    }

    public void enterLastName(String lastName) {
        type(Locators.Register.LAST_NAME_FIELD, lastName);
    }

    public void enterConfirmPassword(String confirmPassword) {
        type(Locators.Register.CONFIRM_PASSWORD_FIELD, confirmPassword);
    }

    public void clickRegister() {
        clickPrimarySubmit();
    }

    public void clickLoginLink() {
        click(Locators.Register.LOGIN_LINK);
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

    public void navigateViaNavbar() {
        navbar().clickRegisterAndWaitForRedirect();
    }

    public void submitRegistrationWithShortPassword(String fName, String lName, String email) {
        enterFirstName(fName);
        enterLastName(lName);
        enterEmail(email);
        enterPassword("short12"); // 7 chars — below the 8-character minimum
        enterConfirmPassword("short12");
        clickRegister();
    }

    public void submitRegistrationWithMismatchedPasswords(String fName, String lName, String email, String pass) {
        enterFirstName(fName);
        enterLastName(lName);
        enterEmail(email);
        enterPassword(pass);
        enterConfirmPassword(pass + "DIFF");
        clickRegister();
    }

    public void registerAndWaitForUrl(String fName, String lName, String email, String pass, String expectedUrl) {
        submitRegistration(fName, lName, email, pass);
        waitForUrlToBe(expectedUrl);
    }
}
