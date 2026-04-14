package com.staybnb.tests.ui;

import com.staybnb.assertions.ErrorMessages;
import com.staybnb.config.AppConstants;
import com.staybnb.pages.EditPropertyPage;
import com.staybnb.pages.LoginPage;
import com.staybnb.tests.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.ResourceLock;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("Properties")
@Feature("Property Amenities")
@Tag("regression")
@ResourceLock("property-1088")
public class PropertyAmenitiesTest extends BaseTest {
    private LoginPage loginPage;
    private EditPropertyPage editPropertyPage;
    private boolean airConditioningModified = false;

    @BeforeEach
    public void setup() {
        loginPage = new LoginPage(driver);
        editPropertyPage = new EditPropertyPage(driver);
        loginAsTestUserAndLandOnHome(loginPage);
    }

    private void openEditPage() {
        editPropertyPage.navigateTo(AppConstants.EditProperty.EDITABLE_PROPERTY_ID);
        editPropertyPage.waitForSectionsToBeVisible();
    }

    @Test
    @DisplayName("Edit property amenities section shows a checkbox grid")
    public void testEditPropertyAmenitiesSectionShowsCheckboxGrid() {
        openEditPage();

        assertTrue(
                editPropertyPage.isAmenitiesGridDisplayed(),
                ErrorMessages.EDIT_PROPERTY_AMENITIES_SECTION_SHOULD_SHOW_CHECKBOX_GRID
        );
    }

    @Test
    @DisplayName("Edit property pre-checks previously selected amenities")
    public void testEditPropertyPreChecksSelectedAmenities() {
        openEditPage();

        assertTrue(
                editPropertyPage.isAmenityCheckedByLabelContaining("Air Conditioning"),
                ErrorMessages.EDIT_PROPERTY_SHOULD_PRE_CHECK_PREVIOUSLY_SELECTED_AMENITIES
        );
    }

    @Test
    @DisplayName("Deselecting an amenity and saving removes it from the property")
    public void testDeselectingAmenityAndSavingRemovesItFromProperty() {
        openEditPage();
        editPropertyPage.toggleAmenityByLabelContaining("Air Conditioning");
        editPropertyPage.clickSaveChangesAndDismissAlert();
        airConditioningModified = true;
        editPropertyPage.navigateTo(AppConstants.EditProperty.EDITABLE_PROPERTY_ID);
        editPropertyPage.waitForSectionsToBeVisible();

        assertFalse(
                editPropertyPage.isAmenityCheckedByLabelContaining("Air Conditioning"),
                ErrorMessages.DESELECTING_AMENITY_AND_SAVING_SHOULD_REMOVE_IT_FROM_PROPERTY
        );
    }

    @AfterEach
    public void restoreAmenities() {
        if (airConditioningModified) {
            editPropertyPage.navigateTo(AppConstants.EditProperty.EDITABLE_PROPERTY_ID);
            editPropertyPage.waitForSectionsToBeVisible();
            if (!editPropertyPage.isAmenityCheckedByLabelContaining("Air Conditioning")) {
                editPropertyPage.toggleAmenityByLabelContaining("Air Conditioning");
                editPropertyPage.clickSaveChangesAndDismissAlert();
            }
        }
    }
}
