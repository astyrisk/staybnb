package com.staybnb.tests.api;

import com.staybnb.assertions.ErrorMessages;
import com.staybnb.config.AppConstants;
import com.staybnb.data.PropertyPayloads;
import com.staybnb.pages.LoginPage;
import com.staybnb.pages.LogoutPage;
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
@Feature("Publish Property API")
@Tag("api")
public class PublishPropertyApiTest extends BaseTest {
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
    @DisplayName("Publish property API returns 200 and sets isPublished to true for owner")
    public void testPublishPropertyApiReturns200AndIsPublishedTrueForOwner() {
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
        new LogoutPage(driver).logoutAndWaitForTokenCleared();
        registerNewUserAndLandOnHome("testpublishproperty");
        long status = publishPropertyPage.updatePublishPropertyStatusViaApi(AppConstants.PublishProperty.OWNED_PROPERTY_ID, true);

        assertEquals(
                403L,
                status,
                ErrorMessages.PUBLISH_PROPERTY_API_SHOULD_RETURN_403_FOR_NON_OWNER
        );
    }

    @Test
    @DisplayName("Publish property API returns 404 for a non-existent property ID")
    public void testPublishPropertyApiReturns404ForNonExistentPropertyId() {
        long status = publishPropertyPage.updatePublishPropertyStatusViaApi(AppConstants.NON_EXISTENT_PROPERTY_ID, true);

        assertEquals(
                404L,
                status,
                ErrorMessages.PUBLISH_PROPERTY_API_SHOULD_RETURN_404_FOR_NON_EXISTENT_PROPERTY
        );
    }

    @Test
    @DisplayName("Publish property API returns 401 when not logged in")
    public void testPublishPropertyApiReturns401WhenLoggedOut() {
        publishPropertyPage.navbar().clickLogoutAndWaitForRedirectToHome();
        long status = publishPropertyPage.updatePublishPropertyStatusViaApi(AppConstants.PublishProperty.OWNED_PROPERTY_ID, true);

        assertEquals(
                401L,
                status,
                ErrorMessages.PUBLISH_PROPERTY_API_SHOULD_RETURN_401_WHEN_LOGGED_OUT
        );
    }
}
