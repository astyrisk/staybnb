package com.staybnb.tests.api.hosting;

import com.staybnb.assertions.ErrorMessages;
import com.staybnb.pages.OwnProfilePage;
import com.staybnb.tests.BaseApiTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@Epic("Host Management")
@Feature("Become a Host API")
@Tag("api")
public class BecomeHostApiTest extends BaseApiTest {
    private OwnProfilePage ownProfilePage;

    @BeforeEach
    public void setup() {
        ownProfilePage = new OwnProfilePage(driver);
    }

    @Test
    @DisplayName("Become host API returns 401 when not logged in")
    public void testBecomeHostApiReturns401WhenLoggedOut() {
        long status = unauthedRequest()
                .contentType(ContentType.JSON)
                .body("{}")
                .put("/users/me/host")
                .statusCode();

        assertEquals(
                401L,
                status,
                ErrorMessages.BECOME_HOST_API_SHOULD_RETURN_401_WHEN_NOT_LOGGED_IN
        );
    }

    @Test
    @DisplayName("Become host API response is not null")
    public void testBecomeHostApiResponseNotNull() {
        registerNewUser();
        String jsonResponse = ownProfilePage.becomeHostViaApi("{\"isHost\":true}");

        assertNotNull(
                jsonResponse,
                ErrorMessages.API_RESPONSE_SHOULD_NOT_BE_NULL
        );
    }

    @Test
    @DisplayName("Become host API response reflects isHost true")
    public void testBecomeHostApiResponseReflectsIsHostTrue() {
        registerNewUser();
        String jsonResponse = ownProfilePage.becomeHostViaApi("{\"isHost\":true}");

        assertTrue(
                jsonResponse.contains("\"isHost\":true"),
                ErrorMessages.BECOME_HOST_API_SHOULD_REFLECT_IS_HOST_TRUE
        );
    }
}
