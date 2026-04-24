package com.staybnb.pages;

import com.staybnb.config.AppConstants;
import com.staybnb.locators.Locators;
import io.restassured.http.ContentType;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeletePropertyPage extends BasePage {

    public DeletePropertyPage(WebDriver driver) {
        super(driver);
    }

    public void navigateToEditPage(String propertyId) {
        super.navigateTo(AppConstants.HOSTING_URL + "/" + propertyId + "/edit");
    }

    public void navigateToHostingDashboard() {
        super.navigateTo(AppConstants.HOSTING_URL);
    }

    public void clickDeleteOnEditPage() {
        waitForElementClickable(Locators.DeleteProperty.EDIT_PAGE_DELETE_BUTTON).click();
    }

    public void clickDeleteOnDashboardForTitle(String propertyTitle) {
        List<WebElement> cards = driver.findElements(By.cssSelector(".host-dashboard-card"));
        for (WebElement card : cards) {
            String cardTitle = card.findElement(Locators.DeleteProperty.DASHBOARD_PROPERTY_TITLE).getText().trim();
            if (cardTitle.equals(propertyTitle)) {
                card.findElement(Locators.HostDashboard.CARD_DELETE_BUTTON).click();
                return;
            }
        }
        throw new IllegalStateException("Could not find dashboard card with title: " + propertyTitle);
    }

    public void clickFirstDashboardDelete() {
        waitForElementClickable(Locators.DeleteProperty.DASHBOARD_DELETE_BUTTONS).click();
    }

    public boolean hasDeleteConfirmationMessage(String expectedMessage) {
        String expected = expectedMessage.trim().toLowerCase();
        return getAlertText().toLowerCase().contains(expected);
    }

    public void confirmDeletion() {
        if (!tryAcceptAlert()) {
            waitForElementClickable(Locators.DeleteProperty.CONFIRM_BUTTON).click();
        }
    }

    public void cancelDeletion() {
        if (!tryDismissAlert()) {
            waitForElementClickable(Locators.DeleteProperty.CANCEL_BUTTON).click();
        }
    }

    public boolean isPropertyListedOnDashboard(String propertyTitle) {
        navigateToHostingDashboard();
        wait.until(d -> !d.findElements(By.cssSelector(".host-dashboard-grid, .host-dashboard-empty")).isEmpty());
        return driver.findElements(Locators.DeleteProperty.DASHBOARD_PROPERTY_TITLE)
                .stream()
                .anyMatch(el -> propertyTitle.equals(el.getText().trim()));
    }

    public long deletePropertyStatusViaApi(String propertyId) {
        return apiRequest()
                .delete("/properties/" + propertyId)
                .statusCode();
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

    private boolean tryAcceptAlert() {
        try {
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            alert.accept();
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    private boolean tryDismissAlert() {
        try {
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            alert.dismiss();
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    private String getAlertText() {
        try {
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            return alert.getText() == null ? "" : alert.getText();
        } catch (NoAlertPresentException | org.openqa.selenium.TimeoutException e) {
            return "";
        }
    }
}
