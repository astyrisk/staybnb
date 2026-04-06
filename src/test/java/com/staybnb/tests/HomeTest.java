package com.staybnb.tests;

import com.staybnb.pages.HomePage;
import com.staybnb.data.Constants;
import com.staybnb.assertions.ErrorMessages;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class HomeTest extends BaseTest {
    private HomePage homePage;

    @BeforeEach
    public void setup() {
        homePage = new HomePage(driver);
        homePage.navigateTo();
    }

    @Test
    public void testHeroSectionIsDisplayed() {
        assertTrue(
                homePage.isHeroSectionDisplayed(),
                ErrorMessages.HOME_HERO_SECTION_SHOULD_BE_VISIBLE
        );
    }

    @Test
    public void testHeroSectionHeadlineText() {
        assertEquals(
                "Find your next stay",
                homePage.getHeroHeadlineText(),
                ErrorMessages.HOME_HERO_HEADLINE_TEXT_SHOULD_MATCH
        );
    }

    @Test
    public void testHeroSectionHasBackgroundImage() {
        String backgroundImage = homePage.getHeroBackgroundImage();
        assertTrue(
                backgroundImage != null && !backgroundImage.isEmpty() && !backgroundImage.equals("none"),
                ErrorMessages.HOME_HERO_SECTION_SHOULD_HAVE_BACKGROUND_IMAGE
        );
    }

    @Test
    public void testCategoryBarIsDisplayed() {
        assertTrue(
                homePage.isCategoryBarDisplayed(),
                ErrorMessages.HOME_CATEGORY_BAR_SHOULD_BE_VISIBLE
        );
    }

    @Test
    public void testCategoryBarContainsIcons() {
        List<WebElement> categories = homePage.getCategoryIcons();

        assertFalse(
                categories.isEmpty(),
                ErrorMessages.HOME_CATEGORY_BAR_SHOULD_CONTAIN_ICONS
        );
    }

    @Test
    public void testFeaturedPropertiesGridCount() {
        homePage.scrollFeaturedPropertiesIntoView();
        List<WebElement> cards = homePage.getPropertyCards();
        int cardCount = cards.size();

        assertTrue(
                cardCount >= 8 && cardCount <= 12,
                ErrorMessages.HOME_GRID_SHOULD_DISPLAY_8_TO_12_FEATURED_CARDS
        );
    }

    @Test
    public void testPropertyCardsArePresent() {
        List<WebElement> cards = homePage.getPropertyCards();

        assertFalse(
                cards.isEmpty(),
                ErrorMessages.HOME_PROPERTY_CARDS_SHOULD_BE_PRESENT
        );
    }

    @Test
    public void testPropertyCardsDetailsAreComplete() {
        List<WebElement> cards = homePage.getPropertyCards();

        assertTrue(
            cards.stream().allMatch(card -> homePage.isCardDetailsComplete(card)),
            ErrorMessages.HOME_EACH_PROPERTY_CARD_SHOULD_DISPLAY_ALL_DETAILS
        );
    }

    @Test
    public void testGridColumnsDesktopWide() {
        driver.manage().window().setSize(new Dimension(Constants.WIDE_DESKTOP_WIDTH, Constants.WIDE_DESKTOP_HEIGHT));
        homePage.waitForGridColumns(4);

        assertEquals(
                4,
                homePage.getGridColumnCount(),
                ErrorMessages.HOME_GRID_SHOULD_HAVE_4_COLUMNS_ON_DESKTOP
        );
    }

    @Test
    public void testGridColumnsDesktopSmall() {
        driver.manage().window().setSize(new Dimension(Constants.MEDIUM_DESKTOP_WIDTH, Constants.MEDIUM_DESKTOP_HEIGHT));
        homePage.waitForGridColumns(3);

        assertEquals(
                3,
                homePage.getGridColumnCount(),
                ErrorMessages.HOME_GRID_SHOULD_HAVE_3_COLUMNS_ON_DESKTOP
        );
    }

    @Test
    public void testGridColumnsTablet() {
        driver.manage().window().setSize(new Dimension(Constants.TABLET_TEST_WIDTH, Constants.TABLET_TEST_HEIGHT));
        homePage.waitForGridColumns(2);

        assertEquals(
                2,
                homePage.getGridColumnCount(),
                ErrorMessages.HOME_GRID_SHOULD_HAVE_2_COLUMNS_ON_TABLET
        );
    }
}
