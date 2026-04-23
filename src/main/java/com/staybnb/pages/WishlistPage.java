package com.staybnb.pages;

import com.staybnb.config.AppConstants;
import com.staybnb.locators.Locators;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Collections;
import java.util.List;

public class WishlistPage extends BasePage {
    private static final Logger log = LogManager.getLogger(WishlistPage.class);

    private static final String WISHLIST_ADD_STATUS_JS      = "com/staybnb/scripts/wishlistAddStatusApi.js";
    private static final String WISHLIST_REMOVE_STATUS_JS   = "com/staybnb/scripts/wishlistRemoveStatusApi.js";
    private static final String WISHLIST_GET_IDS_JS         = "com/staybnb/scripts/wishlistGetIdsApi.js";
    private static final String GET_FIRST_PROPERTY_ID_JS   = "com/staybnb/scripts/getFirstPropertyIdApi.js";

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
        WebElement btn = cards.get(0).findElement(Locators.Wishlist.CARD_FAVORITE_BTN);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
    }

    public void waitForCardCountToDecrease(int countBefore) {
        wait.until(d -> d.findElements(Locators.Wishlist.PROPERTY_CARDS).size() < countBefore);
    }

    public long addToWishlistViaApi(String propertyId) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Object result = js.executeAsyncScript(loadScript(WISHLIST_ADD_STATUS_JS), AppConstants.SLUG, propertyId);
        if (result instanceof Number n) return n.longValue();
        throw new RuntimeException("Unexpected wishlist add status type: " + result);
    }

    public long removeFromWishlistViaApi(String propertyId) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Object result = js.executeAsyncScript(loadScript(WISHLIST_REMOVE_STATUS_JS), AppConstants.SLUG, propertyId);
        if (result instanceof Number n) return n.longValue();
        throw new RuntimeException("Unexpected wishlist remove status type: " + result);
    }

    public String getFirstPropertyIdViaApi() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Object result = js.executeAsyncScript(loadScript(GET_FIRST_PROPERTY_ID_JS), AppConstants.SLUG);
        if (result == null) throw new RuntimeException("Could not retrieve first property ID from API");
        return String.valueOf(result);
    }

    public void clearWishlistViaApi() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Object raw = js.executeAsyncScript(loadScript(WISHLIST_GET_IDS_JS), AppConstants.SLUG);
        List<Object> ids = (raw instanceof List) ? (List<Object>) raw : Collections.emptyList();
        log.info("clearWishlistViaApi: found {} item(s) to remove — ids: {}", ids.size(), ids);
        for (Object id : ids) {
            removeFromWishlistViaApi(String.valueOf(id));
        }
    }

    public boolean areAllCardsShowingFilledHeart() {
        List<WebElement> cards = driver.findElements(Locators.Wishlist.PROPERTY_CARDS);
        List<WebElement> favoritedBtns = driver.findElements(Locators.Wishlist.CARD_FAVORITED_BTN);
        return !cards.isEmpty() && cards.size() == favoritedBtns.size();
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
