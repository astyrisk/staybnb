package com.staybnb.tests.api.auth;

import com.staybnb.assertions.ErrorMessages;
import com.staybnb.pages.ProfilePage;
import com.staybnb.tests.BaseApiTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@Epic("User Management")
@Feature("Auth Me API")
@Tag("api")
public class AuthMeApiTest extends BaseApiTest {
    private ProfilePage ownProfilePage;

    @BeforeEach
    public void setup() {
        ownProfilePage = new ProfilePage(driver);
        loginAsUser();
    }

    @Test
    @DisplayName("Auth/me API response is not null when logged in")
    public void testAuthMeApiLoggedInResponseNotNull() {
        assertNotNull(ownProfilePage.getAuthMeApiResponse(), ErrorMessages.API_RESPONSE_SHOULD_NOT_BE_NULL);
    }

    @ParameterizedTest(name = "Auth/me API response contains {0} field")
    @ValueSource(strings = {"\"id\"", "\"email\"", "\"firstName\"", "\"lastName\"", "\"phone\"", "\"bio\"", "\"avatarUrl\"", "\"isHost\"", "\"createdAt\""})
    public void testAuthMeApiLoggedInContainsExpectedField(String field) {
        assertTrue(ownProfilePage.getAuthMeApiResponse().contains(field), ErrorMessages.RESPONSE_SHOULD_CONTAIN_EXPECTED_FIELD);
    }

    @Test
    @DisplayName("Auth/me API returns 401 when not logged in")
    public void testAuthMeApiLoggedOut() {
        long status = unauthedRequest().get("/auth/me").statusCode();

        assertEquals(401L, status, ErrorMessages.API_SHOULD_RETURN_401_WHEN_NOT_LOGGED_IN);
    }
}
