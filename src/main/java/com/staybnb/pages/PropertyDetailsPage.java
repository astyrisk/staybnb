package com.staybnb.pages;

import com.staybnb.locators.Locators;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;
import java.util.stream.Collectors;
import com.staybnb.config.AppConstants;

public class PropertyDetailsPage extends BasePage {
    private final String BASE_URL = AppConstants.PROPERTY_DETAILS_BASE_URL;

    private By detailTitle = Locators.PropertyDetails.DETAIL_TITLE;
    private By detailLocation = Locators.PropertyDetails.DETAIL_LOCATION;
    private By detailType = Locators.PropertyDetails.DETAIL_TYPE;
    private By detailSpecs = Locators.PropertyDetails.DETAIL_SPECS;
    private By detailDescription = Locators.PropertyDetails.DETAIL_DESCRIPTION;
    private By showMoreBtn = Locators.PropertyDetails.SHOW_MORE_BUTTON;
    private By hostAvatar = Locators.PropertyDetails.HOST_AVATAR;
    private By hostName = Locators.PropertyDetails.HOST_NAME;
    private By hostSince = Locators.PropertyDetails.HOST_SINCE;
    private By amenityItems = Locators.PropertyDetails.AMENITY_ITEMS;
    private By showAllAmenitiesBtn = Locators.PropertyDetails.SHOW_ALL_AMENITIES_BUTTON;
    private By priceAmount = Locators.PropertyDetails.PRICE_AMOUNT;
    private By authError = Locators.PropertyDetails.AUTH_ERROR;
    private By bookingWidget = Locators.PropertyDetails.BOOKING_WIDGET;
    private By reviewsSection = Locators.PropertyDetails.REVIEWS_SECTION;

    private By galleryImages = Locators.PropertyDetails.GALLERY_IMAGES;
    private By showAllPhotosBtn = Locators.PropertyDetails.SHOW_ALL_PHOTOS_BUTTON;
    private By galleryModal = Locators.PropertyDetails.GALLERY_MODAL;
    private By galleryCarousel = Locators.PropertyDetails.GALLERY_CAROUSEL;

    public PropertyDetailsPage(WebDriver driver) {
        super(driver);
    }

    public void navigateTo(String propertyId) {
        super.navigateTo(BASE_URL + propertyId);
        waitForDetailsToLoad();
    }

    public String getTitle() {
        return waitForElementVisible(detailTitle).getText();
    }

    public String getLocation() {
        return waitForElementVisible(detailLocation).getText();
    }

    public String getType() {
        try {
            return getText(detailType);
        } catch (Exception e) {
            return "";
        }
    }

    public List<WebElement> getSpecs() {
        return waitForElementsPresent(detailSpecs);
    }

    public String getDescription() {
        return waitForElementVisible(detailDescription).getText();
    }

    public boolean isShowMoreButtonDisplayed() {
        return isDisplayed(showMoreBtn);
    }

    public void clickShowMore() {
        click(showMoreBtn);
    }

    public boolean isHostAvatarDisplayed() {
        return isDisplayed(hostAvatar);
    }

    public String getHostName() {
        return waitForElementVisible(hostName).getText();
    }

    public String getHostSince() {
        return waitForElementVisible(hostSince).getText();
    }

    public List<WebElement> getAmenities() {
        return waitForElementsPresent(amenityItems);
    }

    public boolean isShowAllAmenitiesButtonDisplayed() {
        return isDisplayed(showAllAmenitiesBtn);
    }

    public String getPrice() {
        return waitForElementVisible(priceAmount).getText();
    }

    public boolean isBookingWidgetPresent() {
        return !driver.findElements(bookingWidget).isEmpty();
    }

    public boolean isReviewsSectionPresent() {
        return !driver.findElements(reviewsSection).isEmpty();
    }

    public boolean isPropertyNotFoundDisplayed() {
        try {
            WebElement error = waitForElementVisible(authError);
            return error.getText().toLowerCase().contains("property not found");
        } catch (Exception e) {
            return false;
        }
    }

    public String getErrorMessage() {
        try {
            return getText(authError);
        } catch (Exception e) {
            return "";
        }
    }

    public List<WebElement> getGalleryImages() {
        return waitForElementsPresent(galleryImages);
    }

    public void clickShowAllPhotos() {
        click(showAllPhotosBtn);
    }

    public boolean isGalleryModalDisplayed() {
        return isDisplayed(galleryModal);
    }

    public boolean isGalleryCarouselDisplayed() {
        return isDisplayed(galleryCarousel);
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
        return driver.findElements(amenityItems).size();
    }

    public boolean isAmenitiesSectionPresent() {
        return !driver.findElements(Locators.PropertyDetails.AMENITIES_SECTION).isEmpty();
    }

    public boolean amenitiesHaveIconAndLabel() {
        List<WebElement> items = driver.findElements(amenityItems);
        if (items.isEmpty()) return false;
        return items.stream().allMatch(item -> {
            List<WebElement> spans = item.findElements(By.tagName("span"));
            return spans.size() >= 2
                    && !spans.get(0).getText().isBlank()
                    && !spans.get(1).getText().isBlank();
        });
    }

    private void waitForDetailsToLoad() {
        wait.until(d ->
                d.findElements(detailTitle).size() > 0 ||
                d.findElements(authError).size() > 0
        );
    }
}
