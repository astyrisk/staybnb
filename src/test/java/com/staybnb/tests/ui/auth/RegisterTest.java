package com.staybnb.tests.ui.auth;

import com.staybnb.config.AppConstants;
import com.staybnb.config.TestConfig;
import com.staybnb.pages.RegisterPage;
import com.staybnb.assertions.ErrorMessages;
import com.staybnb.tests.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@Epic("Authentication")
@Feature("Registration")
@Tag("smoke")
public class RegisterTest extends BaseTest {
    private RegisterPage registerPage;

    @BeforeEach
    public void setup() {
        registerPage = new RegisterPage(driver);
        registerPage.navigateViaNavbar();
    }

    @Test
    @DisplayName("Successful registration redirects to home page")
    public void testSuccessfulRegistration() {
        registerNewUser();

        assertTrue(
                registerPage.urlIs(AppConstants.HOME_URL)
        );
    }

    @Test
    @DisplayName("Register page has a 'Log in' link that navigates to the login page")
    public void testRegisterPageHasLoginLink() {
        registerPage.clickLoginLink();

        assertTrue(
                registerPage.urlIs(AppConstants.LOGIN_URL),
                ErrorMessages.REGISTER_PAGE_SHOULD_NAVIGATE_TO_LOGIN
        );
    }

    @ParameterizedTest(name = "[{index}]- {5}")
    @MethodSource("registrationValidationCases")
    public void testRegistrationValidation(String firstName, String lastName, String email, String password, String confirmPassword, String expectedError) {
        registerPage.fillAndSubmitRegistration(firstName, lastName, email, password, confirmPassword);

        String inlineError = registerPage.getInlineErrorMessageText();
        assertTrue(
                inlineError.toLowerCase().contains(expectedError.toLowerCase()),
                ErrorMessages.EXPECTED_INLINE_VALIDATION_ERROR
        );
    }

    private static Stream<Arguments> registrationValidationCases() {
        long ts = System.currentTimeMillis();
        return Stream.of(
                Arguments.of("",                         TestConfig.TEST_LAST_NAME,  TestConfig.TEST_USER_EMAIL,              TestConfig.TEST_PASSWORD,     TestConfig.TEST_PASSWORD,           ErrorMessages.FIRST_NAME_REQUIRED),
                Arguments.of(TestConfig.TEST_FIRST_NAME, "",                         TestConfig.TEST_USER_EMAIL,              TestConfig.TEST_PASSWORD,     TestConfig.TEST_PASSWORD,           ErrorMessages.LAST_NAME_REQUIRED),
                Arguments.of(TestConfig.TEST_FIRST_NAME, TestConfig.TEST_LAST_NAME,  "",                                      TestConfig.TEST_PASSWORD,     TestConfig.TEST_PASSWORD,           ErrorMessages.EMAIL_REQUIRED),
                Arguments.of(TestConfig.TEST_FIRST_NAME, TestConfig.TEST_LAST_NAME,  TestConfig.TEST_USER_EMAIL,              "",                           "",                                 ErrorMessages.PASSWORD_REQUIRED),
                Arguments.of(TestConfig.TEST_FIRST_NAME, TestConfig.TEST_LAST_NAME,  TestConfig.TEST_USER_EMAIL,              TestConfig.TEST_PASSWORD,     TestConfig.TEST_PASSWORD,           ErrorMessages.EMAIL_ALREADY_REGISTERED),
                Arguments.of(TestConfig.TEST_FIRST_NAME, TestConfig.TEST_LAST_NAME,  "short_"    + ts       + "@gmail.com",   "short12",                    "short12",                          ErrorMessages.PASSWORD_MINIMUM_LENGTH),
                Arguments.of(TestConfig.TEST_FIRST_NAME, TestConfig.TEST_LAST_NAME,  "mismatch_" + (ts + 1) + "@gmail.com",   TestConfig.TEST_PASSWORD,     TestConfig.TEST_PASSWORD + "DIFF",  ErrorMessages.PASSWORDS_MUST_MATCH)
        );
    }
}
