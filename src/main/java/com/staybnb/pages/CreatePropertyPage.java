package com.staybnb.pages;

import com.staybnb.locators.Locators;
import com.staybnb.utils.Constants;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class CreatePropertyPage extends BasePage {
    private static final String PAGE_URL = Constants.HOSTING_CREATE_URL;

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

    public CreatePropertyPage(WebDriver driver) {
        super(driver);
    }

    public void load() {
        navigateTo(PAGE_URL);
        waitForElementVisible(container);
    }

    public boolean isStep1BasicsLoaded() {
        return isDisplayed(step1PropertyTypeSelect)
                && isDisplayed(step1CategorySelect)
                && isDisplayed(step1TitleInput)
                && isDisplayed(step1DescriptionTextarea);
    }

    public boolean isStep2LocationLoaded() {
        return isDisplayed(step2CountryInput)
                && isDisplayed(step2CityInput)
                && isDisplayed(step2AddressInput);
    }

    public boolean isStep3DetailsLoaded() {
        return isDisplayed(step3MaxGuestsInput)
                && isDisplayed(step3BedroomsInput)
                && isDisplayed(step3BedsInput)
                && isDisplayed(step3BathroomsInput);
    }

    public boolean isStep4AmenitiesLoaded() {
        return isDisplayed(step4AmenitiesTitle)
                && isDisplayed(step4AmenitiesGrid)
                && !driver.findElements(step4AmenityCheckboxes).isEmpty();
    }

    public boolean isStep5PhotosLoaded() {
        return isDisplayed(step5PhotosTitle);
    }

    public boolean step5HasUploadAreaSupportingDropOrBrowse() {
        String dropzoneText = waitForElementVisible(step5UploadDropzone).getText().toLowerCase();
        String uploadText = waitForElementVisible(step5UploadText).getText().toLowerCase();

        WebElement input = driver.findElement(step5UploadFileInput);
        String accepts = input.getAttribute("accept");

        return accepts != null
                && accepts.contains("image/")
                && (dropzoneText.contains("drag") || uploadText.contains("drag"))
                && (dropzoneText.contains("browse") || uploadText.contains("browse"));
    }

    public String getProgressText() {
        return waitForElementVisible(progressText).getText().trim();
    }

    public void clickNext() {
        waitForElementClickable(nextButton).click();
    }

    public void clickBack() {
        waitForElementClickable(backButton).click();
    }

    public void selectPropertyTypeByVisibleText(String text) {
        new Select(waitForElementVisible(step1PropertyTypeSelect)).selectByVisibleText(text);
    }

    public void selectCategoryByIndex(int index) {
        new Select(waitForElementVisible(step1CategorySelect)).selectByIndex(index);
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
        waitForElementVisible(step5UploadDropzone);
        return driver.findElements(step5ImagePreviews).size() >= minimumCount;
    }

    public boolean uploadedImagesShowSortHandleAndDelete() {
        return !driver.findElements(step5ImageMoveUpButtons).isEmpty()
                && !driver.findElements(step5ImageMoveDownButtons).isEmpty()
                && !driver.findElements(step5ImageDeleteButtons).isEmpty();
    }

    public boolean firstUploadedImageIsMarkedPrimaryOrCover() {
        List<WebElement> previewItems = driver.findElements(step5ImagePreviews);
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

    public boolean hasInlineErrorContaining(String expectedText) {
        List<WebElement> errors = driver.findElements(fieldErrors);
        String expectedLower = expectedText.toLowerCase();
        return errors.stream().anyMatch(el -> el.getText().toLowerCase().contains(expectedLower));
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
        return driver.getPageSource().contains("403");
    }
}
