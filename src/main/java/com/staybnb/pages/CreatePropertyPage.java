package com.staybnb.pages;

import com.staybnb.locators.Locators;
import com.staybnb.config.AppConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.Select;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class CreatePropertyPage extends BasePage {
    private static final Logger log = LogManager.getLogger(CreatePropertyPage.class);
    private static final String PAGE_URL = AppConstants.HOSTING_CREATE_URL;
    private static final String CREATE_PROPERTY_API_JS_RESOURCE = "com/staybnb/scripts/createPropertyApi.js";
    private static final String CREATE_PROPERTY_STATUS_API_JS_RESOURCE = "com/staybnb/scripts/createPropertyStatusApi.js";

    private final By container = Locators.CreateProperty.CONTAINER;
    private final By progressText = Locators.CreateProperty.PROGRESS_TEXT;
    private final By nextButton = Locators.CreateProperty.NEXT_BUTTON;
    private final By backButton = Locators.CreateProperty.BACK_BUTTON;
    private final By fieldErrors = Locators.CreateProperty.FIELD_ERRORS;

    private final By step1PropertyTypeSelect = Locators.CreateProperty.STEP_1_PROPERTY_TYPE_SELECT;
    private final By step1CategorySelect = Locators.CreateProperty.STEP_1_CATEGORY_SELECT;
    private final By step1TitleInput = Locators.CreateProperty.STEP_1_TITLE_INPUT;
    private final By step1DescriptionTextarea = Locators.CreateProperty.STEP_1_DESCRIPTION_TEXTAREA;

    private final By step2CountryInput = Locators.CreateProperty.STEP_2_COUNTRY_INPUT;
    private final By step2CityInput = Locators.CreateProperty.STEP_2_CITY_INPUT;
    private final By step2AddressInput = Locators.CreateProperty.STEP_2_ADDRESS_INPUT;

    private final By step3MaxGuestsInput = Locators.CreateProperty.STEP_3_MAX_GUESTS_INPUT;
    private final By step3BedroomsInput = Locators.CreateProperty.STEP_3_BEDROOMS_INPUT;
    private final By step3BedsInput = Locators.CreateProperty.STEP_3_BEDS_INPUT;
    private final By step3BathroomsInput = Locators.CreateProperty.STEP_3_BATHROOMS_INPUT;
    private final By step4AmenitiesTitle = Locators.CreateProperty.STEP_4_AMENITIES_TITLE;
    private final By step4AmenitiesGrid = Locators.CreateProperty.STEP_4_AMENITIES_GRID;
    private final By step4AmenityCheckboxes = Locators.CreateProperty.STEP_4_AMENITY_CHECKBOXES;
    private final By step4AmenityItems = Locators.CreateProperty.STEP_4_AMENITY_ITEMS;
    private final By step4GroupHeaders = Locators.CreateProperty.STEP_4_GROUP_HEADERS;
    private final By step5PhotosTitle = Locators.CreateProperty.STEP_5_PHOTOS_TITLE;
    private final By step5UploadDropzone = Locators.CreateProperty.STEP_5_UPLOAD_DROPZONE;
    private final By step5UploadFileInput = Locators.CreateProperty.STEP_5_UPLOAD_FILE_INPUT;
    private final By step5UploadText = Locators.CreateProperty.STEP_5_UPLOAD_TEXT;
    private final By step5ImagePreviews = Locators.CreateProperty.STEP_5_IMAGE_PREVIEWS;
    private final By step5ImageMoveUpButtons = Locators.CreateProperty.STEP_5_IMAGE_MOVE_UP_BUTTONS;
    private final By step5ImageMoveDownButtons = Locators.CreateProperty.STEP_5_IMAGE_MOVE_DOWN_BUTTONS;
    private final By step5ImageDeleteButtons = Locators.CreateProperty.STEP_5_IMAGE_DELETE_BUTTONS;
    private final By step6PricingTitle = Locators.CreateProperty.STEP_6_PRICING_TITLE;
    private final By step6PriceInput = Locators.CreateProperty.STEP_6_PRICE_INPUT;
    private final By step7ReviewTitle = Locators.CreateProperty.STEP_7_REVIEW_TITLE;
    private final By step7ReviewSections = Locators.CreateProperty.STEP_7_REVIEW_SECTIONS;
    private final By step7CreatePropertyButton = Locators.CreateProperty.STEP_7_CREATE_PROPERTY_BUTTON;
    private final By step7SuccessMessage = Locators.CreateProperty.STEP_7_SUCCESS_MESSAGE;

    public CreatePropertyPage(WebDriver driver) {
        super(driver);
    }

    public void navigateTo() {
        super.navigateTo(PAGE_URL);
        waitForElementVisible(container);
    }

    public boolean isStep1BasicsLoaded() {
        waitForElementVisible(step1PropertyTypeSelect);
        return isDisplayed(step1CategorySelect)
                && isDisplayed(step1TitleInput)
                && isDisplayed(step1DescriptionTextarea);
    }

    public boolean isStep2LocationLoaded() {
        waitForElementVisible(step2CountryInput);
        return isDisplayed(step2CityInput)
                && isDisplayed(step2AddressInput);
    }

    public boolean isStep3DetailsLoaded() {
        waitForElementVisible(step3MaxGuestsInput);
        return isDisplayed(step3BedroomsInput)
                && isDisplayed(step3BedsInput)
                && isDisplayed(step3BathroomsInput);
    }

    public boolean isStep4AmenitiesLoaded() {
        waitForElementVisible(step4AmenitiesTitle);
        return isDisplayed(step4AmenitiesGrid)
                && !waitForElementsPresent(step4AmenityCheckboxes).isEmpty();
    }

    public boolean isStep5PhotosLoaded() {
        waitForElementVisible(step5PhotosTitle);
        return true;
    }

    public boolean step5HasUploadAreaSupportingDropOrBrowse() {
        String dropzoneText = waitForElementVisible(step5UploadDropzone).getText().toLowerCase();
        String uploadText = waitForElementVisible(step5UploadText).getText().toLowerCase();

        WebElement input = waitForElementsPresent(step5UploadFileInput).get(0);
        String accepts = input.getAttribute("accept");

        return accepts != null
                && accepts.contains("image/")
                && (dropzoneText.contains("drag") || uploadText.contains("drag"))
                && (dropzoneText.contains("browse") || uploadText.contains("browse"));
    }

    public String getProgressText() {
        return waitForElementVisible(progressText).getText().trim();
    }

    public int getCurrentStep() {
        String text = getProgressText(); // e.g. "Step 3 of 7"
        try {
            return Integer.parseInt(text.split("\\s+")[1]);
        } catch (Exception e) {
            return -1;
        }
    }

    public void clickNext() {
        int currentStep = getCurrentStep();
        waitForElementClickable(nextButton).click();
        wait.until(d -> getCurrentStep() > currentStep || !d.findElements(fieldErrors).isEmpty());
    }

    public void clickBack() {
        int currentStep = getCurrentStep();
        waitForElementClickable(backButton).click();
        wait.until(d -> getCurrentStep() < currentStep);
    }

    public void selectPropertyTypeByVisibleText(String text) {
        new Select(waitForElementVisible(step1PropertyTypeSelect)).selectByVisibleText(text);
    }

    public void selectCategoryByIndex(int index) {
        wait.until(d -> new Select(waitForElementVisible(step1CategorySelect)).getOptions().size() > index);
        new Select(driver.findElement(step1CategorySelect)).selectByIndex(index);
    }

    public int getCategoryDropdownOptionCount() {
        wait.until(d -> new Select(waitForElementVisible(step1CategorySelect)).getOptions().size() > 1);
        return new Select(driver.findElement(step1CategorySelect)).getOptions().size();
    }

    public void enterTitle(String title) {
        type(step1TitleInput, title);
    }

    public void clearTitle() {
        type(step1TitleInput, "");
    }

    public void enterDescription(String description) {
        type(step1DescriptionTextarea, description);
    }

    public void clearDescription() {
        type(step1DescriptionTextarea, "");
    }

    public void clearCountry() {
        type(step2CountryInput, "");
    }

    public void enterCountry(String country) {
        type(step2CountryInput, country);
    }

    public void clearCity() {
        type(step2CityInput, "");
    }

    public void clearStep2RequiredField(String fieldName) {
        switch (fieldName) {
            case "country" -> clearCountry();
            case "city" -> clearCity();
            default -> throw new IllegalArgumentException("Unsupported step 2 required field: " + fieldName);
        }
    }

    public void enterCity(String city) {
        type(step2CityInput, city);
    }

    public void enterAddress(String address) {
        type(step2AddressInput, address);
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
        WebElement fileInput = driver.findElement(step5UploadFileInput);
        ((JavascriptExecutor) driver).executeScript("arguments[0].style.display='block';", fileInput);
        String joinedAbsolutePaths = Arrays.stream(relativePaths)
                .map(path -> new File(System.getProperty("user.dir"), path).getAbsolutePath())
                .reduce((a, b) -> a + "\n" + b)
                .orElse("");
        fileInput.sendKeys(joinedAbsolutePaths);
    }

    public boolean hasAtLeastNImagePreviews(int minimumCount) {
        wait.until(d -> d.findElements(step5ImagePreviews).size() >= minimumCount);
        return true;
    }

    public int getUploadedImagePreviewCount() {
        waitForElementVisible(step5UploadDropzone);
        return driver.findElements(step5ImagePreviews).size();
    }

    public boolean uploadedImagesShowSortHandleAndDelete() {
        waitForElementsPresent(step5ImagePreviews);
        return !driver.findElements(step5ImageMoveUpButtons).isEmpty()
                && !driver.findElements(step5ImageMoveDownButtons).isEmpty()
                && !driver.findElements(step5ImageDeleteButtons).isEmpty();
    }

    private List<WebElement> getStep5PreviewItems() {
        waitForElementVisible(step5UploadDropzone);
        return driver.findElements(step5ImagePreviews);
    }

    private String previewSignature(WebElement previewItem) {
        try {
            WebElement img = previewItem.findElement(By.cssSelector("img"));
            String src = img.getAttribute("src");
            if (src != null && !src.trim().isEmpty()) {
                return src.trim();
            }
        } catch (Exception ignored) {
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
        List<WebElement> buttons = waitForElementsPresent(step5ImageMoveDownButtons);
        if (index < 0 || index >= buttons.size()) {
            throw new IllegalArgumentException("Invalid move-down index: " + index + " (count=" + buttons.size() + ")");
        }
        buttons.get(index).click();
    }

    public void moveImageUpAt(int index) {
        List<WebElement> buttons = waitForElementsPresent(step5ImageMoveUpButtons);
        if (index < 0 || index >= buttons.size()) {
            throw new IllegalArgumentException("Invalid move-up index: " + index + " (count=" + buttons.size() + ")");
        }
        buttons.get(index).click();
    }

    public void deleteImageAt(int index) {
        List<WebElement> buttons = waitForElementsPresent(step5ImageDeleteButtons);
        if (index < 0 || index >= buttons.size()) {
            throw new IllegalArgumentException("Invalid delete index: " + index + " (count=" + buttons.size() + ")");
        }
        buttons.get(index).click();
    }

    public void waitForFirstPreviewSignatureToBe(String expectedSignature) {
        wait.until(d -> {
            List<WebElement> items = d.findElements(step5ImagePreviews);
            if (items.isEmpty()) {
                return expectedSignature == null || expectedSignature.isBlank();
            }
            return previewSignature(items.get(0)).equals(expectedSignature);
        });
    }

    public void waitForPreviewCountToBe(int expectedCount) {
        wait.until(d -> d.findElements(step5ImagePreviews).size() == expectedCount);
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
        List<WebElement> previewItems = waitForElementsPresent(step5ImagePreviews);
        if (previewItems.isEmpty()) {
            return false;
        }

        WebElement firstItem = previewItems.get(0);
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
        waitForElementVisible(step6PricingTitle);
        return isDisplayed(step6PriceInput);
    }

    public boolean step6PriceInputUsesUsdLabel() {
        By priceUsdLabel = By.xpath(
                "//div[contains(@class,'create-property-field')][.//input[@type='number']]//label[contains(normalize-space(),'Price per Night') and (contains(normalize-space(),'$') or contains(normalize-space(),'USD'))]"
        );
        return isDisplayed(priceUsdLabel);
    }

    public void enterPricePerNight(String price) {
        type(step6PriceInput, price);
    }

    public boolean hasPriceGreaterThanZeroValidationMessage() {
        return hasInlineErrorContaining("greater than 0")
                || hasInlineErrorContaining("price is required")
                || hasInlineErrorContaining("price must be");
    }

    public boolean isStep7ReviewLoaded() {
        waitForElementVisible(step7ReviewTitle);
        return !waitForElementsPresent(step7ReviewSections).isEmpty()
                && isDisplayed(step7CreatePropertyButton);
    }

    public boolean reviewContainsAllStep1ToStep6Sections() {
        waitForElementVisible(step7ReviewTitle);
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

    public boolean reviewShowsDraftNote() {
        return driver.getPageSource().toLowerCase().contains("draft");
    }

    public void clickCreateProperty() {
        waitForElementClickable(step7CreatePropertyButton).click();
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
        wait.until(d -> !d.findElements(fieldErrors).isEmpty());
        return driver.findElements(fieldErrors).stream()
                .anyMatch(el -> el.getText().toLowerCase().contains(expectedLower));
    }

    public boolean isStep1TitleValuePreserved(String expected) {
        return waitForElementVisible(step1TitleInput).getAttribute("value").equals(expected);
    }

    public boolean isStep1DescriptionValuePreserved(String expected) {
        return waitForElementVisible(step1DescriptionTextarea).getAttribute("value").equals(expected);
    }

    public boolean isStep2CityValuePreserved(String expected) {
        return waitForElementVisible(step2CityInput).getAttribute("value").equals(expected);
    }

    public boolean isStep2CountryValuePreserved(String expected) {
        return waitForElementVisible(step2CountryInput).getAttribute("value").equals(expected);
    }

    public boolean maxGuestsMinIsOne() {
        return "1".equals(waitForElementVisible(step3MaxGuestsInput).getAttribute("min"));
    }

    public boolean bedroomsMinIsZero() {
        return "0".equals(waitForElementVisible(step3BedroomsInput).getAttribute("min"));
    }

    public boolean bedsMinIsOne() {
        return "1".equals(waitForElementVisible(step3BedsInput).getAttribute("min"));
    }

    public boolean bathroomsMinIsZero() {
        return "0".equals(waitForElementVisible(step3BathroomsInput).getAttribute("min"));
    }

    public boolean bathroomsStepIsHalfIncrement() {
        return "0.5".equals(waitForElementVisible(step3BathroomsInput).getAttribute("step"));
    }

    public boolean hasAmenityGroupNamed(String groupName) {
        String expected = groupName.toLowerCase();
        List<WebElement> groups = driver.findElements(step4GroupHeaders);
        return groups.stream().anyMatch(el -> el.getText().trim().toLowerCase().contains(expected));
    }

    public boolean hasAmenityItemContaining(String text) {
        String expected = text.toLowerCase();
        List<WebElement> items = driver.findElements(step4AmenityItems);
        return items.stream().anyMatch(el -> el.getText().toLowerCase().contains(expected));
    }

    public void toggleAmenityByLabelContaining(String labelText) {
        String normalized = labelText.toLowerCase();
        By amenityCheckbox = By.xpath(
                "//label[contains(@class,'create-property-amenity-item')][contains(translate(normalize-space(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'"
                        + normalized + "')]//input[@type='checkbox']"
        );
        waitForElementClickable(amenityCheckbox).click();
    }

    public boolean isAmenityCheckedByLabelContaining(String labelText) {
        String normalized = labelText.toLowerCase();
        By amenityCheckbox = By.xpath(
                "//label[contains(@class,'create-property-amenity-item')][contains(translate(normalize-space(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'"
                        + normalized + "')]//input[@type='checkbox']"
        );
        return waitForElementVisible(amenityCheckbox).isSelected();
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

//        driver.get(AppConstants.HOME_URL);

        JavascriptExecutor js = (JavascriptExecutor) driver;
        String script = loadJavascriptResource(CREATE_PROPERTY_STATUS_API_JS_RESOURCE);
        Object responseStatus = js.executeAsyncScript(script, AppConstants.SLUG, payloadJsonToObject(js, payloadJson));
        if (responseStatus instanceof Number n) {
            return n.longValue();
        }
        throw new RuntimeException("Unexpected create-property status response type: " +
                (responseStatus == null ? "null" : responseStatus.getClass().getName()));
    }

    public String createPropertyViaApi(String payloadJson) {
        driver.get(AppConstants.HOME_URL);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String script = loadJavascriptResource(CREATE_PROPERTY_API_JS_RESOURCE);
        Object response = js.executeAsyncScript(script, AppConstants.SLUG, payloadJsonToObject(js, payloadJson));
        return (String) response;
    }

    public String createPropertyErrorBodyViaApi(String payloadJson) {
        String raw = createPropertyViaApi(payloadJson);
        if (raw == null) {
            return null;
        }
        try {
            Object parsed = ((JavascriptExecutor) driver).executeScript("return JSON.parse(arguments[0]);", raw);
            if (parsed instanceof java.util.Map<?, ?> map) {
                Object body = map.get("body");
                return body == null ? null : body.toString();
            }
            return raw;
        } catch (Exception e) {
            return raw;
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
