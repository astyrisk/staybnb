package com.staybnb.tests.api;

import com.staybnb.assertions.ErrorMessages;
import com.staybnb.config.TestDataConstants;
import com.staybnb.data.PropertyPayloads;
import com.staybnb.pages.EditPropertyPage;
import com.staybnb.pages.LoginPage;
import com.staybnb.pages.LogoutPage;
import com.staybnb.tests.BaseApiTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.ResourceLock;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Epic("Property Management")
@Feature("Edit Property API")
@Tag("api")
@ResourceLock("property-1088")
public class EditPropertyApiTest extends BaseApiTest {
    private EditPropertyPage editPropertyPage;

    @BeforeEach
    public void setup() {
        LoginPage loginPage = new LoginPage(driver);
        editPropertyPage = new EditPropertyPage(driver);
        loginAsTestUserAndLandOnHome(loginPage);
    }

    private long updateEditablePropertyStatus() {
        return editPropertyPage.updatePropertyStatusViaApi(
                TestDataConstants.EditProperty.EDITABLE_PROPERTY_ID,
                PropertyPayloads.validEditPayloadJson()
        );
    }

    @Test
    @DisplayName("Saving edit property returns 200 for the property owner")
    public void testEditPropertySaveReturns200ForOwner() {
        assertEquals(
                200L,
                updateEditablePropertyStatus(),
                ErrorMessages.EDIT_PROPERTY_SAVE_SHOULD_RETURN_200_FOR_OWNER
        );
    }

    @Test
    @DisplayName("Edit property API returns 403 for a non-owner user")
    public void testEditPropertyApiReturns403ForNonOwner() {
        new LogoutPage(driver).logoutAndWaitForTokenCleared();
        registerNewUserAndLandOnHome("testeditproperty");

        assertEquals(
                403L,
                updateEditablePropertyStatus(),
                ErrorMessages.EDIT_PROPERTY_API_SHOULD_RETURN_403_FOR_NON_OWNER
        );
    }

    @Test
    @DisplayName("Edit property API returns 404 for a non-existent property ID")
    public void testEditPropertyApiReturns404ForNonExistentPropertyId() {
        long status = editPropertyPage.updatePropertyStatusViaApi(
                TestDataConstants.NON_EXISTENT_PROPERTY_ID,
                PropertyPayloads.validEditPayloadJson()
        );

        assertEquals(
                404L,
                status,
                ErrorMessages.EDIT_PROPERTY_API_SHOULD_RETURN_404_FOR_NON_EXISTENT_PROPERTY
        );
    }

    @Test
    @DisplayName("Edit property API returns 401 when not logged in")
    public void testEditPropertyApiReturns401WhenLoggedOut() {
        long status = unauthedRequest()
                .contentType(ContentType.JSON)
                .body(PropertyPayloads.validEditPayloadJson())
                .put("/properties/" + TestDataConstants.EditProperty.EDITABLE_PROPERTY_ID)
                .statusCode();

        assertEquals(
                401L,
                status,
                ErrorMessages.EDIT_PROPERTY_API_SHOULD_RETURN_401_WHEN_LOGGED_OUT
        );
    }
}
