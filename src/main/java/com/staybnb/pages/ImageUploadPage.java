package com.staybnb.pages;

import com.staybnb.config.AppConstants;
import org.openqa.selenium.WebDriver;

import java.util.Base64;

public class ImageUploadPage extends BasePage {

    public ImageUploadPage(WebDriver driver) {
        super(driver);
    }

    public long uploadImageStatusViaApi(String base64, String fileName, String mimeType) {
        byte[] imageBytes = Base64.getDecoder().decode(base64);
        return apiRequest()
                .multiPart("image", fileName, imageBytes, mimeType)
                .post("/upload")
                .statusCode();
    }

    public long uploadImageStatusViaApiWithoutFile() {
        return apiRequest()
                .post("/upload")
                .statusCode();
    }

    public boolean isUploadResponseContainsUrl(String response) {
        String normalized = response == null ? "" : response.replaceAll("\\s+", "").toLowerCase();
        String expectedPathPrefix = ("/uploads/t/" + AppConstants.SLUG + "/").toLowerCase();
        return normalized.contains("\"url\"") && normalized.contains(expectedPathPrefix);
    }

    public String uploadImageViaApi(String base64, String fileName, String mimeType) {
        byte[] imageBytes = Base64.getDecoder().decode(base64);
        return apiRequest()
                .multiPart("image", fileName, imageBytes, mimeType)
                .post("/upload")
                .asString();
    }

}

