package com.staybnb.tests.ui.hosting;

import com.staybnb.config.AppConstants;
import com.staybnb.config.WaitConstants;
import com.staybnb.assertions.ErrorMessages;
import com.staybnb.components.PropertyCard;
import com.staybnb.pages.PropertyDetailsPage;
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

    @AfterEach
    public void restoreWindowSize() {
        driver.manage().window().maximize();
    }

    @Test
    @DisplayName("Property listing page displays property cards")
    public void testPropertyListingHasCards() {
        assertFalse(
                propertyListingPage.getCards().isEmpty(),
                ErrorMessages.PROPERTY_LISTING_SHOULD_DISPLAY_PROPERTY_CARDS
        );
    }

    @Test
    @DisplayName("First property card has an image")
    public void testFirstPropertyCardHasImage() {
        assertTrue(
                propertyListingPage.getFirstCard().hasImage(),
                ErrorMessages.PROPERTY_CARD_SHOULD_HAVE_AN_IMAGE
        );
    }

    @Test
    @DisplayName("First property card has a title")
    public void testFirstPropertyCardHasTitle() {
        assertFalse(
                propertyListingPage.getFirstCard().getTitle().isEmpty(),
                ErrorMessages.PROPERTY_CARD_SHOULD_HAVE_A_TITLE
        );
    }

    @Test
    @DisplayName("First property card location is in 'City, Country' format")
    public void testFirstPropertyCardLocationFormat() {
        assertTrue(
                propertyListingPage.getFirstCard().getLocation().contains(","),
                ErrorMessages.LOCATION_SHOULD_BE_CITY_AND_COUNTRY_SEPARATED_BY_COMMA
        );
    }

    @Test
    @DisplayName("First property card price contains '/ night'")
    public void testFirstPropertyCardPriceFormat() {
        assertTrue(
                propertyListingPage.getFirstCard().getPrice().contains("/ night"),
                ErrorMessages.PRICE_SHOULD_CONTAIN_PER_NIGHT
        );
    }

    @Test
    @DisplayName("Clicking a property card navigates to the property detail page")
    public void testPropertyCardNavigation() {
        PropertyCard firstCard = propertyListingPage.getFirstCard();
        String expectedHref = firstCard.getHref();
        PropertyDetailsPage detailsPage = firstCard.click();
        String currentUrl = detailsPage.getCurrentUrl();

        assertTrue(
                currentUrl.endsWith(expectedHref) || expectedHref.contains(currentUrl.replace(AppConstants.BASE_URL, "")),
                ErrorMessages.SHOULD_NAVIGATE_TO_PROPERTY_DETAIL_PAGE
        );
    }

    @Test
    @DisplayName("Property grid shows 4 columns on large desktop")
    public void testGridColumnsOnDesktopLarge() {
        propertyListingPage
                .setWindowSize(WaitConstants.WIDE_DESKTOP_WIDTH, WaitConstants.WIDE_DESKTOP_HEIGHT)
                .waitForGridColumns(4);

        assertEquals(
                4,
                propertyListingPage.getGridColumnCount(),
                ErrorMessages.LISTING_GRID_SHOULD_HAVE_4_COLUMNS_ON_LARGE_DESKTOP
        );
    }

    @Test
    @DisplayName("Property grid shows 3 columns on medium desktop")
    public void testGridColumnsOnDesktopMedium() {
        propertyListingPage
                .setWindowSize(WaitConstants.MEDIUM_DESKTOP_WIDTH, WaitConstants.MEDIUM_DESKTOP_HEIGHT)
                .waitForGridColumns(3);

        assertEquals(
                3,
                propertyListingPage.getGridColumnCount(),
                ErrorMessages.LISTING_GRID_SHOULD_HAVE_3_COLUMNS_ON_MEDIUM_DESKTOP
        );
    }

    @Test
    @DisplayName("Property grid shows 2 columns on tablet viewport")
    public void testGridColumnsOnTablet() {
        propertyListingPage
                .setWindowSize(WaitConstants.TABLET_TEST_WIDTH, WaitConstants.TABLET_TEST_HEIGHT)
                .waitForGridColumns(2);

        assertEquals(
                2,
                propertyListingPage.getGridColumnCount(),
                ErrorMessages.LISTING_GRID_SHOULD_HAVE_2_COLUMNS_ON_TABLET
        );
    }
}
