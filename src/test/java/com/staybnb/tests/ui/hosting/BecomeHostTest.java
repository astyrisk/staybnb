package com.staybnb.tests.ui.hosting;

import static org.junit.jupiter.api.Assertions.*;

import com.staybnb.assertions.ErrorMessages;
import com.staybnb.config.AppConstants;
import com.staybnb.pages.OwnProfilePage;
import com.staybnb.tests.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Epic("Host Management")
@Feature("Become a Host")
@Tag("regression")
public class BecomeHostTest extends BaseTest {

    private OwnProfilePage ownProfilePage;

    @BeforeEach
    public void setup() {
        ownProfilePage = new OwnProfilePage(driver);
    }

    @Test
    @DisplayName("Navbar shows 'Become a Host' link for a non-host user")
    public void testNavbarShowsBecomeHostForNonHostUser() {
        registerNewUserAndLandOnHome("testhost");

        assertTrue(
            ownProfilePage.navbar().isBecomeAHostDisplayed(),
            ErrorMessages.NAVBAR_BECOME_HOST_SHOULD_BE_VISIBLE_FOR_GUEST_USER
        );
    }

    @Test
    @DisplayName("Navbar does not show 'My Properties' for a non-host user")
    public void testNavbarDoesNotShowMyPropertiesForNonHostUser() {
        registerNewUserAndLandOnHome("testhost");

        assertFalse(
            ownProfilePage.navbar().isHostDashboardDisplayed(),
            ErrorMessages.NAVBAR_HOST_DASHBOARD_SHOULD_NOT_BE_VISIBLE_FOR_NON_HOST_USER
        );
    }

    private void becomeHostAsNewUser() {
        registerNewUserAndLandOnHome("testhost");
        ownProfilePage.navbar().clickBecomeAHost();
        driver.get(AppConstants.HOME_URL);
    }

    // fails: 'My properties' doesn't exist
    @Test
    @DisplayName("Navbar shows 'My Properties' after becoming a host")
    public void testNavbarShowsMyPropertiesAfterBecomingHost() {
        becomeHostAsNewUser();

        assertTrue(
            ownProfilePage.navbar().isHostDashboardDisplayed(),
            ErrorMessages.NAVBAR_HOST_DASHBOARD_SHOULD_BE_VISIBLE_FOR_HOST_USER
        );
    }

    @Test
    @DisplayName("Navbar does not show 'Become a Host' after becoming a host")
    public void testNavbarDoesNotShowBecomeHostAfterBecomingHost() {
        becomeHostAsNewUser();

        assertFalse(
            ownProfilePage.navbar().isBecomeAHostDisplayed(),
            ErrorMessages.NAVBAR_BECOME_HOST_SHOULD_NOT_BE_VISIBLE_FOR_HOST_USER
        );
    }

    //    @Test
    //    @DisplayName("'My Properties' link navigates to hosting dashboard")
    //    public void testMyPropertiesLinkNavigatesToHosting() {
    //        becomeHostAsNewUser();
    //        ownProfilePage.navbar().clickMyProperties();
    //
    //        assertTrue(
    //                isUrlContains(AppConstants.HOSTING_URL),
    //                ErrorMessages.SHOULD_NAVIGATE_TO_HOSTING_PAGE
    //        );
    //    }

    @Test
    @DisplayName("Profile page shows 'Become a Host' button for non-host user")
    public void testProfileShowsBecomeHostButtonForNonHostUser() {
        registerNewUserAndLandOnHome("testhost");
        ownProfilePage.navigateViaNavbar();

        assertTrue(
            ownProfilePage.isBecomeHostButtonVisible(),
            ErrorMessages.BECOME_HOST_BUTTON_SHOULD_BE_VISIBLE_ON_PROFILE_PAGE
        );
    }

    @Test
    @DisplayName("Become host redirect from navbar")
    public void testBecomeHostRedirectsToHostingFromNavbar() {
        registerNewUserAndLandOnHome("testhost");
        ownProfilePage.navbar().clickBecomeAHost();

        assertTrue(
            isUrlContains(AppConstants.HOSTING_URL),
            ErrorMessages.SHOULD_NAVIGATE_TO_HOSTING_PAGE
        );
    }

    // fails
    @Test
    @DisplayName("Become host redirect from profile page")
    public void testBecomeHostRedirectsToHostingFromProfilePage() {
        registerNewUserAndLandOnHome("testhost");
        ownProfilePage.navigateViaNavbar();
        ownProfilePage.clickBecomeHost();

        assertTrue(
            isUrlContains(AppConstants.HOSTING_URL),
            ErrorMessages.SHOULD_NAVIGATE_TO_HOSTING_PAGE
        );
    }
}
