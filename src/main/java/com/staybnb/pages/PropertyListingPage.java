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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import com.staybnb.config.AppConstants;

public class PropertyListingPage extends BasePage {
    private static final String PAGE_URL = AppConstants.PROPERTY_LISTING_URL;

    public PropertyListingPage(WebDriver driver) {
        super(driver);
    }

    public void navigateTo() {
        super.navigateTo(AppConstants.PROPERTY_LISTING_URL);
        waitForGridToLoad();
    }

    public List<WebElement> getPropertyCards() {
        return waitForElementsPresent(Locators.PropertyListing.PROPERTY_CARD);
    }

    public boolean hasImage(WebElement card) {
        return !card.findElements(Locators.PropertyListing.CARD_IMAGE).isEmpty()
                && card.findElement(Locators.PropertyListing.CARD_IMAGE).isDisplayed();
    }

    public String getTitle(WebElement card) {
        return card.findElement(Locators.PropertyListing.CARD_TITLE).getText();
    }

    public String getLocation(WebElement card) {
        return card.findElement(Locators.PropertyListing.CARD_LOCATION).getText();
    }

    public String getPrice(WebElement card) {
        return card.findElement(Locators.PropertyListing.CARD_PRICE).getText();
    }

    public void clickPropertyCard(WebElement card) {
        card.click();
    }

    public int getGridColumnCount() {
        WebElement grid = driver.findElement(Locators.PropertyListing.PROPERTY_GRID);
        String gridTemplate = grid.getCssValue("grid-template-columns");
        if (gridTemplate == null || gridTemplate.isEmpty() || gridTemplate.equals("none")) return 1;
        return gridTemplate.split(" ").length;
    }

    public void waitForGridColumns(int expectedCount) {
        wait.until(driver -> getGridColumnCount() == expectedCount);
    }

    public boolean hasSearchOrFilters() {
        return !driver.findElements(Locators.PropertyListing.SEARCH_INPUT).isEmpty()
                || !driver.findElements(Locators.PropertyListing.FILTER_BUTTONS).isEmpty()
                || !driver.findElements(Locators.PropertyListing.SORT_SELECT).isEmpty();
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
        return isDisplayed(Locators.PropertyListing.EMPTY_STATE);
    }

    public int getPropertiesCount() {
        String text = waitForElementVisible(Locators.PropertyListing.PROPERTIES_COUNT).getText();
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

    public void checkAmenityByName(String name) {
        WebElement label = waitForElementVisible(Locators.FilterSidebar.amenityCheckbox(name));
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", label);
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", label);
    }

    public void setMinBedrooms(int count) {
        WebElement input = waitForElementVisible(Locators.FilterSidebar.BEDROOMS_INPUT);
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", input);
        input.click();
        input.sendKeys(Keys.chord(Keys.CONTROL, "a"), String.valueOf(count));
        input.sendKeys(Keys.TAB);
    }

    public void setMinBathrooms(int count) {
        WebElement input = waitForElementVisible(Locators.FilterSidebar.BATHROOMS_INPUT);
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", input);
        input.click();
        input.sendKeys(Keys.chord(Keys.CONTROL, "a"), String.valueOf(count));
        input.sendKeys(Keys.TAB);
    }

    public void waitForCountToChangeTo(int previousCount) {
        wait.until(d -> {
            String text = d.findElement(Locators.PropertyListing.PROPERTIES_COUNT).getText();
            return Integer.parseInt(text.split(" ")[0]) != previousCount;
        });
    }

    public void navigateToWithAmenity(String amenityId) {
        super.navigateTo(PAGE_URL + "?amenities=" + amenityId);
        waitForSearchResults();
    }

    public void navigateToWithBedrooms(int count) {
        super.navigateTo(PAGE_URL + "?minBedrooms=" + count);
        waitForSearchResults();
    }

    public void navigateToWithBathrooms(int count) {
        super.navigateTo(PAGE_URL + "?minBathrooms=" + count);
        waitForSearchResults();
    }

    public void navigateToWithCombinedAmenityBedroomBathroomPriceFilters(
            String amenityId, int bedrooms, int bathrooms, int minPrice, int maxPrice) {
        super.navigateTo(PAGE_URL
                + "?amenities=" + amenityId
                + "&minBedrooms=" + bedrooms
                + "&minBathrooms=" + bathrooms
                + "&minPrice=" + minPrice
                + "&maxPrice=" + maxPrice);
        waitForSearchResults();
    }

    public List<String> getSortOptionTexts() {
        WebElement selectEl = waitForElementVisible(Locators.PropertyListing.SORT_SELECT);
        return new Select(selectEl).getOptions().stream()
                .map(WebElement::getText)
                .filter(t -> !t.isBlank())
                .collect(Collectors.toList());
    }

    public void selectSortOption(String value) {
        new Select(waitForElementVisible(Locators.PropertyListing.SORT_SELECT)).selectByValue(value);
    }

    public void waitForSortToApply() {
        waitForUrlContains("sort=");
    }

    public void waitForSortParamRemoved() {
        wait.until(d -> !d.getCurrentUrl().contains("sort="));
    }

    public void navigateToWithSort(String sortValue) {
        super.navigateTo(PAGE_URL + "?sort=" + sortValue);
        waitForSearchResults();
    }

    public List<Integer> getVisibleCardPrices() {
        return getPropertyCards().stream()
                .map(this::getPriceAmount)
                .collect(Collectors.toList());
    }

    public double getCardRating(WebElement card) {
        List<WebElement> ratingEls = card.findElements(Locators.PropertyListing.CARD_RATING);
        if (ratingEls.isEmpty()) return -1.0;
        Matcher m = Pattern.compile("(\\d+\\.\\d+)").matcher(ratingEls.get(0).getText());
        return m.find() ? Double.parseDouble(m.group(1)) : -1.0;
    }

    public String getPaginationInfoText() {
        return waitForElementVisible(Locators.PropertyListing.PAGINATION_INFO).getText();
    }

    public void clickNextPage() {
        waitForElementClickable(Locators.PropertyListing.PAGINATION_NEXT_BTN).click();
    }

    public void clickPreviousPage() {
        waitForElementClickable(Locators.PropertyListing.PAGINATION_PREV_BTN).click();
    }

    public boolean isPreviousButtonDisabled() {
        return waitForElementVisible(Locators.PropertyListing.PAGINATION_PREV_BTN).getAttribute("disabled") != null;
    }

    public boolean isNextButtonDisabled() {
        return waitForElementVisible(Locators.PropertyListing.PAGINATION_NEXT_BTN).getAttribute("disabled") != null;
    }

    public void navigateToPage(int page) {
        super.navigateTo(PAGE_URL + "?page=" + page);
        waitForSearchResults();
    }

    public void waitForPageInUrl(int page) {
        waitForUrlContains("page=" + page);
    }

    public void waitForPageParamRemoved() {
        wait.until(d -> !d.getCurrentUrl().contains("page="));
    }

    public void clickFavoriteOnFirstCard() {
        List<WebElement> cards = getPropertyCards();
        WebElement btn = cards.get(0).findElement(Locators.PropertyListing.CARD_FAVORITE_BTN);
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
    }

    public void clickFavoriteOnCardById(String propertyId) {
        By cardLocator = By.cssSelector("a.property-card[href*='/properties/" + propertyId + "']");
        WebElement card = waitForElementVisible(cardLocator);
        WebElement btn = card.findElement(Locators.PropertyListing.CARD_FAVORITE_BTN);
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
    }

    public boolean isCardFavoritedById(String propertyId) {
        By cardLocator = By.cssSelector("a.property-card[href*='/properties/" + propertyId + "']");
        List<WebElement> cards = driver.findElements(cardLocator);
        if (cards.isEmpty()) return false;
        return !cards.get(0).findElements(Locators.PropertyListing.CARD_FAVORITE_FAV_BTN).isEmpty();
    }

    public void waitForCardFavoritedById(String propertyId) {
        By cardLocator = By.cssSelector("a.property-card[href*='/properties/" + propertyId + "']");
        wait.until(d -> {
            List<WebElement> cards = d.findElements(cardLocator);
            return !cards.isEmpty() &&
                    !cards.get(0).findElements(Locators.PropertyListing.CARD_FAVORITE_FAV_BTN).isEmpty();
        });
    }

    public void waitForCardUnfavoritedById(String propertyId) {
        By cardLocator = By.cssSelector("a.property-card[href*='/properties/" + propertyId + "']");
        wait.until(d -> {
            List<WebElement> cards = d.findElements(cardLocator);
            return !cards.isEmpty() &&
                    cards.get(0).findElements(Locators.PropertyListing.CARD_FAVORITE_FAV_BTN).isEmpty();
        });
    }

    public boolean isMobileFilterButtonDisplayed() {
        return isDisplayed(Locators.FilterSidebar.MOBILE_FILTER_BTN);
    }

    public void clickMobileFilterButton() {
        waitForElementClickable(Locators.FilterSidebar.MOBILE_FILTER_BTN).click();
    }

    public boolean isMobileFilterModalDisplayed() {
        return isDisplayed(Locators.FilterSidebar.MOBILE_FILTER_MODAL);
    }

    private void waitForGridToLoad() {
        waitForSearchResults();
        waitForElementsPresent(Locators.PropertyListing.PROPERTY_CARD);
    }
}
