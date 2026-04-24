package com.staybnb.components;

import com.staybnb.config.AppConstants;
import com.staybnb.config.WaitConstants;
import com.staybnb.locators.Locators;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;


public class Navbar extends BaseComponent {
    private static final String SET_DATE_INPUT_JS = "com/staybnb/scripts/setDateInput.js";

    public Navbar(WebDriver driver) {
        super(driver);
    }

    public boolean isLogoDisplayed() {
        return isDisplayed(Locators.Navbar.NAVBAR_LOGO);
    }

    public boolean isUserAvatarDisplayed() {
        return isDisplayed(Locators.Navbar.USER_AVATAR);
    }

    public boolean isLoginLinkDisplayed() {
        return isDisplayed(Locators.Navbar.LOGIN_LINK);
    }

    public boolean isRegisterLinkDisplayed() {
        return isDisplayed(Locators.Navbar.REGISTER_LINK);
    }

    public boolean isBecomeAHostDisplayed() {
        return isDisplayed(Locators.Navbar.BECOME_A_HOST);
    }

    public boolean isHostDashboardDisplayed() {
        return isDisplayed(Locators.Navbar.HOST_DASHBOARD);
    }

    public void clickBecomeAHost() {
        waitForElementClickable(Locators.Navbar.BECOME_A_HOST).click();
    }

    public void clickHostDashboard() {
        openUserMenu();
        waitForElementClickable(Locators.Navbar.HOST_DASHBOARD).click();
    }

    public void clickLogoAndWaitForHome() {
        waitForElementClickable(Locators.Navbar.NAVBAR_LOGO).click();
        waitForUrlToBe(AppConstants.HOME_URL);
    }

    public void openUserMenu() {
        waitForElementClickable(Locators.Navbar.USER_MENU_BUTTON).click();
    }

    public boolean isDropdownDisplayed() {
        return isDisplayed(Locators.Navbar.DROPDOWN_MENU);
    }

    public boolean isProfileLinkDisplayed() {
        return isDisplayed(Locators.Navbar.PROFILE_LINK);
    }

    public boolean isWishlistsLinkDisplayed() {
        return isDisplayed(Locators.Navbar.WISHLISTS_LINK);
    }

    public boolean isLogoutButtonDisplayed() {
        return isDisplayed(Locators.Navbar.LOGOUT_BUTTON);
    }

    public void clickProfile() {
        openUserMenu();
        waitForElementClickable(Locators.Navbar.PROFILE_LINK).click();
    }

    public void clickWishlists() {
        openUserMenu();
        waitForElementClickable(Locators.Navbar.WISHLISTS_LINK).click();
    }

    public void clickWishlistsAndWaitForRedirect() {
        clickWishlists();
        waitForUrlToBe(AppConstants.WISHLIST_URL);
    }

    public void clickLogout() {
        openUserMenu();
        waitForElementClickable(Locators.Navbar.LOGOUT_BUTTON).click();
    }

    public void clickProfileAndWaitForRedirect() {
        clickProfile();
        waitForUrlToBe(AppConstants.PROFILE_URL);
    }

    public void clickLogoutAndWaitForRedirectToHome() {
        clickLogout();
        waitForUrlToBe(AppConstants.HOME_URL);
    }

    public void clickLoginAndWaitForRedirect() {
        waitForElementClickable(Locators.Navbar.LOGIN_LINK).click();
        waitForUrlToBe(AppConstants.LOGIN_URL);
    }

    public void clickRegisterAndWaitForRedirect() {
        waitForElementClickable(Locators.Navbar.REGISTER_LINK).click();
        waitForUrlToBe(AppConstants.REGISTER_URL);
    }

    public boolean isHamburgerMenuDisplayed() {
        return isDisplayed(Locators.Navbar.HAMBURGER_MENU);
    }

    public boolean isAuthenticatedNavbarCheckMet(String checkName) {
        return switch (checkName) {
            case "logo displayed" -> isLogoDisplayed();
            case "user avatar displayed" -> isUserAvatarDisplayed();
            case "login link hidden" -> !isLoginLinkDisplayed();
            case "wishlists link displayed" -> { openUserMenu(); yield isWishlistsLinkDisplayed(); }
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
        driver.manage().window().setSize(new Dimension(WaitConstants.MOBILE_WIDTH, 812));
    }

    public void setDesktopLayout() {
        driver.manage().window().maximize();
    }

    public void clickCompactSearchBar() {
        waitForElementClickable(Locators.SearchBar.COMPACT_SEARCH_BTN).click();
    }

    public boolean isSearchFormExpanded() {
        return isDisplayed(Locators.SearchBar.EXPANDED_FORM);
    }

    public boolean isCompactSearchBarDisplayed() {
        return isDisplayed(Locators.SearchBar.COMPACT_SEARCH_BTN);
    }

    public void enterDestination(String city) {
        WebElement input = waitForElementVisible(Locators.SearchBar.DESTINATION_INPUT);
        input.clear();
        input.sendKeys(city);
    }

    public void clickSearch() {
        waitForElementClickable(Locators.SearchBar.SEARCH_SUBMIT_BTN).click();
    }

    public void searchForCity(String city) {
        clickCompactSearchBar();
        enterDestination(city);
        clickSearch();
        waitForUrlContains("city=");
    }

    public void setCheckInDate(String isoDate) {
        setDateInput(Locators.SearchBar.CHECK_IN_INPUT, isoDate);
    }

    public void setCheckOutDate(String isoDate) {
        setDateInput(Locators.SearchBar.CHECK_OUT_INPUT, isoDate);
    }

    public String getCheckInMinAttribute() {
        return waitForElementVisible(Locators.SearchBar.CHECK_IN_INPUT).getAttribute("min");
    }

    public String getCheckOutMinAttribute() {
        return waitForElementVisible(Locators.SearchBar.CHECK_OUT_INPUT).getAttribute("min");
    }

    public void waitForCheckOutMinToBe(String expectedMin) {
        wait.until(ExpectedConditions.attributeToBe(Locators.SearchBar.CHECK_OUT_INPUT, "min", expectedMin));
    }

    public int getGuestsCount() {
        return Integer.parseInt(waitForElementVisible(Locators.SearchBar.GUESTS_DISPLAY).getText());
    }

    public void incrementGuests(int times) {
        for (int i = 0; i < times; i++) {
            waitForElementClickable(Locators.SearchBar.GUESTS_INCREMENT_BTN).click();
        }
    }

    public void decrementGuests(int times) {
        for (int i = 0; i < times; i++) {
            waitForElementClickable(Locators.SearchBar.GUESTS_DECREMENT_BTN).click();
        }
    }

    public void searchWithDates(String checkIn, String checkOut) {
        clickCompactSearchBar();
        setCheckInDate(checkIn);
        setCheckOutDate(checkOut);
        clickSearch();
        waitForUrlContains("checkIn=");
    }

    public void searchWithGuests(int targetGuests) {
        clickCompactSearchBar();
        int current = getGuestsCount();
        if (targetGuests > current) {
            incrementGuests(targetGuests - current);
        } else if (targetGuests < current) {
            decrementGuests(current - targetGuests);
        }
        clickSearch();
        waitForUrlContains("guests=");
    }

    private void setDateInput(By locator, String isoDate) {
        WebElement input = waitForElementVisible(locator);
        String script = loadScript(SET_DATE_INPUT_JS);
        ((JavascriptExecutor) driver).executeScript(script, input, isoDate);
    }
}
