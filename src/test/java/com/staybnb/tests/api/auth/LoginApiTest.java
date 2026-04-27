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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

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
        long status = unauthedRequest()
                .contentType(ContentType.JSON)
                .body("{\"email\":\"wrong@example.com\",\"password\":\"wrongpassword\"}")
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

    @ParameterizedTest(name = "Login API response contains {0} field")
    @ValueSource(strings = {"\"token\"", "\"id\"", "\"email\"", "\"firstName\"", "\"lastName\"", "\"isHost\"", "\"avatarUrl\"", "\"createdAt\""})
    public void testLoginApiResponseContainsExpectedField(String field) {
        assertTrue(getLoginResponse().contains(field), ErrorMessages.RESPONSE_SHOULD_CONTAIN_EXPECTED_FIELD);
    }
}
