package com.staybnb.pages;

import com.staybnb.components.Navbar;
import com.staybnb.config.AppConstants;
import com.staybnb.core.SeleniumBase;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

public abstract class BasePage extends SeleniumBase {
    private final Navbar navbar;

    public BasePage(WebDriver driver) {
        super(driver);
        this.navbar = new Navbar(driver);
    }

    public void navigateTo(String url) {
        log.info("Navigating to: {}", url);
        driver.get(url);
    }

    public String getStaybnbToken() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        try {
            return (String) js.executeScript("return window.localStorage.getItem('staybnb_token');");
        } catch (WebDriverException e) {
            return null;
        }
    }

    protected RequestSpecification apiRequest() {
        String token = getStaybnbToken();
        RequestSpecification spec = RestAssured.given().baseUri(AppConstants.API_BASE_URL);
        if (token != null && !token.isBlank()) {
            spec.header("Authorization", "Bearer " + token);
        }
        return spec;
    }

    protected void type(By locator, String text) {
        WebElement element = waitForElementVisible(locator);
        element.clear();
        element.sendKeys(text);
    }

    protected String getText(By locator) {
        return driver.findElement(locator).getText();
    }

    public Navbar navbar() {
        return navbar;
    }
}
