package com.staybnb.tests;

import com.staybnb.pages.HostDashboardPage;
import com.staybnb.pages.LoginPage;
import com.staybnb.pages.Navbar;
import com.staybnb.utils.Constants;
import com.staybnb.utils.ErrorMessages;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.*;

public class HostDashboardTest extends BaseTest {
    private LoginPage loginPage;
    private Navbar navbar;
    private HostDashboardPage hostDashboardPage;

    @BeforeEach
    public void setup() {
        loginPage = new LoginPage(driver);
        navbar = new Navbar(driver);
        hostDashboardPage = new HostDashboardPage(driver);
    }

    private WebElement getFirstPropertyCardForExistingHost() {
        loginAsTestUserAndLandOnHome(loginPage);
        hostDashboardPage.load();
        if (hostDashboardPage.getPropertyCards().isEmpty()) {
            throw new IllegalStateException("Host account has no properties to validate dashboard cards.");
        }
        return hostDashboardPage.getPropertyCards().get(0);
    }

    @Test
    public void testHostDashboardShowsPropertyCardsForHostWithProperties() {
        loginAsTestUserAndLandOnHome(loginPage);
        hostDashboardPage.load();

        assertFalse(
                hostDashboardPage.getPropertyCards().isEmpty(),
                ErrorMessages.HOST_DASHBOARD_SHOULD_DISPLAY_PROPERTY_CARDS_FOR_HOST_WITH_PROPERTIES
        );
    }

    @Test
    public void testHostDashboardPropertyCardShowsThumbnail() {
        WebElement firstCard = getFirstPropertyCardForExistingHost();
        assertTrue(
                hostDashboardPage.hasThumbnail(firstCard),
                ErrorMessages.HOST_DASHBOARD_PROPERTY_CARD_SHOULD_DISPLAY_REQUIRED_DETAILS
        );
    }

    @Test
    public void testHostDashboardPropertyCardShowsTitle() {
        WebElement firstCard = getFirstPropertyCardForExistingHost();
        assertFalse(
                hostDashboardPage.getTitle(firstCard).isEmpty(),
                ErrorMessages.HOST_DASHBOARD_PROPERTY_CARD_SHOULD_DISPLAY_REQUIRED_DETAILS
        );
    }

    @Test
    public void testHostDashboardPropertyCardShowsLocation() {
        WebElement firstCard = getFirstPropertyCardForExistingHost();
        assertFalse(
                hostDashboardPage.getLocation(firstCard).isEmpty(),
                ErrorMessages.HOST_DASHBOARD_PROPERTY_CARD_SHOULD_DISPLAY_REQUIRED_DETAILS
        );
    }

    @Test
    public void testHostDashboardPropertyCardShowsPricePerNight() {
        WebElement firstCard = getFirstPropertyCardForExistingHost();
        assertTrue(
                hostDashboardPage.getPrice(firstCard).contains("/night"),
                ErrorMessages.HOST_DASHBOARD_PROPERTY_CARD_SHOULD_DISPLAY_REQUIRED_DETAILS
        );
    }

    @Test
    public void testHostDashboardPropertyCardShowsPublishedOrDraftStatus() {
        WebElement firstCard = getFirstPropertyCardForExistingHost();
        String statusText = hostDashboardPage.getStatus(firstCard).trim();
        assertTrue(
                statusText.equalsIgnoreCase("Published") || statusText.equalsIgnoreCase("Draft"),
                ErrorMessages.HOST_DASHBOARD_PROPERTY_CARD_SHOULD_DISPLAY_REQUIRED_DETAILS
        );
    }

    @Test
    public void testHostDashboardPropertyCardShowsRating() {
        WebElement firstCard = getFirstPropertyCardForExistingHost();
        assertTrue(
                hostDashboardPage.hasRating(firstCard),
                ErrorMessages.HOST_DASHBOARD_PROPERTY_CARD_SHOULD_DISPLAY_REQUIRED_DETAILS
        );
    }

    @Test
    public void testHostDashboardShowsSummaryCount() {
        loginAsTestUserAndLandOnHome(loginPage);
        hostDashboardPage.load();

        String subtitle = hostDashboardPage.getSummarySubtitle().toLowerCase();
        assertTrue(
                subtitle.matches(".*\\d+\\s+properties?.*"),
                ErrorMessages.HOST_DASHBOARD_SUMMARY_SHOULD_INCLUDE_TOTAL_PROPERTIES_COUNT
        );
    }

    @Test
    public void testHostDashboardShowsCreateNewPropertyButton() {
        loginAsTestUserAndLandOnHome(loginPage);
        hostDashboardPage.load();

        assertTrue(
                hostDashboardPage.isCreateNewPropertyButtonVisible(),
                ErrorMessages.HOST_DASHBOARD_SHOULD_DISPLAY_CREATE_NEW_PROPERTY_BUTTON
        );
    }

    @Test
    public void testHostDashboardCreateNewPropertyButtonLinksToCreatePage() {
        loginAsTestUserAndLandOnHome(loginPage);
        hostDashboardPage.load();

        assertTrue(
                hostDashboardPage.getCreateNewPropertyHref().endsWith("/hosting/create"),
                ErrorMessages.HOST_DASHBOARD_CREATE_NEW_PROPERTY_LINK_SHOULD_POINT_TO_CREATE_PAGE
        );
    }

    @Test
    public void testHostDashboardPropertyCardShowsEditAction() {
        WebElement firstCard = getFirstPropertyCardForExistingHost();
        assertTrue(
                hostDashboardPage.hasEditAction(firstCard),
                ErrorMessages.HOST_DASHBOARD_PROPERTY_CARD_SHOULD_DISPLAY_EDIT_DELETE_AND_PUBLISH_TOGGLE_ACTIONS
        );
    }

    @Test
    public void testHostDashboardPropertyCardShowsDeleteAction() {
        WebElement firstCard = getFirstPropertyCardForExistingHost();
        assertTrue(
                hostDashboardPage.hasDeleteAction(firstCard),
                ErrorMessages.HOST_DASHBOARD_PROPERTY_CARD_SHOULD_DISPLAY_EDIT_DELETE_AND_PUBLISH_TOGGLE_ACTIONS
        );
    }

    @Test
    public void testHostDashboardPropertyCardShowsPublishToggleAction() {
        WebElement firstCard = getFirstPropertyCardForExistingHost();
        assertTrue(
                hostDashboardPage.hasPublishToggleAction(firstCard),
                ErrorMessages.HOST_DASHBOARD_PROPERTY_CARD_SHOULD_DISPLAY_EDIT_DELETE_AND_PUBLISH_TOGGLE_ACTIONS
        );
    }

    //FIX
    @Test
    public void testHostDashboardEmptyStateVisibleForHostWithNoProperties() {
        registerNewUserAndLandOnHome("testhosting");
        navbar.clickBecomeAHost();
        hostDashboardPage.load();

        assertTrue(
                hostDashboardPage.isEmptyStateVisible(),
                ErrorMessages.HOST_DASHBOARD_EMPTY_STATE_SHOULD_BE_VISIBLE_FOR_HOST_WITH_NO_PROPERTIES
        );
    }

    @Test
    public void testHostingPropertiesApiResponseNotNullForHost() {
        loginAsTestUserAndLandOnHome(loginPage);

        String response = hostDashboardPage.getHostingPropertiesViaApi();
        assertNotNull(response, ErrorMessages.API_RESPONSE_SHOULD_NOT_BE_NULL);
    }

    @Test
    public void testHostingPropertiesApiIncludesPublishedAndUnpublishedForHost() {
        loginAsTestUserAndLandOnHome(loginPage);

        String response = hostDashboardPage.getHostingPropertiesViaApi();
        String normalized = response.replaceAll("\\s+", "").toLowerCase();
        boolean hasPublished = normalized.contains("\"published\":true")
                || normalized.contains("\"ispublished\":true")
                || normalized.contains("\"status\":\"published\"");
        boolean hasUnpublished = normalized.contains("\"published\":false")
                || normalized.contains("\"ispublished\":false")
                || normalized.contains("\"status\":\"draft\"");

        assertTrue(
                hasPublished && hasUnpublished,
                ErrorMessages.HOST_DASHBOARD_API_RESPONSE_SHOULD_INCLUDE_BOTH_PUBLISHED_AND_UNPUBLISHED_PROPERTIES
        );
    }

    @Test
    public void testHostingPropertiesApiReturns403ForNonHost() {
        registerNewUserAndLandOnHome("testhosting");

        long status = hostDashboardPage.getHostingPropertiesStatusViaApi();
        assertEquals(403L, status, ErrorMessages.HOST_DASHBOARD_API_SHOULD_RETURN_403_FOR_NON_HOST);
    }

    @Test
    public void testHostingPropertiesApiReturns401WhenLoggedOut() {
        long status = hostDashboardPage.getHostingPropertiesStatusViaApi();
        assertEquals(401L, status, ErrorMessages.HOST_DASHBOARD_API_SHOULD_RETURN_401_WHEN_NOT_LOGGED_IN);
    }
}
