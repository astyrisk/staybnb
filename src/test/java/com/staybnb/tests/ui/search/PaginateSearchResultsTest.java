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

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Epic("Search")
@Feature("Paginate Search Results")
@Tag("regression")
public class PaginateSearchResultsTest extends BaseTest {
    private PropertyListingPage propertyListingPage;

    @BeforeEach
    public void setup() {
        propertyListingPage = new PropertyListingPage(driver);
        propertyListingPage.navigateTo();
    }

    // AC1: when results page loads, only the first 20 properties are displayed
    @Test
    @DisplayName("First page displays at most 20 property cards")
    public void testFirstPageShowsAtMostTwentyCards() {
        List<?> cards = propertyListingPage.getPropertyCards();
        assertTrue(
                cards.size() <= TestDataConstants.Pagination.PAGE_SIZE,
                ErrorMessages.PAGINATION_FIRST_PAGE_SHOULD_SHOW_AT_MOST_PAGE_SIZE_CARDS
        );
    }

    // AC2: clicking "Next" loads the next page of results
    @Test
    @DisplayName("Clicking 'Next' navigates to the next page of results")
    public void testClickingNextLoadsNextPage() {
        propertyListingPage.clickNextPage();
        propertyListingPage.waitForPageInUrl(2);
        assertTrue(
                propertyListingPage.urlContains("page=2"),
                ErrorMessages.PAGINATION_CLICKING_NEXT_SHOULD_LOAD_NEXT_PAGE
        );
    }

    // AC3: clicking "Previous" on page 2 returns to page 1
    @Test
    @DisplayName("Clicking 'Previous' on page 2 navigates back to page 1")
    public void testClickingPreviousOnPage2LoadsPreviousPage() {
        propertyListingPage.navigateToPage(2);
        propertyListingPage.clickPreviousPage();
        propertyListingPage.waitForPageParamRemoved();
        assertFalse(
                propertyListingPage.urlContains("page=2"),
                ErrorMessages.PAGINATION_CLICKING_PREVIOUS_SHOULD_LOAD_PREVIOUS_PAGE
        );
    }

    // AC4: pagination info text "Showing X–Y of Z results" is displayed
    @Test
    @DisplayName("Pagination info displays 'Showing X–Y of Z results' format")
    public void testPaginationInfoTextMatchesFormat() {
        String text = propertyListingPage.getPaginationInfoText();
        assertTrue(
                text.matches("Showing \\d+.\\d+ of \\d+ results"),
                ErrorMessages.PAGINATION_INFO_SHOULD_MATCH_SHOWING_FORMAT
        );
    }

    // AC5: URL reflects the current page number
    @Test
    @DisplayName("Navigating to page 2 reflects 'page=2' in the URL")
    public void testPageNumberReflectedInUrl() {
        propertyListingPage.navigateToPage(2);
        assertTrue(
                propertyListingPage.urlContains("page=2"),
                ErrorMessages.PAGINATION_URL_SHOULD_REFLECT_CURRENT_PAGE
        );
    }

    // AC6: "Previous" button is disabled on the first page
    @Test
    @DisplayName("'Previous' button is disabled on the first page")
    public void testPreviousButtonDisabledOnFirstPage() {
        assertTrue(
                propertyListingPage.isPreviousButtonDisabled(),
                ErrorMessages.PAGINATION_PREVIOUS_BUTTON_SHOULD_BE_DISABLED_ON_FIRST_PAGE
        );
    }

    // AC7: "Next" button is disabled on the last page
    @Test
    @DisplayName("'Next' button is disabled on the last page")
    public void testNextButtonDisabledOnLastPage() {
        propertyListingPage.navigateToPage(TestDataConstants.Pagination.LAST_PAGE);
        assertTrue(
                propertyListingPage.isNextButtonDisabled(),
                ErrorMessages.PAGINATION_NEXT_BUTTON_SHOULD_BE_DISABLED_ON_LAST_PAGE
        );
    }

    // AC8: changing sort/filter while on page 2 resets pagination to page 1
    @Test
    @DisplayName("Changing sort while on page 2 resets pagination to page 1")
    public void testPaginationResetsToFirstPageOnFilterChange() {
        propertyListingPage.navigateToPage(2);
        propertyListingPage.selectSortOption(TestDataConstants.SortFilter.SORT_PRICE_ASC);
        propertyListingPage.waitForSortToApply();
        assertFalse(
                propertyListingPage.urlContains("page=2"),
                ErrorMessages.PAGINATION_SHOULD_RESET_TO_PAGE_1_ON_FILTER_CHANGE
        );
    }
}
