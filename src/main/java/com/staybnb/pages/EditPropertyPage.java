package com.staybnb.pages;

import com.staybnb.config.AppConstants;
import com.staybnb.locators.Locators;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class EditPropertyPage extends BasePage {
    private static final String UPDATE_PROPERTY_API_JS_RESOURCE = "com/staybnb/scripts/updatePropertyApi.js";
    private static final String UPDATE_PROPERTY_STATUS_API_JS_RESOURCE = "com/staybnb/scripts/updatePropertyStatusApi.js";

    private final By container = Locators.EditProperty.CONTAINER;
    private final By headerTitle = Locators.EditProperty.HEADER_TITLE;
    private final By deletePropertyButton = Locators.EditProperty.DELETE_PROPERTY_BUTTON;
    private final By sectionHeaders = Locators.EditProperty.SECTION_HEADERS;
    private final By saveChangesButton = Locators.EditProperty.SAVE_CHANGES_BUTTON;
    private final By fieldErrors = Locators.EditProperty.FIELD_ERRORS;
    private final By titleInput = Locators.EditProperty.TITLE_INPUT;
    private final By descriptionTextarea = Locators.EditProperty.DESCRIPTION_TEXTAREA;
    private final By countryInput = Locators.EditProperty.COUNTRY_INPUT;
    private final By cityInput = Locators.EditProperty.CITY_INPUT;
    private final By priceInput = Locators.EditProperty.PRICE_INPUT;
    private final By propertyTypeSelect = Locators.EditProperty.PROPERTY_TYPE_SELECT;
    private final By categorySelect = Locators.EditProperty.CATEGORY_SELECT;

    public EditPropertyPage(WebDriver driver) {
        super(driver);
    }

    public void navigateTo(String propertyId) {
        super.navigateTo(AppConstants.HOSTING_URL + "/" + propertyId + "/edit");
    }

    public boolean isEditPageLoaded() {
        return isDisplayed(container) && isDisplayed(headerTitle) && isDisplayed(saveChangesButton);
    }

    public void waitForSectionsToBeVisible() {
        wait.until(d -> d.findElements(sectionHeaders).size() >= 6);
    }

    public boolean hasAllMainSectionsVisible() {
        String page = driver.getPageSource().toLowerCase();
        return page.contains("basic information")
                && page.contains("location")
                && page.contains("property details")
                && page.contains("amenities")
                && page.contains("photos")
                && page.contains("pricing");
    }

    public boolean hasCreateWizardProgressOrNavigation() {
        String page = driver.getPageSource().toLowerCase();
        return page.contains("step 1 of")
                || page.contains("create property")
                || (page.contains("next") && page.contains("back"));
    }

    public boolean hasPrePopulatedCoreFields() {
        return !waitForElementVisible(titleInput).getAttribute("value").trim().isEmpty()
                && !waitForElementVisible(descriptionTextarea).getAttribute("value").trim().isEmpty()
                && !waitForElementVisible(countryInput).getAttribute("value").trim().isEmpty()
                && !waitForElementVisible(cityInput).getAttribute("value").trim().isEmpty()
                && !waitForElementVisible(priceInput).getAttribute("value").trim().isEmpty()
                && !waitForElementVisible(propertyTypeSelect).getAttribute("value").trim().isEmpty()
                && !waitForElementVisible(categorySelect).getAttribute("value").trim().isEmpty();
    }

    public boolean hasSectionHeader(String expectedHeader) {
        String expected = expectedHeader.toLowerCase();
        List<WebElement> headers = driver.findElements(sectionHeaders);
        return headers.stream().anyMatch(h -> h.getText().trim().toLowerCase().contains(expected));
    }

    public void updateTitleAndSave(String title) {
        type(titleInput, title);
        clickSaveChanges();
    }

    public void clearRequiredField(String fieldName) {
        By locator = switch (fieldName) {
            case "title" -> titleInput;
            case "description" -> descriptionTextarea;
            case "city" -> cityInput;
            case "country" -> countryInput;
            case "price" -> priceInput;
            default -> throw new IllegalArgumentException("Unsupported required field: " + fieldName);
        };

        WebElement el = waitForElementVisible(locator);
        el.click();
        el.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
    }

    public void clickSaveChanges() {
        waitForElementClickable(saveChangesButton).click();
    }

    public void clickSaveChangesAndDismissAlert() {
        waitForElementClickable(saveChangesButton).click();
        try {
            wait.until(ExpectedConditions.alertIsPresent()).accept();
        } catch (Exception ignored) {
        }
    }

    public boolean hasInlineErrorContaining(String expectedText) {
        String expected = expectedText.toLowerCase();
        List<WebElement> errors = driver.findElements(fieldErrors);
        return errors.stream().anyMatch(el -> el.getText().toLowerCase().contains(expected));
    }

    public boolean isDeletePropertyButtonVisible() {
        return isDisplayed(deletePropertyButton);
    }

    public boolean isAmenitiesGridDisplayed() {
        return isDisplayed(Locators.EditProperty.AMENITY_GRID)
                && !driver.findElements(Locators.EditProperty.AMENITY_CHECKBOXES).isEmpty();
    }

    public boolean hasAnyAmenityChecked() {
        List<WebElement> checkboxes = driver.findElements(Locators.EditProperty.AMENITY_CHECKBOXES);
        return checkboxes.stream().anyMatch(WebElement::isSelected);
    }

    public boolean isAmenityCheckedByLabelContaining(String labelText) {
        String normalized = labelText.toLowerCase();
        By checkbox = By.xpath(
                "//label[contains(@class,'edit-property-amenity-item')][contains(translate(normalize-space(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'"
                        + normalized + "')]//input[@type='checkbox']"
        );
        return waitForElementVisible(checkbox).isSelected();
    }

    public void toggleAmenityByLabelContaining(String labelText) {
        String normalized = labelText.toLowerCase();
        By checkbox = By.xpath(
                "//label[contains(@class,'edit-property-amenity-item')][contains(translate(normalize-space(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'"
                        + normalized + "')]//input[@type='checkbox']"
        );
        waitForElementClickable(checkbox).click();
    }

    public long updatePropertyStatusViaApi(String propertyId, String payloadJson) {
        driver.get(AppConstants.HOME_URL);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String script = loadJavascriptResource(UPDATE_PROPERTY_STATUS_API_JS_RESOURCE);
        Object responseStatus = js.executeAsyncScript(script, AppConstants.SLUG, propertyId, payloadJsonToObject(js, payloadJson));
        if (responseStatus instanceof Number n) {
            return n.longValue();
        }
        throw new RuntimeException("Unexpected edit-property status response type: " +
                (responseStatus == null ? "null" : responseStatus.getClass().getName()));
    }

    public String updatePropertyViaApi(String propertyId, String payloadJson) {
        driver.get(AppConstants.HOME_URL);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String script = loadJavascriptResource(UPDATE_PROPERTY_API_JS_RESOURCE);
        Object response = js.executeAsyncScript(script, AppConstants.SLUG, propertyId, payloadJsonToObject(js, payloadJson));
        return (String) response;
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
