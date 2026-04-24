package com.staybnb.pages;

import com.staybnb.locators.Locators;
import io.restassured.http.ContentType;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.staybnb.config.AppConstants;

public class EditProfilePage extends BasePage {
    private static final String PAGE_URL = AppConstants.EDIT_PROFILE_URL;

    public EditProfilePage(WebDriver driver) {
        super(driver);
    }

    public void navigateTo() {
        super.navigateTo(PAGE_URL);
        waitForEditProfileToLoad();
    }

    public void enterFirstName(String firstName) {
        type(Locators.EditProfile.FIRST_NAME_FIELD, firstName);
    }

    public void enterLastName(String lastName) {
        type(Locators.EditProfile.LAST_NAME_FIELD, lastName);
    }

    public void enterPhone(String phone) {
        type(Locators.EditProfile.PHONE_FIELD, phone);
    }

    public void enterBio(String bio) {
        type(Locators.EditProfile.BIO_FIELD, bio);
    }

    public void enterAvatarUrl(String avatarUrl) {
        type(Locators.EditProfile.AVATAR_URL_FIELD, avatarUrl);
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
        waitForElementClickable(Locators.EditProfile.SAVE_CHANGES_BUTTON).click();
    }

    public void clickCancel() {
        waitForElementClickable(Locators.EditProfile.CANCEL_BUTTON).click();
    }

    public boolean isValidationErrorDisplayed() {
        return isDisplayed(Locators.EditProfile.VALIDATION_ERROR);
    }

    public String getFieldError(String fieldId) {
        try {
            return driver.findElement(By.xpath("//input[@id='" + fieldId + "']/following-sibling::span[@class='field-error']")).getText();
        } catch (NoSuchElementException e) {
            return "";
        }
    }

    public boolean is401Displayed() {
        return driver.getPageSource().contains("401") || driver.getPageSource().contains("Unauthorized");
    }

    public String getFirstNameValue() {
        return waitForElementVisible(Locators.EditProfile.FIRST_NAME_FIELD).getAttribute("value");
    }

    public String updateMyProfileViaApi(String updatePayloadJson) {
        return apiRequest()
                .contentType(ContentType.JSON)
                .body(updatePayloadJson)
                .put("/users/me")
                .asString();
    }

    private void waitForEditProfileToLoad() {
        wait.until(d ->
                !d.findElements(Locators.EditProfile.FIRST_NAME_FIELD).isEmpty() ||
                d.getPageSource().contains("401") ||
                d.getPageSource().contains("Unauthorized")
        );
    }
}
