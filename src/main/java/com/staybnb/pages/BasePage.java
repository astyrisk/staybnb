package com.staybnb.pages;

import com.staybnb.components.BaseComponent;
import com.staybnb.components.Navbar;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public abstract class BasePage extends BaseComponent {
    private final Navbar navbar;

    public BasePage(WebDriver driver) {
        super(driver);
        this.navbar = new Navbar(driver);
    }

    public void navigateTo(String url) {
        driver.get(url);
    }

    public String getStaybnbToken() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        try {
            return (String) js.executeScript("return window.localStorage.getItem('staybnb_token');");
        } catch (Exception e) {
            return null;
        }
    }

    protected void type(By locator, String text) {
        WebElement element = waitForElementVisible(locator);
        element.clear();
        element.sendKeys(text);
    }

    protected String getText(By locator) {
        return driver.findElement(locator).getText();
    }

    public void acceptConfirmationIfPresent() {
        try {
            wait.until(ExpectedConditions.alertIsPresent());
            driver.switchTo().alert().accept();
        } catch (Exception ignored) {
            // no browser confirmation dialog present
        }
    }

    public Navbar navbar() {
        return navbar;
    }
}
