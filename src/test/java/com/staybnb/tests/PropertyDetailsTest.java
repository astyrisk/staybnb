package com.staybnb.tests;

import com.staybnb.config.TestConfig;
import com.staybnb.pages.PropertyDetailsPage;
import com.staybnb.utils.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class PropertyDetailsTest extends BaseTest {
    private PropertyDetailsPage propertyDetailsPage;
    private final String PROPERTY_ID = Constants.DEFAULT_PROPERTY_ID;

    @BeforeEach
    public void setup() {
        propertyDetailsPage = new PropertyDetailsPage(driver);
    }

    private void navigateToProperty() {
        propertyDetailsPage.navigateTo(PROPERTY_ID);
    }

    @Test
    public void testPropertyTitle() {
        navigateToProperty();
        assertEquals("Ski Chalet in Zermatt", propertyDetailsPage.getTitle(), "Property title should match.");
    }

    @Test
    public void testPropertyLocation() {
        navigateToProperty();
        assertEquals("Zermatt, Switzerland", propertyDetailsPage.getLocation(), "Property location should match.");
    }

    private List<String> getPropertySpecTexts() {
        navigateToProperty();
        return propertyDetailsPage.getSpecs().stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    @Test
    public void testPropertyGuestCapacity() {
        assertTrue(getPropertySpecTexts().contains("10 guests"), "Should display number of guests.");
    }

    @Test
    public void testPropertyBedroomCount() {
        assertTrue(getPropertySpecTexts().contains("5 bedrooms"), "Should display number of bedrooms.");
    }

    @Test
    public void testPropertyBedCount() {
        assertTrue(getPropertySpecTexts().contains("7 beds"), "Should display number of beds.");
    }

    @Test
    public void testPropertyBathroomCount() {
        assertTrue(getPropertySpecTexts().contains("3 bathrooms"), "Should display number of bathrooms.");
    }

    @Test
    public void testDescriptionNotEmpty() {
        navigateToProperty();
        assertFalse(propertyDetailsPage.getDescription().isEmpty(), "Description should not be empty.");
    }

    @Test
    public void testHostAvatarDisplayed() {
        navigateToProperty();
        assertTrue(propertyDetailsPage.isHostAvatarDisplayed(), "Host avatar should be displayed.");
    }

    @Test
    public void testHostNamePrefix() {
        navigateToProperty();
        assertTrue(propertyDetailsPage.getHostName().contains("Hosted by"), "Host name should include 'Hosted by'.");
    }

    @Test
    public void testHostNameValue() {
        navigateToProperty();
        assertTrue(propertyDetailsPage.getHostName().contains("John D."), "Host name should match 'John D.'.");
    }

    @Test
    public void testHostMemberSincePrefix() {
        navigateToProperty();
        assertTrue(propertyDetailsPage.getHostSince().contains("Member since"), "Host member since date should be displayed.");
    }

    @Test
    public void testHostMemberSinceValue() {
        navigateToProperty();
        assertTrue(propertyDetailsPage.getHostSince().contains("March 2026"), "Host member since date should match 'March 2026'.");
    }

    @Test
    public void testAmenitiesCount() {
        navigateToProperty();
        assertEquals(9, propertyDetailsPage.getAmenities().size(), "There should be 9 amenities.");
    }

    private List<String> getAmenityTexts() {
        navigateToProperty();
        return propertyDetailsPage.getAmenities().stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    @Test
    public void testWiFiAmenityPresent() {
        assertTrue(getAmenityTexts().stream().anyMatch(t -> t.contains("WiFi")), "WiFi should be listed.");
    }

    @Test
    public void testSkiAccessAmenityPresent() {
        assertTrue(getAmenityTexts().stream().anyMatch(t -> t.contains("Ski Access")), "Ski Access should be listed.");
    }

    @Test
    public void testPriceIsPositive() {
        navigateToProperty();
        String numericPrice = propertyDetailsPage.getPrice().replaceAll("[^0-9]", "");
        assertTrue(Integer.parseInt(numericPrice) > 0, "Price should be a positive number.");
    }

    @Test
    public void testNonExistentPropertyReturns404() {
        propertyDetailsPage.navigateTo(Constants.NON_EXISTENT_ID);
        assertTrue(propertyDetailsPage.isPropertyNotFoundDisplayed());
    }

    @Test
    public void testAbsenceOfBookingWidget() {
        navigateToProperty();
        assertFalse(propertyDetailsPage.isBookingWidgetPresent(), "Booking widget should be absent.");
    }

    @Test
    public void testAbsenceOfReviewsSection() {
        navigateToProperty();
        assertFalse(propertyDetailsPage.isReviewsSectionPresent(), "Reviews section should be absent.");
    }

    @Test
    public void testImageGalleryHasImages() {
        navigateToProperty();
        assertTrue(propertyDetailsPage.getGalleryImages().size() >= 1, "There should be at least one image.");
    }

    @Test
    public void testFirstImageAltText() {
        navigateToProperty();
        List<WebElement> images = propertyDetailsPage.getGalleryImages();
        assertEquals("Living Room", images.get(0).getAttribute("alt"), "First image should be 'Living Room'.");
    }
}
