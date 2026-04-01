package com.staybnb.pages;

import com.staybnb.config.Constants;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class ImageUploadPage extends BasePage {
    private static final String UPLOAD_IMAGE_API_JS_RESOURCE = "com/staybnb/scripts/uploadImageApi.js";
    private static final String UPLOAD_IMAGE_STATUS_API_JS_RESOURCE = "com/staybnb/scripts/uploadImageStatusApi.js";

    public ImageUploadPage(WebDriver driver) {
        super(driver);
    }

    public long uploadImageStatusViaApi(String base64, String fileName, String mimeType) {
        driver.get(Constants.HOME_URL);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String script = loadJavascriptResource(UPLOAD_IMAGE_STATUS_API_JS_RESOURCE);
        Object responseStatus = js.executeAsyncScript(script, Constants.SLUG, base64, fileName, mimeType, true);
        if (responseStatus instanceof Number n) {
            return n.longValue();
        }
        throw new RuntimeException("Unexpected upload-image status response type: " +
                (responseStatus == null ? "null" : responseStatus.getClass().getName()));
    }

    public long uploadImageStatusViaApiWithoutFile() {
        driver.get(Constants.HOME_URL);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String script = loadJavascriptResource(UPLOAD_IMAGE_STATUS_API_JS_RESOURCE);
        Object responseStatus = js.executeAsyncScript(script, Constants.SLUG, "", "", "", false);
        if (responseStatus instanceof Number n) {
            return n.longValue();
        }
        throw new RuntimeException("Unexpected upload-image status response type: " +
                (responseStatus == null ? "null" : responseStatus.getClass().getName()));
    }

    public String uploadImageViaApi(String base64, String fileName, String mimeType) {
        driver.get(Constants.HOME_URL);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String script = loadJavascriptResource(UPLOAD_IMAGE_API_JS_RESOURCE);
        Object response = js.executeAsyncScript(script, Constants.SLUG, base64, fileName, mimeType, true);
        return (String) response;
    }

    private String loadJavascriptResource(String resourcePath) {
        try (InputStream stream = getClass().getClassLoader().getResourceAsStream(resourcePath)) {
            if (stream == null) {
                throw new IllegalStateException("Missing JS resource on classpath: " + resourcePath);
            }
            return new String(stream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read JS resource on classpath: " + resourcePath, e);
        }
    }
}

