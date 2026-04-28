package com.staybnb.tests.ui.navigation;

import com.staybnb.components.PropertyCard;
import com.staybnb.pages.HomePage;
import com.staybnb.config.WaitConstants;
import com.staybnb.assertions.ErrorMessages;
import com.staybnb.tests.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@Epic("Navigation")
@Feature("Home Page")
@Tag("regression")
public class HomeTest extends BaseTest {
    private HomePage homePage;

    @BeforeEach
    public void setup() {
        homePage = new HomePage(driver);
        homePage.navigateTo();
    }

    @AfterEach
    public void restoreWindowSize() {
        driver.manage().window().maximize();
    }

    @Test
    @DisplayName("Hero section is displayed on home page")
    public void testHeroSectionIsDisplayed() {
        assertTrue(
                homePage.isHeroSectionDisplayed(),
                ErrorMessages.HOME_HERO_SECTION_SHOULD_BE_VISIBLE
        );
    }

    @Test
    @DisplayName("Hero section shows correct headline text")
    public void testHeroSectionHeadlineText() {
        assertEquals(
                "Find your next stay",
                homePage.getHeroHeadlineText(),
                ErrorMessages.HOME_HERO_HEADLINE_TEXT_SHOULD_MATCH
        );
    }

    @Test
    @DisplayName("Hero section has a background image")
    public void testHeroSectionHasBackgroundImage() {
        String backgroundImage = homePage.getHeroBackgroundImage();
        assertTrue(
                backgroundImage != null && !backgroundImage.isEmpty() && !backgroundImage.equals("none"),
                ErrorMessages.HOME_HERO_SECTION_SHOULD_HAVE_BACKGROUND_IMAGE
        );
    }

    @Test
    @DisplayName("Category bar is displayed on home page")
    public void testCategoryBarIsDisplayed() {
        assertTrue(
                homePage.isCategoryBarDisplayed(),
                ErrorMessages.HOME_CATEGORY_BAR_SHOULD_BE_VISIBLE
        );
    }

    @Test
    @DisplayName("Category bar contains at least one category icon")
    public void testCategoryBarContainsIcons() {
        List<WebElement> categories = homePage.getCategoryIcons();

        assertFalse(
                categories.isEmpty(),
                ErrorMessages.HOME_CATEGORY_BAR_SHOULD_CONTAIN_ICONS
        );
    }

    @Test
    @DisplayName("Featured properties grid shows between 8 and 12 cards")
    public void testFeaturedPropertiesGridCount() {
        homePage.scrollFeaturedPropertiesIntoView();
        List<PropertyCard> cards = homePage.getCards();

        assertTrue(
                cards.size() >= 8 && cards.size() <= 12,
                ErrorMessages.HOME_GRID_SHOULD_DISPLAY_8_TO_12_FEATURED_CARDS
        );
    }

    @Test
    @DisplayName("Each property card displays all required details")
    public void testPropertyCardsDetailsAreComplete() {
        assertTrue(
                homePage.getCards().stream().allMatch(PropertyCard::isComplete),
                ErrorMessages.HOME_EACH_PROPERTY_CARD_SHOULD_DISPLAY_ALL_DETAILS
        );
    }

    @Test
    @DisplayName("Property grid shows 4 columns on wide desktop")
    public void testGridColumnsDesktopWide() {
        homePage.setWindowSize(WaitConstants.WIDE_DESKTOP_WIDTH, WaitConstants.WIDE_DESKTOP_HEIGHT);
        homePage.waitForGridColumns(4);

        assertEquals(
                4,
                homePage.getGridColumnCount(),
                ErrorMessages.HOME_GRID_SHOULD_HAVE_4_COLUMNS_ON_DESKTOP
        );
    }

    @Test
    @DisplayName("Property grid shows 3 columns on medium desktop")
    public void testGridColumnsDesktopSmall() {
        homePage.setWindowSize(WaitConstants.MEDIUM_DESKTOP_WIDTH, WaitConstants.MEDIUM_DESKTOP_HEIGHT);
        homePage.waitForGridColumns(3);

        assertEquals(
                3,
                homePage.getGridColumnCount(),
                ErrorMessages.HOME_GRID_SHOULD_HAVE_3_COLUMNS_ON_DESKTOP
        );
    }

    @Test
    @DisplayName("Property grid shows 2 columns on tablet viewport")
    public void testGridColumnsTablet() {
        homePage.setWindowSize(WaitConstants.TABLET_TEST_WIDTH, WaitConstants.TABLET_TEST_HEIGHT);
        homePage.waitForGridColumns(2);

        assertEquals(
                2,
                homePage.getGridColumnCount(),
                ErrorMessages.HOME_GRID_SHOULD_HAVE_2_COLUMNS_ON_TABLET
        );
    }
}
