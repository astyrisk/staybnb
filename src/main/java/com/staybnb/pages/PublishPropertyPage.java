package com.staybnb.pages;

import com.staybnb.components.HostDashboardCard;
import com.staybnb.config.AppConstants;
import com.staybnb.locators.Locators;
import io.restassured.http.ContentType;
import org.openqa.selenium.*;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class PublishPropertyPage extends BasePage {

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
        findDashboardCardByTitle(propertyTitle).getRoot().findElement(Locators.HostDashboard.CARD_PUBLISH_TOGGLE).click();
    }

    public boolean isPropertyDraftOnDashboard(String propertyTitle) {
        return findDashboardCardByTitle(propertyTitle).getStatus().trim().equalsIgnoreCase("Draft");
    }

    public boolean isPropertyPublishedOnDashboard(String propertyTitle) {
        return findDashboardCardByTitle(propertyTitle).getStatus().trim().equalsIgnoreCase("Published");
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

    private HostDashboardCard findDashboardCardByTitle(String propertyTitle) {
        waitForDashboardCardsToLoad();
        return driver.findElements(Locators.HostDashboard.PROPERTY_CARD).stream()
                .map(el -> new HostDashboardCard(driver, el))
                .filter(card -> card.getTitle().trim().equals(propertyTitle))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Could not find dashboard card with title: " + propertyTitle));
    }

    private void waitForDashboardCardsToLoad() {
        wait.until(d -> !d.findElements(Locators.HostDashboard.PROPERTY_CARD).isEmpty()
                || !d.findElements(By.className("host-dashboard-empty")).isEmpty());
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
