package com.staybnb.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import com.staybnb.utils.Constants;

public class LogoutPage {
    private WebDriver driver;
    private final String DASHBOARD_URL = Constants.HOME_URL;

    // --- Locators based on provided HTML ---
    private By userMenuButton = By.className("navbar-user-btn");
    private By logoutButton = By.xpath("//div[@class='navbar-dropdown']//button[text()='Log out']");

    public LogoutPage(WebDriver driver) {
        this.driver = driver;
    }

    public void navigateToDashboard() {
        driver.get(DASHBOARD_URL);
    }

    public void openUserMenu() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement menu = wait.until(ExpectedConditions.elementToBeClickable(userMenuButton));
        menu.click();
    }

    public void clickLogout() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement logoutBtn = wait.until(ExpectedConditions.elementToBeClickable(logoutButton));
        logoutBtn.click();
    }

    public void logout() {
        openUserMenu();
        clickLogout();
    }

    public String getStaybnbToken() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        try {
            return (String) js.executeScript("return window.localStorage.getItem('staybnb_token');");
        } catch (Exception e) {
            return null;
        }
    }
}
