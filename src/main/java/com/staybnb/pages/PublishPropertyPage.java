package com.staybnb.pages;

import com.staybnb.config.AppConstants;
import com.staybnb.locators.Locators;
import io.restassured.http.ContentType;
import org.openqa.selenium.*;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PublishPropertyPage extends BasePage {

    //TODO Redundant, repeated
    private final By dashboardCard = Locators.HostDashboard.PROPERTY_CARD;
    private final By dashboardTitle = Locators.HostDashboard.CARD_TITLE;
    private final By dashboardStatus = Locators.HostDashboard.CARD_STATUS;
    private final By dashboardPublishToggle = Locators.HostDashboard.CARD_PUBLISH_TOGGLE;

    public PublishPropertyPage(WebDriver driver) {
        super(driver);
    }

    public void navigateToHostingDashboard() {
        super.navigateTo(AppConstants.HOSTING_URL);
        waitForDashboardCardsToLoad();
    }

    public void navigateToPropertyListing() {
        super.navigateTo(AppConstants.PROPERTY_LISTING_URL);
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
        return apiRequest()
                .contentType(ContentType.JSON)
                .body(payloadJson)
                .post("/properties")
                .asString();
    }

    public String extractCreatedPropertyId(String createPropertyResponse) {
        if (createPropertyResponse == null) {
            throw new IllegalStateException("Create property API response was null.");
        }
        Matcher idMatcher = Pattern.compile("\"id\"\\s*:\\s*(\\d+)").matcher(createPropertyResponse);
        if (idMatcher.find()) {
            return idMatcher.group(1);
        }
        throw new IllegalStateException("Could not extract property id from response: " + createPropertyResponse);
    }

    public String updatePublishPropertyViaApi(String propertyId, boolean isPublished) {
        return apiRequest()
                .contentType(ContentType.JSON)
                .body("{\"isPublished\":" + isPublished + "}")
                .put("/properties/" + propertyId)
                .asString();
    }

    public long updatePublishPropertyStatusViaApi(String propertyId, boolean isPublished) {
        return apiRequest()
                .contentType(ContentType.JSON)
                .body("{\"isPublished\":" + isPublished + "}")
                .put("/properties/" + propertyId)
                .statusCode();
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
        wait.until(d -> !d.findElements(dashboardCard).isEmpty() || !d.findElements(By.className("host-dashboard-empty")).isEmpty());
    }

    public boolean isPublishSuccessfulResponse(String response) {
        String normalized = response == null ? "" : response.replaceAll("\\s+", "").toLowerCase();
        return normalized.contains("\"message\":\"propertypublishedsuccessfully\"");
    }

    public void waitForPropertyStatusToChangeToPublished(String propertyTitle) {
        wait.until(d -> {
            try {
                return isPropertyPublishedOnDashboard(propertyTitle);
            } catch (StaleElementReferenceException | IllegalStateException e) {
                return false;
            }
        });
    }
}
