package com.staybnb.tests;

import com.staybnb.assertions.ErrorMessages;
import com.staybnb.data.Constants;
import com.staybnb.pages.AmenitiesApiPage;
import com.staybnb.pages.EditPropertyPage;
import com.staybnb.pages.LoginPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PropertyAmenitiesTest extends BaseTest {
    private LoginPage loginPage;
    private EditPropertyPage editPropertyPage;
    private AmenitiesApiPage amenitiesApiPage;

    @BeforeEach
    public void setup() {
        loginPage = new LoginPage(driver);
        editPropertyPage = new EditPropertyPage(driver);
        amenitiesApiPage = new AmenitiesApiPage(driver);
    }

    private void loginAsHostAndOpenEditPage() {
        loginAsTestUserAndLandOnHome(loginPage);
        editPropertyPage.navigateTo(Constants.EditProperty.EDITABLE_PROPERTY_ID);
        editPropertyPage.waitForSectionsToBeVisible();
    }

    // AC1 — GET /amenities returns a list with id, name, and icon fields
    @Test
    public void testAmenitiesApiReturnsListWithIdNameAndIcon() {
        assertTrue(
                amenitiesApiPage.amenitiesResponseHasRequiredFieldsViaApi(),
                ErrorMessages.AMENITIES_API_SHOULD_RETURN_LIST_WITH_ID_NAME_ICON
        );
    }

    // AC2 — Edit property amenities section shows a non-empty checkbox grid
    @Test
    public void testEditPropertyAmenitiesSectionShowsCheckboxGrid() {
        loginAsHostAndOpenEditPage();
        assertTrue(
                editPropertyPage.isAmenitiesGridDisplayed(),
                ErrorMessages.EDIT_PROPERTY_AMENITIES_SECTION_SHOULD_SHOW_CHECKBOX_GRID
        );
    }

    // AC4 — Previously selected amenities are pre-checked on the edit page
    @Test
    public void testEditPropertyPreChecksSelectedAmenities() {
        loginAsHostAndOpenEditPage();
        assertTrue(
                editPropertyPage.isAmenityCheckedByLabelContaining("Air Conditioning"),
                ErrorMessages.EDIT_PROPERTY_SHOULD_PRE_CHECK_PREVIOUSLY_SELECTED_AMENITIES
        );
    }

    // AC3 + AC5 — Deselecting an amenity and saving removes it from the property
    // Note: this test modifies property 960's amenity state (removes Air Conditioning).
    // Re-check it manually via the edit page if this test needs to be run again in isolation.
    @Test
    public void testDeselectingAmenityAndSavingRemovesItFromProperty() {
        loginAsHostAndOpenEditPage();
        editPropertyPage.toggleAmenityByLabelContaining("Air Conditioning");
        editPropertyPage.clickSaveChangesAndDismissAlert();
        editPropertyPage.navigateTo(Constants.EditProperty.EDITABLE_PROPERTY_ID);
        editPropertyPage.waitForSectionsToBeVisible();
        assertFalse(
                editPropertyPage.isAmenityCheckedByLabelContaining("Air Conditioning"),
                ErrorMessages.DESELECTING_AMENITY_AND_SAVING_SHOULD_REMOVE_IT_FROM_PROPERTY
        );
    }
}
