package com.staybnb.tests.ui.search;

import com.staybnb.assertions.ErrorMessages;
import com.staybnb.config.TestDataConstants;
import com.staybnb.pages.PropertyListingPage;
import com.staybnb.tests.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Epic("Filter")
@Feature("Filter by Price Range")
@Tag("regression")
public class PriceFilterTest extends BaseTest {
    private PropertyListingPage propertyListingPage;

    @BeforeEach
    public void setup() {
        propertyListingPage = new PropertyListingPage(driver);
        propertyListingPage.navigateTo();
    }

    // AC1: results update in real-time when the price range is adjusted
    @Test
    @DisplayName("Setting a price range in the filter sidebar updates results in real-time")
    public void testPriceRangeUpdatesResultsInRealTime() {
        propertyListingPage.setPriceRange(
                TestDataConstants.PriceFilter.KNOWN_MIN_PRICE,
                TestDataConstants.PriceFilter.KNOWN_MAX_PRICE
        );
        propertyListingPage.waitForPriceFilterToApply();

        assertTrue(
                propertyListingPage.getPropertiesCount() > 0,
                ErrorMessages.PRICE_FILTER_SHOULD_UPDATE_RESULTS_IN_REAL_TIME
        );
    }

    // AC2: minPrice=50 & maxPrice=150 shows properties in that range
    @Test
    @DisplayName("Navigating with minPrice=50 and maxPrice=150 returns matching properties")
    public void testPriceRangeShowsMatchingResults() {
        propertyListingPage.navigateToWithPriceRange(
                TestDataConstants.PriceFilter.KNOWN_MIN_PRICE,
                TestDataConstants.PriceFilter.KNOWN_MAX_PRICE
        );

        assertTrue(
                propertyListingPage.getPropertiesCount() > 0,
                ErrorMessages.PRICE_FILTER_SHOULD_SHOW_RESULTS_IN_RANGE
        );
    }

    // AC2: every displayed property has pricePerNight within the applied range
    @Test
    @DisplayName("Every displayed property has a price within the applied min/max range")
    public void testAllResultsAreWithinPriceRange() {
        propertyListingPage.navigateToWithPriceRange(
                TestDataConstants.PriceFilter.KNOWN_MIN_PRICE,
                TestDataConstants.PriceFilter.KNOWN_MAX_PRICE
        );

        assertTrue(
                propertyListingPage.getPropertyCards().stream().allMatch(card -> {
                    int price = propertyListingPage.getPriceAmount(card);
                    return price >= TestDataConstants.PriceFilter.KNOWN_MIN_PRICE
                            && price <= TestDataConstants.PriceFilter.KNOWN_MAX_PRICE;
                }),
                ErrorMessages.PRICE_FILTER_ALL_RESULTS_SHOULD_BE_IN_RANGE
        );
    }

    // AC3: no matching properties → empty state
    @Test
    @DisplayName("A price range with no matching properties shows the empty state")
    public void testPriceRangeWithNoMatchShowsEmptyState() {
        propertyListingPage.navigateToWithPriceRange(
                TestDataConstants.PriceFilter.NO_MATCH_MIN_PRICE,
                TestDataConstants.PriceFilter.NO_MATCH_MAX_PRICE
        );

        assertTrue(
                propertyListingPage.isEmptyStateDisplayed(),
                ErrorMessages.PRICE_FILTER_SHOULD_SHOW_EMPTY_STATE_FOR_NO_MATCH
        );
    }

    // AC3: no matching properties → zero results count
    @Test
    @DisplayName("A price range with no matching properties shows zero results count")
    public void testPriceRangeWithNoMatchShowsZeroCount() {
        propertyListingPage.navigateToWithPriceRange(
                TestDataConstants.PriceFilter.NO_MATCH_MIN_PRICE,
                TestDataConstants.PriceFilter.NO_MATCH_MAX_PRICE
        );

        assertEquals(
                0,
                propertyListingPage.getPropertiesCount(),
                ErrorMessages.PRICE_FILTER_SHOULD_SHOW_ZERO_COUNT_FOR_NO_MATCH
        );
    }
}
