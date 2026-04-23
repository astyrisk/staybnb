package com.staybnb.pages;

import com.staybnb.locators.Locators;
import io.restassured.http.ContentType;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import com.staybnb.config.AppConstants;

public class OwnProfilePage extends BasePage {
    private static final String PAGE_URL = AppConstants.PROFILE_URL;

    //TODO remove redundancy, duplication
    private By profileAvatar = Locators.OwnProfile.PROFILE_AVATAR;
    private By profileName = Locators.OwnProfile.PROFILE_NAME;
    private By profileMeta = Locators.OwnProfile.PROFILE_META;
    private By editProfileButton = Locators.OwnProfile.EDIT_PROFILE_BUTTON;
    private By becomeHostButton = Locators.OwnProfile.BECOME_HOST_BUTTON;

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
        return isDisplayed(profileAvatar);
    }

    public String getFullName() {
        return waitForElementVisible(profileName).getText();
    }

    public String getProfileMeta() {
        return waitForElementVisible(profileMeta).getText();
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
        click(editProfileButton);
    }

    public boolean isEditProfileButtonVisible() {
        return isDisplayed(editProfileButton);
    }

    public boolean isBecomeHostButtonVisible() {
        return isDisplayed(becomeHostButton);
    }

    public void clickBecomeHost() {
        click(becomeHostButton);
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
                !d.findElements(profileAvatar).isEmpty() ||
                !d.findElements(profileName).isEmpty()   ||
                !d.findElements(profileMeta).isEmpty()
        );
    }
}
