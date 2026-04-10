package com.staybnb.tests.api;

import com.staybnb.assertions.ErrorMessages;
import com.staybnb.pages.HostDashboardPage;
import com.staybnb.pages.LoginPage;
import com.staybnb.pages.LogoutPage;
import com.staybnb.tests.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@Epic("Host Management")
@Feature("Hosting Properties API")
@Tag("api")
public class HostingPropertiesApiTest extends BaseTest {
    private HostDashboardPage hostDashboardPage;

    @BeforeEach
    public void setup() {
        LoginPage loginPage = new LoginPage(driver);
        hostDashboardPage = new HostDashboardPage(driver);
        loginAsTestUserAndLandOnHome(loginPage);
    }

    @Test
    @DisplayName("Hosting properties API response is not null for a host")
    public void testHostingPropertiesApiResponseNotNullForHost() {
        String response = hostDashboardPage.getHostingPropertiesViaApi();

        assertNotNull(
                response,
                ErrorMessages.API_RESPONSE_SHOULD_NOT_BE_NULL
        );
    }

    @Test
    @DisplayName("Hosting properties API includes both published and unpublished properties")
    public void testHostingPropertiesApiIncludesPublishedAndUnpublishedForHost() {
        assertTrue(
                hostDashboardPage.hasPublishedAndUnpublishedProperties(),
                ErrorMessages.HOST_DASHBOARD_API_RESPONSE_SHOULD_INCLUDE_BOTH_PUBLISHED_AND_UNPUBLISHED_PROPERTIES
        );
    }

    @Test
    @DisplayName("Hosting properties API returns 403 for a non-host user")
    public void testHostingPropertiesApiReturns403ForNonHost() {
        new LogoutPage(driver).logoutAndWaitForTokenCleared();
        registerNewUserAndLandOnHome("testhosting");
        long status = hostDashboardPage.getHostingPropertiesStatusViaApi();

        assertEquals(
                403L,
                status,
                ErrorMessages.HOST_DASHBOARD_API_SHOULD_RETURN_403_FOR_NON_HOST
        );
    }

    @Test
    @DisplayName("Hosting properties API returns 401 when not logged in")
    public void testHostingPropertiesApiReturns401WhenLoggedOut() {
        hostDashboardPage.navbar().clickLogoutAndWaitForRedirectToHome();
        long status = hostDashboardPage.getHostingPropertiesStatusViaApi();

        assertEquals(
                401L,
                status,
                ErrorMessages.HOST_DASHBOARD_API_SHOULD_RETURN_401_WHEN_NOT_LOGGED_IN
        );
    }
}
