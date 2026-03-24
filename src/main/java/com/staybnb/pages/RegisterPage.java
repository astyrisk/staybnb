package com.staybnb.pages;

import com.staybnb.utils.Constants;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import java.util.List;

public class RegisterPage {
    private WebDriver driver;
    private final String PAGE_URL = Constants.REGISTER_URL;

    // --- Locators based on provided HTML ---
    private By firstNameField = By.id("firstName");
    private By lastNameField = By.id("lastName");
    private By emailField = By.id("email");
    private By passwordField = By.id("password");
    private By confirmPasswordField = By.id("confirmPassword");
    private By registerButton = By.cssSelector("button[type='submit'].btn-primary");

    // Scoped to the specific auth-link div at the bottom of the form
    private By loginLink = By.xpath("//div[@class='auth-link']/a[text()='Log in']");

    // Note: These classes are assumptions. Inspect the DOM when an error is visible to confirm.
    private By inlineErrorMessages = By.cssSelector(".error, .field-error, .auth-error");
    private By globalErrorMessage = By.cssSelector(".alert, .alert-danger, .toast-message, .auth-error");

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
    }

    public void navigateTo() {
        driver.get(PAGE_URL);
    }

    public void enterFirstName(String firstName) {
        driver.findElement(firstNameField).clear();
        driver.findElement(firstNameField).sendKeys(firstName);
    }

    public void enterLastName(String lastName) {
        driver.findElement(lastNameField).clear();
        driver.findElement(lastNameField).sendKeys(lastName);
    }

    public void enterEmail(String email) {
        driver.findElement(emailField).clear();
        driver.findElement(emailField).sendKeys(email);
    }

    public void enterPassword(String password) {
        driver.findElement(passwordField).clear();
        driver.findElement(passwordField).sendKeys(password);
    }

    public void enterConfirmPassword(String confirmPassword) {
        driver.findElement(confirmPasswordField).clear();
        driver.findElement(confirmPasswordField).sendKeys(confirmPassword);
    }

    public void clickRegister() {
        driver.findElement(registerButton).click();
    }

    public void clickLoginLink() {
        driver.findElement(loginLink).click();
    }

    public void fillCompleteRegistration(String fName, String lName, String email, String pass) {
        enterFirstName(fName);
        enterLastName(lName);
        enterEmail(email);
        enterPassword(pass);
        enterConfirmPassword(pass);
    }

    // Checks if specific error text is present in any inline validation element
    public boolean isInlineErrorDisplayed(String expectedErrorText) {
        List<WebElement> errors = driver.findElements(inlineErrorMessages);
        return errors.stream().anyMatch(e -> e.getText().toLowerCase().contains(expectedErrorText.toLowerCase()));
    }

    // Gets the text of a top-level error (e.g., 409 Conflict toast)
    public String getGlobalErrorMessageText() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement errorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(globalErrorMessage));
        return errorElement.getText();
    }

    // Retrieves JWT from localStorage
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