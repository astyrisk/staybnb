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

    protected String loginAndGetToken(String email, String password) {
        return unauthedRequest()
                .contentType(ContentType.JSON)
                .body(String.format("{\"email\":\"%s\",\"password\":\"%s\"}", email, password))
                .post("/auth/login")
                .jsonPath()
                .getString("token");
    }

    protected RequestSpecification loggedInRequest() {
        return RestAssured.given()
                .baseUri(AppConstants.API_BASE_URL)
                .header("Authorization", "Bearer " + loginAndGetToken(TestConfig.HOST_TEST_USER_EMAIL, TestConfig.HOST_TEST_PASSWORD));
    }

    protected RequestSpecification nonHostLoggedInRequest() {
        return RestAssured.given()
                .baseUri(AppConstants.API_BASE_URL)
                .header("Authorization", "Bearer " + loginAndGetToken(TestConfig.NON_HOST_TEST_USER_EMAIL, TestConfig.NON_HOST_TEST_PASSWORD));
    }

    protected void injectTokenIntoBrowser(String token) {
        driver.get(AppConstants.BASE_URL);
        ((JavascriptExecutor) driver).executeScript(
                "window.localStorage.setItem('staybnb_token', arguments[0]);", token);
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
