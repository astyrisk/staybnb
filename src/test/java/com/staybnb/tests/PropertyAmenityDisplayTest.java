package com.staybnb.tests;

import com.staybnb.assertions.ErrorMessages;
import com.staybnb.data.Constants;
import com.staybnb.pages.PropertyDetailsPage;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Epic("Properties")
@Feature("Amenity Display")
@Tag("regression")
public class PropertyAmenityDisplayTest extends BaseTest {
    private PropertyDetailsPage propertyDetailsPage;

    @BeforeEach
    public void setup() {
        propertyDetailsPage = new PropertyDetailsPage(driver);
    }

    @Test
    @DisplayName("Amenity items display both an icon and a label")
    public void testAmenitiesDisplayedWithIconAndLabel() {
        propertyDetailsPage.navigateTo(Constants.DEFAULT_PROPERTY_ID);
        assertTrue(
                propertyDetailsPage.amenitiesHaveIconAndLabel(),
                ErrorMessages.AMENITY_ITEMS_SHOULD_DISPLAY_ICON_AND_LABEL
        );
    }

    @Test
    @DisplayName("'Show All Amenities' button appears for properties with more than 8 amenities")
    public void testShowAllAmenitiesButtonAppearsForPropertyWithMoreThanEightAmenities() {
        propertyDetailsPage.navigateTo(Constants.DEFAULT_PROPERTY_ID);
        assertTrue(
                propertyDetailsPage.isShowAllAmenitiesButtonDisplayed(),
                ErrorMessages.SHOW_ALL_AMENITIES_BUTTON_SHOULD_APPEAR_FOR_MORE_THAN_EIGHT_AMENITIES
        );
    }

    @Test
    @DisplayName("All amenities are visible without 'Show All' button for properties with few amenities")
    public void testAllAmenitiesVisibleWithoutShowAllButtonForPropertyWithFewAmenities() {
        propertyDetailsPage.navigateTo(Constants.PROPERTY_WITH_FEW_AMENITIES_ID);
        assertTrue(
                propertyDetailsPage.getDisplayedAmenityCount() > 0
                        && !propertyDetailsPage.isShowAllAmenitiesButtonDisplayed(),
                ErrorMessages.ALL_AMENITIES_SHOULD_BE_VISIBLE_WITHOUT_MODAL_FOR_FEW_AMENITIES
        );
    }

    @Test
    @DisplayName("Amenities section is hidden for a property with no amenities")
    public void testAmenitiesSectionIsHiddenForPropertyWithNoAmenities() {
        propertyDetailsPage.navigateTo(Constants.PROPERTY_WITH_NO_AMENITIES_ID);
        assertFalse(
                propertyDetailsPage.isAmenitiesSectionPresent(),
                ErrorMessages.AMENITIES_SECTION_SHOULD_BE_HIDDEN_FOR_PROPERTY_WITH_NO_AMENITIES
        );
    }
}
