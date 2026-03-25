package com.staybnb.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import com.staybnb.utils.Constants;

public class PropertyListingPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private final String BASE_URL = Constants.DOMAIN;

    // Locators
    private By propertyGrid = By.className("property-grid");
    private By propertyCard = By.className("property-card");
    private By cardImage = By.cssSelector(".property-card-image img");
    private By cardTitle = By.className("property-card-title");
    private By cardLocation = By.className("property-card-location");
    private By cardPrice = By.className("property-card-price");
    private By cardRating = By.className("property-card-rating");
    private By propertyListControls = By.className("property-list-controls");
    
    // Potential search/filter elements (to verify absence)
    private By searchInput = By.cssSelector("input[type='search']");
    private By filterButtons = By.cssSelector("button.filter-btn");
    private By sortSelect = By.tagName("select");

    public PropertyListingPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void navigateTo(String slug) {
        driver.get(Constants.BASE_URL + "/properties");
    }

    public List<WebElement> getPropertyCards() {
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(propertyCard));
    }

    public boolean isPropertyCardDisplayed(WebElement card) {
        return card.isDisplayed();
    }

    public boolean hasImage(WebElement card) {
        return !card.findElements(cardImage).isEmpty() && card.findElement(cardImage).isDisplayed();
    }

    public String getTitle(WebElement card) {
        return card.findElement(cardTitle).getText();
    }

    public String getLocation(WebElement card) {
        return card.findElement(cardLocation).getText();
    }

    public String getPrice(WebElement card) {
        return card.findElement(cardPrice).getText();
    }

    public boolean hasRating(WebElement card) {
        return !card.findElements(cardRating).isEmpty();
    }

    public String getRatingText(WebElement card) {
        if (hasRating(card)) {
            return card.findElement(cardRating).getText();
        }
        return null;
    }

    public void clickPropertyCard(WebElement card) {
        card.click();
    }

    public int getGridColumnCount() {
        WebElement grid = driver.findElement(propertyGrid);
        String gridTemplate = grid.getCssValue("grid-template-columns");
        System.out.println("DEBUG: grid-template-columns = " + gridTemplate);
        if (gridTemplate == null || gridTemplate.isEmpty() || gridTemplate.equals("none")) return 1;
        return gridTemplate.split(" ").length;
    }

    public void waitForGridColumns(int expectedCount) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(driver -> getGridColumnCount() == expectedCount);
    }

    public boolean areControlsEmpty() {
        WebElement controls = driver.findElement(propertyListControls);
        return controls.findElements(By.xpath("./*")).isEmpty();
    }

    public boolean hasSearchOrFilters() {
        return !driver.findElements(searchInput).isEmpty() || 
               !driver.findElements(filterButtons).isEmpty() || 
               !driver.findElements(sortSelect).isEmpty();
    }
    
    public String getCardHref(WebElement card) {
        return card.getAttribute("href");
    }
}
