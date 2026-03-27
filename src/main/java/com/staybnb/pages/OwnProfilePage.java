package com.staybnb.pages;

import com.staybnb.locators.Locators;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import com.staybnb.utils.Constants;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class OwnProfilePage extends BasePage {
    private static final String PAGE_URL = Constants.PROFILE_URL;
    private static final String AUTH_ME_API_JS_RESOURCE = "com/staybnb/scripts/getAuthMeApi.js";
    private static final String AUTH_ME_STATUS_API_JS_RESOURCE = "com/staybnb/scripts/getAuthMeStatusApi.js";
    private static final String BECOME_HOST_API_JS_RESOURCE = "com/staybnb/scripts/becomeHostApi.js";
    private static final String BECOME_HOST_STATUS_API_JS_RESOURCE = "com/staybnb/scripts/becomeHostStatusApi.js";

    private By profileAvatar = Locators.OwnProfile.PROFILE_AVATAR;
    private By profileName = Locators.OwnProfile.PROFILE_NAME;
    private By profileMeta = Locators.OwnProfile.PROFILE_META;
    private By editProfileButton = Locators.OwnProfile.EDIT_PROFILE_BUTTON;
    private By becomeHostButton = Locators.OwnProfile.BECOME_HOST_BUTTON;

    public OwnProfilePage(WebDriver driver) {
        super(driver);
    }

    public void load() {
        navigateTo(PAGE_URL);
        waitForProfileToLoad();
    }

    public void navigateTo() {
        load();
    }

    public boolean isAvatarDisplayed() {
        return isDisplayed(profileAvatar);
    }

    public String getAvatarStyle() {
        return driver.findElement(profileAvatar).getAttribute("class");
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
        // Ensure we're on the app origin so the relative `/api/...` endpoint resolves correctly.
        driver.get(Constants.HOME_URL);

        JavascriptExecutor js = (JavascriptExecutor) driver;
        String script = loadJavascriptResource(AUTH_ME_API_JS_RESOURCE);
        Object response = js.executeAsyncScript(script, Constants.SLUG);
        return (String) response;
    }

    public long getAuthMeApiStatusLoggedOut() {
        driver.get(Constants.HOME_URL);

        JavascriptExecutor js = (JavascriptExecutor) driver;
        String script = loadJavascriptResource(AUTH_ME_STATUS_API_JS_RESOURCE);
        Object responseStatus = js.executeAsyncScript(script, Constants.SLUG);
        if (responseStatus instanceof Number n) {
            return n.longValue();
        }
        throw new RuntimeException("Unexpected auth/me status response type: " +
                (responseStatus == null ? "null" : responseStatus.getClass().getName()));
    }

    public String becomeHostViaApi(String payloadJson) {
        driver.get(Constants.HOME_URL);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String script = loadJavascriptResource(BECOME_HOST_API_JS_RESOURCE);
        Object response = js.executeAsyncScript(script, Constants.SLUG, payloadJson);
        return (String) response;
    }

    public long becomeHostStatusLoggedOut() {
        driver.get(Constants.HOME_URL);

        JavascriptExecutor js = (JavascriptExecutor) driver;
        String script = loadJavascriptResource(BECOME_HOST_STATUS_API_JS_RESOURCE);
        Object responseStatus = js.executeAsyncScript(script, Constants.SLUG);
        if (responseStatus instanceof Number n) {
            return n.longValue();
        }
        throw new RuntimeException("Unexpected become-host status response type: " +
                (responseStatus == null ? "null" : responseStatus.getClass().getName()));
    }

    private void waitForProfileToLoad() {
        wait.until(d ->
                d.findElements(profileAvatar).size() > 0 ||
                d.findElements(profileName).size() > 0 ||
                d.findElements(profileMeta).size() > 0
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
