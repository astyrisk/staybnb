package com.staybnb.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import com.staybnb.utils.Constants;

public class OwnProfilePage {
    private WebDriver driver;
    private final String PAGE_URL = Constants.PROFILE_URL;

    // --- Locators based on provided HTML ---
    private By profileAvatar = By.className("profile-avatar");
    private By profileName = By.className("profile-name");
    private By profileMeta = By.className("profile-meta");
    private By bioText = By.xpath("//h2[text()='About']/following-sibling::p");
    private By phoneText = By.xpath("//h2[text()='Phone']/following-sibling::p");
    private By emailText = By.xpath("//h2[text()='Email']/following-sibling::p");
    private By editProfileButton = By.xpath("//div[@class='profile-actions']/a[contains(text(), 'Edit Profile')]");

    public OwnProfilePage(WebDriver driver) {
        this.driver = driver;
    }

    public void navigateTo() {
        driver.get(PAGE_URL);
    }

    public boolean isAvatarDisplayed() {
        return driver.findElement(profileAvatar).isDisplayed();
    }

    public String getAvatarStyle() {
        return driver.findElement(profileAvatar).getAttribute("class"); // Usually for checking large/circular via CSS classes
    }

    public String getFullName() {
        return driver.findElement(profileName).getText();
    }

    public String getProfileMeta() {
        return driver.findElement(profileMeta).getText();
    }

    public String getBio() {
        return driver.findElement(profileBioLocator()).getText();
    }

    private By profileBioLocator() {
        // Bio is under 'About' section
        return By.xpath("//div[h2='About']/p");
    }

    public String getPhone() {
        return driver.findElement(By.xpath("//div[h2='Phone']/p")).getText();
    }

    public String getEmail() {
        return driver.findElement(By.xpath("//div[h2='Email']/p")).getText();
    }

    public void clickEditProfile() {
        driver.findElement(editProfileButton).click();
    }

    public boolean isEditProfileButtonVisible() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(editProfileButton)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
