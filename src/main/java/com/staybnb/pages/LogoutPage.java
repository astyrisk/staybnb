package com.staybnb.pages;

import com.staybnb.locators.Locators;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import com.staybnb.config.AppConstants;

public class LogoutPage extends BasePage {
    private final By userMenuButton = Locators.Logout.USER_MENU_BUTTON;
    private final By logoutButton = Locators.Logout.LOGOUT_BUTTON;

    public LogoutPage(WebDriver driver) {
        super(driver);
    }

    public void navigateTo() {
        super.navigateTo(AppConstants.HOME_URL);
    }

    public void openUserMenu() {
        waitForElementClickable(userMenuButton).click();
    }

    public void clickLogout() {
        waitForElementClickable(logoutButton).click();
    }

    public void logout() {
        openUserMenu();
        clickLogout();
    }

    public void logoutAndWaitForRedirectToHome() {
        logout();
        waitForUrlToBe(AppConstants.HOME_URL);
    }

    public void logoutAndWaitForTokenCleared() {
        logout();
        wait.until(d -> getStaybnbToken() == null);
    }
}
