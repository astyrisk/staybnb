package com.staybnb.tests.ui.property;

import com.staybnb.pages.PropertyDetailsPage;
import com.staybnb.config.AppConstants;
import com.staybnb.assertions.ErrorMessages;
import com.staybnb.tests.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Epic("Properties")
@Feature("Property Details")
@Tag("regression")
public class PropertyDetailsTest extends BaseTest {
    private PropertyDetailsPage propertyDetailsPage;

    @BeforeEach
    public void setup() {
        propertyDetailsPage = new PropertyDetailsPage(driver);
        propertyDetailsPage.navigateTo(AppConstants.DEFAULT_PROPERTY_ID);
    }

    @Test
    @DisplayName("Property title matches expected value")
    public void testPropertyTitle() {
        assertEquals("Ski Chalet in Zermatt", propertyDetailsPage.getTitle(), ErrorMessages.PROPERTY_TITLE_SHOULD_MATCH);
    }

    @Test
    @DisplayName("Property location matches expected value")
    public void testPropertyLocation() {
        assertEquals("Zermatt, Switzerland", propertyDetailsPage.getLocation(), ErrorMessages.PROPERTY_LOCATION_SHOULD_MATCH);
    }

    @Test
    @DisplayName("Property displays guest capacity")
    public void testPropertyGuestCapacity() {
        assertTrue(propertyDetailsPage.getSpecTexts().contains("10 guests"), ErrorMessages.SHOULD_DISPLAY_NUMBER_OF_GUESTS);
    }

    @Test
    @DisplayName("Property displays bedroom count")
    public void testPropertyBedroomCount() {
        assertTrue(propertyDetailsPage.getSpecTexts().contains("5 bedrooms"), ErrorMessages.SHOULD_DISPLAY_NUMBER_OF_BEDROOMS);
    }

    @Test
    @DisplayName("Property displays bed count")
    public void testPropertyBedCount() {
        assertTrue(propertyDetailsPage.getSpecTexts().contains("7 beds"), ErrorMessages.SHOULD_DISPLAY_NUMBER_OF_BEDS);
    }

    @Test
    @DisplayName("Property displays bathroom count")
    public void testPropertyBathroomCount() {
        assertTrue(propertyDetailsPage.getSpecTexts().contains("3 bathrooms"), ErrorMessages.SHOULD_DISPLAY_NUMBER_OF_BATHROOMS);
    }

    @Test
    @DisplayName("Property description is not empty")
    public void testDescriptionNotEmpty() {
        assertFalse(propertyDetailsPage.getDescription().isEmpty(), ErrorMessages.DESCRIPTION_SHOULD_NOT_BE_EMPTY);
    }

    @Test
    @DisplayName("Host avatar is displayed on property detail page")
    public void testHostAvatarDisplayed() {
        assertTrue(propertyDetailsPage.isHostAvatarDisplayed(), ErrorMessages.HOST_AVATAR_SHOULD_BE_DISPLAYED);
    }

    @Test
    @DisplayName("Host name includes 'Hosted by' prefix")
    public void testHostNamePrefix() {
        assertTrue(propertyDetailsPage.getHostName().contains("Hosted by"), ErrorMessages.HOST_NAME_SHOULD_INCLUDE_HOSTED_BY);
    }

    @Test
    @DisplayName("Host name shows 'John D.'")
    public void testHostNameValue() {
        assertTrue(propertyDetailsPage.getHostName().contains("John D."), ErrorMessages.HOST_NAME_SHOULD_MATCH_JOHN_D);
    }

    @Test
    @DisplayName("Host section shows 'Member since' label")
    public void testHostMemberSincePrefix() {
        assertTrue(propertyDetailsPage.getHostSince().contains("Member since"), ErrorMessages.HOST_MEMBER_SINCE_SHOULD_BE_DISPLAYED);
    }

    @Test
    @DisplayName("Host member since shows 'March 2026'")
    public void testHostMemberSinceValue() {
        assertTrue(propertyDetailsPage.getHostSince().contains("March 2026"), ErrorMessages.HOST_MEMBER_SINCE_SHOULD_MATCH_MARCH_2026);
    }

    @Test
    @DisplayName("Property has 9 amenities listed")
    public void testAmenitiesCount() {
        assertEquals(9, propertyDetailsPage.getAmenities().size(), ErrorMessages.THERE_SHOULD_BE_9_AMENITIES);
    }

    @Test
    @DisplayName("WiFi is listed in amenities")
    public void testWiFiAmenityPresent() {
        assertTrue(propertyDetailsPage.getAmenityTexts().stream().anyMatch(t -> t.contains("WiFi")), ErrorMessages.WIFI_SHOULD_BE_LISTED);
    }

    @Test
    @DisplayName("Ski Access is listed in amenities")
    public void testSkiAccessAmenityPresent() {
        assertTrue(propertyDetailsPage.getAmenityTexts().stream().anyMatch(t -> t.contains("Ski Access")), ErrorMessages.SKI_ACCESS_SHOULD_BE_LISTED);
    }

    @Test
    @DisplayName("Property price is a positive number")
    public void testPriceIsPositive() {
        String numericPrice = propertyDetailsPage.getPrice().replaceAll("[^0-9]", "");

        assertTrue(Integer.parseInt(numericPrice) > 0, ErrorMessages.PRICE_SHOULD_BE_A_POSITIVE_NUMBER);
    }

    @Test
    @DisplayName("Non-existent property shows 404 page")
    public void testNonExistentPropertyReturns404() {
        propertyDetailsPage.navigateTo(AppConstants.NON_EXISTENT_ID);

        assertTrue(propertyDetailsPage.isPropertyNotFoundDisplayed());
    }

    @Test
    @DisplayName("Booking widget is absent on property detail page")
    public void testAbsenceOfBookingWidget() {
        assertFalse(propertyDetailsPage.isBookingWidgetPresent(), ErrorMessages.BOOKING_WIDGET_SHOULD_BE_ABSENT);
    }

    @Test
    @DisplayName("Reviews section is absent on property detail page")
    public void testAbsenceOfReviewsSection() {
        assertFalse(propertyDetailsPage.isReviewsSectionPresent(), ErrorMessages.REVIEWS_SECTION_SHOULD_BE_ABSENT);
    }

    @Test
    @DisplayName("Image gallery has at least one image")
    public void testImageGalleryHasImages() {
        assertFalse(propertyDetailsPage.getGalleryImages().isEmpty(), ErrorMessages.THERE_SHOULD_BE_AT_LEAST_ONE_IMAGE);
    }

    @Test
    @DisplayName("First gallery image has alt text 'Living Room'")
    public void testFirstImageAltText() {
        List<WebElement> images = propertyDetailsPage.getGalleryImages();

        assertEquals("Living Room", images.getFirst().getAttribute("alt"), ErrorMessages.FIRST_IMAGE_SHOULD_BE_LIVING_ROOM);
    }
}
