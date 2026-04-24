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
public class CreatePropertyStep7Test extends CreatePropertyBaseTest {

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
                createPropertyPage.urlContains("/hosting"),
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
}
