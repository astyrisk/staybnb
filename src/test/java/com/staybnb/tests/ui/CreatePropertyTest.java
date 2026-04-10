package com.staybnb.tests.ui;

import com.staybnb.pages.CreatePropertyPage;
import com.staybnb.pages.LoginPage;
import com.staybnb.assertions.ErrorMessages;
import com.staybnb.data.MediaPaths;
import com.staybnb.tests.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@Epic("Property Management")
@Feature("Create Property")
@Tag("regression")
@Execution(ExecutionMode.SAME_THREAD)
public class CreatePropertyTest extends BaseTest {
    private CreatePropertyPage createPropertyPage;

    @BeforeEach
    public void setup() {
        LoginPage loginPage = new LoginPage(driver);
        createPropertyPage = new CreatePropertyPage(driver);
        loginAsTestUserAndLandOnHome(loginPage);
    }

    private void loadCreatePage() {
        createPropertyPage.navigateTo();
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

    private void goToStep3() {
        loadCreatePage();
        goToStep3WithValidStep1AndStep2();
    }

    private void goToStep4() {
        loadCreatePage();
        goToStep4WithValidStep1ToStep3();
    }

    private void goToStep2AndBack() {
        createPropertyPage.fillValidStep1();
        createPropertyPage.clickNext();
        createPropertyPage.clickBack();
    }

    private void goToStep3ThenBack() {
        goToStep3();
        createPropertyPage.clickBack();
    }

    private void goToStep5WithValidStep1ToStep4() {
        goToStep4WithValidStep1ToStep3();
        createPropertyPage.clickNext();
    }

    private void goToStep6WithValidStep1ToStep5() {
        goToStep5WithValidStep1ToStep4();
        createPropertyPage.uploadImagesFromProjectPath(
                MediaPaths.BRASILIA_APT_IMAGE_01,
                MediaPaths.BRASILIA_APT_IMAGE_02
        );
        createPropertyPage.hasAtLeastNImagePreviews(2);
        createPropertyPage.clickNext();
    }

    /* STEP 1 */

    @Test
    @DisplayName("Step 1 displays the Basic Information fields")
    public void testStep1ShowsBasicsFields() {
        loadCreatePage();

        assertTrue(
                createPropertyPage.isStep1BasicsLoaded(),
                ErrorMessages.CREATE_PROPERTY_STEP1_SHOULD_DISPLAY_BASICS_FIELDS
        );
    }

    @Test
    @DisplayName("Step 1 shows validation error when title is missing")
    public void testStep1ShowsValidationForMissingTitle() {
        loadCreatePage();
        createPropertyPage.clearTitle();
        createPropertyPage.clickNext();

        assertTrue(
                createPropertyPage.hasInlineErrorContaining("title"),
                ErrorMessages.CREATE_PROPERTY_STEP1_SHOULD_REQUIRE_TITLE
        );
    }

    @Test
    @DisplayName("Step 1 shows validation error when description is missing")
    public void testStep1ShowsValidationForMissingDescription() {
        loadCreatePage();
        createPropertyPage.clearDescription();
        createPropertyPage.clickNext();

        assertTrue(
                createPropertyPage.hasInlineErrorContaining("description"),
                ErrorMessages.CREATE_PROPERTY_STEP1_SHOULD_REQUIRE_DESCRIPTION
        );
    }

    /* STEP 2 */

    @Test
    @DisplayName("Step 2 displays Location fields after completing Step 1")
    public void testStep2ShowsLocationFieldsAfterCompletingStep1() {
        loadCreatePage();
        goToStep2WithValidStep1();

        assertTrue(
                createPropertyPage.isStep2LocationLoaded(),
                ErrorMessages.CREATE_PROPERTY_STEP2_SHOULD_DISPLAY_LOCATION_FIELDS
        );
    }

    @ParameterizedTest(name = "Step 2 validation for missing {0}")
    @MethodSource("provideStep2RequiredFields")
    public void testStep2ShowsValidationForMissingField(String fieldName) {
        loadCreatePage();
        goToStep2WithValidStep1();
        createPropertyPage.clearStep2RequiredField(fieldName);
        createPropertyPage.clickNext();

        assertTrue(
                createPropertyPage.hasInlineErrorContaining(fieldName),
                ErrorMessages.CREATE_PROPERTY_STEP2_REQUIRED_FIELD_SHOULD_SHOW_VALIDATION
        );
    }

    /* STEP 3 */

    @Test
    @DisplayName("Step 3 displays Property Details fields after completing Step 2")
    public void testStep3ShowsDetailsFieldsAfterCompletingStep2() {
        goToStep3();

        assertTrue(
                createPropertyPage.isStep3DetailsLoaded(),
                ErrorMessages.CREATE_PROPERTY_STEP3_SHOULD_DISPLAY_DETAILS_FIELDS
        );
    }

    @Test
    @DisplayName("Step 3 Max Guests minimum value is 1")
    public void testStep3MaxGuestsMinimumIsOne() {
        goToStep3();

        assertTrue(
                createPropertyPage.maxGuestsMinIsOne(),
                ErrorMessages.CREATE_PROPERTY_MAX_GUESTS_MIN_SHOULD_BE_1
        );
    }

    @Test
    @DisplayName("Step 3 Bedrooms minimum value is 0")
    public void testStep3BedroomsMinimumIsZero() {
        goToStep3();

        assertTrue(
                createPropertyPage.bedroomsMinIsZero(),
                ErrorMessages.CREATE_PROPERTY_BEDROOMS_MIN_SHOULD_BE_0
        );
    }

    //fails: actual beds min is 0
    @Test
    @DisplayName("Step 3 Beds minimum value is 1")
    public void testStep3BedsMinimumIsOne() {
        goToStep3();

        assertTrue(
                createPropertyPage.bedsMinIsOne(),
                ErrorMessages.CREATE_PROPERTY_BEDS_MIN_SHOULD_BE_1
        );
    }

    @Test
    @DisplayName("Step 3 Bathrooms minimum value is 0")
    public void testStep3BathroomsMinimumIsZero() {
        goToStep3();

        assertTrue(
                createPropertyPage.bathroomsMinIsZero(),
                ErrorMessages.CREATE_PROPERTY_BATHROOMS_MIN_SHOULD_BE_0
        );
    }

    @Test
    @DisplayName("Step 3 Bathrooms allows half-step increments")
    public void testStep3BathroomsAllowsHalfIncrements() {
        goToStep3();

        assertTrue(
                createPropertyPage.bathroomsStepIsHalfIncrement(),
                ErrorMessages.CREATE_PROPERTY_BATHROOMS_STEP_SHOULD_BE_HALF
        );
    }

    @Test
    @DisplayName("Going back from Step 2 preserves the Step 1 title value")
    public void testBackFromStep2PreservesStep1Title() {
        loadCreatePage();
        goToStep2AndBack();

        assertTrue(
                createPropertyPage.isStep1TitleValuePreserved("Automation Listing"),
                ErrorMessages.CREATE_PROPERTY_BACK_SHOULD_PRESERVE_STEP1_TITLE
        );
    }

    @Test
    @DisplayName("Going back from Step 2 preserves the Step 1 description value")
    public void testBackFromStep2PreservesStep1Description() {
        loadCreatePage();
        goToStep2AndBack();

        assertTrue(
                createPropertyPage.isStep1DescriptionValuePreserved("Automation flow for create property wizard."),
                ErrorMessages.CREATE_PROPERTY_BACK_SHOULD_PRESERVE_STEP1_DESCRIPTION
        );
    }

    @Test
    @DisplayName("Going back from Step 3 preserves the Step 2 country value")
    public void testBackFromStep3PreservesStep2Country() {
        goToStep3ThenBack();

        assertTrue(
                createPropertyPage.isStep2CountryValuePreserved("Afghanistan"),
                ErrorMessages.CREATE_PROPERTY_BACK_SHOULD_PRESERVE_STEP2_COUNTRY
        );
    }

    @Test
    @DisplayName("Going back from Step 3 preserves the Step 2 city value")
    public void testBackFromStep3PreservesStep2City() {
        goToStep3ThenBack();

        assertTrue(
                createPropertyPage.isStep2CityValuePreserved("Kabul"),
                ErrorMessages.CREATE_PROPERTY_BACK_SHOULD_PRESERVE_STEP2_CITY
        );
    }

    @Test
    @DisplayName("Progress indicator shows 'Step 1 of 7' on the first step")
    public void testProgressIndicatorShowsStep1Of7() {
        loadCreatePage();

        assertEquals(
                "Step 1 of 7", createPropertyPage.getProgressText(),
                ErrorMessages.CREATE_PROPERTY_PROGRESS_SHOULD_SHOW_STEP_1_OF_7
        );
    }

    //fails
    @Test
    @DisplayName("Non-host access to the create property page is blocked with 403")
    public void testNonHostAccessToCreatePropertyIsBlockedWith403() {
        registerNewUserAndLandOnHome("testcreate");
        createPropertyPage.navigateTo();

        assertTrue(
                createPropertyPage.pageShows403Error(),
                ErrorMessages.CREATE_PROPERTY_SHOULD_BLOCK_NON_HOST_WITH_403
        );
    }

    /* STEP 4 */

    @Test
    @DisplayName("Step 4 displays the Amenities grid after completing Step 3")
    public void testStep4ShowsAmenitiesGridAfterCompletingStep3() {
        goToStep4();

        assertTrue(
                createPropertyPage.isStep4AmenitiesLoaded(),
                ErrorMessages.CREATE_PROPERTY_STEP4_SHOULD_DISPLAY_AMENITIES_GRID
        );
    }

    //fails
    @Test
    @DisplayName("Step 4 amenities are grouped under 'Essentials'")
    public void testStep4AmenitiesGroupedByEssentials() {
        goToStep4();

        assertTrue(
                createPropertyPage.hasAmenityGroupNamed("Essentials"),
                ErrorMessages.CREATE_PROPERTY_STEP4_SHOULD_GROUP_AMENITIES_BY_ESSENTIALS
        );
    }

    //fails
    @Test
    @DisplayName("Step 4 amenities are grouped under 'Features'")
    public void testStep4AmenitiesGroupedByFeatures() {
        goToStep4();

        assertTrue(
                createPropertyPage.hasAmenityGroupNamed("Features"),
                ErrorMessages.CREATE_PROPERTY_STEP4_SHOULD_GROUP_AMENITIES_BY_FEATURES
        );
    }

    //fails
    @Test
    @DisplayName("Step 4 amenities are grouped under 'Safety'")
    public void testStep4AmenitiesGroupedBySafety() {
        goToStep4();

        assertTrue(
                createPropertyPage.hasAmenityGroupNamed("Safety"),
                ErrorMessages.CREATE_PROPERTY_STEP4_SHOULD_GROUP_AMENITIES_BY_SAFETY
        );
    }

    @Test
    @DisplayName("Step 4 with selected amenities allows advancing to Step 5")
    public void testStep4SelectedAmenitiesAllowAdvancingToStep5() {
        goToStep4();
        createPropertyPage.toggleAmenityByLabelContaining("WiFi");
        createPropertyPage.toggleAmenityByLabelContaining("TV");
        createPropertyPage.clickNext();

        assertTrue(
                createPropertyPage.isStep5PhotosLoaded(),
                ErrorMessages.CREATE_PROPERTY_STEP4_NEXT_SHOULD_ADVANCE_TO_STEP5_WITH_SELECTED_AMENITIES
        );
    }

    @Test
    @DisplayName("Step 4 allows proceeding to Step 5 with no amenities selected")
    public void testStep4AllowsProceedingWithoutAmenitiesSelected() {
        goToStep4();
        createPropertyPage.clickNext();

        assertTrue(
                createPropertyPage.isStep5PhotosLoaded(),
                ErrorMessages.CREATE_PROPERTY_STEP4_NEXT_SHOULD_ALLOW_EMPTY_AMENITIES
        );
    }

    @Test
    @DisplayName("Going back from Step 4 and returning preserves amenity selection")
    public void testStep4BackToStep3AndReturnPreservesAmenitySelection() {
        goToStep4();
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
    @DisplayName("Step 5 shows an image upload area with drag-and-drop or browse support")
    public void testStep5ShowsImageUploadAreaWithDragDropOrBrowse() {
        loadCreatePage();
        goToStep5WithValidStep1ToStep4();

        assertTrue(
                createPropertyPage.step5HasUploadAreaSupportingDropOrBrowse(),
                ErrorMessages.CREATE_PROPERTY_STEP5_SHOULD_DISPLAY_UPLOAD_AREA_WITH_DRAG_DROP_AND_BROWSE
        );
    }

    @Test
    @DisplayName("Step 5 uploading images shows preview thumbnails")
    public void testStep5UploadingImagesShowsPreviewThumbnails() {
        loadCreatePage();
        goToStep5WithValidStep1ToStep4();
        createPropertyPage.uploadImagesFromProjectPath(
                MediaPaths.BRASILIA_APT_IMAGE_01,
                MediaPaths.BRASILIA_APT_IMAGE_02
        );

        assertTrue(
                createPropertyPage.hasAtLeastNImagePreviews(2),
                ErrorMessages.CREATE_PROPERTY_STEP5_UPLOAD_SHOULD_DISPLAY_PREVIEW_THUMBNAILS
        );
    }

    @Test
    @DisplayName("Step 5 uploaded images show a sort handle and delete button")
    public void testStep5UploadedImagesShowSortHandleAndDeleteButton() {
        loadCreatePage();
        goToStep5WithValidStep1ToStep4();
        createPropertyPage.uploadImagesFromProjectPath(MediaPaths.BRASILIA_APT_IMAGE_01);

        assertTrue(
                createPropertyPage.uploadedImagesShowSortHandleAndDelete(),
                ErrorMessages.CREATE_PROPERTY_STEP5_UPLOADED_IMAGE_SHOULD_SHOW_SORT_HANDLE_AND_DELETE
        );
    }

    @Test
    @DisplayName("Step 5 first uploaded image is marked as the primary cover")
    public void testStep5FirstUploadedImageIsMarkedAsPrimaryCover() {
        loadCreatePage();
        goToStep5WithValidStep1ToStep4();
        createPropertyPage.uploadImagesFromProjectPath(
                MediaPaths.BRASILIA_APT_IMAGE_01,
                MediaPaths.BRASILIA_APT_IMAGE_02
        );

        assertTrue(
                createPropertyPage.firstUploadedImageIsMarkedPrimaryOrCover(),
                ErrorMessages.CREATE_PROPERTY_STEP5_FIRST_IMAGE_SHOULD_BE_MARKED_PRIMARY_OR_COVER
        );
    }

    @Test
    @DisplayName("Step 5 cannot advance without uploading at least one image")
    public void testStep5CannotAdvanceWithoutAtLeastOneImage() {
        loadCreatePage();
        goToStep5WithValidStep1ToStep4();
        createPropertyPage.clickNext();

        assertTrue(
                createPropertyPage.hasMinimumImageRequiredValidationMessage(),
                ErrorMessages.CREATE_PROPERTY_STEP5_NEXT_SHOULD_REQUIRE_MINIMUM_ONE_IMAGE
        );
    }

    @Test
    @DisplayName("Step 5 reordering images updates the preview order")
    public void testStep5ReorderingImagesUpdatesPreviewOrder() {
        loadCreatePage();
        goToStep5WithValidStep1ToStep4();

        createPropertyPage.uploadImagesFromProjectPath(
                MediaPaths.BRASILIA_APT_IMAGE_01,
                MediaPaths.BRASILIA_APT_IMAGE_02
        );
        createPropertyPage.hasAtLeastNImagePreviews(2);

        String firstBefore = createPropertyPage.getImagePreviewSignatureAt(0);
        String secondBefore = createPropertyPage.getImagePreviewSignatureAt(1);
        createPropertyPage.moveImageDownAt(0);
        createPropertyPage.waitForFirstPreviewSignatureToBe(secondBefore);

        assertEquals(
                secondBefore,
                createPropertyPage.getImagePreviewSignatureAt(0),
                ErrorMessages.CREATE_PROPERTY_STEP5_REORDER_SHOULD_UPDATE_SORT_ORDER
        );
    }

    @Test
    @DisplayName("Step 5 reordering changes the primary cover to the new first image")
    public void testStep5ReorderingChangesPrimaryCoverToNewFirstImage() {
        loadCreatePage();
        goToStep5WithValidStep1ToStep4();
        createPropertyPage.uploadImagesFromProjectPath(
                MediaPaths.BRASILIA_APT_IMAGE_01,
                MediaPaths.BRASILIA_APT_IMAGE_02
        );
        createPropertyPage.hasAtLeastNImagePreviews(2);

        createPropertyPage.moveImageDownAt(0);

        assertTrue(
                createPropertyPage.primaryBadgeIsOnPreviewIndex(0),
                ErrorMessages.CREATE_PROPERTY_STEP5_REORDER_SHOULD_UPDATE_PRIMARY_COVER
        );
    }

    @Test
    @DisplayName("Step 5 deleting an image removes it from the preview list")
    public void testStep5DeleteRemovesImageFromList() {
        loadCreatePage();
        goToStep5WithValidStep1ToStep4();
        createPropertyPage.uploadImagesFromProjectPath(
                MediaPaths.BRASILIA_APT_IMAGE_01,
                MediaPaths.BRASILIA_APT_IMAGE_02
        );
        createPropertyPage.hasAtLeastNImagePreviews(2);

        createPropertyPage.deleteImageAt(1);
        createPropertyPage.waitForPreviewCountToBe(1);

        assertEquals(
                1,
                createPropertyPage.getUploadedImagePreviewCount(),
                ErrorMessages.CREATE_PROPERTY_STEP5_DELETE_SHOULD_REMOVE_IMAGE
        );
    }

    @Test
    @DisplayName("Step 5 deleting the primary image promotes the next image as primary")
    public void testStep5DeletingPrimaryPromotesNextImageAsPrimary() {
        loadCreatePage();
        goToStep5WithValidStep1ToStep4();
        createPropertyPage.uploadImagesFromProjectPath(
                MediaPaths.BRASILIA_APT_IMAGE_01,
                MediaPaths.BRASILIA_APT_IMAGE_02
        );
        createPropertyPage.hasAtLeastNImagePreviews(2);

        createPropertyPage.deleteImageAt(0);
        createPropertyPage.waitForPreviewCountToBe(1);

        assertTrue(
                createPropertyPage.primaryBadgeIsOnPreviewIndex(0),
                ErrorMessages.CREATE_PROPERTY_STEP5_DELETE_PRIMARY_SHOULD_PROMOTE_NEXT
        );
    }

    @Test
    @DisplayName("Step 5 deleting the only image and proceeding warns minimum one image is required")
    public void testStep5DeletingOnlyImageWarnsMinimumOneRequired() {
        loadCreatePage();
        goToStep5WithValidStep1ToStep4();
        createPropertyPage.uploadImagesFromProjectPath(MediaPaths.BRASILIA_APT_IMAGE_01);
        createPropertyPage.hasAtLeastNImagePreviews(1);

        createPropertyPage.deleteImageAt(0);
        createPropertyPage.waitForPreviewCountToBe(0);
        createPropertyPage.clickNext();

        assertTrue(
                createPropertyPage.hasMinimumImageRequiredValidationMessage(),
                ErrorMessages.CREATE_PROPERTY_STEP5_SHOULD_WARN_WHEN_DELETING_LAST_IMAGE
        );
    }

    @Test
    @DisplayName("Step 5 going back to Step 4 and returning preserves uploaded images")
    public void testStep5BackToStep4AndReturnPreservesUploadedImages() {
        loadCreatePage();
        goToStep5WithValidStep1ToStep4();
        createPropertyPage.uploadImagesFromProjectPath(
                MediaPaths.BRASILIA_APT_IMAGE_01,
                MediaPaths.BRASILIA_APT_IMAGE_02
        );
        createPropertyPage.clickBack();
        createPropertyPage.clickNext();

        assertTrue(
                createPropertyPage.hasAtLeastNImagePreviews(2),
                ErrorMessages.CREATE_PROPERTY_STEP5_BACK_AND_RETURN_SHOULD_PRESERVE_UPLOADED_IMAGES
        );
    }

    @Test
    @DisplayName("Step 5 prevents adding more than 10 images")
    public void testStep5PreventsAddingMoreThanTenImages() {
        loadCreatePage();
        goToStep5WithValidStep1ToStep4();

        String[] tenImages = new String[] {
                MediaPaths.BRASILIA_APT_IMAGE_01,
                MediaPaths.BRASILIA_APT_IMAGE_01,
                MediaPaths.BRASILIA_APT_IMAGE_01,
                MediaPaths.BRASILIA_APT_IMAGE_01,
                MediaPaths.BRASILIA_APT_IMAGE_01,
                MediaPaths.BRASILIA_APT_IMAGE_01,
                MediaPaths.BRASILIA_APT_IMAGE_01,
                MediaPaths.BRASILIA_APT_IMAGE_01,
                MediaPaths.BRASILIA_APT_IMAGE_01,
                MediaPaths.BRASILIA_APT_IMAGE_01
        };
        createPropertyPage.uploadImagesFromProjectPath(tenImages);
        createPropertyPage.hasAtLeastNImagePreviews(10);

        createPropertyPage.uploadImagesFromProjectPath(MediaPaths.BRASILIA_APT_IMAGE_02);

        assertEquals(
                10,
                createPropertyPage.getUploadedImagePreviewCount(),
                ErrorMessages.IMAGE_UPLOAD_STEP5_SHOULD_ENFORCE_MAX_10_IMAGES
        );
    }

    /* STEP 6 */

    @Test
    @DisplayName("Step 6 shows a price-per-night input with USD label")
    public void testStep6ShowsPricePerNightInputInUsd() {
        loadCreatePage();
        goToStep6WithValidStep1ToStep5();

        assertAll(
                () -> assertTrue(createPropertyPage.isStep6PricingLoaded(),
                        ErrorMessages.CREATE_PROPERTY_STEP6_PRICING_SECTION_SHOULD_LOAD),
                () -> assertTrue(createPropertyPage.step6PriceInputUsesUsdLabel(),
                        ErrorMessages.CREATE_PROPERTY_STEP6_PRICE_INPUT_SHOULD_USE_USD_LABEL)
        );
    }

    @Test
    @DisplayName("Step 6 shows validation error when price is zero")
    public void testStep6ShowsValidationWhenPriceIsZero() {
        loadCreatePage();
        goToStep6WithValidStep1ToStep5();
        createPropertyPage.enterPricePerNight("0");
        createPropertyPage.clickNext();

        assertTrue(
                createPropertyPage.hasPriceGreaterThanZeroValidationMessage(),
                ErrorMessages.CREATE_PROPERTY_STEP6_SHOULD_REQUIRE_PRICE_GREATER_THAN_ZERO
        );
    }

    @Test
    @DisplayName("Step 6 with a valid price advances to Step 7 Review")
    public void testStep6NextWithValidPriceAdvancesToStep7Review() {
        loadCreatePage();
        goToStep6WithValidStep1ToStep5();
        createPropertyPage.enterPricePerNight("120");
        createPropertyPage.clickNext();

        assertTrue(
                createPropertyPage.isStep7ReviewLoaded(),
                ErrorMessages.CREATE_PROPERTY_STEP6_NEXT_SHOULD_ADVANCE_TO_STEP7_REVIEW
        );
    }

    /* STEP 7 */

    @Test
    @DisplayName("Step 7 shows a summary of all information from previous steps")
    public void testStep7ShowsSummaryOfInformationFromPreviousSteps() {
        loadCreatePage();
        goToStep6WithValidStep1ToStep5();
        createPropertyPage.enterPricePerNight("120");
        createPropertyPage.clickNext();

        assertTrue(
                createPropertyPage.reviewContainsAllStep1ToStep6Sections(),
                ErrorMessages.CREATE_PROPERTY_STEP7_SHOULD_SUMMARIZE_ALL_PREVIOUS_STEPS
        );
    }

    @Test
    @DisplayName("Step 7 going back to Step 6 and returning to Step 7 works correctly")
    public void testStep7BackToStep6AndReturnToStep7Works() {
        loadCreatePage();
        goToStep6WithValidStep1ToStep5();
        createPropertyPage.enterPricePerNight("120");
        createPropertyPage.clickNext();
        createPropertyPage.clickBack();
        createPropertyPage.clickNext();

        assertTrue(
                createPropertyPage.isStep7ReviewLoaded(),
                ErrorMessages.CREATE_PROPERTY_STEP7_BACK_AND_RETURN_SHOULD_WORK
        );
    }

    @Test
    @DisplayName("Step 7 submitting the form redirects to the host dashboard")
    public void testStep7CreatePropertyRedirectsToHostDashboard() {
        loadCreatePage();
        goToStep6WithValidStep1ToStep5();
        createPropertyPage.enterPricePerNight("120");
        createPropertyPage.clickNext();
        createPropertyPage.clickCreateProperty();

        assertTrue(
                isUrlContains("/hosting"),
                ErrorMessages.CREATE_PROPERTY_STEP7_SUBMIT_SHOULD_REDIRECT_TO_HOST_DASHBOARD
        );
    }

    @Test
    @DisplayName("Step 7 submitting the form shows a success message")
    public void testStep7CreatePropertyShowsSuccessMessage() {
        loadCreatePage();
        goToStep6WithValidStep1ToStep5();

        createPropertyPage.enterPricePerNight("120");
        createPropertyPage.clickNext();
        createPropertyPage.clickCreateProperty();

        assertTrue(
                createPropertyPage.hasCreatePropertySuccessAlert(),
                ErrorMessages.CREATE_PROPERTY_STEP7_SUBMIT_SHOULD_SHOW_SUCCESS_MESSAGE
        );
    }

    private static Stream<String> provideStep2RequiredFields() {
        return Stream.of("country", "city");
    }
}
