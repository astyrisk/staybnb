package com.staybnb.tests.ui.hosting;

import com.staybnb.assertions.ErrorMessages;
import com.staybnb.data.PropertyPayloads;
import com.staybnb.pages.LoginPage;
import com.staybnb.pages.PublishPropertyPage;
import com.staybnb.tests.BaseTest;
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
    private PublishPropertyPage publishPropertyPage;

    @BeforeEach
    public void setup() {
        LoginPage loginPage = new LoginPage(driver);
        publishPropertyPage = new PublishPropertyPage(driver);
        loginAsTestUserAndLandOnHome(loginPage);
    }

    private String createPropertyAndReturnId(String uniqueTitle) {
        String response = publishPropertyPage.createPropertyViaApi(PropertyPayloads.validCreatePropertyPayloadJson(uniqueTitle));
        return publishPropertyPage.extractCreatedPropertyId(response);
    }

    @Test
    @DisplayName("Publish toggle from dashboard changes property status to Published")
    public void testPublishToggleFromDashboardChangesStatusToPublished() {
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
}
