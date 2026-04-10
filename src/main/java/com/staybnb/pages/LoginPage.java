package com.staybnb.pages;

import com.staybnb.locators.Locators;
import com.staybnb.config.AppConstants;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends AuthPage {
    private By registerLink = Locators.Login.REGISTER_LINK;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected String getPageUrl() {
        return AppConstants.LOGIN_URL;
    }

    public void clickLoginButton() {
        clickPrimarySubmit();
    }

    public void login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickLoginButton();
    }

    public void loginAndExpectSuccess(String email, String password) {
        login(email, password);
        waitForUrlToBe(AppConstants.HOME_URL);
    }

    public void navigateViaNavbar() {
        navigateTo(AppConstants.HOME_URL);
        navbar().clickLoginAndWaitForRedirect();
    }

    public void clickRegisterLink() {
        click(registerLink);
    }
}
