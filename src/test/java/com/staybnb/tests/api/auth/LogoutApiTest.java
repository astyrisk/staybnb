package com.staybnb.tests.api.auth;

import com.staybnb.assertions.ErrorMessages;
import com.staybnb.tests.BaseApiTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Epic("Authentication")
@Feature("Logout API")
@Tag("api")
public class LogoutApiTest extends BaseApiTest {

    @BeforeEach
    public void setup() {
        loginAsUser();
    }

    @Test
    @DisplayName("Logout API returns 401 when not authenticated")
    public void testLogoutApiReturns401WhenNotLoggedIn() {
        long status = unauthedRequest()
                .contentType(ContentType.JSON)
                .post("/auth/logout")
                .statusCode();

        assertEquals(
                401L,
                status,
                ErrorMessages.LOGOUT_API_SHOULD_RETURN_401_WHEN_NOT_LOGGED_IN
        );
    }

    @Test
    @DisplayName("Logout API returns 200 when authenticated")
    public void testLogoutApiReturns200WhenLoggedIn() {
        long status = authedRequest()
                .contentType(ContentType.JSON)
                .post("/auth/logout")
                .statusCode();

        assertEquals(
                200L,
                status,
                ErrorMessages.LOGOUT_API_SHOULD_RETURN_200_WHEN_LOGGED_IN
        );
    }

    @Test
    @DisplayName("Accessing an authenticated endpoint without a token after logout returns 401")
    public void testAuthEndpointReturns401AfterLogout() {
        authedRequest().contentType(ContentType.JSON).post("/auth/logout");

        long status = unauthedRequest().get("/auth/me").statusCode();

        assertEquals(
                401L,
                status,
                ErrorMessages.LOGOUT_API_UNAUTHENTICATED_ENDPOINT_RETURNS_401
        );
    }
}
