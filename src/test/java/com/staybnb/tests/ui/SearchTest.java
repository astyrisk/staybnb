package com.staybnb.tests.ui;

import com.staybnb.assertions.ErrorMessages;
import com.staybnb.data.Constants;
import com.staybnb.pages.PropertyListingPage;
import com.staybnb.tests.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@Epic("Search")
@Feature("Search Properties by Location")
@Tag("regression")
public class SearchTest extends BaseTest {
    private PropertyListingPage propertyListingPage;

    @BeforeEach
    public void setup() {
        propertyListingPage = new PropertyListingPage(driver);
        propertyListingPage.navigateTo();
    }

    @AfterEach
    public void restoreLayout() {
        propertyListingPage.navbar().setDesktopLayout();
    }

    @Test
    @DisplayName("Clicking the compact search bar expands the search form")
    public void testClickingCompactSearchBarExpandsForm() {
        propertyListingPage.navbar().clickCompactSearchBar();

        assertTrue(
                propertyListingPage.navbar().isSearchFormExpanded(),
                ErrorMessages.SEARCH_FORM_SHOULD_EXPAND_ON_COMPACT_BAR_CLICK
        );
    }

    @Test
    @DisplayName("Searching for a city navigates to the city-filtered URL")
    public void testSearchByCityNavigatesToFilteredUrl() {
        propertyListingPage.navbar().searchForCity(Constants.Search.KNOWN_CITY);

        assertTrue(
                isUrlContains("city=" + Constants.Search.KNOWN_CITY),
                ErrorMessages.SEARCH_SHOULD_NAVIGATE_TO_CITY_FILTERED_URL
        );
    }

    @Test
    @DisplayName("Searching for a city with matching properties shows results")
    public void testSearchByCityShowsFilteredResults() {
        propertyListingPage.navigateToWithCity(Constants.Search.KNOWN_CITY);

        assertTrue(
                propertyListingPage.getPropertiesCount() > 0,
                ErrorMessages.SEARCH_SHOULD_SHOW_RESULTS_FOR_KNOWN_CITY
        );
    }

    @Test
    @DisplayName("Searching for a city with no matching properties shows the empty state")
    public void testSearchWithNonExistentCityShowsEmptyState() {
        propertyListingPage.navbar().searchForCity(Constants.Search.UNKNOWN_CITY);
        propertyListingPage.waitForSearchResults();

        assertTrue(
                propertyListingPage.isEmptyStateDisplayed(),
                ErrorMessages.SEARCH_SHOULD_SHOW_EMPTY_STATE_FOR_UNKNOWN_CITY
        );
    }

    @Test
    @DisplayName("Searching for a city with no matching properties shows zero properties count")
    public void testSearchWithNonExistentCityShowsZeroCount() {
        propertyListingPage.navigateToWithCity(Constants.Search.UNKNOWN_CITY);

        assertEquals(
                0,
                propertyListingPage.getPropertiesCount(),
                ErrorMessages.SEARCH_SHOULD_SHOW_ZERO_COUNT_FOR_UNKNOWN_CITY
        );
    }

    @Test
    @DisplayName("Mobile: Compact search bar is visible in the navbar")
    @Tag("mobile")
    public void testMobileCompactSearchBarIsVisible() {
        propertyListingPage.navbar().setMobileLayout();

        assertTrue(
                propertyListingPage.navbar().isCompactSearchBarDisplayed(),
                ErrorMessages.SEARCH_COMPACT_BAR_SHOULD_BE_VISIBLE_ON_MOBILE
        );
    }
}
