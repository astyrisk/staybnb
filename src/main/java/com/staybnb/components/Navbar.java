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
    private final By navbarLogo = Locators.Navbar.NAVBAR_LOGO;
    private final By userMenuButton = Locators.Navbar.USER_MENU_BUTTON;
    private final By userAvatar = Locators.Navbar.USER_AVATAR;
    private final By hamburgerMenu = Locators.Navbar.HAMBURGER_MENU;

    private final By loginLink = Locators.Navbar.LOGIN_LINK;
    private final By registerLink = Locators.Navbar.REGISTER_LINK;

    private final By becomeAHost = Locators.Navbar.BECOME_A_HOST;
    private final By hostDashboard = Locators.Navbar.HOST_DASHBOARD;

    private final By dropdownMenu = Locators.Navbar.DROPDOWN_MENU;
    private final By profileLink = Locators.Navbar.PROFILE_LINK;
    private final By wishlistsLink = Locators.Navbar.WISHLISTS_LINK;
    private final By logoutButton = Locators.Navbar.LOGOUT_BUTTON;

    private final By compactSearchBtn = Locators.SearchBar.COMPACT_SEARCH_BTN;
    private final By expandedSearchForm = Locators.SearchBar.EXPANDED_FORM;
    private final By destinationInput = Locators.SearchBar.DESTINATION_INPUT;
    private final By searchSubmitBtn = Locators.SearchBar.SEARCH_SUBMIT_BTN;
    private final By checkInInput = Locators.SearchBar.CHECK_IN_INPUT;
    private final By checkOutInput = Locators.SearchBar.CHECK_OUT_INPUT;
    private final By guestsDisplay = Locators.SearchBar.GUESTS_DISPLAY;
    private final By guestsIncrementBtn = Locators.SearchBar.GUESTS_INCREMENT_BTN;
    private final By guestsDecrementBtn = Locators.SearchBar.GUESTS_DECREMENT_BTN;

    private static final String SET_DATE_INPUT_JS = "com/staybnb/scripts/setDateInput.js";

    public Navbar(WebDriver driver) {
        super(driver);
    }

    public boolean isLogoDisplayed() {
        return isDisplayed(navbarLogo);
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

    public boolean isHostDashboardDisplayed() {
        return isDisplayed(hostDashboard);
    }

    public void clickBecomeAHost() {
        waitForElementClickable(becomeAHost).click();
    }

    public void clickHostDashboard() {
        openUserMenu();
        waitForElementClickable(hostDashboard).click();
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

    public boolean isWishlistsLinkDisplayed() {
        return isDisplayed(wishlistsLink);
    }

    public boolean isLogoutButtonDisplayed() {
        return isDisplayed(logoutButton);
    }

    public void clickProfile() {
        openUserMenu();
        waitForElementClickable(profileLink).click();
    }

    public void clickWishlists() {
        openUserMenu();
        waitForElementClickable(wishlistsLink).click();
    }

    public void clickWishlistsAndWaitForRedirect() {
        clickWishlists();
        waitForUrlContains(AppConstants.WISHLIST_URL);
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

    public void setCheckInDate(String isoDate) {
        setDateInput(checkInInput, isoDate);
    }

    public void setCheckOutDate(String isoDate) {
        setDateInput(checkOutInput, isoDate);
    }

    public String getCheckInMinAttribute() {
        return waitForElementVisible(checkInInput).getAttribute("min");
    }

    public String getCheckOutMinAttribute() {
        return waitForElementVisible(checkOutInput).getAttribute("min");
    }

    public void waitForCheckOutMinToBe(String expectedMin) {
        wait.until(ExpectedConditions.attributeToBe(checkOutInput, "min", expectedMin));
    }

    public int getGuestsCount() {
        return Integer.parseInt(waitForElementVisible(guestsDisplay).getText());
    }

    public void incrementGuests(int times) {
        for (int i = 0; i < times; i++) {
            waitForElementClickable(guestsIncrementBtn).click();
        }
    }

    public void decrementGuests(int times) {
        for (int i = 0; i < times; i++) {
            waitForElementClickable(guestsDecrementBtn).click();
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
