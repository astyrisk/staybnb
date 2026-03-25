package com.staybnb.tests;

import com.staybnb.config.TestConfig;
import com.staybnb.pages.HomePage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class HomeTest extends BaseTest {
    private HomePage homePage;

    @BeforeEach
    public void setup() {
        homePage = new HomePage(driver);
        homePage.navigateTo(TestConfig.BASE_URL);
    }

    @Test
    public void testHeroSectionIsDisplayed() {
        assertTrue(homePage.isHeroSectionDisplayed(), "Hero section should be visible.");
    }

    @Test
    public void testHeroSectionHeadlineText() {
        assertEquals("Find your next stay", homePage.getHeroHeadlineText(), "Hero headline text should match.");
    }

    @Test
    public void testHeroSectionHasBackgroundImage() {
        String backgroundImage = homePage.getHeroBackgroundImage();
        assertTrue(backgroundImage != null && !backgroundImage.isEmpty() && !backgroundImage.equals("none"),
                "Hero section should have a background image.");
    }

    @Test
    public void testCategoryBarIsDisplayed() {
        assertTrue(homePage.isCategoryBarDisplayed(), "Horizontal, scrollable category bar should be visible.");
    }

    @Test
    public void testCategoryBarContainsIcons() {
        List<WebElement> categories = homePage.getCategoryIcons();
        assertFalse(categories.isEmpty(), "Category bar should contain icons.");
    }

    @Test
    public void testFeaturedPropertiesGridCount() {
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 500)");
        List<WebElement> cards = homePage.getPropertyCards();
        int cardCount = cards.size();
        assertTrue(cardCount >= 8 && cardCount <= 12, "Grid should display 8-12 featured property cards.");
    }

    @Test
    public void testPropertyCardsArePresent() {
        List<WebElement> cards = homePage.getPropertyCards();
        assertFalse(cards.isEmpty(), "Property cards should be present.");
    }

    @Test
    public void testPropertyCardsDetailsAreComplete() {
        List<WebElement> cards = homePage.getPropertyCards();
        assertTrue(cards.stream().allMatch(card -> homePage.isCardDetailsComplete(card)), 
            "Each property card should display all details: image, title, location, price.");
    }

    @Test
    public void testGridColumnsDesktopWide() {
        driver.manage().window().setSize(new Dimension(1920, 1080));
        homePage.waitForGridColumns(4);
        assertEquals(4, homePage.getGridColumnCount(), "Grid should have 4 columns on desktop.");
    }

    @Test
    public void testGridColumnsDesktopSmall() {
        driver.manage().window().setSize(new Dimension(1025, 1080));
        homePage.waitForGridColumns(3);
        assertEquals(3, homePage.getGridColumnCount(), "Grid should have 3 columns on desktop.");
    }

    @Test
    public void testGridColumnsTablet() {
        driver.manage().window().setSize(new Dimension(770, 1024));
        homePage.waitForGridColumns(2);
        assertEquals(2, homePage.getGridColumnCount(), "Grid should have 2 columns on tablet.");
    }
}
