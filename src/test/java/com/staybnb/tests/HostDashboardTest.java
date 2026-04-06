package com.staybnb.tests;

import com.staybnb.pages.HostDashboardPage;
import com.staybnb.pages.LoginPage;
import com.staybnb.data.Constants;
import com.staybnb.assertions.ErrorMessages;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.WebElement;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class HostDashboardTest extends BaseTest {
    private LoginPage loginPage;
    private HostDashboardPage hostDashboardPage;

    @BeforeEach
    public void setup() {
        loginPage = new LoginPage(driver);
        hostDashboardPage = new HostDashboardPage(driver);
    }

    private void loginAndNavigateToDashboard() {
        loginAsTestUserAndLandOnHome(loginPage);
        hostDashboardPage.navigateTo();
    }

    private WebElement getFirstPropertyCardForExistingHost() {
        loginAndNavigateToDashboard();
        if (hostDashboardPage.getPropertyCards().isEmpty()) {
            throw new IllegalStateException("Host account has no properties to validate dashboard cards.");
        }
        return hostDashboardPage.getPropertyCards().get(0);
    }

    @Test
    public void testHostDashboardShowsPropertyCardsForHostWithProperties() {
        loginAndNavigateToDashboard();

        assertFalse(
                hostDashboardPage.getPropertyCards().isEmpty(),
                ErrorMessages.HOST_DASHBOARD_SHOULD_DISPLAY_PROPERTY_CARDS_FOR_HOST_WITH_PROPERTIES
        );
    }

    @ParameterizedTest(name = "Property card shows {0}")
    @MethodSource("providePropertyCardRequiredDetailsChecks")
    public void testHostDashboardPropertyCardShowsRequiredDetails(String detailName) {
        WebElement firstCard = getFirstPropertyCardForExistingHost();

        assertTrue(
                hostDashboardPage.isDetailDisplayed(firstCard, detailName),
                ErrorMessages.HOST_DASHBOARD_PROPERTY_CARD_SHOULD_DISPLAY_REQUIRED_DETAILS
        );
    }

    @Test
    public void testHostDashboardShowsSummaryCount() {
        loginAndNavigateToDashboard();
        String subtitle = hostDashboardPage.getSummarySubtitle().toLowerCase();

        assertTrue(
                subtitle.matches(".*\\d+\\s+properties?.*"),
                ErrorMessages.HOST_DASHBOARD_SUMMARY_SHOULD_INCLUDE_TOTAL_PROPERTIES_COUNT
        );
    }

    @Test
    public void testHostDashboardShowsCreateNewPropertyButton() {
        loginAndNavigateToDashboard();

        assertTrue(
                hostDashboardPage.isCreateNewPropertyButtonVisible(),
                ErrorMessages.HOST_DASHBOARD_SHOULD_DISPLAY_CREATE_NEW_PROPERTY_BUTTON
        );
    }

    @Test
    public void testHostDashboardCreateNewPropertyButtonLinksToCreatePage() {
        loginAndNavigateToDashboard();

        assertTrue(
                hostDashboardPage.getCreateNewPropertyHref().endsWith("/hosting/create"),
                ErrorMessages.HOST_DASHBOARD_CREATE_NEW_PROPERTY_LINK_SHOULD_POINT_TO_CREATE_PAGE
        );
    }

    @ParameterizedTest(name = "Property card shows action {0}")
    @MethodSource("providePropertyCardActionChecks")
    public void testHostDashboardPropertyCardShowsActions(String actionName) {
        WebElement firstCard = getFirstPropertyCardForExistingHost();

        assertTrue(
                hostDashboardPage.hasCardAction(firstCard, actionName),
                ErrorMessages.HOST_DASHBOARD_PROPERTY_CARD_SHOULD_DISPLAY_EDIT_DELETE_AND_PUBLISH_TOGGLE_ACTIONS
        );
    }

    //FIX
    @Test
    public void testHostDashboardEmptyStateVisibleForHostWithNoProperties() {
        registerNewUserAndLandOnHome("testhosting");
        hostDashboardPage.navbar().clickBecomeAHost();
        hostDashboardPage.navigateTo();

        assertTrue(
                hostDashboardPage.isEmptyStateVisible(),
                ErrorMessages.HOST_DASHBOARD_EMPTY_STATE_SHOULD_BE_VISIBLE_FOR_HOST_WITH_NO_PROPERTIES
        );
    }

    @Test
    public void testHostingPropertiesApiResponseNotNullForHost() {
        loginAsTestUserAndLandOnHome(loginPage);
        String response = hostDashboardPage.getHostingPropertiesViaApi();

        assertNotNull(
                response,
                ErrorMessages.API_RESPONSE_SHOULD_NOT_BE_NULL
        );
    }

    @Test
    public void testHostingPropertiesApiIncludesPublishedAndUnpublishedForHost() {
        loginAsTestUserAndLandOnHome(loginPage);

        assertTrue(
                hostDashboardPage.hasPublishedAndUnpublishedProperties(),
                ErrorMessages.HOST_DASHBOARD_API_RESPONSE_SHOULD_INCLUDE_BOTH_PUBLISHED_AND_UNPUBLISHED_PROPERTIES
        );
    }

    @Test
    public void testHostingPropertiesApiReturns403ForNonHost() {
        registerNewUserAndLandOnHome("testhosting");

        long status = hostDashboardPage.getHostingPropertiesStatusViaApi();
        assertEquals(
                403L,
                status,
                ErrorMessages.HOST_DASHBOARD_API_SHOULD_RETURN_403_FOR_NON_HOST
        );
    }

    @Test
    public void testHostingPropertiesApiReturns401WhenLoggedOut() {
        long status = hostDashboardPage.getHostingPropertiesStatusViaApi();
        assertEquals(
                401L,
                status,
                ErrorMessages.HOST_DASHBOARD_API_SHOULD_RETURN_401_WHEN_NOT_LOGGED_IN
        );
    }

    private static Stream<String> providePropertyCardRequiredDetailsChecks() {
        return Stream.of(
                "thumbnail",
                "title",
                "location",
                "price per night",
                "published or draft status",
                "rating"
        );
    }

    private static Stream<String> providePropertyCardActionChecks() {
        return Stream.of(
                "edit",
                "delete",
                "publish toggle"
        );
    }
}
