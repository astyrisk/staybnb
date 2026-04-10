package com.staybnb.components;

import com.staybnb.config.AppConstants;
import com.staybnb.locators.Locators;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Navbar extends BaseComponent {
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

    private final By compactSearchBtn = Locators.SearchBar.COMPACT_SEARCH_BTN;
    private final By expandedSearchForm = Locators.SearchBar.EXPANDED_FORM;
    private final By destinationInput = Locators.SearchBar.DESTINATION_INPUT;
    private final By searchSubmitBtn = Locators.SearchBar.SEARCH_SUBMIT_BTN;

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
        waitForUrlContains(AppConstants.PROFILE_URL);
    }

    public void clickLogoutAndWaitForRedirectToHome() {
        clickLogout();
        waitForUrlToBe(AppConstants.HOME_URL);
    }

    public void clickLoginAndWaitForRedirect() {
        clickLogin();
        waitForUrlContains(AppConstants.LOGIN_URL);
    }

    public void clickRegisterAndWaitForRedirect() {
        clickRegister();
        waitForUrlContains(AppConstants.REGISTER_URL);
    }

    public boolean isHamburgerMenuDisplayed() {
        return isDisplayed(hamburgerMenu);
    }

    public boolean isAuthenticatedNavbarCheckMet(String checkName) {
        return switch (checkName) {
            case "logo displayed" -> isLogoDisplayed();
            case "user avatar displayed" -> isUserAvatarDisplayed();
            case "login link hidden" -> !isLoginLinkDisplayed();
            default -> throw new IllegalArgumentException("Unsupported authenticated navbar check: " + checkName);
        };
    }

    public boolean isVisitorNavbarCheckMet(String checkName) {
        return switch (checkName) {
            case "logo displayed" -> isLogoDisplayed();
            case "login link displayed" -> isLoginLinkDisplayed();
            case "register link displayed" -> isRegisterLinkDisplayed();
            case "user avatar hidden" -> !isUserAvatarDisplayed();
            default -> throw new IllegalArgumentException("Unsupported visitor navbar check: " + checkName);
        };
    }

    public void setMobileLayout() {
        driver.manage().window().setSize(new Dimension(AppConstants.MOBILE_WIDTH, 812));
    }

    public void setDesktopLayout() {
        driver.manage().window().maximize();
    }

    public void clickCompactSearchBar() {
        waitForElementClickable(compactSearchBtn).click();
    }

    public boolean isSearchFormExpanded() {
        return isDisplayed(expandedSearchForm);
    }

    public boolean isCompactSearchBarDisplayed() {
        return isDisplayed(compactSearchBtn);
    }

    public void enterDestination(String city) {
        WebElement input = waitForElementVisible(destinationInput);
        input.clear();
        input.sendKeys(city);
    }

    public void clickSearch() {
        waitForElementClickable(searchSubmitBtn).click();
    }

    public void searchForCity(String city) {
        clickCompactSearchBar();
        enterDestination(city);
        clickSearch();
        waitForUrlContains("city=");
    }
}
