package com.staybnb.tests.ui.search;

import static org.junit.jupiter.api.Assertions.*;

import com.staybnb.assertions.ErrorMessages;
import com.staybnb.config.TestDataConstants;
import com.staybnb.config.WaitConstants;
import com.staybnb.pages.PropertyListingPage;
import com.staybnb.tests.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Dimension;

@Epic("Filter")
@Feature("Filter by Amenities, Bedrooms, and Bathrooms")
@Tag("regression")
public class AmenitiesBedroomsBathroomsFilterTest extends BaseTest {

    private PropertyListingPage propertyListingPage;
    private boolean mobileViewportActive = false;

    @BeforeEach
    public void setup() {
        propertyListingPage = new PropertyListingPage(driver);
        propertyListingPage.navigateTo();
    }

    @AfterEach
    public void resetViewport() {
        if (mobileViewportActive) {
            driver
                .manage()
                .window()
                .setSize(
                    new Dimension(
                        WaitConstants.WIDE_DESKTOP_WIDTH,
                        WaitConstants.WIDE_DESKTOP_HEIGHT
                    )
                );
            mobileViewportActive = false;
        }
    }

    // AC1: checking an amenity checkbox updates results in real-time
    @Test
    @DisplayName("Checking an amenity checkbox updates results in real-time")
    public void testAmenityFilterUpdatesResultsInRealTime() {
        int countBefore = propertyListingPage.getPropertiesCount();

        propertyListingPage.checkAmenityByName(
            TestDataConstants.AmenitiesFilter.KNOWN_AMENITY_NAME
        );
        propertyListingPage.waitForCountToChangeTo(countBefore);

        assertNotEquals(
            countBefore,
            propertyListingPage.getPropertiesCount(),
            ErrorMessages.AMENITY_FILTER_SHOULD_UPDATE_RESULTS_IN_REAL_TIME
        );
    }

    // AC1: navigating with amenities= returns only properties with ALL selected amenities
    @Test
    @DisplayName("Navigating with amenities filter returns matching properties")
    public void testNavigatingWithAmenityFilterShowsResults() {
        propertyListingPage.navigateToWithAmenity(
            TestDataConstants.AmenitiesFilter.KNOWN_AMENITY_ID
        );

        assertTrue(
            propertyListingPage.getPropertiesCount() > 0,
            ErrorMessages.AMENITY_FILTER_SHOULD_SHOW_RESULTS
        );
    }

    // AC2: setting a minimum bedrooms value updates results in real-time
    @Test
    @DisplayName(
        "Setting a minimum bedrooms value updates results in real-time"
    )
    public void testBedroomFilterUpdatesResultsInRealTime() {
        int countBefore = propertyListingPage.getPropertiesCount();

        propertyListingPage.setMinBedrooms(
            TestDataConstants.AmenitiesFilter.KNOWN_MIN_BEDROOMS
        );
        propertyListingPage.waitForCountToChangeTo(countBefore);

        assertNotEquals(
            countBefore,
            propertyListingPage.getPropertiesCount(),
            ErrorMessages.BEDROOM_FILTER_SHOULD_UPDATE_RESULTS_IN_REAL_TIME
        );
    }

    // AC2: navigating with bedrooms= returns only properties with at least that many bedrooms
    @Test
    @DisplayName("Navigating with bedrooms filter returns matching properties")
    public void testNavigatingWithBedroomFilterShowsResults() {
        propertyListingPage.navigateToWithBedrooms(
            TestDataConstants.AmenitiesFilter.KNOWN_MIN_BEDROOMS
        );

        assertTrue(
            propertyListingPage.getPropertiesCount() > 0,
            ErrorMessages.BEDROOM_FILTER_SHOULD_SHOW_RESULTS
        );
    }

    // AC3: setting a minimum bathrooms value updates results in real-time
    @Test
    @DisplayName(
        "Setting a minimum bathrooms value updates results in real-time"
    )
    public void testBathroomFilterUpdatesResultsInRealTime() {
        int countBefore = propertyListingPage.getPropertiesCount();

        propertyListingPage.setMinBathrooms(2);
        propertyListingPage.waitForCountToChangeTo(countBefore);

        assertNotEquals(
            countBefore,
            propertyListingPage.getPropertiesCount(),
            ErrorMessages.BATHROOM_FILTER_SHOULD_UPDATE_RESULTS_IN_REAL_TIME
        );
    }

    // AC3: navigating with bathrooms= returns only properties with at least that many bathrooms
    @Test
    @DisplayName("Navigating with bathrooms filter returns matching properties")
    public void testNavigatingWithBathroomFilterShowsResults() {
        propertyListingPage.navigateToWithBathrooms(
            TestDataConstants.AmenitiesFilter.KNOWN_MIN_BATHROOMS
        );

        assertTrue(
            propertyListingPage.getPropertiesCount() > 0,
            ErrorMessages.BATHROOM_FILTER_SHOULD_SHOW_RESULTS
        );
    }

    // AC4: all filters applied simultaneously — URL must reflect all active params (AND logic)
    @Test
    @DisplayName(
        "Applying amenity, bedroom, bathroom, and price filters together reflects all params in URL"
    )
    public void testCombinedFiltersApplyAndLogic() {
        propertyListingPage.navigateToWithCombinedAmenityBedroomBathroomPriceFilters(
            TestDataConstants.AmenitiesFilter.KNOWN_AMENITY_ID,
            TestDataConstants.AmenitiesFilter.KNOWN_MIN_BEDROOMS,
            TestDataConstants.AmenitiesFilter.KNOWN_MIN_BATHROOMS,
            TestDataConstants.PriceFilter.KNOWN_MIN_PRICE,
            TestDataConstants.PriceFilter.KNOWN_MAX_PRICE
        );

        assertAll(
            () ->
                assertTrue(
                    propertyListingPage.urlContains("amenities="),
                    ErrorMessages.COMBINED_AMENITY_BEDROOM_BATHROOM_PRICE_FILTER_URL_SHOULD_CONTAIN_ALL_PARAMS
                ),
            () ->
                assertTrue(
                    propertyListingPage.urlContains("minBedrooms="),
                    ErrorMessages.COMBINED_AMENITY_BEDROOM_BATHROOM_PRICE_FILTER_URL_SHOULD_CONTAIN_ALL_PARAMS
                ),
            () ->
                assertTrue(
                    propertyListingPage.urlContains("minBathrooms="),
                    ErrorMessages.COMBINED_AMENITY_BEDROOM_BATHROOM_PRICE_FILTER_URL_SHOULD_CONTAIN_ALL_PARAMS
                ),
            () ->
                assertTrue(
                    propertyListingPage.urlContains("minPrice="),
                    ErrorMessages.COMBINED_AMENITY_BEDROOM_BATHROOM_PRICE_FILTER_URL_SHOULD_CONTAIN_ALL_PARAMS
                )
        );
    }

    // AC5: mobile — Filters button is visible on mobile viewport
    @Test
    @DisplayName("A 'Filters' button is visible on mobile viewport")
    public void testMobileFilterButtonIsVisible() {
        driver
            .manage()
            .window()
            .setSize(new Dimension(WaitConstants.MOBILE_WIDTH, 812));
        mobileViewportActive = true;

        assertTrue(
            propertyListingPage.isMobileFilterButtonDisplayed(),
            ErrorMessages.MOBILE_FILTER_BUTTON_SHOULD_BE_VISIBLE
        );
    }

    // AC5: mobile — tapping Filters button opens the slide-out filter modal
    @Test
    @DisplayName(
        "Tapping the mobile Filters button opens the slide-out filter modal"
    )
    public void testMobileFilterButtonOpensModal() {
        driver
            .manage()
            .window()
            .setSize(new Dimension(WaitConstants.MOBILE_WIDTH, 812));
        mobileViewportActive = true;
        propertyListingPage.clickMobileFilterButton();

        assertTrue(
            propertyListingPage.isMobileFilterModalDisplayed(),
            ErrorMessages.MOBILE_FILTER_BUTTON_SHOULD_OPEN_MODAL
        );
    }
}
