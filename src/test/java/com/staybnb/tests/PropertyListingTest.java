package com.staybnb.tests;

import com.staybnb.data.Constants;
import com.staybnb.assertions.ErrorMessages;
import com.staybnb.pages.PropertyListingPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.*;

public class PropertyListingTest extends BaseTest {
    private PropertyListingPage propertyListingPage;

    @BeforeEach
    public void setup() {
        propertyListingPage = new PropertyListingPage(driver);
        propertyListingPage.navigateTo();
    }

    @Test
    public void testPropertyListingHasCards() {
        assertFalse(
                propertyListingPage.getPropertyCards().isEmpty(),
                ErrorMessages.PROPERTY_LISTING_SHOULD_DISPLAY_PROPERTY_CARDS
        );
    }

    @Test
    public void testFirstPropertyCardHasImage() {
        WebElement card = propertyListingPage.getPropertyCards().get(0);

        assertTrue(
                propertyListingPage.hasImage(card),
                ErrorMessages.PROPERTY_CARD_SHOULD_HAVE_AN_IMAGE
        );
    }

    @Test
    public void testFirstPropertyCardHasTitle() {
        WebElement card = propertyListingPage.getPropertyCards().get(0);

        assertFalse(
                propertyListingPage.getTitle(card).isEmpty(),
                ErrorMessages.PROPERTY_CARD_SHOULD_HAVE_A_TITLE
        );
    }

    @Test
    public void testFirstPropertyCardLocationFormat() {
        WebElement card = propertyListingPage.getPropertyCards().get(0);
        String location = propertyListingPage.getLocation(card);

        assertTrue(
                location.contains(","),
                ErrorMessages.LOCATION_SHOULD_BE_CITY_AND_COUNTRY_SEPARATED_BY_COMMA
        );
    }

    @Test
    public void testFirstPropertyCardPriceFormat() {
        WebElement card = propertyListingPage.getPropertyCards().get(0);
        String price = propertyListingPage.getPrice(card);

        assertTrue(
                price.contains("/ night"),
                ErrorMessages.PRICE_SHOULD_CONTAIN_PER_NIGHT
        );
    }

    @Test
    public void testPropertyCardNavigation() {
        WebElement firstCard = propertyListingPage.getPropertyCards().get(0);
        String expectedHref = propertyListingPage.getCardHref(firstCard);
        propertyListingPage.clickPropertyCard(firstCard);
        String currentUrl = driver.getCurrentUrl();

        assertTrue(
                currentUrl.endsWith(expectedHref) || expectedHref.contains(currentUrl.replace(Constants.BASE_URL, "")),
                ErrorMessages.SHOULD_NAVIGATE_TO_PROPERTY_DETAIL_PAGE
        );
    }

    @Test
    public void testGridColumnsOnDesktopLarge() {
        driver.manage().window().setSize(new Dimension(Constants.WIDE_DESKTOP_WIDTH, Constants.WIDE_DESKTOP_HEIGHT));
        propertyListingPage.waitForGridColumns(4);

        assertEquals(
                4,
                propertyListingPage.getGridColumnCount(),
                ErrorMessages.LISTING_GRID_SHOULD_HAVE_4_COLUMNS_ON_LARGE_DESKTOP
        );
    }

    @Test
    public void testGridColumnsOnDesktopMedium() {
        driver.manage().window().setSize(new Dimension(Constants.MEDIUM_DESKTOP_WIDTH, Constants.MEDIUM_DESKTOP_HEIGHT));
        propertyListingPage.waitForGridColumns(3);

        assertEquals(
                3,
                propertyListingPage.getGridColumnCount(),
                ErrorMessages.LISTING_GRID_SHOULD_HAVE_3_COLUMNS_ON_MEDIUM_DESKTOP
        );
    }

    @Test
    public void testGridColumnsOnTablet() {
        driver.manage().window().setSize(new Dimension(Constants.TABLET_TEST_WIDTH, Constants.TABLET_TEST_HEIGHT));
        propertyListingPage.waitForGridColumns(2);

        assertEquals(
                2,
                propertyListingPage.getGridColumnCount(),
                ErrorMessages.LISTING_GRID_SHOULD_HAVE_2_COLUMNS_ON_TABLET
        );
    }

    @Test
    public void testAbsenceOfFilters() {

        assertTrue(
                propertyListingPage.areControlsEmpty(),
                ErrorMessages.PROPERTY_LIST_CONTROLS_SHOULD_BE_EMPTY
        );
    }

    @Test
    public void testAbsenceOfSearchAndSort() {
        assertFalse(
                propertyListingPage.hasSearchOrFilters(),
                ErrorMessages.THERE_SHOULD_BE_NO_SEARCH_OR_FILTERS
        );
    }
}
