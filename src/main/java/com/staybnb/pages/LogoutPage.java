package com.staybnb.pages;

import com.staybnb.locators.Locators;
import org.openqa.selenium.WebDriver;
import com.staybnb.config.AppConstants;

public class LogoutPage extends BasePage {
    public LogoutPage(WebDriver driver) {
        super(driver);
    }

    public void openUserMenu() {
        waitForElementClickable(Locators.Logout.USER_MENU_BUTTON).click();
    }

    public void clickLogout() {
        waitForElementClickable(Locators.Logout.LOGOUT_BUTTON).click();
    }

    public void logout() {
        openUserMenu();
        clickLogout();
        wait.until(d -> getStaybnbToken() == null);
        waitForUrlToBe(AppConstants.HOME_URL);
    }
}
