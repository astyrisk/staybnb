package com.staybnb.tests.ui.createproperty;

import com.staybnb.assertions.ErrorMessages;
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
public class CreatePropertyStep1Test extends CreatePropertyBaseTest {

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
}
