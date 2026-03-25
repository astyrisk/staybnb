package com.staybnb.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import com.staybnb.config.TestConfig;

public class EditProfilePage {
    private WebDriver driver;
    private final String PAGE_URL = TestConfig.BASE_URL + "/profile/edit";

    private By firstNameField = By.id("firstName");
    private By lastNameField = By.id("lastName");
    private By phoneField = By.id("phone");
    private By bioField = By.id("bio");
    private By avatarUrlField = By.id("avatarUrl");
    private By saveChangesButton = By.xpath("//button[text()='Save Changes']");
    private By cancelButton = By.xpath("//button[text()='Cancel']");
    private By validationError = By.cssSelector(".error, .field-error, .auth-error");

    public EditProfilePage(WebDriver driver) {
        this.driver = driver;
    }

    public void navigateTo() {
        driver.get(PAGE_URL);
    }

    public void navigateTo(String url) {
        driver.get(url);
    }

    public void enterFirstName(String firstName) {
        WebElement el = driver.findElement(firstNameField);
        el.clear();
        el.sendKeys(firstName);
    }

    public void enterLastName(String lastName) {
        WebElement el = driver.findElement(lastNameField);
        el.clear();
        el.sendKeys(lastName);
    }

    public void enterPhone(String phone) {
        WebElement el = driver.findElement(phoneField);
        el.clear();
        el.sendKeys(phone);
    }

    public void enterBio(String bio) {
        WebElement el = driver.findElement(bioField);
        el.clear();
        el.sendKeys(bio);
    }

    public void enterAvatarUrl(String avatarUrl) {
        WebElement el = driver.findElement(avatarUrlField);
        el.clear();
        el.sendKeys(avatarUrl);
    }

    public void clearField(String fieldId) {
        WebElement el = driver.findElement(By.id(fieldId));
        el.sendKeys(Keys.CONTROL + "a");
        el.sendKeys(Keys.BACK_SPACE);
    }

    public void clickSaveChanges() {
        driver.findElement(saveChangesButton).click();
    }

    public void clickCancel() {
        driver.findElement(cancelButton).click();
    }

    public boolean isValidationErrorDisplayed() {
        try {
            return driver.findElement(validationError).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getFieldError(String fieldId) {
        try {
            // Find the span with class field-error that is a sibling or child of the group containing the input
            return driver.findElement(By.xpath("//input[@id='" + fieldId + "']/following-sibling::span[@class='field-error']")).getText();
        } catch (Exception e) {
            return "";
        }
    }

    public boolean is401Displayed() {
        return driver.getPageSource().contains("401") || driver.getPageSource().contains("Unauthorized");
    }

    public String getFirstNameValue() {
        return driver.findElement(firstNameField).getAttribute("value");
    }

    public String getLastNameValue() {
        return driver.findElement(lastNameField).getAttribute("value");
    }
}
