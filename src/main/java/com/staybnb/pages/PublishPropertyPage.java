package com.staybnb.pages;

import com.staybnb.config.Constants;
import com.staybnb.locators.Locators;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PublishPropertyPage extends BasePage {
    private static final String CREATE_PROPERTY_API_JS_RESOURCE = "com/staybnb/scripts/createPropertyApi.js";
    private static final String UPDATE_PUBLISH_PROPERTY_API_JS_RESOURCE = "com/staybnb/scripts/updatePublishPropertyApi.js";
    private static final String UPDATE_PUBLISH_PROPERTY_STATUS_API_JS_RESOURCE = "com/staybnb/scripts/updatePublishPropertyStatusApi.js";

    private final By dashboardCard = Locators.HostDashboard.PROPERTY_CARD;
    private final By dashboardTitle = Locators.HostDashboard.CARD_TITLE;
    private final By dashboardStatus = Locators.HostDashboard.CARD_STATUS;
    private final By dashboardPublishToggle = Locators.HostDashboard.CARD_PUBLISH_TOGGLE;

    public PublishPropertyPage(WebDriver driver) {
        super(driver);
    }

    public void navigateToHostingDashboard() {
        super.navigateTo(Constants.HOSTING_URL);
        waitForDashboardCardsToLoad();
    }

    public void navigateToPropertyListing() {
        super.navigateTo(Constants.PROPERTY_LISTING_URL);
        wait.until(d -> !d.findElements(By.className("property-grid")).isEmpty());
    }

    public void clickPublishToggleOnDashboardForTitle(String propertyTitle) {
        WebElement card = findDashboardCardByTitle(propertyTitle);
        card.findElement(dashboardPublishToggle).click();
    }

    public boolean isPropertyDraftOnDashboard(String propertyTitle) {
        WebElement card = findDashboardCardByTitle(propertyTitle);
        String statusText = card.findElement(dashboardStatus).getText().trim();
        return statusText.equalsIgnoreCase("Draft");
    }

    public boolean isPropertyPublishedOnDashboard(String propertyTitle) {
        WebElement card = findDashboardCardByTitle(propertyTitle);
        String statusText = card.findElement(dashboardStatus).getText().trim();
        return statusText.equalsIgnoreCase("Published");
    }

    public boolean isPropertyListedOnCurrentPage(String propertyTitle) {
        return driver.getPageSource().contains(propertyTitle);
    }

    public String createPropertyViaApi(String payloadJson) {
        driver.get(Constants.HOME_URL);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String script = loadJavascriptResource(CREATE_PROPERTY_API_JS_RESOURCE);
        Object response = js.executeAsyncScript(script, Constants.SLUG, payloadJsonToObject(js, payloadJson));
        return (String) response;
    }

    public String extractCreatedPropertyId(String createPropertyResponse) {
        if (createPropertyResponse == null) {
            throw new IllegalStateException("Create property API response was null.");
        }
        Matcher bodyMatcher = Pattern.compile("\"body\"\\s*:\\s*\"(.*)\"\\s*}", Pattern.DOTALL).matcher(createPropertyResponse);
        String searchable = bodyMatcher.find()
                ? bodyMatcher.group(1).replace("\\\"", "\"")
                : createPropertyResponse;

        Matcher idMatcher = Pattern.compile("\"id\"\\s*:\\s*(\\d+)").matcher(searchable);
        if (idMatcher.find()) {
            return idMatcher.group(1);
        }
        throw new IllegalStateException("Could not extract property id from response: " + createPropertyResponse);
    }

    public String updatePublishPropertyViaApi(String propertyId, boolean isPublished) {
        driver.get(Constants.HOME_URL);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String script = loadJavascriptResource(UPDATE_PUBLISH_PROPERTY_API_JS_RESOURCE);
        Object response = js.executeAsyncScript(script, Constants.SLUG, propertyId, publishPayload(js, isPublished));
        return (String) response;
    }

    public long updatePublishPropertyStatusViaApi(String propertyId, boolean isPublished) {
        driver.get(Constants.HOME_URL);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String script = loadJavascriptResource(UPDATE_PUBLISH_PROPERTY_STATUS_API_JS_RESOURCE);
        Object responseStatus = js.executeAsyncScript(script, Constants.SLUG, propertyId, publishPayload(js, isPublished));
        if (responseStatus instanceof Number n) {
            return n.longValue();
        }
        throw new RuntimeException("Unexpected publish-property status response type: " +
                (responseStatus == null ? "null" : responseStatus.getClass().getName()));
    }

    private Object publishPayload(JavascriptExecutor js, boolean isPublished) {
        return js.executeScript("return { isPublished: arguments[0] };", isPublished);
    }

    private WebElement findDashboardCardByTitle(String propertyTitle) {
        waitForDashboardCardsToLoad();
        List<WebElement> cards = driver.findElements(dashboardCard);
        for (WebElement card : cards) {
            String cardTitle = card.findElement(dashboardTitle).getText().trim();
            if (cardTitle.equals(propertyTitle)) {
                return card;
            }
        }
        throw new IllegalStateException("Could not find dashboard card with title: " + propertyTitle);
    }

    private void waitForDashboardCardsToLoad() {
        wait.until(d -> d.findElements(dashboardCard).size() > 0 || !d.findElements(By.className("host-dashboard-empty")).isEmpty());
    }

    private Object payloadJsonToObject(JavascriptExecutor js, String payloadJson) {
        return js.executeScript("return JSON.parse(arguments[0]);", payloadJson);
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

    public void waitForPropertyStatusToChangeToPublished(String propertyTitle) {
        wait.until(d -> {
            try {
                return isPropertyPublishedOnDashboard(propertyTitle);
            } catch (org.openqa.selenium.StaleElementReferenceException e) {
                return false;
            } catch (IllegalStateException e) {
                return false;
            }
        });
    }
}
