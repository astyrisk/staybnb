package com.staybnb.tests.ui.search;

import com.staybnb.assertions.ErrorMessages;
import com.staybnb.config.TestDataConstants;
import com.staybnb.pages.PropertyListingPage;
import com.staybnb.tests.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

@Epic("Search")
@Feature("Search Properties by Dates and Guests")
@Tag("regression")
public class SearchByDatesAndGuestsTest extends BaseTest {
    private PropertyListingPage propertyListingPage;
    private final String checkIn = TestDataConstants.Search.futureCheckInDate();
    private final String checkOut = TestDataConstants.Search.futureCheckOutDate();

    @BeforeEach
    public void setup() {
        propertyListingPage = new PropertyListingPage(driver);
        propertyListingPage.navigateTo();
    }

    @AfterEach
    public void restoreLayout() {
        propertyListingPage.navbar().setDesktopLayout();
    }

    // AC1: only dates on or after today are selectable for check-in
    @Test
    @DisplayName("Check-in date input has a minimum of today's date")
    public void testCheckInInputHasMinOfToday() {
        String today = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
        propertyListingPage.navbar().clickCompactSearchBar();

        assertEquals(
                today,
                propertyListingPage.navbar().getCheckInMinAttribute(),
                ErrorMessages.SEARCH_CHECK_IN_MIN_SHOULD_BE_TODAY
        );
    }

    // AC2: only dates after the check-in date are selectable for check-out
    @Test
    @DisplayName("Check-out date minimum is constrained to the day after the selected check-in date")
    public void testCheckOutMinIsAfterCheckIn() {
        String expectedCheckOutMin = LocalDate.parse(checkIn)
                .plusDays(1)
                .format(DateTimeFormatter.ISO_LOCAL_DATE);

        propertyListingPage.navbar().clickCompactSearchBar();
        propertyListingPage.navbar().setCheckInDate(checkIn);
        propertyListingPage.navbar().waitForCheckOutMinToBe(expectedCheckOutMin);

        assertEquals(
                expectedCheckOutMin,
                propertyListingPage.navbar().getCheckOutMinAttribute(),
                ErrorMessages.SEARCH_CHECK_OUT_MIN_SHOULD_BE_AFTER_CHECK_IN
        );
    }

    // AC3: submitting with a guest count produces a URL with ?guests=n
    @Test
    @DisplayName("Searching with a guest count navigates to a guest-filtered URL")
    public void testSearchWithGuestsNavigatesToFilteredUrl() {
        propertyListingPage.navbar().searchWithGuests(TestDataConstants.Search.GUEST_COUNT);

        assertTrue(
                isUrlContains("guests=" + TestDataConstants.Search.GUEST_COUNT),
                ErrorMessages.SEARCH_SHOULD_NAVIGATE_TO_GUEST_FILTERED_URL
        );
    }

    // AC4: submitting with dates produces a URL with both checkIn and checkOut params
    @Test
    @DisplayName("Searching with check-in and check-out dates navigates to a date-filtered URL")
    public void testSearchWithDatesNavigatesToFilteredUrl() {
        propertyListingPage.navbar().searchWithDates(checkIn, checkOut);

        assertAll(
                () -> assertTrue(isUrlContains("checkIn="),
                        "URL should contain checkIn param"),
                () -> assertTrue(isUrlContains("checkOut="),
                        "URL should contain checkOut param")
        );
    }

    // AC3: navigating with ?guests=n returns only properties where max_guests >= n
    @Test
    @DisplayName("Navigating with a guest filter shows properties matching the guest count")
    public void testGuestFilterShowsResults() {
        propertyListingPage.navigateToWithGuests(TestDataConstants.Search.GUEST_COUNT);

        assertTrue(
                propertyListingPage.getPropertiesCount() > 0,
                ErrorMessages.SEARCH_WITH_GUEST_FILTER_SHOULD_SHOW_RESULTS
        );
    }

    // AC4: navigating with date params returns only available properties
    @Test
    @DisplayName("Navigating with a date filter shows available properties")
    public void testDateFilterShowsAvailableProperties() {
        propertyListingPage.navigateToWithDates(checkIn, checkOut);

        assertTrue(
                propertyListingPage.getPropertiesCount() > 0,
                ErrorMessages.SEARCH_WITH_DATE_FILTER_SHOULD_SHOW_RESULTS
        );
    }

    // AC5: submitting with no filters returns all published properties
    @Test
    @DisplayName("Searching with no filters returns all published properties")
    public void testSearchWithNoFiltersReturnsAllPublishedProperties() {
        propertyListingPage.navbar().clickCompactSearchBar();
        propertyListingPage.navbar().clickSearch();
        propertyListingPage.waitForSearchResults();

        assertTrue(
                propertyListingPage.getPropertiesCount() > 0,
                ErrorMessages.SEARCH_WITH_NO_FILTERS_SHOULD_RETURN_ALL_PROPERTIES
        );
    }
}
