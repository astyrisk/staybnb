package com.staybnb.pages;

import com.staybnb.locators.Locators;
import com.staybnb.utils.Constants;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;

public class Navbar extends BasePage {
    private final By navbarLogo = Locators.Navbar.NAVBAR_LOGO;
    private final By userMenuButton = Locators.Navbar.USER_MENU_BUTTON;
    private final By userAvatar = Locators.Navbar.USER_AVATAR;
    private final By hamburgerMenu = Locators.Navbar.HAMBURGER_MENU;

    private final By loginLink = Locators.Navbar.LOGIN_LINK;
    private final By registerLink = Locators.Navbar.REGISTER_LINK;

    private final By becomeAHost = Locators.Navbar.BECOME_A_HOST;
    private final By myProperties = Locators.Navbar.MY_PROPERTIES;

    private final By dropdownMenu = Locators.Navbar.DROPDOWN_MENU;
    private final By profileLink = Locators.Navbar.PROFILE_LINK;
    private final By logoutButton = Locators.Navbar.LOGOUT_BUTTON;

    public Navbar(WebDriver driver) {
        super(driver);
    }

    public boolean isLogoDisplayed() {
        return isDisplayed(navbarLogo);
    }

    public void clickLogo() {
        waitForElementClickable(navbarLogo).click();
    }

    public String getLogoHref() {
        return waitForElementVisible(navbarLogo).getAttribute("href");
    }

    public boolean isUserAvatarDisplayed() {
        return isDisplayed(userAvatar);
    }

    public boolean isLoginLinkDisplayed() {
        return isDisplayed(loginLink);
    }

    public boolean isRegisterLinkDisplayed() {
        return isDisplayed(registerLink);
    }

    public boolean isBecomeAHostDisplayed() {
        return isDisplayed(becomeAHost);
    }

    public boolean isMyPropertiesDisplayed() {
        return isDisplayed(myProperties);
    }

    public void clickBecomeAHost() {
        waitForElementClickable(becomeAHost).click();
    }

    public void clickMyProperties() {
        waitForElementClickable(myProperties).click();
    }

    public void clickLogin() {
        waitForElementClickable(loginLink).click();
    }

    public void clickRegister() {
        waitForElementClickable(registerLink).click();
    }

    public void openUserMenu() {
        waitForElementClickable(userMenuButton).click();
    }

    public boolean isDropdownDisplayed() {
        return isDisplayed(dropdownMenu);
    }

    public boolean isProfileLinkDisplayed() {
        return isDisplayed(profileLink);
    }

    public boolean isLogoutButtonDisplayed() {
        return isDisplayed(logoutButton);
    }

    public void clickProfile() {
        openUserMenu();
        waitForElementClickable(profileLink).click();
    }

    public void clickLogout() {
        openUserMenu();
        waitForElementClickable(logoutButton).click();
    }

    public void clickProfileAndWaitForRedirect() {
        clickProfile();
        waitForUrlContains(Constants.PROFILE_URL);
    }

    public void clickLogoutAndWaitForRedirectToHome() {
        clickLogout();
        waitForUrlToBe(Constants.HOME_URL);
    }

    public void clickLoginAndWaitForRedirect() {
        clickLogin();
        waitForUrlContains(Constants.LOGIN_URL);
    }

    public void clickRegisterAndWaitForRedirect() {
        clickRegister();
        waitForUrlContains(Constants.REGISTER_URL);
    }

    public boolean isHamburgerMenuDisplayed() {
        return isDisplayed(hamburgerMenu);
    }

    public void setMobileLayout() {
        driver.manage().window().setSize(new Dimension(Constants.MOBILE_WIDTH, 812));
    }

    public void setDesktopLayout() {
        driver.manage().window().maximize();
    }
}
