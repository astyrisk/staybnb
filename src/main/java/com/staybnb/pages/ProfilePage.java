package com.staybnb.pages;

import com.staybnb.config.AppConstants;
import com.staybnb.locators.Locators;
import io.restassured.http.ContentType;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ProfilePage extends BasePage {

    public ProfilePage(WebDriver driver) {
        super(driver);
    }

    // ── Navigation ────────────────────────────────────────────────────────────

    public void navigateTo(String userId) {
        super.navigateTo(AppConstants.OTHER_PROFILE_BASE_URL + userId);
        waitForProfileToLoad();
    }

    public void navigateViaNavbar() {
        navbar().clickProfileAndWaitForRedirect();
        waitForProfileToLoad();
    }

    // ── Avatar ────────────────────────────────────────────────────────────────

    public String getAvatarSrc() {
        return waitForElementVisible(Locators.OwnProfile.PROFILE_AVATAR)
                .findElement(By.tagName("img"))
                .getAttribute("src");
    }

    // ── Name ──────────────────────────────────────────────────────────────────

    public String getFullName() {
        return waitForElementVisible(Locators.OwnProfile.PROFILE_NAME).getText();
    }

    public void waitForFullNameToBe(String expected) {
        wait.until(ExpectedConditions.textToBe(Locators.OwnProfile.PROFILE_NAME, expected));
    }

    public String getFirstName() {
        return getProfileNameTextNode(0);
    }

    public String getLastName() {
        return getProfileNameTextNode(1);
    }

    // ── Profile Info ──────────────────────────────────────────────────────────

    public String getProfileMeta() {
        return waitForElementVisible(Locators.OwnProfile.PROFILE_META).getText();
    }

    public String getBio() {
        return waitForElementVisible(Locators.OwnProfile.BIO_TEXT).getText();
    }

    public String getPhone() {
        return waitForElementVisible(Locators.OwnProfile.PHONE_TEXT).getText();
    }

    public String getEmail() {
        return waitForElementVisible(Locators.OwnProfile.EMAIL_TEXT).getText();
    }

    public boolean isPhoneSectionVisible() {
        return isDisplayed(Locators.OtherProfile.PHONE_SECTION);
    }

    public boolean isEmailSectionVisible() {
        return isDisplayed(Locators.OtherProfile.EMAIL_SECTION);
    }

    // ── Own Profile Actions ───────────────────────────────────────────────────

    public void clickEditProfile() {
        click(Locators.OwnProfile.EDIT_PROFILE_BUTTON);
    }

    public boolean isEditProfileButtonVisible() {
        return isDisplayed(Locators.OwnProfile.EDIT_PROFILE_BUTTON);
    }

    public boolean isBecomeHostButtonVisible() {
        return isDisplayed(Locators.OwnProfile.BECOME_HOST_BUTTON);
    }

    public void clickBecomeHost() {
        click(Locators.OwnProfile.BECOME_HOST_BUTTON);
    }

    // ── Error States ──────────────────────────────────────────────────────────

    public boolean is404Displayed() {
        return isErrorPagePresent();
    }

    // ── API ───────────────────────────────────────────────────────────────────

    public String getAuthMeApiResponse() {
        return apiRequest().get("/auth/me").asString();
    }

    public String becomeHostViaApi(String payloadJson) {
        return apiRequest()
                .contentType(ContentType.JSON)
                .body(payloadJson)
                .put("/users/me/host")
                .asString();
    }

    public String getOtherUserApiResponse(String userId) {
        return apiRequest().get("/users/" + userId).asString();
    }

    // ── Private ───────────────────────────────────────────────────────────────

    private String getProfileNameTextNode(int index) {
        WebElement el = waitForElementVisible(Locators.OwnProfile.PROFILE_NAME);
        return (String) ((JavascriptExecutor) driver).executeScript(
                loadScript("com/staybnb/scripts/getTextNodeByIndex.js"), el, index);
    }

    private void waitForProfileToLoad() {
        wait.until(ExpectedConditions.or(
                ExpectedConditions.presenceOfElementLocated(Locators.OwnProfile.PROFILE_AVATAR),
                ExpectedConditions.presenceOfElementLocated(Locators.OwnProfile.PROFILE_NAME),
                ExpectedConditions.presenceOfElementLocated(Locators.OwnProfile.PROFILE_META),
                ExpectedConditions.presenceOfElementLocated(Locators.OwnProfile.BIO_TEXT),
                d -> isErrorPagePresent()
        ));
    }

    private boolean isErrorPagePresent() {
        return driver.getPageSource().contains("404")
                || driver.getPageSource().contains("User not found")
                || !driver.findElements(Locators.OtherProfile.ERROR_MESSAGE).isEmpty();
    }
}
