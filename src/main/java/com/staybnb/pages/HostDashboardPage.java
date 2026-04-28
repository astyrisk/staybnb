package com.staybnb.pages;

import com.staybnb.components.HostDashboardCard;
import com.staybnb.locators.Locators;
import com.staybnb.config.AppConstants;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

public class HostDashboardPage extends BasePage {

    public HostDashboardPage(WebDriver driver) {
        super(driver);
    }

    public void navigateTo() {
        super.navigateTo(AppConstants.HOSTING_URL);
        waitForDashboardToLoad();
    }

    public HostDashboardPage navigateViaNavbar() {
        navbar().clickHostDashboard();
        waitForUrlToBe(AppConstants.HOSTING_URL);
        waitForDashboardToLoad();
        return this;
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

    public List<HostDashboardCard> getCards() {
        return waitForElementsPresent(Locators.HostDashboard.PROPERTY_CARD).stream()
                .map(el -> new HostDashboardCard(driver, el))
                .collect(Collectors.toList());
    }

    public HostDashboardCard getFirstCard() {
        return new HostDashboardCard(driver, waitForElementsPresent(Locators.HostDashboard.PROPERTY_CARD).getFirst());
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
