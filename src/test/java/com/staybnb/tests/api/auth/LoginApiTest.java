package com.staybnb.tests.api.auth;

import com.staybnb.assertions.ErrorMessages;
import com.staybnb.config.TestConfig;
import com.staybnb.tests.BaseApiTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Epic("Authentication")
@Feature("Login API")
@Tag("api")
public class LoginApiTest extends BaseApiTest {

    private String buildValidLoginPayload() {
        return String.format(
                "{\"email\":\"%s\",\"password\":\"%s\"}",
                TestConfig.TEST_USER_EMAIL, TestConfig.TEST_PASSWORD
        );
    }

    private String getLoginResponse() {
        return unauthedRequest()
                .contentType(ContentType.JSON)
                .body(buildValidLoginPayload())
                .post("/auth/login")
                .then().extract().asString();
    }

    @Test
    @DisplayName("Login API returns 200 for valid credentials")
    public void testLoginApiReturns200ForValidCredentials() {
        long status = unauthedRequest()
                .contentType(ContentType.JSON)
                .body(buildValidLoginPayload())
                .post("/auth/login")
                .statusCode();

        assertEquals(200L, status, ErrorMessages.LOGIN_API_SHOULD_RETURN_200_FOR_VALID_CREDENTIALS);
    }

    @Test
    @DisplayName("Login API returns 401 for invalid credentials")
    public void testLoginApiReturns401ForInvalidCredentials() {
        String body = "{\"email\":\"wrong@example.com\",\"password\":\"wrongpassword\"}";

        long status = unauthedRequest()
                .contentType(ContentType.JSON)
                .body(body)
                .post("/auth/login")
                .statusCode();

        assertEquals(401L, status, ErrorMessages.LOGIN_API_SHOULD_RETURN_401_FOR_INVALID_CREDENTIALS);
    }

    @Test
    @DisplayName("Login API returns 400 when required fields are missing")
    public void testLoginApiReturns400ForMissingFields() {
        long status = unauthedRequest()
                .contentType(ContentType.JSON)
                .body("{}")
                .post("/auth/login")
                .statusCode();

        assertEquals(400L, status, ErrorMessages.LOGIN_API_SHOULD_RETURN_400_FOR_MISSING_FIELDS);
    }

    @Test
    @DisplayName("Login API response contains 'token' string")
    public void testLoginApiResponseContainsToken() {
        String response = getLoginResponse();

        assertTrue(response.contains("\"token\""), ErrorMessages.LOGIN_API_RESPONSE_SHOULD_CONTAIN_TOKEN);
    }

    @Test
    @DisplayName("Login API response contains 'id' field")
    public void testLoginApiResponseContainsId() {
        String response = getLoginResponse();

        assertTrue(response.contains("\"id\""), ErrorMessages.RESPONSE_SHOULD_CONTAIN_ID);
    }

    @Test
    @DisplayName("Login API response contains 'email' field")
    public void testLoginApiResponseContainsEmail() {
        String response = getLoginResponse();

        assertTrue(response.contains("\"email\""), ErrorMessages.RESPONSE_SHOULD_CONTAIN_EMAIL);
    }

    @Test
    @DisplayName("Login API response contains 'firstName' field")
    public void testLoginApiResponseContainsFirstName() {
        String response = getLoginResponse();

        assertTrue(response.contains("\"firstName\""), ErrorMessages.RESPONSE_SHOULD_CONTAIN_FIRST_NAME);
    }

    @Test
    @DisplayName("Login API response contains 'lastName' field")
    public void testLoginApiResponseContainsLastName() {
        String response = getLoginResponse();

        assertTrue(response.contains("\"lastName\""), ErrorMessages.RESPONSE_SHOULD_CONTAIN_LAST_NAME);
    }

    @Test
    @DisplayName("Login API response contains 'isHost' field")
    public void testLoginApiResponseContainsIsHost() {
        String response = getLoginResponse();

        assertTrue(response.contains("\"isHost\""), ErrorMessages.RESPONSE_SHOULD_CONTAIN_IS_HOST);
    }

    @Test
    @DisplayName("Login API response contains 'avatarUrl' field")
    public void testLoginApiResponseContainsAvatarUrl() {
        String response = getLoginResponse();

        assertTrue(response.contains("\"avatarUrl\""), ErrorMessages.RESPONSE_SHOULD_CONTAIN_AVATAR_URL);
    }

    @Test
    @DisplayName("Login API response contains 'createdAt' field")
    public void testLoginApiResponseContainsCreatedAt() {
        String response = getLoginResponse();

        assertTrue(response.contains("\"createdAt\""), ErrorMessages.RESPONSE_SHOULD_CONTAIN_CREATED_AT);
    }
}
