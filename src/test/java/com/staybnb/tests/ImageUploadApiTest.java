package com.staybnb.tests;

import com.staybnb.assertions.ErrorMessages;
import com.staybnb.config.Constants;
import com.staybnb.pages.ImageUploadPage;
import com.staybnb.pages.LoginPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ImageUploadApiTest extends BaseTest {
    private LoginPage loginPage;
    private ImageUploadPage imageUploadPage;

    @BeforeEach
    public void setup() {
        loginPage = new LoginPage(driver);
        imageUploadPage = new ImageUploadPage(driver);
    }

    private static Stream<UploadCase> provideSupportedUploadCases() {
        // Tiny 1x1 PNG (transparent)
        String pngBase64 =
                "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR42mP8/x8AAwMB/ax3m6kAAAAASUVORK5CYII=";

        // Tiny 1x1 WebP
        String webpBase64 =
                "UklGRiYAAABXRUJQVlA4IBgAAAAwAQCdASoBAAEAAUAmJaQAA3AA/vuUAAA=";

        // For JPEG we can use any bytes; server validation should rely on file type checks.
        // This payload is a small text blob but named/mimed as jpeg to keep the test light.
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
        loginAsTestUserAndLandOnHome(loginPage);
        String response = imageUploadPage.uploadImageViaApi(
                c.base64(),
                "upload_test_" + System.currentTimeMillis() + "." + c.ext(),
                c.mimeType()
        );
        String normalized = response == null ? "" : response.replaceAll("\\s+", "").toLowerCase();
        String expectedPathPrefix = ("/uploads/t/" + Constants.SLUG + "/").toLowerCase();

        assertTrue(
                normalized.contains("\"url\"") && normalized.contains(expectedPathPrefix),
                ErrorMessages.IMAGE_UPLOAD_API_SHOULD_RETURN_200_AND_INCLUDE_URL
        );
    }

    // errs, it should send the data to
    @Test
    public void testUploadUnsupportedFileTypeReturns400() {
        loginAsTestUserAndLandOnHome(loginPage);
        String base64 = Base64.getEncoder().encodeToString("not-an-image".getBytes(StandardCharsets.UTF_8));
        long status = imageUploadPage.uploadImageStatusViaApi(base64, "bad.gif", "image/gif");
        assertEquals(
                400L,
                status,
                ErrorMessages.IMAGE_UPLOAD_API_SHOULD_RETURN_400_FOR_UNSUPPORTED_FILE_TYPE
        );
    }

    @Test
    public void testUploadWithNoFileAttachedReturns400() {
        loginAsTestUserAndLandOnHome(loginPage);
        long status = imageUploadPage.uploadImageStatusViaApiWithoutFile();
        assertEquals(
                400L,
                status,
                ErrorMessages.IMAGE_UPLOAD_API_SHOULD_RETURN_400_WHEN_NO_FILE_ATTACHED
        );
    }

    @Test
    public void testUploadReturns401WhenLoggedOut() {
        String base64 = Base64.getEncoder().encodeToString("not-an-image".getBytes(StandardCharsets.UTF_8));
        long status = imageUploadPage.uploadImageStatusViaApi(base64, "any.png", "image/png");
        assertEquals(
                401L,
                status,
                ErrorMessages.IMAGE_UPLOAD_API_SHOULD_RETURN_401_WHEN_NOT_LOGGED_IN
        );
    }

    private record UploadCase(String ext, String mimeType, String base64) {}
}

