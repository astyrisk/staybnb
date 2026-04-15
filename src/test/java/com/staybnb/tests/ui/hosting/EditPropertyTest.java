package com.staybnb.tests.ui.hosting;

import com.staybnb.assertions.ErrorMessages;
import com.staybnb.config.AppConstants;
import com.staybnb.pages.EditPropertyPage;
import com.staybnb.pages.LoginPage;
import com.staybnb.tests.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.ResourceLock;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@Epic("Property Management")
@Feature("Edit Property")
@Tag("regression")
@ResourceLock("property-1088")
public class EditPropertyTest extends BaseTest {
    private EditPropertyPage editPropertyPage;

    @BeforeEach
    public void setup() {
        LoginPage loginPage = new LoginPage(driver);
        editPropertyPage = new EditPropertyPage(driver);
        loginAsTestUserAndLandOnHome(loginPage);
    }

    private void openEditPage() {
        editPropertyPage.navigateTo(AppConstants.EditProperty.EDITABLE_PROPERTY_ID);
    }

    @Test
    @DisplayName("Edit property page loads with all sections and pre-populated data")
    public void testEditPropertyPageLoadsWithPrePopulatedData() {
        openEditPage();

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
        openEditPage();

        assertTrue(
                editPropertyPage.hasSectionHeader(sectionHeader),
                ErrorMessages.EDIT_PROPERTY_PAGE_SHOULD_DISPLAY_ALL_SINGLE_PAGE_SECTIONS
        );
    }

    //TODO should be separated to two tests?
    @Test
    @DisplayName("Edit property page is a single-page form, not a wizard")
    public void testEditPropertyPageDoesNotUseCreateWizardFlow() {
        openEditPage();
        editPropertyPage.waitForSectionsToBeVisible();

        assertAll(
                () -> assertTrue(editPropertyPage.hasAllMainSectionsVisible(),
                        ErrorMessages.EDIT_PROPERTY_PAGE_SHOULD_DISPLAY_ALL_SECTIONS),
                () -> assertFalse(editPropertyPage.hasCreateWizardProgressOrNavigation(),
                        ErrorMessages.EDIT_PROPERTY_PAGE_SHOULD_NOT_SHOW_WIZARD_NAVIGATION)
        );
    }

    @ParameterizedTest(name = "Required field validation: {0}")
    @MethodSource("provideRequiredFields")
    public void testEditPropertyRequiredFieldValidation(String requiredField) {
        openEditPage();
        editPropertyPage.clearRequiredField(requiredField);
        editPropertyPage.clickSaveChanges();

        assertTrue(
                editPropertyPage.hasInlineErrorContaining(requiredField),
                ErrorMessages.EDIT_PROPERTY_REQUIRED_FIELD_SHOULD_SHOW_INLINE_VALIDATION
        );
    }

    @Test
    @DisplayName("Edit property page shows a Delete Property button")
    public void testEditPropertyPageShowsDeletePropertyButton() {
        openEditPage();

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
