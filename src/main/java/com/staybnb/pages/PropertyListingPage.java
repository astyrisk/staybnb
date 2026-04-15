package com.staybnb.pages;

import com.staybnb.locators.Locators;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import com.staybnb.config.AppConstants;

public class PropertyListingPage extends BasePage {
    private final String PAGE_URL = AppConstants.PROPERTY_LISTING_URL;

    private final By propertyGrid = Locators.PropertyListing.PROPERTY_GRID;
    private final By propertyCard = Locators.PropertyListing.PROPERTY_CARD;
    private final By cardImage = Locators.PropertyListing.CARD_IMAGE;
    private final By cardTitle = Locators.PropertyListing.CARD_TITLE;
    private final By cardLocation = Locators.PropertyListing.CARD_LOCATION;
    private final By cardPrice = Locators.PropertyListing.CARD_PRICE;
    private final By cardRating = Locators.PropertyListing.CARD_RATING;
    private final By propertyListControls = Locators.PropertyListing.PROPERTY_LIST_CONTROLS;

    private final By searchInput = Locators.PropertyListing.SEARCH_INPUT;
    private final By filterButtons = Locators.PropertyListing.FILTER_BUTTONS;
    private final By sortSelect = Locators.PropertyListing.SORT_SELECT;
    private final By emptyState = Locators.PropertyListing.EMPTY_STATE;
    private final By propertiesCount = Locators.PropertyListing.PROPERTIES_COUNT;

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

    public void navigateToWithDates(String checkIn, String checkOut) {
        super.navigateTo(PAGE_URL + "?checkIn=" + checkIn + "&checkOut=" + checkOut);
        waitForSearchResults();
    }

    public void navigateToWithGuests(int guests) {
        super.navigateTo(PAGE_URL + "?guests=" + guests);
        waitForSearchResults();
    }

    public void navigateToWithPriceRange(int min, int max) {
        super.navigateTo(PAGE_URL + "?minPrice=" + min + "&maxPrice=" + max);
        waitForSearchResults();
    }

    public void setPriceRange(int min, int max) {
        WebElement minInput = waitForElementVisible(Locators.FilterSidebar.PRICE_MIN_INPUT);
        minInput.clear();
        minInput.sendKeys(String.valueOf(min));

        WebElement maxInput = waitForElementVisible(Locators.FilterSidebar.PRICE_MAX_INPUT);
        maxInput.clear();
        maxInput.sendKeys(String.valueOf(max));
        maxInput.sendKeys(Keys.TAB);
    }

    public void waitForPriceFilterToApply() {
        waitForUrlContains("minPrice=");
    }

    public int getPriceAmount(WebElement card) {
        String text = card.findElement(Locators.PropertyListing.CARD_PRICE_AMOUNT).getText();
        return Integer.parseInt(text.replaceAll("[^0-9]", ""));
    }

    public boolean isEmptyStateDisplayed() {
        return isDisplayed(emptyState);
    }

    public int getPropertiesCount() {
        String text = waitForElementVisible(propertiesCount).getText();
        return Integer.parseInt(text.split(" ")[0]);
    }

    public void navigateToWithPropertyType(String type) {
        super.navigateTo(PAGE_URL + "?propertyType=" + type);
        waitForSearchResults();
    }

    public void navigateToWithCategory(String categoryId) {
        super.navigateTo(PAGE_URL + "?categoryId=" + categoryId);
        waitForSearchResults();
    }

    public void selectPropertyType(String value) {
        waitForElementClickable(Locators.FilterSidebar.propertyTypeRadio(value)).click();
    }

    public void selectCategory(String categoryId) {
        WebElement selectEl = waitForElementVisible(Locators.FilterSidebar.CATEGORY_SELECT);
        new Select(selectEl).selectByValue(categoryId);
    }

    public void waitForPropertyTypeFilterToApply() {
        waitForUrlContains("propertyType=");
    }

    public void waitForCategoryFilterToApply() {
        waitForUrlContains("categoryId=");
    }

    public void clearAllFilters() {
        waitForElementClickable(Locators.FilterSidebar.CLEAR_ALL_FILTERS_BTN).click();
    }

    public void waitForFiltersCleared() {
        wait.until(d -> {
            String url = d.getCurrentUrl();
            return !url.contains("propertyType=") && !url.contains("categoryId=");
        });
    }

    public void waitForSearchResults() {
        wait.until(d ->
                !d.findElements(Locators.PropertyListing.PROPERTY_GRID).isEmpty() ||
                !d.findElements(Locators.PropertyListing.EMPTY_STATE).isEmpty()
        );
    }

    private void waitForGridToLoad() {
        waitForSearchResults();
        waitForElementsPresent(propertyCard);
    }
}
