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

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@Epic("Authentication")
@Feature("Register API")
@Tag("api")
public class RegisterApiTest extends BaseApiTest {

    private String buildValidPayload() {
        String uniqueEmail = "api_reg_" + UUID.randomUUID().toString().replace("-", "") + "@gmail.com";
        return String.format(
                "{\"firstName\":\"%s\",\"lastName\":\"%s\",\"email\":\"%s\",\"password\":\"%s\"}",
                TestConfig.TEST_FIRST_NAME, TestConfig.TEST_LAST_NAME, uniqueEmail, TestConfig.TEST_PASSWORD
        );
    }

    private String getRegisterResponse() {
        return unauthedRequest()
                .contentType(ContentType.JSON)
                .body(buildValidPayload())
                .post("/auth/register")
                .then().extract().asString();
    }

    @Test
    @DisplayName("Register API returns 201 for a valid payload")
    public void testRegisterApiReturns201ForValidPayload() {
        long status = unauthedRequest()
                .contentType(ContentType.JSON)
                .body(buildValidPayload())
                .post("/auth/register")
                .statusCode();

        assertEquals(201L, status, ErrorMessages.REGISTER_API_SHOULD_RETURN_201_FOR_VALID_PAYLOAD);
    }

    @Test
    @DisplayName("Register API returns 409 when email already exists")
    public void testRegisterApiReturns409ForExistingEmail() {
        String body = String.format(
                "{\"firstName\":\"%s\",\"lastName\":\"%s\",\"email\":\"%s\",\"password\":\"%s\"}",
                TestConfig.TEST_FIRST_NAME, TestConfig.TEST_LAST_NAME,
                TestConfig.TEST_USER_EMAIL, TestConfig.TEST_PASSWORD
        );

        long status = unauthedRequest()
                .contentType(ContentType.JSON)
                .body(body)
                .post("/auth/register")
                .statusCode();

        assertEquals(409L, status, ErrorMessages.REGISTER_API_SHOULD_RETURN_409_FOR_EXISTING_EMAIL);
    }

    @Test
    @DisplayName("Register API returns 400 when required fields are missing")
    public void testRegisterApiReturns400ForMissingFields() {
        long status = unauthedRequest()
                .contentType(ContentType.JSON)
                .body("{}")
                .post("/auth/register")
                .statusCode();

        assertEquals(400L, status, ErrorMessages.REGISTER_API_SHOULD_RETURN_400_FOR_MISSING_FIELDS);
    }

    @Test
    @DisplayName("Register API response contains 'token' string")
    public void testRegisterApiResponseContainsToken() {
        String response = getRegisterResponse();

        assertTrue(response.contains("\"token\""), ErrorMessages.REGISTER_API_RESPONSE_SHOULD_CONTAIN_TOKEN);
    }

    @Test
    @DisplayName("Register API response contains 'id' field")
    public void testRegisterApiResponseContainsId() {
        String response = getRegisterResponse();

        assertTrue(response.contains("\"id\""), ErrorMessages.RESPONSE_SHOULD_CONTAIN_ID);
    }

    @Test
    @DisplayName("Register API response contains 'email' field")
    public void testRegisterApiResponseContainsEmail() {
        String response = getRegisterResponse();

        assertTrue(response.contains("\"email\""), ErrorMessages.RESPONSE_SHOULD_CONTAIN_EMAIL);
    }

    @Test
    @DisplayName("Register API response contains 'firstName' field")
    public void testRegisterApiResponseContainsFirstName() {
        String response = getRegisterResponse();

        assertTrue(response.contains("\"firstName\""), ErrorMessages.RESPONSE_SHOULD_CONTAIN_FIRST_NAME);
    }

    @Test
    @DisplayName("Register API response contains 'lastName' field")
    public void testRegisterApiResponseContainsLastName() {
        String response = getRegisterResponse();

        assertTrue(response.contains("\"lastName\""), ErrorMessages.RESPONSE_SHOULD_CONTAIN_LAST_NAME);
    }

    @Test
    @DisplayName("Register API response contains 'isHost' field")
    public void testRegisterApiResponseContainsIsHost() {
        String response = getRegisterResponse();

        assertTrue(response.contains("\"isHost\""), ErrorMessages.RESPONSE_SHOULD_CONTAIN_IS_HOST);
    }

    @Test
    @DisplayName("Register API response contains 'avatarUrl' field")
    public void testRegisterApiResponseContainsAvatarUrl() {
        String response = getRegisterResponse();

        assertTrue(response.contains("\"avatarUrl\""), ErrorMessages.RESPONSE_SHOULD_CONTAIN_AVATAR_URL);
    }

    @Test
    @DisplayName("Register API response contains 'createdAt' field")
    public void testRegisterApiResponseContainsCreatedAt() {
        String response = getRegisterResponse();

        assertTrue(response.contains("\"createdAt\""), ErrorMessages.RESPONSE_SHOULD_CONTAIN_CREATED_AT);
    }
}
