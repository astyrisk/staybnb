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

    @Test
    public void testAmenitiesDisplayedWithIconAndLabel() {
        propertyDetailsPage.navigateTo(Constants.DEFAULT_PROPERTY_ID);
        assertTrue(
                propertyDetailsPage.amenitiesHaveIconAndLabel(),
                ErrorMessages.AMENITY_ITEMS_SHOULD_DISPLAY_ICON_AND_LABEL
        );
    }

    @Test
    public void testShowAllAmenitiesButtonAppearsForPropertyWithMoreThanEightAmenities() {
        propertyDetailsPage.navigateTo(Constants.DEFAULT_PROPERTY_ID);
        assertTrue(
                propertyDetailsPage.isShowAllAmenitiesButtonDisplayed(),
                ErrorMessages.SHOW_ALL_AMENITIES_BUTTON_SHOULD_APPEAR_FOR_MORE_THAN_EIGHT_AMENITIES
        );
    }

    @Test
    public void testAllAmenitiesVisibleWithoutShowAllButtonForPropertyWithFewAmenities() {
        propertyDetailsPage.navigateTo(Constants.PROPERTY_WITH_FEW_AMENITIES_ID);
        assertTrue(
                propertyDetailsPage.getDisplayedAmenityCount() > 0
                        && !propertyDetailsPage.isShowAllAmenitiesButtonDisplayed(),
                ErrorMessages.ALL_AMENITIES_SHOULD_BE_VISIBLE_WITHOUT_MODAL_FOR_FEW_AMENITIES
        );
    }

    @Test
    public void testAmenitiesSectionIsHiddenForPropertyWithNoAmenities() {
        propertyDetailsPage.navigateTo(Constants.PROPERTY_WITH_NO_AMENITIES_ID);
        assertFalse(
                propertyDetailsPage.isAmenitiesSectionPresent(),
                ErrorMessages.AMENITIES_SECTION_SHOULD_BE_HIDDEN_FOR_PROPERTY_WITH_NO_AMENITIES
        );
    }
}
