package com.staybnb.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import java.util.List;

public class LoginPage {
    private WebDriver driver;
    private final String PAGE_URL = "https://qa-playground.nixdev.co/t/automation-adel/login";

    private By emailField = By.id("email");
    private By passwordField = By.id("password");
    private By loginButton = By.cssSelector("button[type='submit'].btn-primary");
    private By registerLink = By.xpath("//div[@class='auth-link']/a[text()='Register']");

    private By inlineErrorMessages = By.cssSelector(".error, .field-error, .auth-error");
    private By globalErrorMessage = By.cssSelector(".alert, .alert-danger, .toast-message, .auth-error");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void navigateTo() {
        driver.get(PAGE_URL);
    }

    public void enterEmail(String email) {
        driver.findElement(emailField).clear();
        driver.findElement(emailField).sendKeys(email);
    }

    public void enterPassword(String password) {
        driver.findElement(passwordField).clear();
        driver.findElement(passwordField).sendKeys(password);
    }

    public void clickLoginButton() {
        driver.findElement(loginButton).click();
    }

    public void login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickLoginButton();
    }

    public void clickRegisterLink() {
        driver.findElement(registerLink).click();
    }

    public boolean isInlineErrorDisplayed(String expectedErrorText) {
        List<WebElement> errors = driver.findElements(inlineErrorMessages);
        return errors.stream().anyMatch(e -> e.getText().toLowerCase().contains(expectedErrorText.toLowerCase()));
    }

    public String getGlobalErrorMessageText() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement errorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(globalErrorMessage));
        return errorElement.getText();
    }

    public String getStaybnbToken() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        try {
            wait.until(d -> js.executeScript("return window.localStorage.getItem('staybnb_token');") != null);
            return (String) js.executeScript("return window.localStorage.getItem('staybnb_token');");
        } catch (Exception e) {
            return null;
        }
    }
}