package com.staybnb.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class Navbar {
    private WebDriver driver;
    private WebDriverWait wait;

    // --- Locators ---
    private By navbarLogo = By.className("navbar-logo");
    private By navbarRight = By.className("navbar-right");
    private By userMenuButton = By.className("navbar-user-btn");
    private By userAvatar = By.className("navbar-avatar");
    private By hamburgerMenu = By.className("navbar-hamburger");
    
    // Auth links (Visitor)
    private By loginLink = By.xpath("//div[@class='navbar-auth-links']//a[text()='Log in']");
    private By registerLink = By.xpath("//div[@class='navbar-auth-links']//a[text()='Sign up']");
    
    // Dropdown (Authenticated)
    private By dropdownMenu = By.className("navbar-dropdown");
    private By profileLink = By.xpath("//div[@class='navbar-dropdown']//a[contains(@href, '/profile')]");
    private By logoutButton = By.xpath("//div[@class='navbar-dropdown']//button[text()='Log out']");

    public Navbar(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    public boolean isLogoDisplayed() {
        return driver.findElement(navbarLogo).isDisplayed();
    }

    public void clickLogo() {
        driver.findElement(navbarLogo).click();
    }

    public String getLogoHref() {
        return driver.findElement(navbarLogo).getAttribute("href");
    }

    public boolean isUserAvatarDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(userAvatar)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isLoginLinkDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(loginLink)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isRegisterLinkDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(registerLink)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickLogin() {
        wait.until(ExpectedConditions.elementToBeClickable(loginLink)).click();
    }

    public void clickRegister() {
        wait.until(ExpectedConditions.elementToBeClickable(registerLink)).click();
    }

    public void openUserMenu() {
        wait.until(ExpectedConditions.elementToBeClickable(userMenuButton)).click();
    }

    public boolean isDropdownDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(dropdownMenu)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isProfileLinkDisplayed() {
        try {
            return driver.findElement(profileLink).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isLogoutButtonDisplayed() {
        try {
            return driver.findElement(logoutButton).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickProfile() {
        openUserMenu();
        wait.until(ExpectedConditions.elementToBeClickable(profileLink)).click();
    }

    public void clickLogout() {
        openUserMenu();
        wait.until(ExpectedConditions.elementToBeClickable(logoutButton)).click();
    }

    public boolean isHamburgerMenuDisplayed() {
        try {
            return driver.findElement(hamburgerMenu).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void setMobileLayout() {
        driver.manage().window().setSize(new Dimension(375, 812)); // iPhone 12 Pro size
    }

    public void setDesktopLayout() {
        driver.manage().window().maximize();
    }
}
