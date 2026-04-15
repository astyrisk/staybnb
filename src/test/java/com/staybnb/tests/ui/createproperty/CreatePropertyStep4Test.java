package com.staybnb.tests.ui.createproperty;

import com.staybnb.assertions.ErrorMessages;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("Property Management")
@Feature("Create Property")
@Tag("regression")
public class CreatePropertyStep4Test extends CreatePropertyBaseTest {

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
}
