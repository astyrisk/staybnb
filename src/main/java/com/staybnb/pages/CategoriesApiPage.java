package com.staybnb.pages;

import org.openqa.selenium.WebDriver;

public class CategoriesApiPage extends BasePage {

    public CategoriesApiPage(WebDriver driver) {
        super(driver);
    }

    public boolean categoriesResponseHasRequiredFieldsViaApi() {
        String body = apiRequest().get("/categories").asString();
        return body.contains("\"id\"") && body.contains("\"name\"") && body.contains("\"icon\"");
    }

}

