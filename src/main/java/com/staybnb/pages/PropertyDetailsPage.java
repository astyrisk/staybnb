package com.staybnb.pages;

import com.staybnb.locators.Locators;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;
import java.util.stream.Collectors;
import com.staybnb.config.AppConstants;

public class PropertyDetailsPage extends BasePage {
    private static final String BASE_URL = AppConstants.PROPERTY_DETAILS_BASE_URL;

    public PropertyDetailsPage(WebDriver driver) {
        super(driver);
    }

    public void navigateTo(String propertyId) {
        super.navigateTo(BASE_URL + propertyId);
        waitForDetailsToLoad();
    }

    public String getTitle() {
        return waitForElementVisible(Locators.PropertyDetails.DETAIL_TITLE).getText();
    }

    public String getLocation() {
        return waitForElementVisible(Locators.PropertyDetails.DETAIL_LOCATION).getText();
    }

    public String getType() {
        try {
            return getText(Locators.PropertyDetails.DETAIL_TYPE);
        } catch (NoSuchElementException e) {
            return "";
        }
    }

    public List<WebElement> getSpecs() {
        return waitForElementsPresent(Locators.PropertyDetails.DETAIL_SPECS);
    }

    public String getDescription() {
        return waitForElementVisible(Locators.PropertyDetails.DETAIL_DESCRIPTION).getText();
    }

    public boolean isShowMoreButtonDisplayed() {
        return isDisplayed(Locators.PropertyDetails.SHOW_MORE_BUTTON);
    }

    public void clickShowMore() {
        click(Locators.PropertyDetails.SHOW_MORE_BUTTON);
    }

    public boolean isHostAvatarDisplayed() {
        return isDisplayed(Locators.PropertyDetails.HOST_AVATAR);
    }

    public String getHostName() {
        return waitForElementVisible(Locators.PropertyDetails.HOST_NAME).getText();
    }

    public String getHostSince() {
        return waitForElementVisible(Locators.PropertyDetails.HOST_SINCE).getText();
    }

    public List<WebElement> getAmenities() {
        return waitForElementsPresent(Locators.PropertyDetails.AMENITY_ITEMS);
    }

    public boolean isShowAllAmenitiesButtonDisplayed() {
        return isDisplayed(Locators.PropertyDetails.SHOW_ALL_AMENITIES_BUTTON);
    }

    public String getPrice() {
        return waitForElementVisible(Locators.PropertyDetails.PRICE_AMOUNT).getText();
    }

    public boolean isBookingWidgetPresent() {
        return !driver.findElements(Locators.PropertyDetails.BOOKING_WIDGET).isEmpty();
    }

    public boolean isReviewsSectionPresent() {
        return !driver.findElements(Locators.PropertyDetails.REVIEWS_SECTION).isEmpty();
    }

    public boolean isPropertyNotFoundDisplayed() {
        try {
            WebElement error = waitForElementVisible(Locators.PropertyDetails.AUTH_ERROR);
            return error.getText().toLowerCase().contains("property not found");
        } catch (TimeoutException e) {
            return false;
        }
    }

    public String getErrorMessage() {
        try {
            return getText(Locators.PropertyDetails.AUTH_ERROR);
        } catch (NoSuchElementException e) {
            return "";
        }
    }

    public List<WebElement> getGalleryImages() {
        return waitForElementsPresent(Locators.PropertyDetails.GALLERY_IMAGES);
    }

    public void clickShowAllPhotos() {
        click(Locators.PropertyDetails.SHOW_ALL_PHOTOS_BUTTON);
    }

    public boolean isGalleryModalDisplayed() {
        return isDisplayed(Locators.PropertyDetails.GALLERY_MODAL);
    }

    public boolean isGalleryCarouselDisplayed() {
        return isDisplayed(Locators.PropertyDetails.GALLERY_CAROUSEL);
    }

    public List<String> getSpecTexts() {
        return getSpecs().stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public List<String> getAmenityTexts() {
        return getAmenities().stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public int getDisplayedAmenityCount() {
        return driver.findElements(Locators.PropertyDetails.AMENITY_ITEMS).size();
    }

    public boolean isAmenitiesSectionPresent() {
        return !driver.findElements(Locators.PropertyDetails.AMENITIES_SECTION).isEmpty();
    }

    public boolean amenitiesHaveIconAndLabel() {
        List<WebElement> items = driver.findElements(Locators.PropertyDetails.AMENITY_ITEMS);
        if (items.isEmpty()) return false;
        return items.stream().allMatch(item -> {
            List<WebElement> spans = item.findElements(By.tagName("span"));
            return spans.size() >= 2
                    && !spans.get(0).getText().isBlank()
                    && !spans.get(1).getText().isBlank();
        });
    }

    public void clickWishlistButton() {
        waitForElementClickable(Locators.PropertyDetails.DETAIL_WISHLIST_BTN).click();
    }

    public boolean isWishlistFavorited() {
        return !driver.findElements(Locators.PropertyDetails.DETAIL_WISHLIST_FAVORITED_BTN).isEmpty();
    }

    public void waitForWishlistFavorited() {
        wait.until(d -> !d.findElements(Locators.PropertyDetails.DETAIL_WISHLIST_FAVORITED_BTN).isEmpty());
    }

    public void waitForWishlistUnfavorited() {
        wait.until(d -> d.findElements(Locators.PropertyDetails.DETAIL_WISHLIST_FAVORITED_BTN).isEmpty());
    }

    private void waitForDetailsToLoad() {
        wait.until(d ->
                d.findElements(Locators.PropertyDetails.DETAIL_TITLE).size() > 0 ||
                d.findElements(Locators.PropertyDetails.AUTH_ERROR).size() > 0
        );
    }
}
