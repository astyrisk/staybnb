package com.staybnb.pages;

import com.staybnb.config.AppConstants;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class AmenitiesApiPage extends BasePage {
    private static final String GET_AMENITIES_API_JS_RESOURCE = "com/staybnb/scripts/getAmenitiesApi.js";
    private static final String GET_AMENITIES_STATUS_API_JS_RESOURCE = "com/staybnb/scripts/getAmenitiesStatusApi.js";

    public AmenitiesApiPage(WebDriver driver) {
        super(driver);
    }

    public boolean amenitiesResponseHasRequiredFieldsViaApi() {
        driver.get(AppConstants.HOME_URL);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String script = loadScript(GET_AMENITIES_API_JS_RESOURCE);
        Object response = js.executeAsyncScript(script, AppConstants.SLUG);
        if (response instanceof Boolean b) {
            return b;
        }
        throw new RuntimeException("Unexpected amenities response type: " +
                (response == null ? "null" : response.getClass().getName()));
    }

}
