package com.staybnb.tests.ui.search;

import com.staybnb.assertions.ErrorMessages;
import com.staybnb.components.PropertyCard;
import com.staybnb.config.TestDataConstants;
import com.staybnb.pages.PropertyListingPage;
import com.staybnb.tests.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Epic("Search")
@Feature("Sort Search Results")
@Tag("regression")
public class SortSearchResultsTest extends BaseTest {
    private PropertyListingPage propertyListingPage;

    @BeforeEach
    public void setup() {
        propertyListingPage = new PropertyListingPage(driver);
        propertyListingPage.navigateTo();
    }

    // AC1: sort dropdown has exactly 4 visible options
    @Test
    @DisplayName("Sort dropdown displays exactly four sort options")
    public void testSortDropdownShowsFourOptions() {
        List<String> options = propertyListingPage.getSortOptionTexts();
        assertEquals(4, options.size(), ErrorMessages.SORT_DROPDOWN_SHOULD_HAVE_FOUR_OPTIONS);
    }

    // AC1: each specific option label is present in the dropdown
    @ParameterizedTest
    @ValueSource(strings = {"Newest", "Price: Low to High", "Price: High to Low", "Top Rated"})
    @DisplayName("Sort dropdown contains expected option label")
    public void testSortDropdownContainsExpectedOption(String expectedOption) {
        List<String> options = propertyListingPage.getSortOptionTexts();
        assertTrue(options.contains(expectedOption), ErrorMessages.SORT_DROPDOWN_SHOULD_CONTAIN_EXPECTED_OPTIONS);
    }

    // AC2: selecting Newest after another sort removes the sort param (Newest is the default)
    @Test
    @DisplayName("Selecting 'Newest' after another sort removes the sort param from the URL")
    public void testSortByNewestResetsToDefault() {
        propertyListingPage.navigateToWithSort(TestDataConstants.SortFilter.SORT_PRICE_ASC);
        propertyListingPage.selectSortOption(TestDataConstants.SortFilter.SORT_NEWEST);
        propertyListingPage.waitForSortParamRemoved();
        assertFalse(propertyListingPage.urlContains("sort="), ErrorMessages.SORT_BY_NEWEST_SHOULD_RESET_URL);
    }

    // AC3: price ASC - first page of results is ordered by price ascending (non-decreasing)
    @Test
    @DisplayName("Sort by 'Price: Low to High' orders all visible properties by price ascending")
    public void testSortByPriceAscendingOrdersResults() {
        propertyListingPage.navigateToWithSort(TestDataConstants.SortFilter.SORT_PRICE_ASC);
        List<Integer> prices = propertyListingPage.getVisibleCardPrices();
        assertTrue(isNonDecreasing(prices), ErrorMessages.SORT_BY_PRICE_ASC_RESULTS_SHOULD_BE_IN_ASCENDING_ORDER);
    }

    // AC4: price DESC - first page of results is ordered by price descending (non-increasing)
    @Test
    @DisplayName("Sort by 'Price: High to Low' orders all visible properties by price descending")
    public void testSortByPriceDescendingOrdersResults() {
        propertyListingPage.navigateToWithSort(TestDataConstants.SortFilter.SORT_PRICE_DESC);
        List<Integer> prices = propertyListingPage.getVisibleCardPrices();
        assertTrue(isNonIncreasing(prices), ErrorMessages.SORT_BY_PRICE_DESC_RESULTS_SHOULD_BE_IN_DESCENDING_ORDER);
    }

    // AC5: top rated - first card rating is >= second card rating
    @Test
    @DisplayName("Sort by 'Top Rated' places the highest-rated property first")
    public void testSortByTopRatedPlacesHighestRatedFirst() {
        propertyListingPage.navigateToWithSort(TestDataConstants.SortFilter.SORT_RATING_DESC);
        List<PropertyCard> cards = propertyListingPage.getCards();
        double firstRating = cards.get(0).getRating();
        double secondRating = cards.get(1).getRating();
        assertTrue(firstRating >= secondRating, ErrorMessages.SORT_BY_RATING_DESC_FIRST_RESULT_SHOULD_HAVE_HIGHEST_RATING);
    }

    // AC6: sort + active filter both reflected in URL after changing sort option
    @Test
    @DisplayName("Changing sort while a price filter is active preserves both sort and filter params in URL")
    public void testSortAndFilterApplyTogether() {
        propertyListingPage.navigateToWithPriceRange(
                TestDataConstants.PriceFilter.KNOWN_MIN_PRICE,
                TestDataConstants.PriceFilter.KNOWN_MAX_PRICE
        );
        propertyListingPage.selectSortOption(TestDataConstants.SortFilter.SORT_PRICE_ASC);
        propertyListingPage.waitForSortToApply();
        assertAll(
                () -> assertTrue(propertyListingPage.urlContains("sort="), ErrorMessages.SORT_AND_FILTER_URL_SHOULD_CONTAIN_SORT_PARAM),
                () -> assertTrue(propertyListingPage.urlContains("minPrice="), ErrorMessages.SORT_AND_FILTER_URL_SHOULD_CONTAIN_FILTER_PARAM)
        );
    }

    private static boolean isNonDecreasing(List<Integer> list) {
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i) < list.get(i - 1)) return false;
        }
        return true;
    }

    private static boolean isNonIncreasing(List<Integer> list) {
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i) > list.get(i - 1)) return false;
        }
        return true;
    }
}
