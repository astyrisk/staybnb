package com.staybnb.pages;

import com.staybnb.locators.Locators;
import com.staybnb.config.AppConstants;
import org.openqa.selenium.WebDriver;

public class LoginPage extends AuthPage {
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected String getPageUrl() {
        return AppConstants.LOGIN_URL;
    }

    public void login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickSubmit();
    }

    public void loginAndExpectSuccess(String email, String password) {
        login(email, password);
        waitForUrlToBe(AppConstants.HOME_URL);
    }

    public void navigateViaNavbar() {
        navbar().clickLoginAndWaitForRedirect();
    }

    public void clickRegisterLink() {
        click(Locators.Login.REGISTER_LINK);
    }
}
