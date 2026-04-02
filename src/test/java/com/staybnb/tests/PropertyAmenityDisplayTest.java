package com.staybnb.tests;

import com.staybnb.assertions.ErrorMessages;
import com.staybnb.data.Constants;
import com.staybnb.pages.PropertyDetailsPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PropertyAmenityDisplayTest extends BaseTest {
    private PropertyDetailsPage propertyDetailsPage;

    @BeforeEach
    public void setup() {
        propertyDetailsPage = new PropertyDetailsPage(driver);
    }

    // AC1 — Amenities are displayed as a grid with both an icon span and a text label span
    @Test
    public void testAmenitiesDisplayedWithIconAndLabel() {
        propertyDetailsPage.navigateTo(Constants.DEFAULT_PROPERTY_ID);
        assertTrue(
                propertyDetailsPage.amenitiesHaveIconAndLabel(),
                ErrorMessages.AMENITY_ITEMS_SHOULD_DISPLAY_ICON_AND_LABEL
        );
    }

    //NOTE Fails — "Show all N amenities" button is not yet implemented in the UI
    // AC2 — "Show all N amenities" button appears when property has more than 8 amenities
    @Test
    public void testShowAllAmenitiesButtonAppearsForPropertyWithMoreThanEightAmenities() {
        propertyDetailsPage.navigateTo(Constants.DEFAULT_PROPERTY_ID);
        assertTrue(
                propertyDetailsPage.isShowAllAmenitiesButtonDisplayed(),
                ErrorMessages.SHOW_ALL_AMENITIES_BUTTON_SHOULD_APPEAR_FOR_MORE_THAN_EIGHT_AMENITIES
        );
    }

    // AC3 — Modal with amenities grouped by type (Essentials, Features, Safety)
    // Not testable: depends on AC2 ("Show all" button) which is not yet implemented.

    // AC4 — All amenities are directly visible without a "Show all" button for ≤ 8 amenities
    // Requires Constants.PROPERTY_WITH_FEW_AMENITIES_ID to be a published property with 1–8 amenities.
    @Test
    public void testAllAmenitiesVisibleWithoutShowAllButtonForPropertyWithFewAmenities() {
        propertyDetailsPage.navigateTo(Constants.PROPERTY_WITH_FEW_AMENITIES_ID);
        assertTrue(
                propertyDetailsPage.getDisplayedAmenityCount() > 0
                        && !propertyDetailsPage.isShowAllAmenitiesButtonDisplayed(),
                ErrorMessages.ALL_AMENITIES_SHOULD_BE_VISIBLE_WITHOUT_MODAL_FOR_FEW_AMENITIES
        );
    }

    // AC5 — Amenities section is hidden entirely when the property has no amenities
    @Test
    public void testAmenitiesSectionIsHiddenForPropertyWithNoAmenities() {
        propertyDetailsPage.navigateTo(Constants.PROPERTY_WITH_NO_AMENITIES_ID);
        assertFalse(
                propertyDetailsPage.isAmenitiesSectionPresent(),
                ErrorMessages.AMENITIES_SECTION_SHOULD_BE_HIDDEN_FOR_PROPERTY_WITH_NO_AMENITIES
        );
    }
}
