package com.staybnb.pages;

import com.staybnb.locators.Locators;
import org.openqa.selenium.WebDriver;

public class RegisterPage extends AuthPage {
    public RegisterPage(WebDriver driver) {
        super(driver);
    }

    public void fillAndSubmitRegistration(String fName, String lName, String email, String pass, String confirmPass) {
        enterFirstName(fName);
        enterLastName(lName);
        enterEmail(email);
        enterPassword(pass);
        enterConfirmPassword(confirmPass);
        clickSubmit();
    }

    public LoginPage clickLoginLink() {
        click(Locators.Register.LOGIN_LINK);
        return new LoginPage(driver);
    }

    public RegisterPage navigateViaNavbar() {
        navbar().clickRegisterAndWaitForRedirect();
        return this;
    }

    public void registerAndWaitForUrl(String fName, String lName, String email, String pass, String expectedUrl) {
        fillAndSubmitRegistration(fName, lName, email, pass, pass);
        waitForUrlToBe(expectedUrl);
    }
}
