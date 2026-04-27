package com.staybnb.tests.ui.hosting;

import com.staybnb.assertions.ErrorMessages;
import com.staybnb.pages.HostDashboardPage;
import com.staybnb.tests.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@Epic("Host Management")
@Feature("Host Dashboard")
@Tag("regression")
public class HostDashboardTest extends BaseTest {
    private HostDashboardPage hostDashboardPage;

    @BeforeEach
    public void setup() {
        hostDashboardPage = new HostDashboardPage(driver);
        loginAsUser();
    }

    private WebElement getFirstPropertyCardForExistingHost() {
        List<WebElement> cards = hostDashboardPage.navigateViaNavbar().getPropertyCards();
        if (cards.isEmpty()) {
            throw new IllegalStateException("Host account has no properties to validate dashboard cards.");
        }
        return cards.getFirst();
    }

    @Test
    @DisplayName("Host dashboard shows property cards for a host with properties")
    public void testHostDashboardShowsPropertyCardsForHostWithProperties() {
        assertFalse(
                hostDashboardPage.navigateViaNavbar().getPropertyCards().isEmpty(),
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
    @DisplayName("Host dashboard summary shows total properties count")
    public void testHostDashboardShowsSummaryCount() {
        String subtitle = hostDashboardPage.navigateViaNavbar().getSummarySubtitle().toLowerCase();

        assertTrue(
                subtitle.matches(".*\\d+\\s+properties?.*"),
                ErrorMessages.HOST_DASHBOARD_SUMMARY_SHOULD_INCLUDE_TOTAL_PROPERTIES_COUNT
        );
    }

    @Test
    @DisplayName("Host dashboard shows 'Create New Property' button")
    public void testHostDashboardShowsCreateNewPropertyButton() {
        assertTrue(
                hostDashboardPage.navigateViaNavbar().isCreateNewPropertyButtonVisible(),
                ErrorMessages.HOST_DASHBOARD_SHOULD_DISPLAY_CREATE_NEW_PROPERTY_BUTTON
        );
    }

    @Test
    @DisplayName("'Create New Property' button links to the create property page")
    public void testHostDashboardCreateNewPropertyButtonLinksToCreatePage() {
        assertTrue(
                hostDashboardPage.navigateViaNavbar().getCreateNewPropertyHref().endsWith("/hosting/create"),
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

    @Test
    @DisplayName("Host dashboard shows empty state for a host with no properties")
    public void testHostDashboardEmptyStateVisibleForHostWithNoProperties() {
        hostDashboardPage.logoutAndGoHome();
        registerNewUser();
        hostDashboardPage.clickNavbarBecomeAHost();

        assertTrue(
                hostDashboardPage.navigateViaNavbar().isEmptyStateVisible(),
                ErrorMessages.HOST_DASHBOARD_EMPTY_STATE_SHOULD_BE_VISIBLE_FOR_HOST_WITH_NO_PROPERTIES
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
