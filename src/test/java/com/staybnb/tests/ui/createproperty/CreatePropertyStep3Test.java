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
public class CreatePropertyStep3Test extends CreatePropertyBaseTest {

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
}
