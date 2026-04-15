package com.staybnb.tests.ui.createproperty;

import com.staybnb.assertions.ErrorMessages;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("Property Management")
@Feature("Create Property")
@Tag("regression")
public class CreatePropertyStep2Test extends CreatePropertyBaseTest {

    @Test
    @DisplayName("Step 2 displays Location fields after completing Step 1")
    public void testStep2ShowsLocationFieldsAfterCompletingStep1() {
        loadCreatePage();
        goToStep2WithValidStep1();

        assertTrue(
                createPropertyPage.isStep2LocationLoaded(),
                ErrorMessages.CREATE_PROPERTY_STEP2_SHOULD_DISPLAY_LOCATION_FIELDS
        );
    }

    @ParameterizedTest(name = "Step 2 validation for missing {0}")
    @MethodSource("provideStep2RequiredFields")
    public void testStep2ShowsValidationForMissingField(String fieldName) {
        loadCreatePage();
        goToStep2WithValidStep1();
        createPropertyPage.clearStep2RequiredField(fieldName);
        createPropertyPage.clickNext();

        assertTrue(
                createPropertyPage.hasInlineErrorContaining(fieldName),
                ErrorMessages.CREATE_PROPERTY_STEP2_REQUIRED_FIELD_SHOULD_SHOW_VALIDATION
        );
    }

    private static Stream<String> provideStep2RequiredFields() {
        return Stream.of("country", "city");
    }
}
