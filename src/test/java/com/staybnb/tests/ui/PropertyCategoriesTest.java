package com.staybnb.tests.ui;

import com.staybnb.assertions.ErrorMessages;
import com.staybnb.config.AppConstants;
import com.staybnb.pages.CreatePropertyPage;
import com.staybnb.pages.HomePage;
import com.staybnb.pages.LoginPage;
import com.staybnb.pages.PropertyDetailsPage;
import com.staybnb.tests.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@Epic("Properties")
@Feature("Property Categories")
@Tag("regression")
public class PropertyCategoriesTest extends BaseTest {
    private HomePage homePage;

    @BeforeEach
    public void setup() {
        homePage = new HomePage(driver);
        homePage.navigateTo();
    }

    @Test
    @DisplayName("Category bar is horizontally scrollable")
    public void testCategoryBarIsHorizontallyScrollable() {
        assertTrue(
                homePage.isCategoryBarHorizontallyScrollable(),
                ErrorMessages.CATEGORIES_BAR_SHOULD_BE_HORIZONTALLY_SCROLLABLE
        );
    }

    // fails
    @Test
    @DisplayName("Property detail page shows category alongside property type")
    public void testPropertyDetailsShowsCategoryAlongsidePropertyType() {
        PropertyDetailsPage propertyDetailsPage = new PropertyDetailsPage(driver);
        propertyDetailsPage.navigateTo(AppConstants.DEFAULT_PROPERTY_ID);

        assertTrue(
                propertyDetailsPage.getType().contains("·"),
                ErrorMessages.PROPERTY_DETAILS_SHOULD_SHOW_TYPE_AND_CATEGORY
        );
    }

    // fails on automation-abdelbary
    @Test
    @DisplayName("Selecting a category chip marks it as active")
    public void testSelectingCategoryMarksChipAsActive() {
        homePage.clickCategoryByName("Bungalow");

        assertEquals(
                "Bungalow",
                homePage.getActiveCategoryName(),
                ErrorMessages.SELECTING_CATEGORY_SHOULD_MARK_CHIP_ACTIVE
        );
    }

    @Test
    @DisplayName("Selecting a category filters the property grid")
    public void testSelectingCategoryFiltersPropertyGrid() {
        homePage.clickCategoryByName("Bungalow");

        homePage.waitForPropertiesCountToContain("Showing 1 of 1");

        assertTrue(
                homePage.getPropertiesCountText().contains("Showing 1 of 1"),
                ErrorMessages.SELECTING_CATEGORY_SHOULD_FILTER_PROPERTIES
        );
    }

    @Test
    @DisplayName("Create property category dropdown is populated with options")
    public void testCreatePropertyCategoryDropdownIsPopulated() {
        LoginPage loginPage = new LoginPage(driver);
        CreatePropertyPage createPropertyPage = new CreatePropertyPage(driver);
        loginAsTestUserAndLandOnHome(loginPage);
        createPropertyPage.navigateTo();

        assertTrue(
                createPropertyPage.getCategoryDropdownOptionCount() > 1,
                ErrorMessages.CREATE_PROPERTY_CATEGORY_DROPDOWN_SHOULD_BE_POPULATED
        );
    }

    @ParameterizedTest(name = "Category chip should exist: {0}")
    @MethodSource("provideExpectedCategoryChips")
    public void testCategoryChipExists(String categoryName) {
        assertTrue(
                homePage.hasCategoryChipNamed(categoryName),
                ErrorMessages.CATEGORIES_BAR_SHOULD_INCLUDE_EXPECTED_CATEGORY_CHIPS
        );
    }

    private static Stream<String> provideExpectedCategoryChips() {
        return Stream.of(
                "All",
                "Apartment",
                "Bungalow",
                "Cabin",
                "Condo",
                "Cottage",
                "House",
                "Loft",
                "Studio",
                "Townhouse",
                "Villa"
        );
    }
}
