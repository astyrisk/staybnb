package com.staybnb.pages;

import com.staybnb.config.AppConstants;
import com.staybnb.locators.Locators;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;
import java.util.List;

public class OtherProfilePage extends BasePage {
    private static final String OTHER_PROFILE_BASE_URL = AppConstants.OTHER_PROFILE_BASE_URL;

    public OtherProfilePage(WebDriver driver) {
        super(driver);
    }

    public void navigateTo(String userId) {
        super.navigateTo(buildOtherProfileUrl(userId));
        waitForProfileToLoad();
    }

    public boolean isAvatarDisplayed() {
        return isDisplayed(Locators.OtherProfile.PROFILE_AVATAR);
    }

    public String getAvatarText() {
        return waitForElementVisible(Locators.OtherProfile.PROFILE_AVATAR).getText();
    }

    public String getProfileName() {
        return waitForElementVisible(Locators.OtherProfile.PROFILE_NAME).getText();
    }

    public String getProfileMeta() {
        return waitForElementVisible(Locators.OtherProfile.PROFILE_META).getText();
    }

    public String getBio() {
        return waitForElementVisible(Locators.OtherProfile.BIO_TEXT).getText();
    }

    public boolean isPhoneSectionVisible() {
        return isElementVisible(Locators.OtherProfile.PHONE_SECTION);
    }

    public boolean isEmailSectionVisible() {
        return isElementVisible(Locators.OtherProfile.EMAIL_SECTION);
    }

    public boolean is404Displayed() {
        return isErrorPagePresent(driver);
    }

    public String getOtherUserApiResponse(String userId) {
        return apiRequest().get("/users/" + userId).asString();
    }

    private String buildOtherProfileUrl(String userId) {
        return OTHER_PROFILE_BASE_URL + userId;
    }

    private void waitForProfileToLoad() {
        wait.until(this::hasAnyProfileIndicator);
    }

    private boolean hasAnyProfileIndicator(WebDriver d) {
        return !d.findElements(Locators.OtherProfile.PROFILE_AVATAR).isEmpty()
                || !d.findElements(Locators.OtherProfile.PROFILE_NAME).isEmpty()
                || !d.findElements(Locators.OtherProfile.PROFILE_META).isEmpty()
                || !d.findElements(Locators.OtherProfile.BIO_TEXT).isEmpty()
                || isErrorPagePresent(d);
    }

    private boolean isErrorPagePresent(WebDriver d) {
        return d.getPageSource().contains("404")
                || d.getPageSource().contains("User not found")
                || !d.findElements(Locators.OtherProfile.ERROR_MESSAGE).isEmpty();
    }

    private boolean isElementVisible(By locator) {
        try {
            List<WebElement> elements = driver.findElements(locator);
            return elements.stream().anyMatch(WebElement::isDisplayed);
        } catch (StaleElementReferenceException e) {
            return false;
        }
    }
}
