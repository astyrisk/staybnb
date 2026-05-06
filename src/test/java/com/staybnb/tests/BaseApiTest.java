package com.staybnb.tests;

import com.staybnb.config.AppConstants;
import com.staybnb.config.TestConfig;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
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

    protected RequestSpecification loggedInRequest() {
        String token = unauthedRequest()
                .contentType(ContentType.JSON)
                .body(String.format("{\"email\":\"%s\",\"password\":\"%s\"}",
                        TestConfig.TEST_USER_EMAIL, TestConfig.TEST_PASSWORD))
                .post("/auth/login")
                .jsonPath()
                .getString("token");

        return RestAssured.given()
                .baseUri(AppConstants.API_BASE_URL)
                .header("Authorization", "Bearer " + token);
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
