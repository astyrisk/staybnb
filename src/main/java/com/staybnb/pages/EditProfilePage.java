package com.staybnb.pages;

import com.staybnb.locators.Locators;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.staybnb.config.AppConstants;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class EditProfilePage extends BasePage {
    private static final String PAGE_URL = AppConstants.EDIT_PROFILE_URL;
    private static final String UPDATE_PROFILE_API_JS_RESOURCE = "com/staybnb/scripts/updateMyProfileApi.js";

    private By firstNameField = Locators.EditProfile.FIRST_NAME_FIELD;
    private By lastNameField = Locators.EditProfile.LAST_NAME_FIELD;
    private By phoneField = Locators.EditProfile.PHONE_FIELD;
    private By bioField = Locators.EditProfile.BIO_FIELD;
    private By avatarUrlField = Locators.EditProfile.AVATAR_URL_FIELD;
    private By saveChangesButton = Locators.EditProfile.SAVE_CHANGES_BUTTON;
    private By cancelButton = Locators.EditProfile.CANCEL_BUTTON;
    private By validationError = Locators.EditProfile.VALIDATION_ERROR;

    public EditProfilePage(WebDriver driver) {
        super(driver);
    }

    public void navigateTo() {
        super.navigateTo(PAGE_URL);
        waitForEditProfileToLoad();
    }

    public void enterFirstName(String firstName) {
        type(firstNameField, firstName);
    }

    public void enterLastName(String lastName) {
        type(lastNameField, lastName);
    }

    public void enterPhone(String phone) {
        type(phoneField, phone);
    }

    public void enterBio(String bio) {
        type(bioField, bio);
    }

    public void enterAvatarUrl(String avatarUrl) {
        type(avatarUrlField, avatarUrl);
    }

    public void updateProfile(String firstName, String lastName, String phone, String bio, String avatarUrl) {
        navigateTo();
        enterFirstName(firstName);
        enterLastName(lastName);
        enterPhone(phone);
        enterBio(bio);
        enterAvatarUrl(avatarUrl);
        clickSaveChanges();
        waitForUrlContains("/profile");
    }

    public void submitWithEmptyFirstName() {
        navigateTo();
        clearField("firstName");
        clickSaveChanges();
    }

    public void submitWithEmptyLastName(String firstName) {
        navigateTo();
        enterFirstName(firstName);
        clearField("lastName");
        clickSaveChanges();
    }

    public String attemptFirstNameChangeThenCancel(String newFirstName) {
        navigateTo();
        String originalFirstName = getFirstNameValue();
        enterFirstName(newFirstName);
        clickCancel();
        waitForUrlContains("/profile");
        navigateTo();
        return originalFirstName;
    }

    public void clearField(String fieldId) {
        WebElement el = waitForElementVisible(By.id(fieldId));
        el.sendKeys(Keys.CONTROL + "a");
        el.sendKeys(Keys.BACK_SPACE);
    }

    public void clickSaveChanges() {
        waitForElementClickable(saveChangesButton).click();
    }

    public void clickCancel() {
        waitForElementClickable(cancelButton).click();
    }

    public boolean isValidationErrorDisplayed() {
        return isDisplayed(validationError);
    }

    public String getFieldError(String fieldId) {
        try {
            return driver.findElement(By.xpath("//input[@id='" + fieldId + "']/following-sibling::span[@class='field-error']")).getText();
        } catch (Exception e) {
            return "";
        }
    }

    public boolean is401Displayed() {
        return driver.getPageSource().contains("401") || driver.getPageSource().contains("Unauthorized");
    }

    public String getFirstNameValue() {
        return waitForElementVisible(firstNameField).getAttribute("value");
    }

    public String getLastNameValue() {
        return waitForElementVisible(lastNameField).getAttribute("value");
    }

    public String updateMyProfileViaApi(String updatePayloadJson) {
        driver.get(AppConstants.HOME_URL);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String script = loadJavascriptResource(UPDATE_PROFILE_API_JS_RESOURCE);
        Object response = js.executeAsyncScript(script, AppConstants.SLUG, updatePayloadJson);
        return (String) response;
    }

    private void waitForEditProfileToLoad() {
        wait.until(d ->
                d.findElements(firstNameField).size() > 0 ||
                d.getPageSource().contains("401") ||
                d.getPageSource().contains("Unauthorized")
        );
    }

    private String loadJavascriptResource(String resourcePath) {
        try (InputStream stream = getClass().getClassLoader().getResourceAsStream(resourcePath)) {
            if (stream == null) {
                throw new IllegalStateException("Missing JS resource on classpath: " + resourcePath);
            }
            return new String(stream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read JS resource on classpath: " + resourcePath, e);
        }
    }
}
