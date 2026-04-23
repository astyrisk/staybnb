package com.staybnb.pages;

import org.openqa.selenium.WebDriver;

public class AmenitiesApiPage extends BasePage {

    public AmenitiesApiPage(WebDriver driver) {
        super(driver);
    }

    public boolean amenitiesResponseHasRequiredFieldsViaApi() {
        String body = apiRequest().get("/amenities").asString();
        return body.contains("\"id\"") && body.contains("\"name\"") && body.contains("\"icon\"");
    }

}
