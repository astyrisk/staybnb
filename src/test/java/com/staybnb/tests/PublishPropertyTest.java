package com.staybnb.tests;

import com.staybnb.assertions.ErrorMessages;
import com.staybnb.data.Constants;
import com.staybnb.pages.LoginPage;
import com.staybnb.pages.PublishPropertyPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PublishPropertyTest extends BaseTest {
    private LoginPage loginPage;
    private PublishPropertyPage publishPropertyPage;

    @BeforeEach
    public void setup() {
        loginPage = new LoginPage(driver);
        publishPropertyPage = new PublishPropertyPage(driver);
    }

    private String validCreatePropertyPayloadJson(String uniqueTitle) {
        return "{"
                + "\"propertyType\":\"ENTIRE_PLACE\","
                + "\"categoryId\":71,"
                + "\"title\":\"" + uniqueTitle + "\","
                + "\"description\":\"Automation flow for publish property.\","
                + "\"locationCountry\":\"Afghanistan\","
                + "\"locationCity\":\"Kabul\","
                + "\"locationAddress\":\"Street 1\","
                + "\"maxGuests\":1,"
                + "\"numBedrooms\":1,"
                + "\"numBeds\":1,"
                + "\"numBathrooms\":1,"
                + "\"amenities\":[],"
                + "\"images\":["
                + "{\"url\":\"https://emplavi.com.br/wp-content/uploads/2024/09/HORZON-Fachada-1-Diurna-jpg.webp\",\"caption\":\"\"}"
                + "],"
                + "\"pricePerNight\":120"
                + "}";
    }

    private String createPropertyAndReturnId(String uniqueTitle) {
        String response = publishPropertyPage.createPropertyViaApi(validCreatePropertyPayloadJson(uniqueTitle));
        return publishPropertyPage.extractCreatedPropertyId(response);
    }

    @Test
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
    public void testUnpublishedPropertyDoesNotAppearOnPublicListingPage() {
        loginAsTestUserAndLandOnHome(loginPage);
        String uniqueTitle = "Automation Draft Hidden " + System.currentTimeMillis();
        String propertyId = createPropertyAndReturnId(uniqueTitle);
        publishPropertyPage.updatePublishPropertyStatusViaApi(propertyId, false);
        publishPropertyPage.navbar().clickLogoutAndWaitForRedirectToHome();
        publishPropertyPage.navigateToPropertyListing();
        assertTrue(
                !publishPropertyPage.isPropertyListedOnCurrentPage(uniqueTitle),
                ErrorMessages.UNPUBLISHED_PROPERTY_SHOULD_NOT_APPEAR_ON_LISTING_PAGE
        );
    }

    @Test
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
    public void testPublishPropertyApiReturns200AndIsPublishedTrueForOwner() {
        loginAsTestUserAndLandOnHome(loginPage);
        String uniqueTitle = "Automation Publish API " + System.currentTimeMillis();
        String propertyId = createPropertyAndReturnId(uniqueTitle);
        long status = publishPropertyPage.updatePublishPropertyStatusViaApi(propertyId, true);
        String response = publishPropertyPage.updatePublishPropertyViaApi(propertyId, true);
        String normalized = response == null ? "" : response.replaceAll("\\s+", "").toLowerCase();

        assertTrue(
                status == 200L && normalized.contains("\"message\":\"propertypublishedsuccessfully\""),
                ErrorMessages.PUBLISH_PROPERTY_API_200_SHOULD_SET_IS_PUBLISHED_TRUE
        );
    }

    @Test
    public void testPublishPropertyApiReturns403ForNonOwner() {
        registerNewUserAndLandOnHome("testpublishproperty");
        long status = publishPropertyPage.updatePublishPropertyStatusViaApi(Constants.PublishProperty.OWNED_PROPERTY_ID, true);
        assertEquals(403L, status, ErrorMessages.PUBLISH_PROPERTY_API_SHOULD_RETURN_403_FOR_NON_OWNER);
    }

    @Test
    public void testPublishPropertyApiReturns404ForNonExistentPropertyId() {
        loginAsTestUserAndLandOnHome(loginPage);
        long status = publishPropertyPage.updatePublishPropertyStatusViaApi(Constants.PublishProperty.NON_EXISTENT_PROPERTY_ID, true);
        assertEquals(404L, status, ErrorMessages.PUBLISH_PROPERTY_API_SHOULD_RETURN_404_FOR_NON_EXISTENT_PROPERTY);
    }

    @Test
    public void testPublishPropertyApiReturns401WhenLoggedOut() {
        long status = publishPropertyPage.updatePublishPropertyStatusViaApi(Constants.PublishProperty.OWNED_PROPERTY_ID, true);
        assertEquals(401L, status, ErrorMessages.PUBLISH_PROPERTY_API_SHOULD_RETURN_401_WHEN_LOGGED_OUT);
    }
}
