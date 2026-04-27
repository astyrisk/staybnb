package com.staybnb.tests.ui.hosting;

import static org.junit.jupiter.api.Assertions.*;

import com.staybnb.assertions.ErrorMessages;
import com.staybnb.config.AppConstants;
import com.staybnb.pages.CreatePropertyPage;
import com.staybnb.pages.ProfilePage;
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

    private ProfilePage ownProfilePage;

    @BeforeEach
    public void setup() {
        ownProfilePage = new ProfilePage(driver);
    }

    @Test
    @DisplayName("Navbar shows 'Become a Host' link for a non-host user")
    public void testNavbarShowsBecomeHostForNonHostUser() {
        registerNewUser();

        assertTrue(
            ownProfilePage.isNavbarBecomeAHostVisible(),
            ErrorMessages.NAVBAR_BECOME_HOST_SHOULD_BE_VISIBLE_FOR_GUEST_USER
        );
    }

    @Test
    @DisplayName("Navbar does not show 'My Properties' for a non-host user")
    public void testNavbarDoesNotShowMyPropertiesForNonHostUser() {
        registerNewUser();

        assertFalse(
            ownProfilePage.isNavbarHostDashboardVisible(),
            ErrorMessages.NAVBAR_HOST_DASHBOARD_SHOULD_NOT_BE_VISIBLE_FOR_NON_HOST_USER
        );
    }

    private void becomeHostAsNewUser() {
        registerNewUser();
        ownProfilePage.clickNavbarBecomeAHost().goHomeViaLogo();
    }

    // fails: 'My properties' doesn't exist
    @Test
    @DisplayName("Navbar shows 'My Properties' after becoming a host")
    public void testNavbarShowsMyPropertiesAfterBecomingHost() {
        becomeHostAsNewUser();

        assertTrue(
            ownProfilePage.isNavbarHostDashboardVisible(),
            ErrorMessages.NAVBAR_HOST_DASHBOARD_SHOULD_BE_VISIBLE_FOR_HOST_USER
        );
    }

    @Test
    @DisplayName("Navbar does not show 'Become a Host' after becoming a host")
    public void testNavbarDoesNotShowBecomeHostAfterBecomingHost() {
        becomeHostAsNewUser();

        assertFalse(
            ownProfilePage.isNavbarBecomeAHostVisible(),
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
    //                ownProfilePage.urlContains(AppConstants.HOSTING_URL),
    //                ErrorMessages.SHOULD_NAVIGATE_TO_HOSTING_PAGE
    //        );
    //    }

    @Test
    @DisplayName("Profile page shows 'Become a Host' button for non-host user")
    public void testProfileShowsBecomeHostButtonForNonHostUser() {
        registerNewUser();
        ownProfilePage.navigateViaNavbar();

        assertTrue(
            ownProfilePage.isBecomeHostButtonVisible(),
            ErrorMessages.BECOME_HOST_BUTTON_SHOULD_BE_VISIBLE_ON_PROFILE_PAGE
        );
    }

    @Test
    @DisplayName("Become host redirect from navbar")
    public void testBecomeHostRedirectsToHostingFromNavbar() {
        registerNewUser();
        ownProfilePage.clickNavbarBecomeAHost();

        assertTrue(
            ownProfilePage.urlContains(AppConstants.HOSTING_URL),
            ErrorMessages.SHOULD_NAVIGATE_TO_HOSTING_PAGE
        );
    }

    // fails
    @Test
    @DisplayName("Become host redirect from profile page")
    public void testBecomeHostRedirectsToHostingFromProfilePage() {
        registerNewUser();
        CreatePropertyPage createPage = ownProfilePage.navigateViaNavbar().clickBecomeHost();

        assertTrue(
            createPage.urlContains(AppConstants.HOSTING_URL),
            ErrorMessages.SHOULD_NAVIGATE_TO_HOSTING_PAGE
        );
    }
}
