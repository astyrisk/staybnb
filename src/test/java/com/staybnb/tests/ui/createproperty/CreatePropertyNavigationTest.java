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
public class CreatePropertyNavigationTest extends CreatePropertyBaseTest {

    @Test
    @DisplayName("Going back from Step 2 preserves the Step 1 title value")
    public void testBackFromStep2PreservesStep1Title() {
        loadCreatePage();
        goToStep2AndBack();

        assertTrue(
                createPropertyPage.isStep1TitleValuePreserved("Automation Listing"),
                ErrorMessages.CREATE_PROPERTY_BACK_SHOULD_PRESERVE_STEP1_TITLE
        );
    }

    @Test
    @DisplayName("Going back from Step 2 preserves the Step 1 description value")
    public void testBackFromStep2PreservesStep1Description() {
        loadCreatePage();
        goToStep2AndBack();

        assertTrue(
                createPropertyPage.isStep1DescriptionValuePreserved("Automation flow for create property wizard."),
                ErrorMessages.CREATE_PROPERTY_BACK_SHOULD_PRESERVE_STEP1_DESCRIPTION
        );
    }

    @Test
    @DisplayName("Going back from Step 3 preserves the Step 2 country value")
    public void testBackFromStep3PreservesStep2Country() {
        goToStep3ThenBack();

        assertTrue(
                createPropertyPage.isStep2CountryValuePreserved("Afghanistan"),
                ErrorMessages.CREATE_PROPERTY_BACK_SHOULD_PRESERVE_STEP2_COUNTRY
        );
    }

    @Test
    @DisplayName("Going back from Step 3 preserves the Step 2 city value")
    public void testBackFromStep3PreservesStep2City() {
        goToStep3ThenBack();

        assertTrue(
                createPropertyPage.isStep2CityValuePreserved("Kabul"),
                ErrorMessages.CREATE_PROPERTY_BACK_SHOULD_PRESERVE_STEP2_CITY
        );
    }
}
