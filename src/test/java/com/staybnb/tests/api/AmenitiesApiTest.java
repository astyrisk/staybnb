package com.staybnb.tests.api;

import com.staybnb.assertions.ErrorMessages;
import com.staybnb.pages.AmenitiesApiPage;
import com.staybnb.pages.LoginPage;
import com.staybnb.tests.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("Properties")
@Feature("Amenities API")
@Tag("api")
public class AmenitiesApiTest extends BaseTest {
    private AmenitiesApiPage amenitiesApiPage;

    @BeforeEach
    public void setup() {
        LoginPage loginPage = new LoginPage(driver);
        amenitiesApiPage = new AmenitiesApiPage(driver);
        loginAsTestUserAndLandOnHome(loginPage);
    }

    @Test
    @DisplayName("Amenities API returns list with id, name, and icon fields")
    public void testAmenitiesApiReturnsListWithIdNameAndIcon() {
        assertTrue(
                amenitiesApiPage.amenitiesResponseHasRequiredFieldsViaApi(),
                ErrorMessages.AMENITIES_API_SHOULD_RETURN_LIST_WITH_ID_NAME_ICON
        );
    }
}
