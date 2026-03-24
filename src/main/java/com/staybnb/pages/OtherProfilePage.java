package com.staybnb.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import com.staybnb.utils.Constants;

public class OtherProfilePage {
    private WebDriver driver;
    private final String BASE_URL = Constants.OTHER_PROFILE_BASE_URL;

    private By profileAvatar = By.className("profile-avatar");
    private By profileName = By.className("profile-name");
    private By profileMeta = By.className("profile-meta");
    private By aboutSectionHeader = By.xpath("//h2[text()='About']");
    private By bioText = By.xpath("//div[@class='profile-section']/p");
    private By phoneSection = By.xpath("//h2[text()='Phone']");
    private By emailSection = By.xpath("//h2[text()='Email']");
    private By errorMessage = By.xpath("//div[contains(@class, 'error')]|//h1[contains(text(), '404')]|//*[contains(text(), 'User not found')]");

    public OtherProfilePage(WebDriver driver) {
        this.driver = driver;
    }

    public void navigateTo(String userId) {
        driver.get(BASE_URL + userId);
    }

    public boolean isAvatarDisplayed() {
        return driver.findElement(profileAvatar).isDisplayed();
    }

    public String getAvatarText() {
        return driver.findElement(profileAvatar).getText();
    }

    public String getProfileName() {
        return driver.findElement(profileName).getText();
    }

    public String getProfileMeta() {
        return driver.findElement(profileMeta).getText();
    }

    public String getBio() {
        return driver.findElement(bioText).getText();
    }

    public boolean isPhoneSectionVisible() {
        return !driver.findElements(phoneSection).isEmpty();
    }

    public boolean isEmailSectionVisible() {
        return !driver.findElements(emailSection).isEmpty();
    }

    public boolean is404Displayed() {
        // Simple check for 404 text or an error container
        return driver.getPageSource().contains("404") || driver.getPageSource().contains("User not found") || !driver.findElements(errorMessage).isEmpty();
    }
}
