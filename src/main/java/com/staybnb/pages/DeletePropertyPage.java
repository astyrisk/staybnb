package com.staybnb.pages;

import com.staybnb.config.AppConstants;
import com.staybnb.locators.Locators;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeletePropertyPage extends BasePage {
    private static final String DELETE_PROPERTY_STATUS_API_JS_RESOURCE = "com/staybnb/scripts/deletePropertyStatusApi.js";
    private static final String DELETE_PROPERTY_API_JS_RESOURCE = "com/staybnb/scripts/deletePropertyApi.js";
    private static final String CREATE_PROPERTY_API_JS_RESOURCE = "com/staybnb/scripts/createPropertyApi.js";

    private final By editPageDeleteButton = Locators.DeleteProperty.EDIT_PAGE_DELETE_BUTTON;
    private final By dashboardDeleteButtons = Locators.DeleteProperty.DASHBOARD_DELETE_BUTTONS;
    private final By confirmationMessage = Locators.DeleteProperty.CONFIRMATION_MESSAGE;
    private final By confirmButton = Locators.DeleteProperty.CONFIRM_BUTTON;
    private final By cancelButton = Locators.DeleteProperty.CANCEL_BUTTON;
    private final By dashboardPropertyTitle = Locators.DeleteProperty.DASHBOARD_PROPERTY_TITLE;

    public DeletePropertyPage(WebDriver driver) {
        super(driver);
    }

    public void navigateToEditPage(String propertyId) {
        super.navigateTo(AppConstants.HOSTING_URL + "/" + propertyId + "/edit");
    }

    public void navigateToHostingDashboard() {
        super.navigateTo(AppConstants.HOSTING_URL);
    }

    public boolean isDeleteButtonVisibleOnEditPage() {
        return isDisplayed(editPageDeleteButton);
    }

    public void clickDeleteOnEditPage() {
        waitForElementClickable(editPageDeleteButton).click();
    }

    public void clickDeleteOnDashboardForTitle(String propertyTitle) {
        List<WebElement> cards = driver.findElements(By.cssSelector(".host-dashboard-card"));
        for (WebElement card : cards) {
            String cardTitle = card.findElement(dashboardPropertyTitle).getText().trim();
            if (cardTitle.equals(propertyTitle)) {
                card.findElement(Locators.HostDashboard.CARD_DELETE_BUTTON).click();
                return;
            }
        }
        throw new IllegalStateException("Could not find dashboard card with title: " + propertyTitle);
    }

    public void clickFirstDashboardDelete() {
        waitForElementClickable(dashboardDeleteButtons).click();
    }

    public boolean hasDeleteConfirmationMessage(String expectedMessage) {
        String expected = expectedMessage.trim().toLowerCase();
        return getAlertText().toLowerCase().contains(expected);
    }

    public void confirmDeletion() {
        if (!tryAcceptAlert()) {
            waitForElementClickable(confirmButton).click();
        }
    }

    public void cancelDeletion() {
        if (!tryDismissAlert()) {
            waitForElementClickable(cancelButton).click();
        }
    }

    public boolean isPropertyListedOnDashboard(String propertyTitle) {
        navigateToHostingDashboard();
        wait.until(d -> !d.findElements(By.cssSelector(".host-dashboard-grid, .host-dashboard-empty")).isEmpty());
        return driver.findElements(dashboardPropertyTitle)
                .stream()
                .anyMatch(el -> propertyTitle.equals(el.getText().trim()));
    }

    public long deletePropertyStatusViaApi(String propertyId) {
        driver.get(AppConstants.HOME_URL);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String script = loadJavascriptResource(DELETE_PROPERTY_STATUS_API_JS_RESOURCE);
        Object responseStatus = js.executeAsyncScript(script, AppConstants.SLUG, propertyId);
        if (responseStatus instanceof Number n) {
            return n.longValue();
        }
        throw new RuntimeException("Unexpected delete-property status response type: " +
                (responseStatus == null ? "null" : responseStatus.getClass().getName()));
    }

    public String createPropertyViaApi(String payloadJson) {
        driver.get(AppConstants.HOME_URL);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String script = loadJavascriptResource(CREATE_PROPERTY_API_JS_RESOURCE);
        Object response = js.executeAsyncScript(script, AppConstants.SLUG, payloadJsonToObject(js, payloadJson));
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

    private boolean tryAcceptAlert() {
        try {
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            alert.accept();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean tryDismissAlert() {
        try {
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            alert.dismiss();
            return true;
        } catch (Exception e) {
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
}
