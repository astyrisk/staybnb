package com.staybnb.pages;

import com.staybnb.config.Constants;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public abstract class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(Constants.MEDIUM_WAIT));
    }

    public void navigateTo(String url) {
        driver.get(url);
    }

    protected WebElement waitForElementVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected List<WebElement> waitForElementsVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    protected List<WebElement> waitForElementsPresent(By locator) {
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    }

    protected WebElement waitForElementClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
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
        WebElement element = driver.findElement(locator);
        element.clear();
        element.sendKeys(text);
    }

    protected void click(By locator) {
        driver.findElement(locator).click();
    }

    protected String getText(By locator) {
        return driver.findElement(locator).getText();
    }

    protected boolean isDisplayed(By locator) {
        try {
            return driver.findElement(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void waitForUrlContains(String text) {
        wait.until(ExpectedConditions.urlContains(text));
    }

    public void waitForUrlToBe(String url) {
        wait.until(ExpectedConditions.urlToBe(url));
    }
}
