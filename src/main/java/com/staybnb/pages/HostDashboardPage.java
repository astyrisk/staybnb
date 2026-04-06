package com.staybnb.pages;

import com.staybnb.locators.Locators;
import com.staybnb.config.Constants;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class HostDashboardPage extends BasePage {
    private static final String PAGE_URL = Constants.HOSTING_URL;
    private static final String HOSTING_PROPERTIES_API_JS_RESOURCE = "com/staybnb/scripts/getHostingPropertiesApi.js";
    private static final String HOSTING_PROPERTIES_STATUS_API_JS_RESOURCE = "com/staybnb/scripts/getHostingPropertiesStatusApi.js";

    private final By container = Locators.HostDashboard.CONTAINER;
    private final By summarySubtitle = Locators.HostDashboard.SUMMARY_SUBTITLE;
    private final By createNewPropertyButton = Locators.HostDashboard.CREATE_NEW_PROPERTY_BUTTON;
    private final By propertyCard = Locators.HostDashboard.PROPERTY_CARD;
    private final By emptyStateMessage = Locators.HostDashboard.EMPTY_STATE_MESSAGE;

    public HostDashboardPage(WebDriver driver) {
        super(driver);
    }

    public void navigateTo() {
        super.navigateTo(PAGE_URL);
        waitForDashboardToLoad();
    }

    public String getSummarySubtitle() {
        return waitForElementVisible(summarySubtitle).getText();
    }

    public boolean isCreateNewPropertyButtonVisible() {
        return isDisplayed(createNewPropertyButton);
    }

    public String getCreateNewPropertyHref() {
        return waitForElementVisible(createNewPropertyButton).getAttribute("href");
    }

    public List<WebElement> getPropertyCards() {
        return driver.findElements(propertyCard);
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
        return isDisplayed(emptyStateMessage);
    }

    public String getEmptyStateText() {
        return waitForElementVisible(emptyStateMessage).getText();
    }

    public String getHostingPropertiesViaApi() {
        driver.get(Constants.HOME_URL);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String script = loadJavascriptResource(HOSTING_PROPERTIES_API_JS_RESOURCE);
        Object response = js.executeAsyncScript(script, Constants.SLUG);
        return (String) response;
    }

    public long getHostingPropertiesStatusViaApi() {
        driver.get(Constants.HOME_URL);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String script = loadJavascriptResource(HOSTING_PROPERTIES_STATUS_API_JS_RESOURCE);
        Object responseStatus = js.executeAsyncScript(script, Constants.SLUG);
        if (responseStatus instanceof Number n) {
            return n.longValue();
        }
        throw new RuntimeException("Unexpected hosting/properties status response type: " +
                (responseStatus == null ? "null" : responseStatus.getClass().getName()));
    }

    private void waitForDashboardToLoad() {
        wait.until(d ->
                !d.findElements(container).isEmpty() ||
                !d.findElements(emptyStateMessage).isEmpty() ||
                !d.findElements(propertyCard).isEmpty()
        );
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
}
