package com.staybnb.tests.api;

import com.staybnb.assertions.ErrorMessages;
import com.staybnb.data.PropertyPayloads;
import com.staybnb.pages.CreatePropertyPage;
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

@Epic("Property Management")
@Feature("Create Property API")
@Tag("api")
public class CreatePropertyApiTest extends BaseTest {
    private CreatePropertyPage createPropertyPage;

    @BeforeEach
    public void setup() {
        LoginPage loginPage = new LoginPage(driver);
        createPropertyPage = new CreatePropertyPage(driver);
        loginAsTestUserAndLandOnHome(loginPage);
    }

    @Test
    @DisplayName("Create property API returns 201 for a valid host payload")
    public void testCreatePropertyApiReturns201ForValidHostPayload() {
        long status = createPropertyPage.createPropertyStatusViaApi(PropertyPayloads.validCreatePropertyPayloadJson());

        assertEquals(
                201L, status,
                ErrorMessages.CREATE_PROPERTY_API_SHOULD_RETURN_201_FOR_VALID_HOST_PAYLOAD
        );
    }

//    @Test
//    @DisplayName("Create property API creates a draft with isPublished set to false")
//    public void testCreatePropertyApiCreatesDraftWithIsPublishedFalse() {
//        String response = createPropertyPage.createPropertyViaApi(PropertyPayloads.validCreatePropertyPayloadJson());
//
//        assertTrue(
//                response != null && response.toLowerCase().contains("\"is_published\":false"),
//                ErrorMessages.CREATE_PROPERTY_API_SHOULD_CREATE_DRAFT_IS_PUBLISHED_FALSE
//        );
//    }

    @Test
    @DisplayName("Create property API returns 400 when a required field is missing")
    public void testCreatePropertyApiReturns400WhenRequiredFieldMissing() {
        long status = createPropertyPage.createPropertyStatusViaApi(PropertyPayloads.invalidCreatePropertyPayloadMissingTitleJson());

        assertEquals(
                400L,
                status,
                ErrorMessages.CREATE_PROPERTY_API_SHOULD_RETURN_400_FOR_MISSING_REQUIRED_FIELDS
        );
    }

    @Test
    @DisplayName("Create property API returns 403 for a non-host user")
    public void testCreatePropertyApiReturns403ForNonHost() {
        new LogoutPage(driver).logoutAndWaitForTokenCleared();
        registerNewUserAndLandOnHome("testcreateproperty");
        long status = createPropertyPage.createPropertyStatusViaApi(PropertyPayloads.validCreatePropertyPayloadJson());

        assertEquals(
                403L,
                status,
                ErrorMessages.CREATE_PROPERTY_API_SHOULD_RETURN_403_FOR_NON_HOST
        );
    }
}
