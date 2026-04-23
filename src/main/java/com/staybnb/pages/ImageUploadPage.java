package com.staybnb.pages;

import com.staybnb.config.AppConstants;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class ImageUploadPage extends BasePage {
    private static final String UPLOAD_IMAGE_API_JS_RESOURCE = "com/staybnb/scripts/uploadImageApi.js";
    private static final String UPLOAD_IMAGE_STATUS_API_JS_RESOURCE = "com/staybnb/scripts/uploadImageStatusApi.js";

    public ImageUploadPage(WebDriver driver) {
        super(driver);
    }

    public long uploadImageStatusViaApi(String base64, String fileName, String mimeType) {
        driver.get(AppConstants.HOME_URL);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String script = loadScript(UPLOAD_IMAGE_STATUS_API_JS_RESOURCE);
        Object responseStatus = js.executeAsyncScript(script, AppConstants.SLUG, base64, fileName, mimeType, true);
        if (responseStatus instanceof Number n) {
            return n.longValue();
        }
        throw new RuntimeException("Unexpected upload-image status response type: " +
                (responseStatus == null ? "null" : responseStatus.getClass().getName()));
    }

    public long uploadImageStatusViaApiWithoutFile() {
        driver.get(AppConstants.HOME_URL);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String script = loadScript(UPLOAD_IMAGE_STATUS_API_JS_RESOURCE);
        Object responseStatus = js.executeAsyncScript(script, AppConstants.SLUG, "", "", "", false);
        if (responseStatus instanceof Number n) {
            return n.longValue();
        }
        throw new RuntimeException("Unexpected upload-image status response type: " +
                (responseStatus == null ? "null" : responseStatus.getClass().getName()));
    }

    public boolean isUploadResponseContainsUrl(String response) {
        String normalized = response == null ? "" : response.replaceAll("\\s+", "").toLowerCase();
        String expectedPathPrefix = ("/uploads/t/" + AppConstants.SLUG + "/").toLowerCase();
        return normalized.contains("\"url\"") && normalized.contains(expectedPathPrefix);
    }

    public String uploadImageViaApi(String base64, String fileName, String mimeType) {
        driver.get(AppConstants.HOME_URL);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String script = loadScript(UPLOAD_IMAGE_API_JS_RESOURCE);
        Object response = js.executeAsyncScript(script, AppConstants.SLUG, base64, fileName, mimeType, true);
        return (String) response;
    }

}

