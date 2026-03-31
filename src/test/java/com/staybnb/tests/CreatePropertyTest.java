package com.staybnb.tests;

import com.staybnb.pages.CreatePropertyPage;
import com.staybnb.pages.LoginPage;
import com.staybnb.utils.ErrorMessages;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreatePropertyTest extends BaseTest {
    private LoginPage loginPage;
    private CreatePropertyPage createPropertyPage;

    @BeforeEach
    public void setup() {
        loginPage = new LoginPage(driver);
        createPropertyPage = new CreatePropertyPage(driver);
    }

    private void loginAsExistingHostAndLoadCreatePage() {
        loginAsTestUserAndLandOnHome(loginPage);
        createPropertyPage.load();
    }

    private void goToStep2WithValidStep1() {
        createPropertyPage.fillValidStep1();
        createPropertyPage.clickNext();
    }

    private void goToStep3WithValidStep1AndStep2() {
        goToStep2WithValidStep1();
        createPropertyPage.fillValidStep2();
        createPropertyPage.clickNext();
    }

    private void goToStep4WithValidStep1ToStep3() {
        goToStep3WithValidStep1AndStep2();
        createPropertyPage.clickNext();
    }

    private void goToStep5WithValidStep1ToStep4() {
        goToStep4WithValidStep1ToStep3();
        createPropertyPage.clickNext();
    }

    @Test
    public void testStep1ShowsBasicsFields() {
        loginAsExistingHostAndLoadCreatePage();
        assertTrue(
                createPropertyPage.isStep1BasicsLoaded(), ErrorMessages.CREATE_PROPERTY_STEP1_SHOULD_DISPLAY_BASICS_FIELDS
        );
    }

    @Test
    public void testStep1ShowsValidationForMissingTitle() {
        loginAsExistingHostAndLoadCreatePage();
        createPropertyPage.clearTitle();
        createPropertyPage.clickNext();
        assertTrue(
                createPropertyPage.hasInlineErrorContaining("title"),
                ErrorMessages.CREATE_PROPERTY_STEP1_SHOULD_REQUIRE_TITLE
        );
    }

    @Test
    public void testStep1ShowsValidationForMissingDescription() {
        loginAsExistingHostAndLoadCreatePage();
        createPropertyPage.clearDescription();
        createPropertyPage.clickNext();
        assertTrue(
                createPropertyPage.hasInlineErrorContaining("description"),
                ErrorMessages.CREATE_PROPERTY_STEP1_SHOULD_REQUIRE_DESCRIPTION
        );
    }

    @Test
    public void testStep2ShowsLocationFieldsAfterCompletingStep1() {
        loginAsExistingHostAndLoadCreatePage();
        goToStep2WithValidStep1();
        assertTrue(
                createPropertyPage.isStep2LocationLoaded(),
                ErrorMessages.CREATE_PROPERTY_STEP2_SHOULD_DISPLAY_LOCATION_FIELDS
        );
    }

    @Test
    public void testStep2ShowsValidationForMissingCountry() {
        loginAsExistingHostAndLoadCreatePage();
        goToStep2WithValidStep1();
        createPropertyPage.clearCountry();
        createPropertyPage.clickNext();
        assertTrue(
                createPropertyPage.hasInlineErrorContaining("country"),
                ErrorMessages.CREATE_PROPERTY_STEP2_SHOULD_REQUIRE_COUNTRY
        );
    }

    @Test
    public void testStep2ShowsValidationForMissingCity() {
        loginAsExistingHostAndLoadCreatePage();
        goToStep2WithValidStep1();
        createPropertyPage.clearCity();
        createPropertyPage.clickNext();
        assertTrue(
                createPropertyPage.hasInlineErrorContaining("city"),
                ErrorMessages.CREATE_PROPERTY_STEP2_SHOULD_REQUIRE_CITY
        );
    }

    @Test
    public void testStep3ShowsDetailsFieldsAfterCompletingStep2() {
        loginAsExistingHostAndLoadCreatePage();
        goToStep3WithValidStep1AndStep2();
        assertTrue(
                createPropertyPage.isStep3DetailsLoaded(),
                ErrorMessages.CREATE_PROPERTY_STEP3_SHOULD_DISPLAY_DETAILS_FIELDS
        );
    }

    @Test
    public void testStep3MaxGuestsMinimumIsOne() {
        loginAsExistingHostAndLoadCreatePage();
        goToStep3WithValidStep1AndStep2();
        assertTrue(
                createPropertyPage.maxGuestsMinIsOne(),
                ErrorMessages.CREATE_PROPERTY_MAX_GUESTS_MIN_SHOULD_BE_1
        );
    }

    @Test
    public void testStep3BedroomsMinimumIsZero() {
        loginAsExistingHostAndLoadCreatePage();
        goToStep3WithValidStep1AndStep2();
        assertTrue(
                createPropertyPage.bedroomsMinIsZero(),
                ErrorMessages.CREATE_PROPERTY_BEDROOMS_MIN_SHOULD_BE_0
        );
    }

    @Test
    public void testStep3BedsMinimumIsOne() {
        loginAsExistingHostAndLoadCreatePage();
        goToStep3WithValidStep1AndStep2();
        assertTrue(
                createPropertyPage.bedsMinIsOne(),
                ErrorMessages.CREATE_PROPERTY_BEDS_MIN_SHOULD_BE_1
        );
    }

    @Test
    public void testStep3BathroomsMinimumIsZero() {
        loginAsExistingHostAndLoadCreatePage();
        goToStep3WithValidStep1AndStep2();
        assertTrue(
                createPropertyPage.bathroomsMinIsZero(),
                ErrorMessages.CREATE_PROPERTY_BATHROOMS_MIN_SHOULD_BE_0
        );
    }

    @Test
    public void testStep3BathroomsAllowsHalfIncrements() {
        loginAsExistingHostAndLoadCreatePage();
        goToStep3WithValidStep1AndStep2();
        assertTrue(
                createPropertyPage.bathroomsStepIsHalfIncrement(),
                ErrorMessages.CREATE_PROPERTY_BATHROOMS_STEP_SHOULD_BE_HALF
        );
    }

    @Test
    public void testBackFromStep2PreservesStep1Title() {
        loginAsExistingHostAndLoadCreatePage();
        createPropertyPage.fillValidStep1();
        createPropertyPage.clickNext();
        createPropertyPage.clickBack();
        assertTrue(
                createPropertyPage.isStep1TitleValuePreserved("Automation Listing"),
                ErrorMessages.CREATE_PROPERTY_BACK_SHOULD_PRESERVE_STEP1_TITLE
        );
    }

    @Test
    public void testBackFromStep2PreservesStep1Description() {
        loginAsExistingHostAndLoadCreatePage();
        createPropertyPage.fillValidStep1();
        createPropertyPage.clickNext();
        createPropertyPage.clickBack();
        assertTrue(
                createPropertyPage.isStep1DescriptionValuePreserved("Automation flow for create property wizard."),
                ErrorMessages.CREATE_PROPERTY_BACK_SHOULD_PRESERVE_STEP1_DESCRIPTION
        );
    }

    @Test
    public void testBackFromStep3PreservesStep2Country() {
        loginAsExistingHostAndLoadCreatePage();
        goToStep3WithValidStep1AndStep2();
        createPropertyPage.clickBack();
        assertTrue(
                createPropertyPage.isStep2CountryValuePreserved("Afghanistan"),
                ErrorMessages.CREATE_PROPERTY_BACK_SHOULD_PRESERVE_STEP2_COUNTRY
        );
    }

    @Test
    public void testBackFromStep3PreservesStep2City() {
        loginAsExistingHostAndLoadCreatePage();
        goToStep3WithValidStep1AndStep2();
        createPropertyPage.clickBack();
        assertTrue(
                createPropertyPage.isStep2CityValuePreserved("Kabul"),
                ErrorMessages.CREATE_PROPERTY_BACK_SHOULD_PRESERVE_STEP2_CITY
        );
    }

    @Test
    public void testProgressIndicatorShowsStep1Of7() {
        loginAsExistingHostAndLoadCreatePage();
        assertEquals(
                "Step 1 of 7", createPropertyPage.getProgressText(),
                ErrorMessages.CREATE_PROPERTY_PROGRESS_SHOULD_SHOW_STEP_1_OF_7
        );
    }

    @Test
    public void testNonHostAccessToCreatePropertyIsBlockedWith403() {
        registerNewUserAndLandOnHome("testcreate");
        createPropertyPage.load();
        assertTrue(
                createPropertyPage.pageShows403Error(),
                ErrorMessages.CREATE_PROPERTY_SHOULD_BLOCK_NON_HOST_WITH_403
        );
    }

    /* STEP 4 */
    @Test
    public void testStep4ShowsAmenitiesGridAfterCompletingStep3() {
        loginAsExistingHostAndLoadCreatePage();
        goToStep4WithValidStep1ToStep3();
        assertTrue(
                createPropertyPage.isStep4AmenitiesLoaded(),
                ErrorMessages.CREATE_PROPERTY_STEP4_SHOULD_DISPLAY_AMENITIES_GRID
        );
    }

    @Test
    public void testStep4AmenitiesGroupedByEssentials() {
        loginAsExistingHostAndLoadCreatePage();
        goToStep4WithValidStep1ToStep3();
        assertTrue(
                createPropertyPage.hasAmenityGroupNamed("Essentials"),
                ErrorMessages.CREATE_PROPERTY_STEP4_SHOULD_GROUP_AMENITIES_BY_ESSENTIALS
        );
    }

    @Test
    public void testStep4AmenitiesGroupedByFeatures() {
        loginAsExistingHostAndLoadCreatePage();
        goToStep4WithValidStep1ToStep3();
        assertTrue(
                createPropertyPage.hasAmenityGroupNamed("Features"),
                ErrorMessages.CREATE_PROPERTY_STEP4_SHOULD_GROUP_AMENITIES_BY_FEATURES
        );
    }

    @Test
    public void testStep4AmenitiesGroupedBySafety() {
        loginAsExistingHostAndLoadCreatePage();
        goToStep4WithValidStep1ToStep3();
        assertTrue(
                createPropertyPage.hasAmenityGroupNamed("Safety"),
                ErrorMessages.CREATE_PROPERTY_STEP4_SHOULD_GROUP_AMENITIES_BY_SAFETY
        );
    }

    @Test
    public void testStep4SelectedAmenitiesAllowAdvancingToStep5() {
        loginAsExistingHostAndLoadCreatePage();
        goToStep4WithValidStep1ToStep3();
        createPropertyPage.toggleAmenityByLabelContaining("WiFi");
        createPropertyPage.toggleAmenityByLabelContaining("TV");
        createPropertyPage.clickNext();
        assertTrue(
                createPropertyPage.isStep5PhotosLoaded(),
                ErrorMessages.CREATE_PROPERTY_STEP4_NEXT_SHOULD_ADVANCE_TO_STEP5_WITH_SELECTED_AMENITIES
        );
    }

    @Test
    public void testStep4AllowsProceedingWithoutAmenitiesSelected() {
        loginAsExistingHostAndLoadCreatePage();
        goToStep4WithValidStep1ToStep3();
        createPropertyPage.clickNext();
        assertTrue(
                createPropertyPage.isStep5PhotosLoaded(),
                ErrorMessages.CREATE_PROPERTY_STEP4_NEXT_SHOULD_ALLOW_EMPTY_AMENITIES
        );
    }

    @Test
    public void testStep4BackToStep3AndReturnPreservesAmenitySelection() {
        loginAsExistingHostAndLoadCreatePage();
        goToStep4WithValidStep1ToStep3();
        createPropertyPage.toggleAmenityByLabelContaining("WiFi");
        createPropertyPage.clickBack();
        createPropertyPage.clickNext();
        assertTrue(
                createPropertyPage.isAmenityCheckedByLabelContaining("WiFi"),
                ErrorMessages.CREATE_PROPERTY_STEP4_BACK_AND_RETURN_SHOULD_PRESERVE_AMENITIES
        );
    }

    /* STEP 5 */
    @Test
    public void testStep5ShowsImageUploadAreaWithDragDropOrBrowse() {
        loginAsExistingHostAndLoadCreatePage();
        goToStep5WithValidStep1ToStep4();
        assertTrue(
                createPropertyPage.step5HasUploadAreaSupportingDropOrBrowse(),
                ErrorMessages.CREATE_PROPERTY_STEP5_SHOULD_DISPLAY_UPLOAD_AREA_WITH_DRAG_DROP_AND_BROWSE
        );
    }

    @Test
    public void testStep5UploadingImagesShowsPreviewThumbnails() {
        loginAsExistingHostAndLoadCreatePage();
        goToStep5WithValidStep1ToStep4();
        createPropertyPage.uploadImagesFromProjectPath(
                "media/apts/Brasilia-01.jpeg",
                "media/apts/Brasilia-02.jpg"
        );
        assertTrue(
                createPropertyPage.hasAtLeastNImagePreviews(2),
                ErrorMessages.CREATE_PROPERTY_STEP5_UPLOAD_SHOULD_DISPLAY_PREVIEW_THUMBNAILS
        );
    }

    @Test
    public void testStep5UploadedImagesShowSortHandleAndDeleteButton() {
        loginAsExistingHostAndLoadCreatePage();
        goToStep5WithValidStep1ToStep4();
        createPropertyPage.uploadImagesFromProjectPath("media/apts/Brasilia-01.jpeg");
        assertTrue(
                createPropertyPage.uploadedImagesShowSortHandleAndDelete(),
                ErrorMessages.CREATE_PROPERTY_STEP5_UPLOADED_IMAGE_SHOULD_SHOW_SORT_HANDLE_AND_DELETE
        );
    }

    @Test
    public void testStep5FirstUploadedImageIsMarkedAsPrimaryCover() {
        loginAsExistingHostAndLoadCreatePage();
        goToStep5WithValidStep1ToStep4();
        createPropertyPage.uploadImagesFromProjectPath(
                "media/apts/Brasilia-01.jpeg",
                "media/apts/Brasilia-02.jpg"
        );
        assertTrue(
                createPropertyPage.firstUploadedImageIsMarkedPrimaryOrCover(),
                ErrorMessages.CREATE_PROPERTY_STEP5_FIRST_IMAGE_SHOULD_BE_MARKED_PRIMARY_OR_COVER
        );
    }

    @Test
    public void testStep5CannotAdvanceWithoutAtLeastOneImage() {
        loginAsExistingHostAndLoadCreatePage();
        goToStep5WithValidStep1ToStep4();
        createPropertyPage.clickNext();
        assertTrue(
                createPropertyPage.hasMinimumImageRequiredValidationMessage(),
                ErrorMessages.CREATE_PROPERTY_STEP5_NEXT_SHOULD_REQUIRE_MINIMUM_ONE_IMAGE
        );
    }

    @Test
    public void testStep5BackToStep4AndReturnPreservesUploadedImages() {
        loginAsExistingHostAndLoadCreatePage();
        goToStep5WithValidStep1ToStep4();
        createPropertyPage.uploadImagesFromProjectPath(
                "media/apts/Brasilia-01.jpeg",
                "media/apts/Brasilia-02.jpg"
        );
        createPropertyPage.clickBack();
        createPropertyPage.clickNext();
        assertTrue(
                createPropertyPage.hasAtLeastNImagePreviews(2),
                ErrorMessages.CREATE_PROPERTY_STEP5_BACK_AND_RETURN_SHOULD_PRESERVE_UPLOADED_IMAGES
        );
    }
}


/* STEP 6 */