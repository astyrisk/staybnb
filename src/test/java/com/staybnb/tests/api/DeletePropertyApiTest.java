package com.staybnb.tests.api;

import com.staybnb.assertions.ErrorMessages;
import com.staybnb.config.TestDataConstants;
import com.staybnb.data.PropertyPayloads;
import com.staybnb.pages.DeletePropertyPage;
import com.staybnb.pages.LoginPage;
import com.staybnb.pages.LogoutPage;
import com.staybnb.tests.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.ResourceAccessMode;
import org.junit.jupiter.api.parallel.ResourceLock;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Epic("Property Management")
@Feature("Delete Property API")
@Tag("api")
@ResourceLock(value = "property-1088", mode = ResourceAccessMode.READ)
public class DeletePropertyApiTest extends BaseTest {
    private DeletePropertyPage deletePropertyPage;

    @BeforeEach
    public void setup() {
        LoginPage loginPage = new LoginPage(driver);
        deletePropertyPage = new DeletePropertyPage(driver);
        loginAsTestUserAndLandOnHome(loginPage);
    }

    private String createPropertyAndReturnId(String uniqueTitle) {
        String response = deletePropertyPage.createPropertyViaApi(PropertyPayloads.validCreatePropertyPayloadJson(uniqueTitle));
        return deletePropertyPage.extractCreatedPropertyId(response);
    }

    @Test
    @DisplayName("Delete property API returns 200 for the property owner")
    public void testDeletePropertyApiReturns200ForOwner() {
        String uniqueTitle = "Automation Delete API " + System.currentTimeMillis();
        String propertyId = createPropertyAndReturnId(uniqueTitle);
        long status = deletePropertyPage.deletePropertyStatusViaApi(propertyId);

        assertEquals(200L,
                status,
                ErrorMessages.DELETE_PROPERTY_API_SHOULD_RETURN_200_FOR_OWNER
        );
    }

    @Test
    @DisplayName("Delete property API returns 403 for a non-owner user")
    public void testDeletePropertyApiReturns403ForNonOwner() {
        new LogoutPage(driver).logoutAndWaitForTokenCleared();
        registerNewUserAndLandOnHome("testdeleteproperty");
        long status = deletePropertyPage.deletePropertyStatusViaApi(TestDataConstants.DeleteProperty.EDITABLE_PROPERTY_ID);

        assertEquals(
                403L,
                status,
                ErrorMessages.DELETE_PROPERTY_API_SHOULD_RETURN_403_FOR_NON_OWNER
        );
    }

    @Test
    @DisplayName("Delete property API returns 404 for a non-existent property ID")
    public void testDeletePropertyApiReturns404ForNonExistentPropertyId() {
        long status = deletePropertyPage.deletePropertyStatusViaApi(TestDataConstants.NON_EXISTENT_PROPERTY_ID);

        assertEquals(
                404L,
                status,
                ErrorMessages.DELETE_PROPERTY_API_SHOULD_RETURN_404_FOR_NON_EXISTENT_PROPERTY
        );
    }

    @Test
    @DisplayName("Delete property API returns 401 when not logged in")
    public void testDeletePropertyApiReturns401WhenLoggedOut() {
        deletePropertyPage.navbar().clickLogoutAndWaitForRedirectToHome();
        long status = deletePropertyPage.deletePropertyStatusViaApi(TestDataConstants.DeleteProperty.EDITABLE_PROPERTY_ID);

        assertEquals(
                401L,
                status,
                ErrorMessages.DELETE_PROPERTY_API_SHOULD_RETURN_401_WHEN_LOGGED_OUT
        );
    }
}
