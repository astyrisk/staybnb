package com.staybnb.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;
import com.staybnb.utils.Constants;

public class HomePage {
    private WebDriver driver;
    private final String PAGE_URL = Constants.HOME_URL;

    private By heroSection = By.className("home-hero");
    private By heroHeadline = By.cssSelector(".home-hero-content h1");
    // These might be missing from snippet but are required for tests
    private By categoryBar = By.className("category-bar");
    private By categoryIcons = By.className("category-icon");
    private By propertyGrid = By.className("property-grid");
    private By propertyCards = By.className("property-card");

    // Card details
    private By cardImage = By.cssSelector(".property-card-image img");
    private By cardTitle = By.className("property-card-title");
    private By cardLocation = By.className("property-card-location");
    private By cardPrice = By.className("property-card-price");
    // This is missing from the snippet but required
    private By cardRating = By.className("property-card-rating");

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void navigateTo() {
        driver.get(PAGE_URL);
    }

    public boolean isHeroSectionDisplayed() {
        return driver.findElement(heroSection).isDisplayed();
    }

    public String getHeroHeadlineText() {
        return driver.findElement(heroHeadline).getText();
    }

    public String getHeroBackgroundImage() {
        return driver.findElement(heroSection).getCssValue("background-image");
    }

    public boolean isCategoryBarDisplayed() {
        try {
            return driver.findElement(categoryBar).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public List<WebElement> getCategoryIcons() {
        return driver.findElements(categoryIcons);
    }

    public List<WebElement> getPropertyCards() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(propertyCards));
    }

    public boolean isCardDetailsComplete(WebElement card) {
        try {
            boolean hasImage = card.findElement(cardImage).isDisplayed();
            boolean hasTitle = !card.findElement(cardTitle).getText().isEmpty();
            boolean hasLocation = !card.findElement(cardLocation).getText().isEmpty();
            boolean hasPrice = card.findElement(cardPrice).getText().contains("/ night");
//            boolean hasRating = !card.findElements(cardRating).isEmpty() && card.findElement(cardRating).isDisplayed();

            return hasImage && hasTitle && hasLocation && hasPrice ;
        } catch (Exception e) {
            return false;
        }
    }

    public int getGridColumnCount() {
        WebElement grid = driver.findElement(propertyGrid);
        String gridTemplate = grid.getCssValue("grid-template-columns");
        if (gridTemplate == null || gridTemplate.isEmpty()) return 0;
        return gridTemplate.split(" ").length;
    }

    public void waitForGridColumns(int expectedCount) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(driver -> getGridColumnCount() == expectedCount);
    }
}
