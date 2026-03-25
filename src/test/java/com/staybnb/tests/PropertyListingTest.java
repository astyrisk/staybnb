package com.staybnb.tests;

import com.staybnb.config.TestConfig;
import com.staybnb.pages.PropertyListingPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;

import java.util.List;

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
        assertFalse(propertyListingPage.getPropertyCards().isEmpty(), "Property listing page should display property cards.");
    }

    @Test
    public void testFirstPropertyCardHasImage() {
        WebElement card = propertyListingPage.getPropertyCards().get(0);
        assertTrue(propertyListingPage.hasImage(card), "Property card should have an image.");
    }

    @Test
    public void testFirstPropertyCardHasTitle() {
        WebElement card = propertyListingPage.getPropertyCards().get(0);
        assertFalse(propertyListingPage.getTitle(card).isEmpty(), "Property card should have a title.");
    }

    @Test
    public void testFirstPropertyCardLocationFormat() {
        WebElement card = propertyListingPage.getPropertyCards().get(0);
        String location = propertyListingPage.getLocation(card);
        assertTrue(location.contains(","), "Location should display city and country separated by comma.");
    }

    @Test
    public void testFirstPropertyCardPriceFormat() {
        WebElement card = propertyListingPage.getPropertyCards().get(0);
        String price = propertyListingPage.getPrice(card);
        assertTrue(price.contains("/ night"), "Price should contain '/ night'.");
    }

    @Test
    public void testPropertyCardNavigation() {
        WebElement firstCard = propertyListingPage.getPropertyCards().get(0);
        String expectedHref = propertyListingPage.getCardHref(firstCard);
        propertyListingPage.clickPropertyCard(firstCard);
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.endsWith(expectedHref) || expectedHref.contains(currentUrl.replace(TestConfig.BASE_URL, "")),
                "Should navigate to the property detail page.");
    }

    @Test
    public void testGridColumnsOnDesktopLarge() {
        driver.manage().window().setSize(new Dimension(1920, 1080));
        propertyListingPage.waitForGridColumns(4);
        assertEquals(4, propertyListingPage.getGridColumnCount(), "Grid should have 4 columns on large desktop.");
    }

    @Test
    public void testGridColumnsOnDesktopMedium() {
        driver.manage().window().setSize(new Dimension(1025, 1080));
        propertyListingPage.waitForGridColumns(3);
        assertEquals(3, propertyListingPage.getGridColumnCount(), "Grid should have 3 columns on medium desktop.");
    }

    @Test
    public void testGridColumnsOnTablet() {
        driver.manage().window().setSize(new Dimension(770, 1024));
        propertyListingPage.waitForGridColumns(2);
        assertEquals(2, propertyListingPage.getGridColumnCount(), "Grid should have 2 columns on tablet.");
    }

    @Test
    public void testAbsenceOfFilters() {
        assertTrue(propertyListingPage.areControlsEmpty(), "Property list controls should be empty.");
    }

    @Test
    public void testAbsenceOfSearchAndSort() {
        assertFalse(propertyListingPage.hasSearchOrFilters(), "There should be no search or filters.");
    }
}
