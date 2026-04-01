package com.staybnb.tests;

import com.staybnb.assertions.ErrorMessages;
import com.staybnb.data.Constants;
import com.staybnb.pages.CategoriesApiPage;
import com.staybnb.pages.CreatePropertyPage;
import com.staybnb.pages.HomePage;
import com.staybnb.pages.LoginPage;
import com.staybnb.pages.PropertyDetailsPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class PropertyCategoriesTest extends BaseTest {
    private HomePage homePage;

    @BeforeEach
    public void setup() {
        homePage = new HomePage(driver);
        homePage.navigateTo();
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

    @Test
    public void testCategoryBarIsHorizontallyScrollable() {
        assertTrue(
                homePage.isCategoryBarHorizontallyScrollable(),
                ErrorMessages.CATEGORIES_BAR_SHOULD_BE_HORIZONTALLY_SCROLLABLE
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

    @Test
    public void testSelectingCategoryMarksChipAsActive() {
        homePage.clickCategoryByName("Bungalow");
        assertEquals(
                "Bungalow",
                homePage.getActiveCategoryName(),
                ErrorMessages.SELECTING_CATEGORY_SHOULD_MARK_CHIP_ACTIVE
        );
    }

    @Test
    public void testSelectingCategoryFiltersPropertyGrid() {
        homePage.clickCategoryByName("Bungalow");
        homePage.waitForPropertiesCountToContain("Showing 1 of 1");
        assertTrue(
                homePage.getPropertiesCountText().contains("Showing 1 of 1"),
                ErrorMessages.SELECTING_CATEGORY_SHOULD_FILTER_PROPERTIES
        );
    }

    @Test
    public void testCategoriesApiReturnsListWithIdNameAndIcon() {
        CategoriesApiPage categoriesApiPage = new CategoriesApiPage(driver);
        assertTrue(
                categoriesApiPage.categoriesResponseHasRequiredFieldsViaApi(),
                ErrorMessages.CATEGORIES_API_SHOULD_RETURN_LIST_WITH_ID_NAME_ICON
        );
    }

    @Test
    public void testPropertyDetailsShowsCategoryAlongsidePropertyType() {
        PropertyDetailsPage propertyDetailsPage = new PropertyDetailsPage(driver);
        propertyDetailsPage.navigateTo(Constants.DEFAULT_PROPERTY_ID);
        assertTrue(
                propertyDetailsPage.getType().contains("·"),
                ErrorMessages.PROPERTY_DETAILS_SHOULD_SHOW_TYPE_AND_CATEGORY
        );
    }

    @Test
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
}

