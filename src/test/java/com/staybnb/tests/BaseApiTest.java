package com.staybnb.tests;

import com.staybnb.config.AppConstants;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriverException;

public abstract class BaseApiTest extends BaseTest {

    protected RequestSpecification authedRequest() {
        String token = getStaybnbToken();
        RequestSpecification spec = RestAssured.given().baseUri(AppConstants.API_BASE_URL);
        if (token != null && !token.isBlank()) {
            spec.header("Authorization", "Bearer " + token);
        }
        return spec;
    }

    protected RequestSpecification unauthedRequest() {
        return RestAssured.given().baseUri(AppConstants.API_BASE_URL);
    }

    private String getStaybnbToken() {
        try {
            return (String) ((JavascriptExecutor) driver)
                    .executeScript("return window.localStorage.getItem('staybnb_token');");
        } catch (WebDriverException e) {
            return null;
        }
    }
}
