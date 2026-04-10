package com.staybnb.tests.api;

import com.staybnb.assertions.ErrorMessages;
import com.staybnb.pages.ImageUploadPage;
import com.staybnb.pages.LoginPage;
import com.staybnb.tests.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("Property Management")
@Feature("Image Upload API")
@Tag("api")
public class ImageUploadApiTest extends BaseTest {
    private ImageUploadPage imageUploadPage;

    private static final String DUMMY_BASE64 =
            Base64.getEncoder().encodeToString("not-an-image".getBytes(StandardCharsets.UTF_8));

    @BeforeEach
    public void setup() {
        LoginPage loginPage = new LoginPage(driver);
        imageUploadPage = new ImageUploadPage(driver);
        loginAsTestUserAndLandOnHome(loginPage);
    }

    private static Stream<UploadCase> provideSupportedUploadCases() {
        String pngBase64 =
                "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR42mP8/x8AAwMB/ax3m6kAAAAASUVORK5CYII=";

        String webpBase64 =
                "UklGRiYAAABXRUJQVlA4IBgAAAAwAQCdASoBAAEAAUAmJaQAA3AA/vuUAAA=";

        String jpegLikeBase64 = Base64.getEncoder().encodeToString("staybnb-jpeg".getBytes(StandardCharsets.UTF_8));

        return Stream.of(
                new UploadCase("png", "image/png", pngBase64),
                new UploadCase("webp", "image/webp", webpBase64),
                new UploadCase("jpg", "image/jpeg", jpegLikeBase64)
        );
    }

    @ParameterizedTest(name = "Upload succeeds for supported format: {0}")
    @MethodSource("provideSupportedUploadCases")
    public void testUploadSupportedImageReturns200AndResponseContainsUrl(UploadCase c) {
        String response = imageUploadPage.uploadImageViaApi(
                c.base64(),
                "upload_test_" + System.currentTimeMillis() + "." + c.ext(),
                c.mimeType()
        );

        assertTrue(
                imageUploadPage.isUploadResponseContainsUrl(response),
                ErrorMessages.IMAGE_UPLOAD_API_SHOULD_RETURN_200_AND_INCLUDE_URL
        );
    }

    // returns 500 instead of 400
    @Test
    @DisplayName("Upload unsupported file type returns 400")
    public void testUploadUnsupportedFileTypeReturns400() {
        long status = imageUploadPage.uploadImageStatusViaApi(DUMMY_BASE64, "bad.gif", "image/gif");

        assertEquals(
                400L,
                status,
                ErrorMessages.IMAGE_UPLOAD_API_SHOULD_RETURN_400_FOR_UNSUPPORTED_FILE_TYPE
        );
    }

    @Test
    @DisplayName("Upload with no file attached returns 400")
    public void testUploadWithNoFileAttachedReturns400() {
        long status = imageUploadPage.uploadImageStatusViaApiWithoutFile();

        assertEquals(
                400L,
                status,
                ErrorMessages.IMAGE_UPLOAD_API_SHOULD_RETURN_400_WHEN_NO_FILE_ATTACHED
        );
    }

    @Test
    @DisplayName("Upload returns 401 when not logged in")
    public void testUploadReturns401WhenLoggedOut() {
        imageUploadPage.navbar().clickLogoutAndWaitForRedirectToHome();
        long status = imageUploadPage.uploadImageStatusViaApi(DUMMY_BASE64, "any.png", "image/png");

        assertEquals(
                401L,
                status,
                ErrorMessages.IMAGE_UPLOAD_API_SHOULD_RETURN_401_WHEN_NOT_LOGGED_IN
        );
    }

    private record UploadCase(String ext, String mimeType, String base64) {}
}
