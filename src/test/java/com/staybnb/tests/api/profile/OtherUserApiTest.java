package com.staybnb.tests.api.profile;

import com.staybnb.assertions.ErrorMessages;
import com.staybnb.config.TestConfig;
import com.staybnb.pages.ProfilePage;
import com.staybnb.tests.BaseApiTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@Epic("User Management")
@Feature("Other User Profile API")
@Tag("api")
public class OtherUserApiTest extends BaseApiTest {
    private ProfilePage otherProfilePage;

    @BeforeEach
    public void setup() {
        otherProfilePage = new ProfilePage(driver);
        loginAsUser();
    }

    @Test
    @DisplayName("View other user API response is not null")
    public void testApiViewOtherUserResponseNotNull() {
        String jsonResponse = otherProfilePage.getOtherUserApiResponse(TestConfig.OTHER_USER_ID_1);

        assertNotNull(
                jsonResponse,
                ErrorMessages.API_RESPONSE_SHOULD_NOT_BE_NULL
        );
    }

    @ParameterizedTest(name = "API response contains field: {0}")
    @MethodSource("provideExpectedApiFields")
    public void testApiViewOtherUserContainsField(String field) {
        String jsonResponse = otherProfilePage.getOtherUserApiResponse(TestConfig.OTHER_USER_ID_1);

        assertTrue(
                jsonResponse.contains("\"" + field + "\""),
                ErrorMessages.RESPONSE_SHOULD_CONTAIN_EXPECTED_FIELD
        );
    }

    @ParameterizedTest(name = "API response excludes field: {0}")
    @MethodSource("provideExcludedApiFields")
    public void testApiViewOtherUserDoesNotContainField(String field) {
        String jsonResponse = otherProfilePage.getOtherUserApiResponse(TestConfig.OTHER_USER_ID_1);

        assertFalse(
                jsonResponse.contains("\"" + field + "\""),
                ErrorMessages.RESPONSE_SHOULD_NOT_CONTAIN_EXCLUDED_FIELD
        );
    }

    private static Stream<String> provideExpectedApiFields() {
        return Stream.of("firstName", "lastName", "bio", "isHost");
    }

    private static Stream<String> provideExcludedApiFields() {
        return Stream.of("email", "phone");
    }
}
