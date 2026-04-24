package com.staybnb.pages;

import com.staybnb.locators.Locators;
import com.staybnb.config.AppConstants;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class HostDashboardPage extends BasePage {
    private static final String PAGE_URL = AppConstants.HOSTING_URL;

    public HostDashboardPage(WebDriver driver) {
        super(driver);
    }

    public void navigateTo() {
        super.navigateTo(PAGE_URL);
        waitForDashboardToLoad();
    }

    public void navigateViaNavbar() {
        navbar().clickHostDashboard();
        waitForUrlToBe(PAGE_URL);
        waitForDashboardToLoad();
    }

    public String getSummarySubtitle() {
        return waitForElementVisible(Locators.HostDashboard.SUMMARY_SUBTITLE).getText();
    }

    public boolean isCreateNewPropertyButtonVisible() {
        return isDisplayed(Locators.HostDashboard.CREATE_NEW_PROPERTY_BUTTON);
    }

    public String getCreateNewPropertyHref() {
        return waitForElementVisible(Locators.HostDashboard.CREATE_NEW_PROPERTY_BUTTON).getAttribute("href");
    }

    public List<WebElement> getPropertyCards() {
        return waitForElementsPresent(Locators.HostDashboard.PROPERTY_CARD);
    }

    public boolean hasThumbnail(WebElement card) {
        return !card.findElements(Locators.HostDashboard.CARD_IMAGE).isEmpty();
    }

    public String getTitle(WebElement card) {
        return card.findElement(Locators.HostDashboard.CARD_TITLE).getText();
    }

    public String getLocation(WebElement card) {
        return card.findElement(Locators.HostDashboard.CARD_LOCATION).getText();
    }

    public String getPrice(WebElement card) {
        return card.findElement(Locators.HostDashboard.CARD_PRICE).getText();
    }

    public String getStatus(WebElement card) {
        return card.findElement(Locators.HostDashboard.CARD_STATUS).getText();
    }

    public boolean hasRating(WebElement card) {
        return !card.findElements(Locators.HostDashboard.CARD_RATING).isEmpty();
    }

    public boolean hasEditAction(WebElement card) {
        return !card.findElements(Locators.HostDashboard.CARD_EDIT_BUTTON).isEmpty();
    }

    public boolean hasDeleteAction(WebElement card) {
        return !card.findElements(Locators.HostDashboard.CARD_DELETE_BUTTON).isEmpty();
    }

    public boolean hasPublishToggleAction(WebElement card) {
        return !card.findElements(Locators.HostDashboard.CARD_PUBLISH_TOGGLE).isEmpty();
    }

    public boolean isDetailDisplayed(WebElement card, String detailName) {
        return switch (detailName) {
            case "thumbnail" -> hasThumbnail(card);
            case "title" -> !getTitle(card).isEmpty();
            case "location" -> !getLocation(card).isEmpty();
            case "price per night" -> getPrice(card).contains("/night");
            case "published or draft status" -> {
                String statusText = getStatus(card).trim();
                yield statusText.equalsIgnoreCase("Published") || statusText.equalsIgnoreCase("Draft");
            }
            case "rating" -> hasRating(card);
            default -> throw new IllegalArgumentException("Unsupported detail: " + detailName);
        };
    }

    public boolean hasCardAction(WebElement card, String actionName) {
        return switch (actionName) {
            case "edit" -> hasEditAction(card);
            case "delete" -> hasDeleteAction(card);
            case "publish toggle" -> hasPublishToggleAction(card);
            default -> throw new IllegalArgumentException("Unsupported action: " + actionName);
        };
    }

    public boolean hasPublishedAndUnpublishedProperties() {
        String response = getHostingPropertiesViaApi();
        String normalized = response.replaceAll("\\s+", "").toLowerCase();
        boolean hasPublished = normalized.contains("\"published\":true")
                || normalized.contains("\"ispublished\":true")
                || normalized.contains("\"status\":\"published\"");
        boolean hasUnpublished = normalized.contains("\"published\":false")
                || normalized.contains("\"ispublished\":false")
                || normalized.contains("\"status\":\"draft\"");
        return hasPublished && hasUnpublished;
    }

    public boolean isEmptyStateVisible() {
        return isDisplayed(Locators.HostDashboard.EMPTY_STATE_MESSAGE);
    }

    public String getHostingPropertiesViaApi() {
        return apiRequest().get("/hosting/properties").asString();
    }

    public long getHostingPropertiesStatusViaApi() {
        return apiRequest().get("/hosting/properties").statusCode();
    }

    private void waitForDashboardToLoad() {
        wait.until(d ->
                !d.findElements(Locators.HostDashboard.CONTAINER).isEmpty() ||
                !d.findElements(Locators.HostDashboard.EMPTY_STATE_MESSAGE).isEmpty() ||
                !d.findElements(Locators.HostDashboard.PROPERTY_CARD).isEmpty()
        );
    }
}
