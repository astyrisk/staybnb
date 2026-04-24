package com.staybnb.pages;

import com.staybnb.locators.Locators;
import io.restassured.http.ContentType;
import org.openqa.selenium.WebDriver;
import com.staybnb.config.AppConstants;

public class OwnProfilePage extends BasePage {
    private static final String PAGE_URL = AppConstants.PROFILE_URL;

    public OwnProfilePage(WebDriver driver) {
        super(driver);
    }

    public void navigateTo() {
        super.navigateTo(PAGE_URL);
        waitForProfileToLoad();
    }

    public void navigateViaNavbar() {
        navbar().clickProfileAndWaitForRedirect();
        waitForProfileToLoad();
    }

    public boolean isAvatarDisplayed() {
        return isDisplayed(Locators.OwnProfile.PROFILE_AVATAR);
    }

    public String getFullName() {
        return waitForElementVisible(Locators.OwnProfile.PROFILE_NAME).getText();
    }

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

    private void waitForProfileToLoad() {
        wait.until(d ->
                !d.findElements(Locators.OwnProfile.PROFILE_AVATAR).isEmpty() ||
                !d.findElements(Locators.OwnProfile.PROFILE_NAME).isEmpty()   ||
                !d.findElements(Locators.OwnProfile.PROFILE_META).isEmpty()
        );
    }
}
