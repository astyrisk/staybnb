package com.staybnb.pages;

import com.staybnb.locators.Locators;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import com.staybnb.config.AppConstants;

public class EditProfilePage extends BasePage {

    public EditProfilePage(WebDriver driver) {
        super(driver);
    }

    public void navigateTo() {
        super.navigateTo(AppConstants.EDIT_PROFILE_URL);
        wait.until(ExpectedConditions.presenceOfElementLocated(Locators.EditProfile.FIRST_NAME_FIELD));
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

    public void submitWithEmptyField(String field) {
        navigateTo();
        clearField(field);
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
        waitForElementClickable(Locators.EditProfile.SAVE_CHANGES_BUTTON).click();
    }

    public ProfilePage clickCancel() {
        waitForElementClickable(Locators.EditProfile.CANCEL_BUTTON).click();
        return new ProfilePage(driver);
    }

    public String getFieldError(String fieldId) {
        try {
            return driver.findElement(Locators.EditProfile.fieldError(fieldId)).getText();
        } catch (NoSuchElementException e) {
            return "";
        }
    }

    public boolean is401Displayed() {
        return driver.getPageSource().contains("401");
    }

    public String getFirstNameValue() {
        return waitForElementVisible(Locators.EditProfile.FIRST_NAME_FIELD).getAttribute("value");
    }

    public String updateMyProfileViaApi(String payload) {
        return apiRequest()
                .contentType("application/json")
                .body(payload)
                .put("/users/me")
                .asString();
    }

    public void updateMyProfileViaScript(String firstName, String lastName, String phone, String bio, String avatarUrl) {
        String payload = String.format(
                "{\"firstName\":\"%s\",\"lastName\":\"%s\",\"phone\":\"%s\",\"bio\":\"%s\",\"avatarUrl\":\"%s\"}",
                firstName, lastName, phone, bio, avatarUrl);
        ((JavascriptExecutor) driver).executeAsyncScript(
                loadScript("com/staybnb/scripts/updateMyProfileApi.js"),
                AppConstants.SLUG,
                payload);
    }

    // ── Private ───────────────────────────────────────────────────────────────

    private void enterFirstName(String firstName) {
        type(Locators.EditProfile.FIRST_NAME_FIELD, firstName);
    }

    private void enterLastName(String lastName) {
        type(Locators.EditProfile.LAST_NAME_FIELD, lastName);
    }

    private void enterPhone(String phone) {
        type(Locators.EditProfile.PHONE_FIELD, phone);
    }

    private void enterBio(String bio) {
        type(Locators.EditProfile.BIO_FIELD, bio);
    }

    private void enterAvatarUrl(String avatarUrl) {
        type(Locators.EditProfile.AVATAR_URL_FIELD, avatarUrl);
    }
}
