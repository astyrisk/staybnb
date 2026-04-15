package com.staybnb.tests.ui.hosting;

import com.staybnb.config.AppConstants;
import com.staybnb.assertions.ErrorMessages;
import com.staybnb.pages.PropertyListingPage;
import com.staybnb.tests.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.*;

@Epic("Properties")
@Feature("Property Listing")
@Tag("regression")
public class PropertyListingTest extends BaseTest {
    private PropertyListingPage propertyListingPage;

    @BeforeEach
    public void setup() {
        propertyListingPage = new PropertyListingPage(driver);
        propertyListingPage.navigateTo();
    }

    @Test
    @DisplayName("Property listing page displays property cards")
    public void testPropertyListingHasCards() {
        assertFalse(
                propertyListingPage.getPropertyCards().isEmpty(),
                ErrorMessages.PROPERTY_LISTING_SHOULD_DISPLAY_PROPERTY_CARDS
        );
    }

    @Test
    @DisplayName("First property card has an image")
    public void testFirstPropertyCardHasImage() {
        WebElement card = propertyListingPage.getPropertyCards().get(0);

        assertTrue(
                propertyListingPage.hasImage(card),
                ErrorMessages.PROPERTY_CARD_SHOULD_HAVE_AN_IMAGE
        );
    }

    @Test
    @DisplayName("First property card has a title")
    public void testFirstPropertyCardHasTitle() {
        WebElement card = propertyListingPage.getPropertyCards().get(0);

        assertFalse(
                propertyListingPage.getTitle(card).isEmpty(),
                ErrorMessages.PROPERTY_CARD_SHOULD_HAVE_A_TITLE
        );
    }

    @Test
    @DisplayName("First property card location is in 'City, Country' format")
    public void testFirstPropertyCardLocationFormat() {
        WebElement card = propertyListingPage.getPropertyCards().get(0);
        String location = propertyListingPage.getLocation(card);

        assertTrue(
                location.contains(","),
                ErrorMessages.LOCATION_SHOULD_BE_CITY_AND_COUNTRY_SEPARATED_BY_COMMA
        );
    }

    @Test
    @DisplayName("First property card price contains '/ night'")
    public void testFirstPropertyCardPriceFormat() {
        WebElement card = propertyListingPage.getPropertyCards().get(0);
        String price = propertyListingPage.getPrice(card);

        assertTrue(
                price.contains("/ night"),
                ErrorMessages.PRICE_SHOULD_CONTAIN_PER_NIGHT
        );
    }

    @Test
    @DisplayName("Clicking a property card navigates to the property detail page")
    public void testPropertyCardNavigation() {
        WebElement firstCard = propertyListingPage.getPropertyCards().get(0);
        String expectedHref = propertyListingPage.getCardHref(firstCard);
        propertyListingPage.clickPropertyCard(firstCard);
        String currentUrl = driver.getCurrentUrl();

        assertTrue(
                currentUrl.endsWith(expectedHref) || expectedHref.contains(currentUrl.replace(AppConstants.BASE_URL, "")),
                ErrorMessages.SHOULD_NAVIGATE_TO_PROPERTY_DETAIL_PAGE
        );
    }

    @Test
    @DisplayName("Property grid shows 4 columns on large desktop")
    public void testGridColumnsOnDesktopLarge() {
        driver.manage().window().setSize(new Dimension(AppConstants.WIDE_DESKTOP_WIDTH, AppConstants.WIDE_DESKTOP_HEIGHT));
        propertyListingPage.waitForGridColumns(4);

        assertEquals(
                4,
                propertyListingPage.getGridColumnCount(),
                ErrorMessages.LISTING_GRID_SHOULD_HAVE_4_COLUMNS_ON_LARGE_DESKTOP
        );
    }

    @Test
    @DisplayName("Property grid shows 3 columns on medium desktop")
    public void testGridColumnsOnDesktopMedium() {
        driver.manage().window().setSize(new Dimension(AppConstants.MEDIUM_DESKTOP_WIDTH, AppConstants.MEDIUM_DESKTOP_HEIGHT));
        propertyListingPage.waitForGridColumns(3);

        assertEquals(
                3,
                propertyListingPage.getGridColumnCount(),
                ErrorMessages.LISTING_GRID_SHOULD_HAVE_3_COLUMNS_ON_MEDIUM_DESKTOP
        );
    }

    @Test
    @DisplayName("Property grid shows 2 columns on tablet viewport")
    public void testGridColumnsOnTablet() {
        driver.manage().window().setSize(new Dimension(AppConstants.TABLET_TEST_WIDTH, AppConstants.TABLET_TEST_HEIGHT));
        propertyListingPage.waitForGridColumns(2);

        assertEquals(
                2,
                propertyListingPage.getGridColumnCount(),
                ErrorMessages.LISTING_GRID_SHOULD_HAVE_2_COLUMNS_ON_TABLET
        );
    }

    //fails
    @Test
    @DisplayName("Property listing page has no search or sort controls")
    public void testAbsenceOfSearchAndSort() {
        assertFalse(
                propertyListingPage.hasSearchOrFilters(),
                ErrorMessages.THERE_SHOULD_BE_NO_SEARCH_OR_FILTERS
        );
    }
}
