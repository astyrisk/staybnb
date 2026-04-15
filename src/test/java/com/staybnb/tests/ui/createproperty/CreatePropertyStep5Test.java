package com.staybnb.tests.ui.createproperty;

import com.staybnb.assertions.ErrorMessages;
import com.staybnb.data.MediaPaths;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("Property Management")
@Feature("Create Property")
@Tag("regression")
public class CreatePropertyStep5Test extends CreatePropertyBaseTest {

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
}
