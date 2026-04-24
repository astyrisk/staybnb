package com.staybnb.pages;

import com.staybnb.config.AppConstants;
import com.staybnb.locators.Locators;
import io.restassured.http.ContentType;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class EditPropertyPage extends BasePage {

    public EditPropertyPage(WebDriver driver) {
        super(driver);
    }

    public void navigateTo(String propertyId) {
        super.navigateTo(AppConstants.HOSTING_URL + "/" + propertyId + "/edit");
    }

    public boolean isEditPageLoaded() {
        return isDisplayed(Locators.EditProperty.CONTAINER)
                && isDisplayed(Locators.EditProperty.HEADER_TITLE)
                && isDisplayed(Locators.EditProperty.SAVE_CHANGES_BUTTON);
    }

    public void waitForSectionsToBeVisible() {
        wait.until(d -> d.findElements(Locators.EditProperty.SECTION_HEADERS).size() >= 6);
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
        return !waitForElementVisible(Locators.EditProperty.TITLE_INPUT).getAttribute("value").trim().isEmpty()
                && !waitForElementVisible(Locators.EditProperty.DESCRIPTION_TEXTAREA).getAttribute("value").trim().isEmpty()
                && !waitForElementVisible(Locators.EditProperty.COUNTRY_INPUT).getAttribute("value").trim().isEmpty()
                && !waitForElementVisible(Locators.EditProperty.CITY_INPUT).getAttribute("value").trim().isEmpty()
                && !waitForElementVisible(Locators.EditProperty.PRICE_INPUT).getAttribute("value").trim().isEmpty()
                && !waitForElementVisible(Locators.EditProperty.PROPERTY_TYPE_SELECT).getAttribute("value").trim().isEmpty()
                && !waitForElementVisible(Locators.EditProperty.CATEGORY_SELECT).getAttribute("value").trim().isEmpty();
    }

    public boolean hasSectionHeader(String expectedHeader) {
        String expected = expectedHeader.toLowerCase();
        List<WebElement> headers = driver.findElements(Locators.EditProperty.SECTION_HEADERS);
        return headers.stream().anyMatch(h -> h.getText().trim().toLowerCase().contains(expected));
    }

    public void clearRequiredField(String fieldName) {
        By locator = switch (fieldName) {
            case "title" -> Locators.EditProperty.TITLE_INPUT;
            case "description" -> Locators.EditProperty.DESCRIPTION_TEXTAREA;
            case "city" -> Locators.EditProperty.CITY_INPUT;
            case "country" -> Locators.EditProperty.COUNTRY_INPUT;
            case "price" -> Locators.EditProperty.PRICE_INPUT;
            default -> throw new IllegalArgumentException("Unsupported required field: " + fieldName);
        };

        WebElement el = waitForElementVisible(locator);
        el.click();
        el.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE));
    }

    public void clickSaveChanges() {
        waitForElementClickable(Locators.EditProperty.SAVE_CHANGES_BUTTON).click();
    }

    public void clickSaveChangesAndDismissAlert() {
        waitForElementClickable(Locators.EditProperty.SAVE_CHANGES_BUTTON).click();
        try {
            wait.until(ExpectedConditions.alertIsPresent()).accept();
        } catch (TimeoutException ignored) {
        }
    }

    public boolean hasInlineErrorContaining(String expectedText) {
        String expected = expectedText.toLowerCase();
        List<WebElement> errors = driver.findElements(Locators.EditProperty.FIELD_ERRORS);
        return errors.stream().anyMatch(el -> el.getText().toLowerCase().contains(expected));
    }

    public boolean isDeletePropertyButtonVisible() {
        return isDisplayed(Locators.EditProperty.DELETE_PROPERTY_BUTTON);
    }

    public boolean isAmenitiesGridDisplayed() {
        return isDisplayed(Locators.EditProperty.AMENITY_GRID)
                && !driver.findElements(Locators.EditProperty.AMENITY_CHECKBOXES).isEmpty();
    }

    public boolean isAmenityCheckedByLabelContaining(String labelText) {
        return waitForElementVisible(Locators.EditProperty.amenityCheckbox(labelText)).isSelected();
    }

    public void toggleAmenityByLabelContaining(String labelText) {
        waitForElementClickable(Locators.EditProperty.amenityCheckbox(labelText)).click();
    }

    public long updatePropertyStatusViaApi(String propertyId, String payloadJson) {
        return apiRequest()
                .contentType(ContentType.JSON)
                .body(payloadJson)
                .put("/properties/" + propertyId)
                .statusCode();
    }
}
