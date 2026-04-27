package com.staybnb.pages;

import com.staybnb.locators.Locators;
import com.staybnb.config.AppConstants;
import org.openqa.selenium.WebDriver;

public class LoginPage extends AuthPage {
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickSubmit();
    }

    public HomePage loginAndExpectSuccess(String email, String password) {
        login(email, password);
        waitForUrlToBe(AppConstants.HOME_URL);
        return new HomePage(driver);
    }

    public LoginPage navigateViaNavbar() {
        navbar().clickLoginAndWaitForRedirect();
        return this;
    }

    public RegisterPage clickRegisterLink() {
        click(Locators.Login.REGISTER_LINK);
        return new RegisterPage(driver);
    }
}
