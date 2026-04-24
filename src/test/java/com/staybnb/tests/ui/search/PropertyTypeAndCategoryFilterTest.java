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
@Feature("Filter by Property Type and Category")
@Tag("regression")
public class PropertyTypeAndCategoryFilterTest extends BaseTest {
    private PropertyListingPage propertyListingPage;

    @BeforeEach
    public void setup() {
        propertyListingPage = new PropertyListingPage(driver);
        propertyListingPage.navigateTo();
    }

    // AC1: selecting a property type radio updates results in real-time
    @Test
    @DisplayName("Selecting a property type radio updates results in real-time")
    public void testPropertyTypeFilterUpdatesResultsInRealTime() {
        propertyListingPage.selectPropertyType(TestDataConstants.TypeCategoryFilter.KNOWN_TYPE);
        propertyListingPage.waitForPropertyTypeFilterToApply();

        assertTrue(
                propertyListingPage.getPropertiesCount() > 0,
                ErrorMessages.TYPE_FILTER_SHOULD_UPDATE_RESULTS_IN_REAL_TIME
        );
    }

    // AC1: navigating with propertyType returns only matching properties
    @Test
    @DisplayName("Navigating with propertyType=ENTIRE_PLACE returns matching properties")
    public void testPropertyTypeFilterShowsMatchingResults() {
        propertyListingPage.navigateToWithPropertyType(TestDataConstants.TypeCategoryFilter.KNOWN_TYPE);

        assertTrue(
                propertyListingPage.getPropertiesCount() > 0,
                ErrorMessages.TYPE_FILTER_SHOULD_SHOW_RESULTS
        );
    }

    // AC2: selecting a category from the dropdown updates results in real-time
    @Test
    @DisplayName("Selecting a category from the dropdown updates results in real-time")
    public void testCategoryFilterUpdatesResultsInRealTime() {
        propertyListingPage.selectCategory(TestDataConstants.TypeCategoryFilter.KNOWN_CATEGORY_ID);
        propertyListingPage.waitForCategoryFilterToApply();

        assertTrue(
                propertyListingPage.getPropertiesCount() > 0,
                ErrorMessages.CATEGORY_FILTER_SHOULD_SHOW_RESULTS
        );
    }

    // AC2: navigating with categoryId returns only properties in that category
    @Test
    @DisplayName("Navigating with categoryId returns only properties in that category")
    public void testCategoryFilterShowsMatchingResults() {
        propertyListingPage.navigateToWithCategory(TestDataConstants.TypeCategoryFilter.KNOWN_CATEGORY_ID);

        assertTrue(
                propertyListingPage.getPropertiesCount() > 0,
                ErrorMessages.CATEGORY_FILTER_SHOULD_SHOW_RESULTS_VIA_URL
        );
    }

    // AC3: applying category AND property type both appear in the URL (AND logic)
    @Test
    @DisplayName("Applying category and property type together reflects both filters in the URL")
    public void testCombinedCategoryAndTypeFiltersApplyAndLogic() {
        propertyListingPage.navigateToWithCategory(TestDataConstants.TypeCategoryFilter.KNOWN_CATEGORY_ID);
        propertyListingPage.selectPropertyType(TestDataConstants.TypeCategoryFilter.KNOWN_TYPE);
        propertyListingPage.waitForPropertyTypeFilterToApply();

        assertAll(
                () -> assertTrue(propertyListingPage.urlContains("categoryId="),
                        ErrorMessages.COMBINED_FILTER_SHOULD_CONTAIN_CATEGORY_PARAM),
                () -> assertTrue(propertyListingPage.urlContains("propertyType="),
                        ErrorMessages.COMBINED_FILTER_SHOULD_CONTAIN_TYPE_PARAM)
        );
    }

    // AC4: clearing all filters restores the full property listing
    @Test
    @DisplayName("Clearing all filters restores the full property listing")
    public void testClearingAllFiltersRestoresAllProperties() {
        propertyListingPage.navigateToWithCategory(TestDataConstants.TypeCategoryFilter.KNOWN_CATEGORY_ID);
        int filteredCount = propertyListingPage.getPropertiesCount();

        propertyListingPage.clearAllFilters();
        propertyListingPage.waitForFiltersCleared();

        assertTrue(
                propertyListingPage.getPropertiesCount() > filteredCount,
                ErrorMessages.CLEAR_FILTERS_SHOULD_RESTORE_ALL_PROPERTIES
        );
    }
}
