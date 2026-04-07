package com.staybnb.tests;

import com.staybnb.assertions.ErrorMessages;
import com.staybnb.data.Constants;
import com.staybnb.data.PropertyPayloads;
import com.staybnb.pages.LoginPage;
import com.staybnb.pages.PublishPropertyPage;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Epic("Property Management")
@Feature("Publish Property")
@Tag("regression")
public class PublishPropertyTest extends BaseTest {
    private LoginPage loginPage;
    private PublishPropertyPage publishPropertyPage;

    @BeforeEach
    public void setup() {
        loginPage = new LoginPage(driver);
        publishPropertyPage = new PublishPropertyPage(driver);
    }

    private String createPropertyAndReturnId(String uniqueTitle) {
        String response = publishPropertyPage.createPropertyViaApi(PropertyPayloads.validCreatePropertyPayloadJson(uniqueTitle));
        return publishPropertyPage.extractCreatedPropertyId(response);
    }

    @Test
    @DisplayName("Publish toggle from dashboard changes property status to Published")
    public void testPublishToggleFromDashboardChangesStatusToPublished() {
        loginAsTestUserAndLandOnHome(loginPage);
        String uniqueTitle = "Automation Publish Toggle " + System.currentTimeMillis();
        createPropertyAndReturnId(uniqueTitle);
        publishPropertyPage.navigateToHostingDashboard();
        publishPropertyPage.clickPublishToggleOnDashboardForTitle(uniqueTitle);

        publishPropertyPage.waitForPropertyStatusToChangeToPublished(uniqueTitle);

        assertTrue(
                publishPropertyPage.isPropertyPublishedOnDashboard(uniqueTitle),
                ErrorMessages.PUBLISH_PROPERTY_DASHBOARD_TOGGLE_SHOULD_SET_STATUS_TO_PUBLISHED
        );
    }

    @Test
    @DisplayName("Unpublished property does not appear on the public listing page")
    public void testUnpublishedPropertyDoesNotAppearOnPublicListingPage() {
        loginAsTestUserAndLandOnHome(loginPage);
        String uniqueTitle = "Automation Draft Hidden " + System.currentTimeMillis();
        String propertyId = createPropertyAndReturnId(uniqueTitle);
        publishPropertyPage.updatePublishPropertyStatusViaApi(propertyId, false);
        publishPropertyPage.navbar().clickLogoutAndWaitForRedirectToHome();
        publishPropertyPage.navigateToPropertyListing();
        assertFalse(
                publishPropertyPage.isPropertyListedOnCurrentPage(uniqueTitle),
                ErrorMessages.UNPUBLISHED_PROPERTY_SHOULD_NOT_APPEAR_ON_LISTING_PAGE
        );
    }

    @Test
    @DisplayName("Unpublished property still appears on the host dashboard as a draft")
    public void testUnpublishedPropertyStillAppearsOnHostDashboardAsDraft() {
        loginAsTestUserAndLandOnHome(loginPage);
        String uniqueTitle = "Automation Draft Dashboard " + System.currentTimeMillis();
        String propertyId = createPropertyAndReturnId(uniqueTitle);
        publishPropertyPage.updatePublishPropertyStatusViaApi(propertyId, false);
        publishPropertyPage.navigateToHostingDashboard();

        assertTrue(
                publishPropertyPage.isPropertyDraftOnDashboard(uniqueTitle),
                ErrorMessages.UNPUBLISHED_PROPERTY_SHOULD_STAY_ON_DASHBOARD_AS_DRAFT
        );
    }

    @Test
    @DisplayName("Published property appears on the public listing page")
    public void testPublishedPropertyAppearsOnPublicListingPage() {
        loginAsTestUserAndLandOnHome(loginPage);
        String uniqueTitle = "Automation Published Visible " + System.currentTimeMillis();
        String propertyId = createPropertyAndReturnId(uniqueTitle);
        publishPropertyPage.updatePublishPropertyStatusViaApi(propertyId, true);
        publishPropertyPage.navbar().clickLogoutAndWaitForRedirectToHome();
        publishPropertyPage.navigateToPropertyListing();

        assertTrue(
                publishPropertyPage.isPropertyListedOnCurrentPage(uniqueTitle),
                ErrorMessages.PUBLISHED_PROPERTY_SHOULD_APPEAR_ON_LISTING_PAGE
        );
    }

    @Test
    @DisplayName("Publish property API returns 200 and sets isPublished to true for owner")
    public void testPublishPropertyApiReturns200AndIsPublishedTrueForOwner() {
        loginAsTestUserAndLandOnHome(loginPage);
        String uniqueTitle = "Automation Publish API " + System.currentTimeMillis();
        String propertyId = createPropertyAndReturnId(uniqueTitle);
        long status = publishPropertyPage.updatePublishPropertyStatusViaApi(propertyId, true);
        String response = publishPropertyPage.updatePublishPropertyViaApi(propertyId, true);

        assertTrue(
                status == 200L && publishPropertyPage.isPublishSuccessfulResponse(response),
                ErrorMessages.PUBLISH_PROPERTY_API_200_SHOULD_SET_IS_PUBLISHED_TRUE
        );
    }

    @Test
    @DisplayName("Publish property API returns 403 for a non-owner user")
    public void testPublishPropertyApiReturns403ForNonOwner() {
        registerNewUserAndLandOnHome("testpublishproperty");
        long status = publishPropertyPage.updatePublishPropertyStatusViaApi(Constants.PublishProperty.OWNED_PROPERTY_ID, true);

        assertEquals(
                403L,
                status,
                ErrorMessages.PUBLISH_PROPERTY_API_SHOULD_RETURN_403_FOR_NON_OWNER
        );
    }

    @Test
    @DisplayName("Publish property API returns 404 for a non-existent property ID")
    public void testPublishPropertyApiReturns404ForNonExistentPropertyId() {
        loginAsTestUserAndLandOnHome(loginPage);
        long status = publishPropertyPage.updatePublishPropertyStatusViaApi(Constants.PublishProperty.NON_EXISTENT_PROPERTY_ID, true);

        assertEquals(
                404L,
                status,
                ErrorMessages.PUBLISH_PROPERTY_API_SHOULD_RETURN_404_FOR_NON_EXISTENT_PROPERTY
        );
    }

    @Test
    @DisplayName("Publish property API returns 401 when not logged in")
    public void testPublishPropertyApiReturns401WhenLoggedOut() {
        long status = publishPropertyPage.updatePublishPropertyStatusViaApi(Constants.PublishProperty.OWNED_PROPERTY_ID, true);

        assertEquals(
                401L,
                status,
                ErrorMessages.PUBLISH_PROPERTY_API_SHOULD_RETURN_401_WHEN_LOGGED_OUT
        );
    }
}
