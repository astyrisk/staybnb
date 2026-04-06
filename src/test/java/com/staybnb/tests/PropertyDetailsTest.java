package com.staybnb.tests;

import com.staybnb.pages.PropertyDetailsPage;
import com.staybnb.data.Constants;
import com.staybnb.assertions.ErrorMessages;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PropertyDetailsTest extends BaseTest {
    private PropertyDetailsPage propertyDetailsPage;
    private final String PROPERTY_ID = Constants.DEFAULT_PROPERTY_ID;

    @BeforeEach
    public void setup() {
        propertyDetailsPage = new PropertyDetailsPage(driver);
        propertyDetailsPage.navigateTo(PROPERTY_ID);
    }

    @Test
    public void testPropertyTitle() {
        assertEquals("Ski Chalet in Zermatt", propertyDetailsPage.getTitle(), ErrorMessages.PROPERTY_TITLE_SHOULD_MATCH);
    }

    @Test
    public void testPropertyLocation() {
        assertEquals("Zermatt, Switzerland", propertyDetailsPage.getLocation(), ErrorMessages.PROPERTY_LOCATION_SHOULD_MATCH);
    }

    @Test
    public void testPropertyGuestCapacity() {
        assertTrue(propertyDetailsPage.getSpecTexts().contains("10 guests"), ErrorMessages.SHOULD_DISPLAY_NUMBER_OF_GUESTS);
    }

    @Test
    public void testPropertyBedroomCount() {
        assertTrue(propertyDetailsPage.getSpecTexts().contains("5 bedrooms"), ErrorMessages.SHOULD_DISPLAY_NUMBER_OF_BEDROOMS);
    }

    @Test
    public void testPropertyBedCount() {
        assertTrue(propertyDetailsPage.getSpecTexts().contains("7 beds"), ErrorMessages.SHOULD_DISPLAY_NUMBER_OF_BEDS);
    }

    @Test
    public void testPropertyBathroomCount() {
        assertTrue(propertyDetailsPage.getSpecTexts().contains("3 bathrooms"), ErrorMessages.SHOULD_DISPLAY_NUMBER_OF_BATHROOMS);
    }

    @Test
    public void testDescriptionNotEmpty() {
        assertFalse(propertyDetailsPage.getDescription().isEmpty(), ErrorMessages.DESCRIPTION_SHOULD_NOT_BE_EMPTY);
    }

    @Test
    public void testHostAvatarDisplayed() {
        assertTrue(propertyDetailsPage.isHostAvatarDisplayed(), ErrorMessages.HOST_AVATAR_SHOULD_BE_DISPLAYED);
    }

    @Test
    public void testHostNamePrefix() {
        assertTrue(propertyDetailsPage.getHostName().contains("Hosted by"), ErrorMessages.HOST_NAME_SHOULD_INCLUDE_HOSTED_BY);
    }

    @Test
    public void testHostNameValue() {
        assertTrue(propertyDetailsPage.getHostName().contains("John D."), ErrorMessages.HOST_NAME_SHOULD_MATCH_JOHN_D);
    }

    @Test
    public void testHostMemberSincePrefix() {
        assertTrue(propertyDetailsPage.getHostSince().contains("Member since"), ErrorMessages.HOST_MEMBER_SINCE_SHOULD_BE_DISPLAYED);
    }

    @Test
    public void testHostMemberSinceValue() {
        assertTrue(propertyDetailsPage.getHostSince().contains("March 2026"), ErrorMessages.HOST_MEMBER_SINCE_SHOULD_MATCH_MARCH_2026);
    }

    @Test
    public void testAmenitiesCount() {
        assertEquals(9, propertyDetailsPage.getAmenities().size(), ErrorMessages.THERE_SHOULD_BE_9_AMENITIES);
    }

    @Test
    public void testWiFiAmenityPresent() {
        assertTrue(propertyDetailsPage.getAmenityTexts().stream().anyMatch(t -> t.contains("WiFi")), ErrorMessages.WIFI_SHOULD_BE_LISTED);
    }

    @Test
    public void testSkiAccessAmenityPresent() {
        assertTrue(propertyDetailsPage.getAmenityTexts().stream().anyMatch(t -> t.contains("Ski Access")), ErrorMessages.SKI_ACCESS_SHOULD_BE_LISTED);
    }

    @Test
    public void testPriceIsPositive() {
        String numericPrice = propertyDetailsPage.getPrice().replaceAll("[^0-9]", "");

        assertTrue(Integer.parseInt(numericPrice) > 0, ErrorMessages.PRICE_SHOULD_BE_A_POSITIVE_NUMBER);
    }

    @Test
    public void testNonExistentPropertyReturns404() {
        propertyDetailsPage.navigateTo(Constants.NON_EXISTENT_ID);

        assertTrue(propertyDetailsPage.isPropertyNotFoundDisplayed());
    }

    @Test
    public void testAbsenceOfBookingWidget() {
        assertFalse(propertyDetailsPage.isBookingWidgetPresent(), ErrorMessages.BOOKING_WIDGET_SHOULD_BE_ABSENT);
    }

    @Test
    public void testAbsenceOfReviewsSection() {
        assertFalse(propertyDetailsPage.isReviewsSectionPresent(), ErrorMessages.REVIEWS_SECTION_SHOULD_BE_ABSENT);
    }

    @Test
    public void testImageGalleryHasImages() {
        assertTrue(propertyDetailsPage.getGalleryImages().size() >= 1, ErrorMessages.THERE_SHOULD_BE_AT_LEAST_ONE_IMAGE);
    }

    @Test
    public void testFirstImageAltText() {
        List<WebElement> images = propertyDetailsPage.getGalleryImages();

        assertEquals("Living Room", images.get(0).getAttribute("alt"), ErrorMessages.FIRST_IMAGE_SHOULD_BE_LIVING_ROOM);
    }
}
