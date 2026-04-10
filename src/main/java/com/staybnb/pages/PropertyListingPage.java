package com.staybnb.pages;

import com.staybnb.locators.Locators;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import com.staybnb.config.AppConstants;

public class PropertyListingPage extends BasePage {
    private final String PAGE_URL = AppConstants.PROPERTY_LISTING_URL;

    private By propertyGrid = Locators.PropertyListing.PROPERTY_GRID;
    private By propertyCard = Locators.PropertyListing.PROPERTY_CARD;
    private By cardImage = Locators.PropertyListing.CARD_IMAGE;
    private By cardTitle = Locators.PropertyListing.CARD_TITLE;
    private By cardLocation = Locators.PropertyListing.CARD_LOCATION;
    private By cardPrice = Locators.PropertyListing.CARD_PRICE;
    private By cardRating = Locators.PropertyListing.CARD_RATING;
    private By propertyListControls = Locators.PropertyListing.PROPERTY_LIST_CONTROLS;

    private By searchInput = Locators.PropertyListing.SEARCH_INPUT;
    private By filterButtons = Locators.PropertyListing.FILTER_BUTTONS;
    private By sortSelect = Locators.PropertyListing.SORT_SELECT;
    private By emptyState = Locators.PropertyListing.EMPTY_STATE;
    private By propertiesCount = Locators.PropertyListing.PROPERTIES_COUNT;

    public PropertyListingPage(WebDriver driver) {
        super(driver);
    }

    public void navigateTo() {
        super.navigateTo(PAGE_URL);
        waitForGridToLoad();
    }

    public List<WebElement> getPropertyCards() {
        return waitForElementsPresent(propertyCard);
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
        if (gridTemplate == null || gridTemplate.isEmpty() || gridTemplate.equals("none")) return 1;
        return gridTemplate.split(" ").length;
    }

    public void waitForGridColumns(int expectedCount) {
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

    public void navigateToWithCity(String city) {
        String encoded = URLEncoder.encode(city, StandardCharsets.UTF_8);
        super.navigateTo(PAGE_URL + "?city=" + encoded);
        waitForSearchResults();
    }

    public boolean isEmptyStateDisplayed() {
        return isDisplayed(emptyState);
    }

    public int getPropertiesCount() {
        String text = waitForElementVisible(propertiesCount).getText();
        return Integer.parseInt(text.split(" ")[0]);
    }

    public void waitForSearchResults() {
        wait.until(d ->
                !d.findElements(Locators.PropertyListing.PROPERTY_GRID).isEmpty() ||
                !d.findElements(Locators.PropertyListing.EMPTY_STATE).isEmpty()
        );
    }

    private void waitForGridToLoad() {
        waitForElementVisible(propertyGrid);
        waitForElementsPresent(propertyCard);
    }
}
