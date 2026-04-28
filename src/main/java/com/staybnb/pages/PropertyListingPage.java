package com.staybnb.pages;

import com.staybnb.components.PropertyCard;
import com.staybnb.components.PropertyGrid;
import com.staybnb.locators.Locators;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;
import com.staybnb.config.AppConstants;

public class PropertyListingPage extends BasePage {
    private final PropertyGrid propertyGrid;

    public PropertyListingPage(WebDriver driver) {
        super(driver);
        this.propertyGrid = new PropertyGrid(driver);
    }

    // ── Navigation ────────────────────────────────────────────────────────────

    public PropertyListingPage navigateTo() {
        super.navigateTo(AppConstants.PROPERTY_LISTING_URL);
        waitForGridToLoad();
        return this;
    }

    public PropertyListingPage navigateToWithCity(String city) {
        return navigateWithQuery("city=" + URLEncoder.encode(city, StandardCharsets.UTF_8));
    }

    public PropertyListingPage navigateToWithDates(String checkIn, String checkOut) {
        return navigateWithQuery("checkIn=" + checkIn + "&checkOut=" + checkOut);
    }

    public PropertyListingPage navigateToWithGuests(int guests) {
        return navigateWithQuery("guests=" + guests);
    }

    public PropertyListingPage navigateToWithPriceRange(int min, int max) {
        return navigateWithQuery("minPrice=" + min + "&maxPrice=" + max);
    }

    public PropertyListingPage navigateToWithPropertyType(String type) {
        return navigateWithQuery("propertyType=" + type);
    }

    public PropertyListingPage navigateToWithCategory(String categoryId) {
        return navigateWithQuery("categoryId=" + categoryId);
    }

    public PropertyListingPage navigateToWithAmenity(String amenityId) {
        return navigateWithQuery("amenities=" + amenityId);
    }

    public PropertyListingPage navigateToWithBedrooms(int count) {
        return navigateWithQuery("minBedrooms=" + count);
    }

    public PropertyListingPage navigateToWithBathrooms(int count) {
        return navigateWithQuery("minBathrooms=" + count);
    }

    public PropertyListingPage navigateToWithCombinedAmenityBedroomBathroomPriceFilters(
            String amenityId, int bedrooms, int bathrooms, int minPrice, int maxPrice) {
        return navigateWithQuery(
                "amenities=" + amenityId
                + "&minBedrooms=" + bedrooms
                + "&minBathrooms=" + bathrooms
                + "&minPrice=" + minPrice
                + "&maxPrice=" + maxPrice);
    }

    public PropertyListingPage navigateToWithSort(String sortValue) {
        return navigateWithQuery("sort=" + sortValue);
    }

    public PropertyListingPage navigateToPage(int page) {
        return navigateWithQuery("page=" + page);
    }

    // ── Cards ─────────────────────────────────────────────────────────────────

    public List<PropertyCard> getCards() {
        return waitForElementsPresent(Locators.PropertyListing.PROPERTY_CARD).stream()
                .map(el -> new PropertyCard(driver, el))
                .collect(Collectors.toList());
    }

    public PropertyCard getFirstCard() {
        return new PropertyCard(driver, waitForElementsPresent(Locators.PropertyListing.PROPERTY_CARD).getFirst());
    }

    public PropertyListingPage clickFavoriteOnFirstCard() {
        getFirstCard().clickFavorite();
        return this;
    }

    public PropertyListingPage clickFavoriteOnCardById(String propertyId) {
        WebElement card = waitForElementVisible(cardByIdLocator(propertyId));
        js().executeScript("arguments[0].click();", card.findElement(Locators.PropertyListing.CARD_FAVORITE_BTN));
        return this;
    }

    // ── Grid ──────────────────────────────────────────────────────────────────

    public int getGridColumnCount() {
        return propertyGrid.getColumnCount();
    }

    public PropertyListingPage setWindowSize(int width, int height) {
        driver.manage().window().setSize(new Dimension(width, height));
        return this;
    }

    // ── Filters ───────────────────────────────────────────────────────────────

    public PropertyListingPage setPriceRange(int min, int max) {
        WebElement minInput = waitForElementVisible(Locators.FilterSidebar.PRICE_MIN_INPUT);
        minInput.clear();
        minInput.sendKeys(String.valueOf(min));

        WebElement maxInput = waitForElementVisible(Locators.FilterSidebar.PRICE_MAX_INPUT);
        maxInput.clear();
        maxInput.sendKeys(String.valueOf(max));
        maxInput.sendKeys(Keys.TAB);
        return this;
    }

    public PropertyListingPage selectPropertyType(String value) {
        waitForElementClickable(Locators.FilterSidebar.propertyTypeRadio(value)).click();
        return this;
    }

    public PropertyListingPage selectCategory(String categoryId) {
        new Select(waitForElementVisible(Locators.FilterSidebar.CATEGORY_SELECT)).selectByValue(categoryId);
        return this;
    }

    public PropertyListingPage selectSortOption(String value) {
        new Select(waitForElementVisible(Locators.PropertyListing.SORT_SELECT)).selectByValue(value);
        return this;
    }

    public PropertyListingPage checkAmenityByName(String name) {
        jsScrollAndClick(waitForElementVisible(Locators.FilterSidebar.amenityCheckbox(name)));
        return this;
    }

    public PropertyListingPage setMinBedrooms(int count) {
        WebElement input = waitForElementVisible(Locators.FilterSidebar.BEDROOMS_INPUT);
        js().executeScript("arguments[0].scrollIntoView({block:'center'});", input);
        input.click();
        input.sendKeys(Keys.chord(Keys.CONTROL, "a"), String.valueOf(count));
        input.sendKeys(Keys.TAB);
        return this;
    }

    public PropertyListingPage setMinBathrooms(int count) {
        WebElement input = waitForElementVisible(Locators.FilterSidebar.BATHROOMS_INPUT);
        js().executeScript("arguments[0].scrollIntoView({block:'center'});", input);
        input.click();
        input.sendKeys(Keys.chord(Keys.CONTROL, "a"), String.valueOf(count));
        input.sendKeys(Keys.TAB);
        return this;
    }

    public PropertyListingPage clearAllFilters() {
        waitForElementClickable(Locators.FilterSidebar.CLEAR_ALL_FILTERS_BTN).click();
        return this;
    }

    public PropertyListingPage clickMobileFilterButton() {
        waitForElementClickable(Locators.FilterSidebar.MOBILE_FILTER_BTN).click();
        return this;
    }

    // ── Pagination ────────────────────────────────────────────────────────────

    public PropertyListingPage clickNextPage() {
        waitForElementClickable(Locators.PropertyListing.PAGINATION_NEXT_BTN).click();
        return this;
    }

    public PropertyListingPage clickPreviousPage() {
        waitForElementClickable(Locators.PropertyListing.PAGINATION_PREV_BTN).click();
        return this;
    }

    // ── Queries / state ───────────────────────────────────────────────────────

    public boolean hasSearchOrFilters() {
        return !driver.findElements(Locators.PropertyListing.SEARCH_INPUT).isEmpty()
                || !driver.findElements(Locators.PropertyListing.FILTER_BUTTONS).isEmpty()
                || !driver.findElements(Locators.PropertyListing.SORT_SELECT).isEmpty();
    }

    public boolean isEmptyStateDisplayed() {
        return isDisplayed(Locators.PropertyListing.EMPTY_STATE);
    }

    public boolean isPreviousButtonDisabled() {
        return waitForElementVisible(Locators.PropertyListing.PAGINATION_PREV_BTN).getAttribute("disabled") != null;
    }

    public boolean isNextButtonDisabled() {
        return waitForElementVisible(Locators.PropertyListing.PAGINATION_NEXT_BTN).getAttribute("disabled") != null;
    }

    public boolean isMobileFilterButtonDisplayed() {
        return isDisplayed(Locators.FilterSidebar.MOBILE_FILTER_BTN);
    }

    public boolean isMobileFilterModalDisplayed() {
        return isDisplayed(Locators.FilterSidebar.MOBILE_FILTER_MODAL);
    }

    public boolean isCardFavoritedById(String propertyId) {
        List<WebElement> cards = driver.findElements(cardByIdLocator(propertyId));
        if (cards.isEmpty()) return false;
        return !cards.getFirst().findElements(Locators.PropertyListing.CARD_FAVORITE_FAV_BTN).isEmpty();
    }

    public int getPropertiesCount() {
        return Integer.parseInt(waitForElementVisible(Locators.PropertyListing.PROPERTIES_COUNT).getText().split(" ")[0]);
    }

    public String getPaginationInfoText() {
        return waitForElementVisible(Locators.PropertyListing.PAGINATION_INFO).getText();
    }

    public List<String> getSortOptionTexts() {
        return new Select(waitForElementVisible(Locators.PropertyListing.SORT_SELECT)).getOptions().stream()
                .map(WebElement::getText)
                .filter(t -> !t.isBlank())
                .collect(Collectors.toList());
    }

    public List<Integer> getVisibleCardPrices() {
        return getCards().stream()
                .map(PropertyCard::getPriceAmount)
                .collect(Collectors.toList());
    }

    // ── Waits ─────────────────────────────────────────────────────────────────

    public PropertyListingPage waitForSearchResults() {
        wait.until(d ->
                !d.findElements(Locators.PropertyListing.PROPERTY_GRID).isEmpty() ||
                !d.findElements(Locators.PropertyListing.EMPTY_STATE).isEmpty()
        );
        return this;
    }

    public PropertyListingPage waitForGridColumns(int expectedCount) {
        propertyGrid.waitForColumns(expectedCount);
        return this;
    }

    public PropertyListingPage waitForPriceFilterToApply() {
        waitForUrlContains("minPrice=");
        return this;
    }

    public PropertyListingPage waitForPropertyTypeFilterToApply() {
        waitForUrlContains("propertyType=");
        return this;
    }

    public PropertyListingPage waitForCategoryFilterToApply() {
        waitForUrlContains("categoryId=");
        return this;
    }

    public PropertyListingPage waitForFiltersCleared() {
        wait.until(d -> !d.getCurrentUrl().contains("propertyType=") && !d.getCurrentUrl().contains("categoryId="));
        return this;
    }

    public PropertyListingPage waitForSortToApply() {
        waitForUrlContains("sort=");
        return this;
    }

    public PropertyListingPage waitForSortParamRemoved() {
        wait.until(d -> !d.getCurrentUrl().contains("sort="));
        return this;
    }

    public PropertyListingPage waitForPageInUrl(int page) {
        waitForUrlContains("page=" + page);
        return this;
    }

    public PropertyListingPage waitForPageParamRemoved() {
        wait.until(d -> !d.getCurrentUrl().contains("page="));
        return this;
    }

    public PropertyListingPage waitForCountToChangeTo(int previousCount) {
        wait.until(d -> Integer.parseInt(
                d.findElement(Locators.PropertyListing.PROPERTIES_COUNT).getText().split(" ")[0]) != previousCount);
        return this;
    }

    public PropertyListingPage waitForCardFavoritedById(String propertyId) {
        By locator = cardByIdLocator(propertyId);
        wait.until(d -> {
            List<WebElement> cards = d.findElements(locator);
            return !cards.isEmpty() && !cards.getFirst().findElements(Locators.PropertyListing.CARD_FAVORITE_FAV_BTN).isEmpty();
        });
        return this;
    }

    public PropertyListingPage waitForCardUnfavoritedById(String propertyId) {
        By locator = cardByIdLocator(propertyId);
        wait.until(d -> {
            List<WebElement> cards = d.findElements(locator);
            return !cards.isEmpty() && cards.getFirst().findElements(Locators.PropertyListing.CARD_FAVORITE_FAV_BTN).isEmpty();
        });
        return this;
    }

    // ── Private helpers ───────────────────────────────────────────────────────

    private PropertyListingPage navigateWithQuery(String query) {
        super.navigateTo(AppConstants.PROPERTY_LISTING_URL + "?" + query);
        waitForSearchResults();
        return this;
    }

    private JavascriptExecutor js() {
        return (JavascriptExecutor) driver;
    }

    private void jsScrollAndClick(WebElement element) {
        js().executeScript("arguments[0].scrollIntoView({block:'center'});", element);
        js().executeScript("arguments[0].click();", element);
    }

    private static By cardByIdLocator(String propertyId) {
        return By.cssSelector("a.property-card[href*='/properties/" + propertyId + "']");
    }

    private void waitForGridToLoad() {
        waitForSearchResults();
        waitForElementsPresent(Locators.PropertyListing.PROPERTY_CARD);
    }
}
