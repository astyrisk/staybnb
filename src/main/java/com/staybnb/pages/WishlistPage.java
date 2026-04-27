package com.staybnb.pages;

import com.staybnb.config.AppConstants;
import com.staybnb.locators.Locators;
import io.restassured.http.ContentType;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WishlistPage extends BasePage {

    public WishlistPage(WebDriver driver) {
        super(driver);
    }

    public void navigateTo() {
        super.navigateTo(AppConstants.WISHLIST_URL);
        waitForPageToLoad();
    }

    public int getPropertyCardCount() {
        return driver.findElements(Locators.Wishlist.PROPERTY_CARDS).size();
    }

    public void clickFavoriteOnFirstCard() {
        List<WebElement> cards = waitForElementsPresent(Locators.Wishlist.PROPERTY_CARDS);
        WebElement btn = cards.getFirst().findElement(Locators.Wishlist.CARD_FAVORITE_BTN);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
    }

    public void waitForCardCountToDecrease(int countBefore) {
        wait.until(d -> d.findElements(Locators.Wishlist.PROPERTY_CARDS).size() < countBefore);
    }

    public long addToWishlistViaApi(String propertyId) {
        return apiRequest()
                .contentType(ContentType.JSON)
                .body("{\"propertyId\":" + propertyId + "}")
                .post("/wishlists")
                .statusCode();
    }

    public long removeFromWishlistViaApi(String propertyId) {
        return apiRequest()
                .delete("/wishlists/" + propertyId)
                .statusCode();
    }

    public String getFirstPropertyIdViaApi() {
        String body = apiRequest().get("/properties?limit=1").asString();
        Matcher m = Pattern.compile("\"id\"\\s*:\\s*(\\d+)").matcher(body);
        if (m.find()) return m.group(1);
        throw new RuntimeException("Could not retrieve first property ID from API");
    }

    public void clearWishlistViaApi() {
        String body = apiRequest().get("/wishlists").asString();
        List<String> ids = extractWishlistPropertyIds(body);
        log.info("clearWishlistViaApi: found {} item(s) to remove — ids: {}", ids.size(), ids);
        for (String id : ids) {
            removeFromWishlistViaApi(id);
        }
    }

    private List<String> extractWishlistPropertyIds(String body) {
        List<String> ids = new ArrayList<>();
        Matcher m = Pattern.compile("\"propertyId\"\\s*:\\s*(\\d+)").matcher(body);
        while (m.find()) ids.add(m.group(1));
        if (!ids.isEmpty()) return ids;
        m = Pattern.compile("\"id\"\\s*:\\s*(\\d+)").matcher(body);
        while (m.find()) ids.add(m.group(1));
        return ids;
    }

    public boolean areAllCardsShowingFilledHeart() {
        List<WebElement> cards = driver.findElements(Locators.Wishlist.PROPERTY_CARDS);
        List<WebElement> favoriteBtns = driver.findElements(Locators.Wishlist.CARD_FAV_BTN);
        return !cards.isEmpty() && cards.size() == favoriteBtns.size();
    }

    public boolean isEmptyStateDisplayed() {
        return !driver.findElements(Locators.Wishlist.EMPTY_STATE).isEmpty();
    }

    public String getEmptyStateText() {
        return waitForElementVisible(Locators.Wishlist.EMPTY_STATE).getText();
    }

    private void waitForPageToLoad() {
        wait.until(d ->
                !d.findElements(Locators.Wishlist.PAGE_TITLE).isEmpty() ||
                !d.findElements(Locators.Wishlist.PROPERTY_CARDS).isEmpty() ||
                !d.findElements(Locators.Wishlist.EMPTY_STATE).isEmpty()
        );
    }

}
