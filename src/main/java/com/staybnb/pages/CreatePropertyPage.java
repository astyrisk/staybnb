package com.staybnb.pages;

import com.staybnb.locators.Locators;
import com.staybnb.config.AppConstants;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class CreatePropertyPage extends BasePage {

    public CreatePropertyPage(WebDriver driver) {
        super(driver);
    }

    public void navigateTo() {
        super.navigateTo(AppConstants.HOSTING_CREATE_URL);
        waitForElementVisible(Locators.CreateProperty.CONTAINER);
    }

    public boolean isStep1BasicsLoaded() {
        waitForElementVisible(Locators.CreateProperty.STEP_1_PROPERTY_TYPE_SELECT);
        return isDisplayed(Locators.CreateProperty.STEP_1_CATEGORY_SELECT)
                && isDisplayed(Locators.CreateProperty.STEP_1_TITLE_INPUT)
                && isDisplayed(Locators.CreateProperty.STEP_1_DESCRIPTION_TEXTAREA);
    }

    public boolean isStep2LocationLoaded() {
        waitForElementVisible(Locators.CreateProperty.STEP_2_COUNTRY_INPUT);
        return isDisplayed(Locators.CreateProperty.STEP_2_CITY_INPUT)
                && isDisplayed(Locators.CreateProperty.STEP_2_ADDRESS_INPUT);
    }

    public boolean isStep3DetailsLoaded() {
        waitForElementVisible(Locators.CreateProperty.STEP_3_MAX_GUESTS_INPUT);
        return isDisplayed(Locators.CreateProperty.STEP_3_BEDROOMS_INPUT)
                && isDisplayed(Locators.CreateProperty.STEP_3_BEDS_INPUT)
                && isDisplayed(Locators.CreateProperty.STEP_3_BATHROOMS_INPUT);
    }

    public boolean isStep4AmenitiesLoaded() {
        waitForElementVisible(Locators.CreateProperty.STEP_4_AMENITIES_TITLE);
        return isDisplayed(Locators.CreateProperty.STEP_4_AMENITIES_GRID)
                && !waitForElementsPresent(Locators.CreateProperty.STEP_4_AMENITY_CHECKBOXES).isEmpty();
    }

    public boolean isStep5PhotosLoaded() {
        waitForElementVisible(Locators.CreateProperty.STEP_5_PHOTOS_TITLE);
        return true;
    }

    public boolean step5HasUploadAreaSupportingDropOrBrowse() {
        String dropzoneText = waitForElementVisible(Locators.CreateProperty.STEP_5_UPLOAD_DROPZONE).getText().toLowerCase();
        String uploadText = waitForElementVisible(Locators.CreateProperty.STEP_5_UPLOAD_TEXT).getText().toLowerCase();

        WebElement input = waitForElementsPresent(Locators.CreateProperty.STEP_5_UPLOAD_FILE_INPUT).getFirst();
        String accepts = input.getAttribute("accept");

        return accepts != null
                && accepts.contains("image/")
                && (dropzoneText.contains("drag") || uploadText.contains("drag"))
                && (dropzoneText.contains("browse") || uploadText.contains("browse"));
    }

    public String getProgressText() {
        return waitForElementVisible(Locators.CreateProperty.PROGRESS_TEXT).getText().trim();
    }

    public int getCurrentStep() {
        String text = getProgressText(); // e.g. "Step 3 of 7"
        try {
            return Integer.parseInt(text.split("\\s+")[1]);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            return -1;
        }
    }

    public void clickNext() {
        int currentStep = getCurrentStep();
        waitForElementClickable(Locators.CreateProperty.NEXT_BUTTON).click();
        wait.until(d -> getCurrentStep() > currentStep
                || !d.findElements(Locators.CreateProperty.FIELD_ERRORS).isEmpty());
    }

    public void clickBack() {
        int currentStep = getCurrentStep();
        waitForElementClickable(Locators.CreateProperty.BACK_BUTTON).click();
        wait.until(d -> getCurrentStep() < currentStep);
    }

    public void selectPropertyTypeByVisibleText(String text) {
        new Select(waitForElementVisible(Locators.CreateProperty.STEP_1_PROPERTY_TYPE_SELECT)).selectByVisibleText(text);
    }

    public void selectCategoryByIndex(int index) {
        wait.until(d -> new Select(waitForElementVisible(Locators.CreateProperty.STEP_1_CATEGORY_SELECT)).getOptions().size() > index);
        new Select(driver.findElement(Locators.CreateProperty.STEP_1_CATEGORY_SELECT)).selectByIndex(index);
    }

    public int getCategoryDropdownOptionCount() {
        wait.until(d -> new Select(waitForElementVisible(Locators.CreateProperty.STEP_1_CATEGORY_SELECT)).getOptions().size() > 1);
        return new Select(driver.findElement(Locators.CreateProperty.STEP_1_CATEGORY_SELECT)).getOptions().size();
    }

    public void enterTitle(String title) {
        type(Locators.CreateProperty.STEP_1_TITLE_INPUT, title);
    }

    public void clearTitle() {
        type(Locators.CreateProperty.STEP_1_TITLE_INPUT, "");
    }

    public void enterDescription(String description) {
        type(Locators.CreateProperty.STEP_1_DESCRIPTION_TEXTAREA, description);
    }

    public void clearDescription() {
        type(Locators.CreateProperty.STEP_1_DESCRIPTION_TEXTAREA, "");
    }

    public void clearCountry() {
        type(Locators.CreateProperty.STEP_2_COUNTRY_INPUT, "");
    }

    public void enterCountry(String country) {
        type(Locators.CreateProperty.STEP_2_COUNTRY_INPUT, country);
    }

    public void clearCity() {
        type(Locators.CreateProperty.STEP_2_CITY_INPUT, "");
    }

    public void clearStep2RequiredField(String fieldName) {
        switch (fieldName) {
            case "country" -> clearCountry();
            case "city" -> clearCity();
            default -> throw new IllegalArgumentException("Unsupported step 2 required field: " + fieldName);
        }
    }

    public void enterCity(String city) {
        type(Locators.CreateProperty.STEP_2_CITY_INPUT, city);
    }

    public void enterAddress(String address) {
        type(Locators.CreateProperty.STEP_2_ADDRESS_INPUT, address);
    }

    public void fillValidStep1() {
        selectPropertyTypeByVisibleText("Entire Place");
        selectCategoryByIndex(1);
        enterTitle("Automation Listing");
        enterDescription("Automation flow for create property wizard.");
    }

    public void fillValidStep2() {
        enterCountry("Afghanistan");
        enterCity("Kabul");
        enterAddress("Street 1");
    }

    public void uploadImagesFromProjectPath(String... relativePaths) {
        WebElement fileInput = driver.findElement(Locators.CreateProperty.STEP_5_UPLOAD_FILE_INPUT);
        ((JavascriptExecutor) driver).executeScript("arguments[0].style.display='block';", fileInput);
        String joinedAbsolutePaths = Arrays.stream(relativePaths)
                .map(path -> new File(System.getProperty("user.dir"), path).getAbsolutePath())
                .reduce((a, b) -> a + "\n" + b)
                .orElse("");
        fileInput.sendKeys(joinedAbsolutePaths);
    }

    public boolean hasAtLeastNImagePreviews(int minimumCount) {
        wait.until(d -> d.findElements(Locators.CreateProperty.STEP_5_IMAGE_PREVIEWS).size() >= minimumCount);
        return true;
    }

    public int getUploadedImagePreviewCount() {
        waitForElementVisible(Locators.CreateProperty.STEP_5_UPLOAD_DROPZONE);
        return driver.findElements(Locators.CreateProperty.STEP_5_IMAGE_PREVIEWS).size();
    }

    public boolean uploadedImagesShowSortHandleAndDelete() {
        waitForElementsPresent(Locators.CreateProperty.STEP_5_IMAGE_PREVIEWS);
        return !driver.findElements(Locators.CreateProperty.STEP_5_IMAGE_MOVE_UP_BUTTONS).isEmpty()
                && !driver.findElements(Locators.CreateProperty.STEP_5_IMAGE_MOVE_DOWN_BUTTONS).isEmpty()
                && !driver.findElements(Locators.CreateProperty.STEP_5_IMAGE_DELETE_BUTTONS).isEmpty();
    }

    private List<WebElement> getStep5PreviewItems() {
        waitForElementVisible(Locators.CreateProperty.STEP_5_UPLOAD_DROPZONE);
        return driver.findElements(Locators.CreateProperty.STEP_5_IMAGE_PREVIEWS);
    }

    private String previewSignature(WebElement previewItem) {
        try {
            WebElement img = previewItem.findElement(By.cssSelector("img"));
            String src = img.getAttribute("src");
            if (src != null && !src.trim().isEmpty()) {
                return src.trim();
            }
        } catch (NoSuchElementException ignored) {
        }
        String text = previewItem.getText();
        return text == null ? "" : text.trim();
    }

    public String getImagePreviewSignatureAt(int index) {
        List<WebElement> items = getStep5PreviewItems();
        if (index < 0 || index >= items.size()) {
            throw new IllegalArgumentException("Invalid preview index: " + index + " (count=" + items.size() + ")");
        }
        return previewSignature(items.get(index));
    }

    public void moveImageDownAt(int index) {
        List<WebElement> buttons = waitForElementsPresent(Locators.CreateProperty.STEP_5_IMAGE_MOVE_DOWN_BUTTONS);
        if (index < 0 || index >= buttons.size()) {
            throw new IllegalArgumentException("Invalid move-down index: " + index + " (count=" + buttons.size() + ")");
        }
        buttons.get(index).click();
    }

    public void moveImageUpAt(int index) {
        List<WebElement> buttons = waitForElementsPresent(Locators.CreateProperty.STEP_5_IMAGE_MOVE_UP_BUTTONS);
        if (index < 0 || index >= buttons.size()) {
            throw new IllegalArgumentException("Invalid move-up index: " + index + " (count=" + buttons.size() + ")");
        }
        buttons.get(index).click();
    }

    public void deleteImageAt(int index) {
        List<WebElement> buttons = waitForElementsPresent(Locators.CreateProperty.STEP_5_IMAGE_DELETE_BUTTONS);
        if (index < 0 || index >= buttons.size()) {
            throw new IllegalArgumentException("Invalid delete index: " + index + " (count=" + buttons.size() + ")");
        }
        buttons.get(index).click();
    }

    public void waitForFirstPreviewSignatureToBe(String expectedSignature) {
        wait.until(d -> {
            List<WebElement> items = d.findElements(Locators.CreateProperty.STEP_5_IMAGE_PREVIEWS);
            if (items.isEmpty()) {
                return expectedSignature == null || expectedSignature.isBlank();
            }
            return previewSignature(items.getFirst()).equals(expectedSignature);
        });
    }

    public void waitForPreviewCountToBe(int expectedCount) {
        wait.until(d -> d.findElements(Locators.CreateProperty.STEP_5_IMAGE_PREVIEWS).size() == expectedCount);
    }

    public boolean primaryBadgeIsOnPreviewIndex(int index) {
        List<WebElement> items = getStep5PreviewItems();
        if (index < 0 || index >= items.size()) {
            return false;
        }
        WebElement preview = items.get(index);
        String previewText = Optional.ofNullable(preview.getText()).orElse("").toLowerCase();
        if (previewText.contains("primary") || previewText.contains("cover")) {
            return true;
        }
        return !preview.findElements(
                By.xpath(".//*[contains(translate(normalize-space(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'primary')"
                        + " or contains(translate(normalize-space(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'cover')]")
        ).isEmpty();
    }

    public boolean firstUploadedImageIsMarkedPrimaryOrCover() {
        List<WebElement> previewItems = waitForElementsPresent(Locators.CreateProperty.STEP_5_IMAGE_PREVIEWS);
        if (previewItems.isEmpty()) {
            return false;
        }

        WebElement firstItem = previewItems.getFirst();
        String firstItemText = firstItem.getText().toLowerCase();
        if (firstItemText.contains("primary") || firstItemText.contains("cover")) {
            return true;
        }

        List<WebElement> badges = firstItem.findElements(
                By.xpath(".//*[contains(translate(normalize-space(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'primary')"
                        + " or contains(translate(normalize-space(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'cover')]")
        );
        return !badges.isEmpty();
    }

    public boolean hasMinimumImageRequiredValidationMessage() {
        return hasInlineErrorContaining("At least one image is required")
                || hasInlineErrorContaining("Minimum 1 image required");
    }

    public boolean isStep6PricingLoaded() {
        waitForElementVisible(Locators.CreateProperty.STEP_6_PRICING_TITLE);
        return isDisplayed(Locators.CreateProperty.STEP_6_PRICE_INPUT);
    }

    public boolean step6PriceInputUsesUsdLabel() {
        By priceUsdLabel = By.xpath(
                "//div[contains(@class,'create-property-field')][.//input[@type='number']]//label[contains(normalize-space(),'Price per Night') and (contains(normalize-space(),'$') or contains(normalize-space(),'USD'))]"
        );
        return isDisplayed(priceUsdLabel);
    }

    public void enterPricePerNight(String price) {
        type(Locators.CreateProperty.STEP_6_PRICE_INPUT, price);
    }

    public boolean hasPriceGreaterThanZeroValidationMessage() {
        return hasInlineErrorContaining("greater than 0")
                || hasInlineErrorContaining("price is required")
                || hasInlineErrorContaining("price must be");
    }

    public boolean isStep7ReviewLoaded() {
        waitForElementVisible(Locators.CreateProperty.STEP_7_REVIEW_TITLE);
        return !waitForElementsPresent(Locators.CreateProperty.STEP_7_REVIEW_SECTIONS).isEmpty()
                && isDisplayed(Locators.CreateProperty.STEP_7_CREATE_PROPERTY_BUTTON);
    }

    public boolean reviewContainsAllStep1ToStep6Sections() {
        waitForElementVisible(Locators.CreateProperty.STEP_7_REVIEW_TITLE);
        String page = driver.getPageSource().toLowerCase();
        return page.contains("type:")
                && page.contains("category:")
                && page.contains("title:")
                && page.contains("description:")
                && page.contains("location")
                && page.contains("guests")
                && page.contains("amenities")
                && page.contains("images")
                && page.contains("pricing");
    }

    public void clickCreateProperty() {
        waitForElementClickable(Locators.CreateProperty.STEP_7_CREATE_PROPERTY_BUTTON).click();
    }

    public boolean hasCreatePropertySuccessAlert() {
        try {
            Alert alert = wait.until(org.openqa.selenium.support.ui.ExpectedConditions.alertIsPresent());
            String text = alert.getText() == null ? "" : alert.getText().toLowerCase();
            alert.accept();
            return text.contains("property created successfully!");
        } catch (NoAlertPresentException | org.openqa.selenium.TimeoutException e) {
            return false;
        }
    }

    public boolean hasInlineErrorContaining(String expectedText) {
        String expectedLower = expectedText.toLowerCase();
        wait.until(d -> !d.findElements(Locators.CreateProperty.FIELD_ERRORS).isEmpty());
        return driver.findElements(Locators.CreateProperty.FIELD_ERRORS).stream()
                .anyMatch(el -> el.getText().toLowerCase().contains(expectedLower));
    }

    public boolean isStep1TitleValuePreserved(String expected) {
        return waitForElementVisible(Locators.CreateProperty.STEP_1_TITLE_INPUT).getAttribute("value").equals(expected);
    }

    public boolean isStep1DescriptionValuePreserved(String expected) {
        return waitForElementVisible(Locators.CreateProperty.STEP_1_DESCRIPTION_TEXTAREA).getAttribute("value").equals(expected);
    }

    public boolean isStep2CityValuePreserved(String expected) {
        return waitForElementVisible(Locators.CreateProperty.STEP_2_CITY_INPUT).getAttribute("value").equals(expected);
    }

    public boolean isStep2CountryValuePreserved(String expected) {
        return waitForElementVisible(Locators.CreateProperty.STEP_2_COUNTRY_INPUT).getAttribute("value").equals(expected);
    }

    public boolean maxGuestsMinIsOne() {
        return "1".equals(waitForElementVisible(Locators.CreateProperty.STEP_3_MAX_GUESTS_INPUT).getAttribute("min"));
    }

    public boolean bedroomsMinIsZero() {
        return "0".equals(waitForElementVisible(Locators.CreateProperty.STEP_3_BEDROOMS_INPUT).getAttribute("min"));
    }

    public boolean bedsMinIsOne() {
        return "1".equals(waitForElementVisible(Locators.CreateProperty.STEP_3_BEDS_INPUT).getAttribute("min"));
    }

    public boolean bathroomsMinIsZero() {
        return "0".equals(waitForElementVisible(Locators.CreateProperty.STEP_3_BATHROOMS_INPUT).getAttribute("min"));
    }

    public boolean bathroomsStepIsHalfIncrement() {
        return "0.5".equals(waitForElementVisible(Locators.CreateProperty.STEP_3_BATHROOMS_INPUT).getAttribute("step"));
    }

    public boolean hasAmenityGroupNamed(String groupName) {
        String expected = groupName.toLowerCase();
        List<WebElement> groups = driver.findElements(Locators.CreateProperty.STEP_4_GROUP_HEADERS);
        return groups.stream().anyMatch(el -> el.getText().trim().toLowerCase().contains(expected));
    }

    public void toggleAmenityByLabelContaining(String labelText) {
        waitForElementClickable(Locators.CreateProperty.amenityCheckbox(labelText)).click();
    }

    public boolean isAmenityCheckedByLabelContaining(String labelText) {
        return waitForElementVisible(Locators.CreateProperty.amenityCheckbox(labelText)).isSelected();
    }

    public boolean pageShows403Error() {
        try {
            wait.until(d -> d.getPageSource().contains("403"));
            return true;
        } catch (org.openqa.selenium.TimeoutException e) {
            return false;
        }
    }

    public long createPropertyStatusViaApi(String payloadJson) {
        log.debug("createPropertyStatusViaApi payload: {}", payloadJson);
        return apiRequest()
                .contentType(ContentType.JSON)
                .body(payloadJson)
                .post("/properties")
                .statusCode();
    }

    public String createPropertyViaApi(String payloadJson) {
        return apiRequest()
                .contentType(ContentType.JSON)
                .body(payloadJson)
                .post("/properties")
                .asString();
    }

    public String createPropertyErrorBodyViaApi(String payloadJson) {
        Response response = apiRequest()
                .contentType(ContentType.JSON)
                .body(payloadJson)
                .post("/properties");
        return response.asString();
    }
}
