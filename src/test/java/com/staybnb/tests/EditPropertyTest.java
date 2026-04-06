package com.staybnb.tests;

import com.staybnb.assertions.ErrorMessages;
import com.staybnb.data.Constants;
import com.staybnb.data.PropertyPayloads;
import com.staybnb.pages.EditPropertyPage;
import com.staybnb.pages.LoginPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class EditPropertyTest extends BaseTest {
    private LoginPage loginPage;
    private EditPropertyPage editPropertyPage;

    @BeforeEach
    public void setup() {
        loginPage = new LoginPage(driver);
        editPropertyPage = new EditPropertyPage(driver);
    }

    private void loginAsHostAndOpenEditPage() {
        loginAsTestUserAndLandOnHome(loginPage);
        editPropertyPage.navigateTo(Constants.EditProperty.EDITABLE_PROPERTY_ID);
    }

    private long updateEditablePropertyStatus() {
        return editPropertyPage.updatePropertyStatusViaApi(
                Constants.EditProperty.EDITABLE_PROPERTY_ID,
                PropertyPayloads.validEditPayloadJson()
        );
    }

    @Test
    public void testEditPropertyPageLoadsWithPrePopulatedData() {
        loginAsHostAndOpenEditPage();

        assertAll(
                () -> assertTrue(editPropertyPage.isEditPageLoaded(),
                        ErrorMessages.EDIT_PROPERTY_PAGE_CONTAINER_AND_CONTROLS_SHOULD_BE_VISIBLE),
                () -> assertTrue(editPropertyPage.hasPrePopulatedCoreFields(),
                        ErrorMessages.EDIT_PROPERTY_PAGE_CORE_FIELDS_SHOULD_BE_PREPOPULATED)
        );
    }

    @ParameterizedTest(name = "Edit page section visible: {0}")
    @MethodSource("provideEditPageSections")
    public void testEditPropertyPageShowsAllSectionsSinglePage(String sectionHeader) {
        loginAsHostAndOpenEditPage();

        assertTrue(
                editPropertyPage.hasSectionHeader(sectionHeader),
                ErrorMessages.EDIT_PROPERTY_PAGE_SHOULD_DISPLAY_ALL_SINGLE_PAGE_SECTIONS
        );
    }


    @Test
    public void testEditPropertyPageDoesNotUseCreateWizardFlow() {
        loginAsHostAndOpenEditPage();
        editPropertyPage.waitForSectionsToBeVisible();

        assertAll(
                () -> assertTrue(editPropertyPage.hasAllMainSectionsVisible(),
                        ErrorMessages.EDIT_PROPERTY_PAGE_SHOULD_DISPLAY_ALL_SECTIONS),
                () -> assertFalse(editPropertyPage.hasCreateWizardProgressOrNavigation(),
                        ErrorMessages.EDIT_PROPERTY_PAGE_SHOULD_NOT_SHOW_WIZARD_NAVIGATION)
        );
    }

    @Test
    public void testEditPropertySaveReturns200ForOwner() {
        loginAsHostAndOpenEditPage();

        assertEquals(
                200L,
                updateEditablePropertyStatus(),
                ErrorMessages.EDIT_PROPERTY_SAVE_SHOULD_RETURN_200_FOR_OWNER
        );
    }

    @ParameterizedTest(name = "Required field validation: {0}")
    @MethodSource("provideRequiredFields")
    public void testEditPropertyRequiredFieldValidation(String requiredField) {
        loginAsHostAndOpenEditPage();
        editPropertyPage.clearRequiredField(requiredField);
        editPropertyPage.clickSaveChanges();

        assertTrue(
                editPropertyPage.hasInlineErrorContaining(requiredField),
                ErrorMessages.EDIT_PROPERTY_REQUIRED_FIELD_SHOULD_SHOW_INLINE_VALIDATION
        );
    }

    @Test
    public void testEditPropertyApiReturns403ForNonOwner() {
        registerNewUserAndLandOnHome("testeditproperty");

        assertEquals(
                403L,
                updateEditablePropertyStatus(),
                ErrorMessages.EDIT_PROPERTY_API_SHOULD_RETURN_403_FOR_NON_OWNER
        );
    }

    @Test
    public void testEditPropertyApiReturns404ForNonExistentPropertyId() {
        loginAsTestUserAndLandOnHome(loginPage);
        long status = editPropertyPage.updatePropertyStatusViaApi(
                Constants.EditProperty.NON_EXISTENT_PROPERTY_ID,
                PropertyPayloads.validEditPayloadJson()
        );

        assertEquals(
                404L,
                status,
                ErrorMessages.EDIT_PROPERTY_API_SHOULD_RETURN_404_FOR_NON_EXISTENT_PROPERTY
        );
    }

    @Test
    public void testEditPropertyApiReturns401WhenLoggedOut() {
        assertEquals(
                401L,
                updateEditablePropertyStatus(),
                ErrorMessages.EDIT_PROPERTY_API_SHOULD_RETURN_401_WHEN_LOGGED_OUT
        );
    }

    @Test
    public void testEditPropertyPageShowsDeletePropertyButton() {
        loginAsHostAndOpenEditPage();

        assertTrue(
                editPropertyPage.isDeletePropertyButtonVisible(),
                ErrorMessages.EDIT_PROPERTY_PAGE_SHOULD_SHOW_DELETE_BUTTON
        );
    }

    private static Stream<String> provideEditPageSections() {
        return Stream.of(
                "Basic Information",
                "Location",
                "Property Details",
                "Amenities",
                "Photos",
                "Pricing"
        );
    }

    private static Stream<String> provideRequiredFields() {
        return Stream.of(
                "title",
                "description",
                "city",
                "country",
                "price"
        );
    }
}
