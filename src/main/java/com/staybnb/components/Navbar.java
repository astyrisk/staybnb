package com.staybnb.components;

import com.staybnb.config.AppConstants;
import com.staybnb.config.WaitConstants;
import com.staybnb.locators.Locators;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;

public class Navbar extends BaseComponent {
    private final SearchForm searchForm;

    public Navbar(WebDriver driver) {
        super(driver);
        this.searchForm = new SearchForm(driver);
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

    // ── Search form delegation ─────────────────────────────────────────────────

    public void clickCompactSearchBar() { searchForm.expand(); }

    public boolean isSearchFormExpanded() { return searchForm.isExpanded(); }

    public boolean isCompactSearchBarDisplayed() { return searchForm.isCompactButtonDisplayed(); }

    public void enterDestination(String city) { searchForm.enterDestination(city); }

    public void clickSearch() { searchForm.submit(); }

    public void searchForCity(String city) { searchForm.searchForCity(city); }

    public void setCheckInDate(String isoDate) { searchForm.setCheckInDate(isoDate); }

    public void setCheckOutDate(String isoDate) { searchForm.setCheckOutDate(isoDate); }

    public String getCheckInMinAttribute() { return searchForm.getCheckInMinAttribute(); }

    public String getCheckOutMinAttribute() { return searchForm.getCheckOutMinAttribute(); }

    public void waitForCheckOutMinToBe(String expectedMin) { searchForm.waitForCheckOutMinToBe(expectedMin); }

    public int getGuestsCount() { return searchForm.getGuestsCount(); }

    public void incrementGuests(int times) { searchForm.incrementGuests(times); }

    public void decrementGuests(int times) { searchForm.decrementGuests(times); }

    public void searchWithDates(String checkIn, String checkOut) { searchForm.searchWithDates(checkIn, checkOut); }

    public void searchWithGuests(int targetGuests) { searchForm.searchWithGuests(targetGuests); }
}
