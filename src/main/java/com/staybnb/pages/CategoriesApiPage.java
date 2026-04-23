package com.staybnb.pages;

import com.staybnb.config.AppConstants;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class CategoriesApiPage extends BasePage {
    private static final String GET_CATEGORIES_API_JS_RESOURCE = "com/staybnb/scripts/getCategoriesApi.js";
    private static final String GET_CATEGORIES_STATUS_API_JS_RESOURCE = "com/staybnb/scripts/getCategoriesStatusApi.js";

    public CategoriesApiPage(WebDriver driver) {
        super(driver);
    }

    public long getCategoriesStatusViaApi() {
        driver.get(AppConstants.HOME_URL);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String script = loadScript(GET_CATEGORIES_STATUS_API_JS_RESOURCE);
        Object responseStatus = js.executeAsyncScript(script, AppConstants.SLUG);
        if (responseStatus instanceof Number n) {
            return n.longValue();
        }
        throw new RuntimeException("Unexpected categories status response type: " +
                (responseStatus == null ? "null" : responseStatus.getClass().getName()));
    }

    public boolean categoriesResponseHasRequiredFieldsViaApi() {
        driver.get(AppConstants.HOME_URL);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String script = loadScript(GET_CATEGORIES_API_JS_RESOURCE);
        Object response = js.executeAsyncScript(script, AppConstants.SLUG);
        if (response instanceof Boolean b) {
            return b;
        }
        throw new RuntimeException("Unexpected categories response type: " +
                (response == null ? "null" : response.getClass().getName()));
    }

}

