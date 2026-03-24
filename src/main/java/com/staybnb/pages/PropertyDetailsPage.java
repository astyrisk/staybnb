package com.staybnb.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class PropertyDetailsPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private final String BASE_URL = "https://qa-playground.nixdev.co/t/automation-adel/properties/";

    // Locators
    private By detailTitle = By.className("detail-title");
    private By detailLocation = By.className("detail-location");
    private By detailType = By.className("detail-category"); // Assuming this based on requirements
    private By detailSpecs = By.cssSelector(".detail-main .detail-specs span");
    private By detailDescription = By.className("detail-description");
    private By showMoreBtn = By.className("detail-description-toggle"); 
    private By hostAvatar = By.className("detail-host-avatar");
    private By hostName = By.className("detail-host-name");
    private By hostSince = By.className("detail-host-since");
    private By amenityItems = By.className("detail-amenity");
    private By showAllAmenitiesBtn = By.className("detail-amenities-show-all");
    private By priceAmount = By.className("detail-price-amount");
    private By authError = By.className("auth-error");
    private By bookingWidget = By.className("detail-booking-widget");
    private By reviewsSection = By.className("detail-reviews");

    // Gallery Locators
    private By galleryContainer = By.className("detail-gallery");
    private By galleryImages = By.cssSelector(".detail-gallery img");
    private By showAllPhotosBtn = By.className("show-all-photos-btn"); // Assuming
    private By galleryModal = By.className("gallery-modal"); // Assuming
    private By galleryCarousel = By.className("detail-gallery-carousel"); // Assuming for mobile

    public PropertyDetailsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void navigateTo(String propertyId) {
        driver.get(BASE_URL + propertyId);
    }

    public String getTitle() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(detailTitle)).getText();
    }

    public String getLocation() {
        return driver.findElement(detailLocation).getText();
    }

    public String getType() {
        try {
            return driver.findElement(detailType).getText();
        } catch (Exception e) {
            return "";
        }
    }

    public List<WebElement> getSpecs() {
        return driver.findElements(detailSpecs);
    }

    public String getDescription() {
        return driver.findElement(detailDescription).getText();
    }

    public boolean isShowMoreButtonDisplayed() {
        try {
            return driver.findElement(showMoreBtn).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickShowMore() {
        driver.findElement(showMoreBtn).click();
    }

    public boolean isHostAvatarDisplayed() {
        return driver.findElement(hostAvatar).isDisplayed();
    }

    public String getHostName() {
        return driver.findElement(hostName).getText();
    }

    public String getHostSince() {
        return driver.findElement(hostSince).getText();
    }

    public List<WebElement> getAmenities() {
        return driver.findElements(amenityItems);
    }

    public boolean isShowAllAmenitiesButtonDisplayed() {
        try {
            return driver.findElement(showAllAmenitiesBtn).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getPrice() {
        return driver.findElement(priceAmount).getText();
    }

    public boolean isBookingWidgetPresent() {
        return !driver.findElements(bookingWidget).isEmpty();
    }

    public boolean isReviewsSectionPresent() {
        return !driver.findElements(reviewsSection).isEmpty();
    }

    public boolean isPropertyNotFoundDisplayed() {
        try {
            WebElement error = wait.until(ExpectedConditions.visibilityOfElementLocated(authError));
            return error.getText().toLowerCase().contains("not found");
        } catch (Exception e) {
            return false;
        }
    }

    public String getErrorMessage() {
        try {
            return driver.findElement(authError).getText();
        } catch (Exception e) {
            return "";
        }
    }

    // Gallery Methods
    public List<WebElement> getGalleryImages() {
        return driver.findElements(galleryImages);
    }

    public void clickShowAllPhotos() {
        driver.findElement(showAllPhotosBtn).click();
    }

    public boolean isGalleryModalDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(galleryModal)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isGalleryCarouselDisplayed() {
        try {
            return driver.findElement(galleryCarousel).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
