package com.staybnb.pages;

import com.staybnb.config.Constants;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class CategoriesApiPage extends BasePage {
    private static final String GET_CATEGORIES_API_JS_RESOURCE = "com/staybnb/scripts/getCategoriesApi.js";
    private static final String GET_CATEGORIES_STATUS_API_JS_RESOURCE = "com/staybnb/scripts/getCategoriesStatusApi.js";

    public CategoriesApiPage(WebDriver driver) {
        super(driver);
    }

    public long getCategoriesStatusViaApi() {
        driver.get(Constants.HOME_URL);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String script = loadJavascriptResource(GET_CATEGORIES_STATUS_API_JS_RESOURCE);
        Object responseStatus = js.executeAsyncScript(script, Constants.SLUG);
        if (responseStatus instanceof Number n) {
            return n.longValue();
        }
        throw new RuntimeException("Unexpected categories status response type: " +
                (responseStatus == null ? "null" : responseStatus.getClass().getName()));
    }

    public boolean categoriesResponseHasRequiredFieldsViaApi() {
        driver.get(Constants.HOME_URL);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String script = loadJavascriptResource(GET_CATEGORIES_API_JS_RESOURCE);
        Object response = js.executeAsyncScript(script, Constants.SLUG);
        if (response instanceof Boolean b) {
            return b;
        }
        throw new RuntimeException("Unexpected categories response type: " +
                (response == null ? "null" : response.getClass().getName()));
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

