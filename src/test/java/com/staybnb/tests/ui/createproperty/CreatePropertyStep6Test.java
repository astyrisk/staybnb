package com.staybnb.tests.ui.createproperty;

import com.staybnb.assertions.ErrorMessages;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("Property Management")
@Feature("Create Property")
@Tag("regression")
public class CreatePropertyStep6Test extends CreatePropertyBaseTest {

    @Test
    @DisplayName("Step 6 shows a price-per-night input with USD label")
    public void testStep6ShowsPricePerNightInputInUsd() {
        loadCreatePage();
        goToStep6WithValidStep1ToStep5();

        assertAll(
                () -> assertTrue(createPropertyPage.isStep6PricingLoaded(),
                        ErrorMessages.CREATE_PROPERTY_STEP6_PRICING_SECTION_SHOULD_LOAD),
                () -> assertTrue(createPropertyPage.step6PriceInputUsesUsdLabel(),
                        ErrorMessages.CREATE_PROPERTY_STEP6_PRICE_INPUT_SHOULD_USE_USD_LABEL)
        );
    }

    @Test
    @DisplayName("Step 6 shows validation error when price is zero")
    public void testStep6ShowsValidationWhenPriceIsZero() {
        loadCreatePage();
        goToStep6WithValidStep1ToStep5();
        createPropertyPage.enterPricePerNight("0");
        createPropertyPage.clickNext();

        assertTrue(
                createPropertyPage.hasPriceGreaterThanZeroValidationMessage(),
                ErrorMessages.CREATE_PROPERTY_STEP6_SHOULD_REQUIRE_PRICE_GREATER_THAN_ZERO
        );
    }

    @Test
    @DisplayName("Step 6 with a valid price advances to Step 7 Review")
    public void testStep6NextWithValidPriceAdvancesToStep7Review() {
        loadCreatePage();
        goToStep6WithValidStep1ToStep5();
        createPropertyPage.enterPricePerNight("120");
        createPropertyPage.clickNext();

        assertTrue(
                createPropertyPage.isStep7ReviewLoaded(),
                ErrorMessages.CREATE_PROPERTY_STEP6_NEXT_SHOULD_ADVANCE_TO_STEP7_REVIEW
        );
    }
}
