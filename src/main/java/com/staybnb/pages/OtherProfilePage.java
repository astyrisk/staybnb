package com.staybnb.pages;

import com.staybnb.utils.Constants;
import com.staybnb.locators.Locators;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class OtherProfilePage extends BasePage {
    private static final String OTHER_PROFILE_BASE_URL = Constants.OTHER_PROFILE_BASE_URL;
    private static final String OTHER_USER_PROFILE_API_JS_RESOURCE = "com/staybnb/scripts/getOtherUserProfileApi.js";

    private By profileAvatar = Locators.OtherProfile.PROFILE_AVATAR;
    private By profileName = Locators.OtherProfile.PROFILE_NAME;
    private By profileMeta = Locators.OtherProfile.PROFILE_META;
    private By bioText = Locators.OtherProfile.BIO_TEXT;
    private By phoneSection = Locators.OtherProfile.PHONE_SECTION;
    private By emailSection = Locators.OtherProfile.EMAIL_SECTION;
    private By errorMessage = Locators.OtherProfile.ERROR_MESSAGE;

    public OtherProfilePage(WebDriver driver) {
        super(driver);
    }

    public void navigateTo(String userId) {
        super.navigateTo(buildOtherProfileUrl(userId));
        waitForProfileToLoad();
    }

    public boolean isAvatarDisplayed() {
        return isDisplayed(profileAvatar);
    }

    public String getAvatarText() {
        return waitForElementVisible(profileAvatar).getText();
    }

    public String getProfileName() {
        return waitForElementVisible(profileName).getText();
    }

    public String getProfileMeta() {
        return waitForElementVisible(profileMeta).getText();
    }

    public String getBio() {
        return waitForElementVisible(bioText).getText();
    }

    public boolean isPhoneSectionVisible() {
        return isElementVisible(phoneSection);
    }

    public boolean isEmailSectionVisible() {
        return isElementVisible(emailSection);
    }

    public boolean is404Displayed() {
        return isErrorPagePresent(driver);
    }

    public String getOtherUserApiResponse(String userId) {
        // Ensure we're on the app origin so the relative `/api/...` endpoint resolves correctly.
        driver.get(Constants.HOME_URL);

        JavascriptExecutor js = (JavascriptExecutor) driver;
        String script = loadJavascriptResource(OTHER_USER_PROFILE_API_JS_RESOURCE);
        Object response = js.executeAsyncScript(script, Constants.SLUG, userId);
        return (String) response;
    }

    private String buildOtherProfileUrl(String userId) {
        return OTHER_PROFILE_BASE_URL + userId;
    }

    private void waitForProfileToLoad() {
        wait.until(d -> hasAnyProfileIndicator(d));
    }

    private boolean hasAnyProfileIndicator(WebDriver d) {
        return d.findElements(profileAvatar).size() > 0
                || d.findElements(profileName).size() > 0
                || d.findElements(profileMeta).size() > 0
                || d.findElements(bioText).size() > 0
                || isErrorPagePresent(d);
    }

    private boolean isErrorPagePresent(WebDriver d) {
        return d.getPageSource().contains("404")
                || d.getPageSource().contains("User not found")
                || d.findElements(errorMessage).size() > 0;
    }

    private boolean isElementVisible(By locator) {
        try {
            List<WebElement> elements = driver.findElements(locator);
            return elements.stream().anyMatch(WebElement::isDisplayed);
        } catch (Exception e) {
            return false;
        }
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
