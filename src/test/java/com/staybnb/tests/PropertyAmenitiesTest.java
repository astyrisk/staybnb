package com.staybnb.tests;

import com.staybnb.assertions.ErrorMessages;
import com.staybnb.data.Constants;
import com.staybnb.pages.AmenitiesApiPage;
import com.staybnb.pages.EditPropertyPage;
import com.staybnb.pages.LoginPage;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("Properties")
@Feature("Property Amenities")
@Tag("regression")
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

    @Test
    @DisplayName("Amenities API returns list with id, name, and icon fields")
    public void testAmenitiesApiReturnsListWithIdNameAndIcon() {
        assertTrue(
                amenitiesApiPage.amenitiesResponseHasRequiredFieldsViaApi(),
                ErrorMessages.AMENITIES_API_SHOULD_RETURN_LIST_WITH_ID_NAME_ICON
        );
    }

    @Test
    @DisplayName("Edit property amenities section shows a checkbox grid")
    public void testEditPropertyAmenitiesSectionShowsCheckboxGrid() {
        loginAsHostAndOpenEditPage();

        assertTrue(
                editPropertyPage.isAmenitiesGridDisplayed(),
                ErrorMessages.EDIT_PROPERTY_AMENITIES_SECTION_SHOULD_SHOW_CHECKBOX_GRID
        );
    }

    @Test
    @DisplayName("Edit property pre-checks previously selected amenities")
    public void testEditPropertyPreChecksSelectedAmenities() {
        loginAsHostAndOpenEditPage();

        assertTrue(
                editPropertyPage.isAmenityCheckedByLabelContaining("Air Conditioning"),
                ErrorMessages.EDIT_PROPERTY_SHOULD_PRE_CHECK_PREVIOUSLY_SELECTED_AMENITIES
        );
    }

    @Test
    @DisplayName("Deselecting an amenity and saving removes it from the property")
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
