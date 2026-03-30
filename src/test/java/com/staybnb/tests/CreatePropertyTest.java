package com.staybnb.tests;

import com.staybnb.pages.CreatePropertyPage;
import com.staybnb.pages.LoginPage;
import com.staybnb.utils.ErrorMessages;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreatePropertyTest extends BaseTest {
    private LoginPage loginPage;
    private CreatePropertyPage createPropertyPage;

    @BeforeEach
    public void setup() {
        loginPage = new LoginPage(driver);
        createPropertyPage = new CreatePropertyPage(driver);
    }

    private void loginAsExistingHostAndLoadCreatePage() {
        loginAsTestUserAndLandOnHome(loginPage);
        createPropertyPage.load();
    }

    private void goToStep2WithValidStep1() {
        createPropertyPage.fillValidStep1();
        createPropertyPage.clickNext();
    }

    private void goToStep3WithValidStep1AndStep2() {
        goToStep2WithValidStep1();
        createPropertyPage.fillValidStep2();
        createPropertyPage.clickNext();
    }

    @Test
    public void testStep1ShowsBasicsFields() {
        loginAsExistingHostAndLoadCreatePage();
        assertTrue(
                createPropertyPage.isStep1BasicsLoaded(), ErrorMessages.CREATE_PROPERTY_STEP1_SHOULD_DISPLAY_BASICS_FIELDS
        );
    }

    @Test
    public void testStep1ShowsValidationForMissingTitle() {
        loginAsExistingHostAndLoadCreatePage();
        createPropertyPage.clearTitle();
        createPropertyPage.clickNext();
        assertTrue(
                createPropertyPage.hasInlineErrorContaining("title"),
                ErrorMessages.CREATE_PROPERTY_STEP1_SHOULD_REQUIRE_TITLE
        );
    }

    @Test
    public void testStep1ShowsValidationForMissingDescription() {
        loginAsExistingHostAndLoadCreatePage();
        createPropertyPage.clearDescription();
        createPropertyPage.clickNext();
        assertTrue(
                createPropertyPage.hasInlineErrorContaining("description"),
                ErrorMessages.CREATE_PROPERTY_STEP1_SHOULD_REQUIRE_DESCRIPTION
        );
    }

    @Test
    public void testStep2ShowsLocationFieldsAfterCompletingStep1() {
        loginAsExistingHostAndLoadCreatePage();
        goToStep2WithValidStep1();
        assertTrue(
                createPropertyPage.isStep2LocationLoaded(),
                ErrorMessages.CREATE_PROPERTY_STEP2_SHOULD_DISPLAY_LOCATION_FIELDS
        );
    }

    @Test
    public void testStep2ShowsValidationForMissingCountry() {
        loginAsExistingHostAndLoadCreatePage();
        goToStep2WithValidStep1();
        createPropertyPage.clearCountry();
        createPropertyPage.clickNext();
        assertTrue(
                createPropertyPage.hasInlineErrorContaining("country"),
                ErrorMessages.CREATE_PROPERTY_STEP2_SHOULD_REQUIRE_COUNTRY
        );
    }

    @Test
    public void testStep2ShowsValidationForMissingCity() {
        loginAsExistingHostAndLoadCreatePage();
        goToStep2WithValidStep1();
        createPropertyPage.clearCity();
        createPropertyPage.clickNext();
        assertTrue(
                createPropertyPage.hasInlineErrorContaining("city"),
                ErrorMessages.CREATE_PROPERTY_STEP2_SHOULD_REQUIRE_CITY
        );
    }

    @Test
    public void testStep3ShowsDetailsFieldsAfterCompletingStep2() {
        loginAsExistingHostAndLoadCreatePage();
        goToStep3WithValidStep1AndStep2();
        assertTrue(
                createPropertyPage.isStep3DetailsLoaded(),
                ErrorMessages.CREATE_PROPERTY_STEP3_SHOULD_DISPLAY_DETAILS_FIELDS
        );
    }

    @Test
    public void testStep3MaxGuestsMinimumIsOne() {
        loginAsExistingHostAndLoadCreatePage();
        goToStep3WithValidStep1AndStep2();
        assertTrue(
                createPropertyPage.maxGuestsMinIsOne(),
                ErrorMessages.CREATE_PROPERTY_MAX_GUESTS_MIN_SHOULD_BE_1
        );
    }

    @Test
    public void testStep3BedroomsMinimumIsZero() {
        loginAsExistingHostAndLoadCreatePage();
        goToStep3WithValidStep1AndStep2();
        assertTrue(
                createPropertyPage.bedroomsMinIsZero(),
                ErrorMessages.CREATE_PROPERTY_BEDROOMS_MIN_SHOULD_BE_0
        );
    }

    @Test
    public void testStep3BedsMinimumIsOne() {
        loginAsExistingHostAndLoadCreatePage();
        goToStep3WithValidStep1AndStep2();
        assertTrue(
                createPropertyPage.bedsMinIsOne(),
                ErrorMessages.CREATE_PROPERTY_BEDS_MIN_SHOULD_BE_1
        );
    }

    @Test
    public void testStep3BathroomsMinimumIsZero() {
        loginAsExistingHostAndLoadCreatePage();
        goToStep3WithValidStep1AndStep2();
        assertTrue(
                createPropertyPage.bathroomsMinIsZero(),
                ErrorMessages.CREATE_PROPERTY_BATHROOMS_MIN_SHOULD_BE_0
        );
    }

    @Test
    public void testStep3BathroomsAllowsHalfIncrements() {
        loginAsExistingHostAndLoadCreatePage();
        goToStep3WithValidStep1AndStep2();
        assertTrue(
                createPropertyPage.bathroomsStepIsHalfIncrement(),
                ErrorMessages.CREATE_PROPERTY_BATHROOMS_STEP_SHOULD_BE_HALF
        );
    }

    @Test
    public void testBackFromStep2PreservesStep1Title() {
        loginAsExistingHostAndLoadCreatePage();
        createPropertyPage.fillValidStep1();
        createPropertyPage.clickNext();
        createPropertyPage.clickBack();
        assertTrue(
                createPropertyPage.isStep1TitleValuePreserved("Automation Listing"),
                ErrorMessages.CREATE_PROPERTY_BACK_SHOULD_PRESERVE_STEP1_TITLE
        );
    }

    @Test
    public void testBackFromStep2PreservesStep1Description() {
        loginAsExistingHostAndLoadCreatePage();
        createPropertyPage.fillValidStep1();
        createPropertyPage.clickNext();
        createPropertyPage.clickBack();
        assertTrue(
                createPropertyPage.isStep1DescriptionValuePreserved("Automation flow for create property wizard."),
                ErrorMessages.CREATE_PROPERTY_BACK_SHOULD_PRESERVE_STEP1_DESCRIPTION
        );
    }

    @Test
    public void testBackFromStep3PreservesStep2Country() {
        loginAsExistingHostAndLoadCreatePage();
        goToStep3WithValidStep1AndStep2();
        createPropertyPage.clickBack();
        assertTrue(
                createPropertyPage.isStep2CountryValuePreserved("Afghanistan"),
                ErrorMessages.CREATE_PROPERTY_BACK_SHOULD_PRESERVE_STEP2_COUNTRY
        );
    }

    @Test
    public void testBackFromStep3PreservesStep2City() {
        loginAsExistingHostAndLoadCreatePage();
        goToStep3WithValidStep1AndStep2();
        createPropertyPage.clickBack();
        assertTrue(
                createPropertyPage.isStep2CityValuePreserved("Kabul"),
                ErrorMessages.CREATE_PROPERTY_BACK_SHOULD_PRESERVE_STEP2_CITY
        );
    }

    @Test
    public void testProgressIndicatorShowsStep1Of7() {
        loginAsExistingHostAndLoadCreatePage();
        assertEquals(
                "Step 1 of 7", createPropertyPage.getProgressText(),
                ErrorMessages.CREATE_PROPERTY_PROGRESS_SHOULD_SHOW_STEP_1_OF_7
        );
    }

    @Test
    public void testNonHostAccessToCreatePropertyIsBlockedWith403() {
        registerNewUserAndLandOnHome("testcreate");
        createPropertyPage.load();
        assertTrue(
                createPropertyPage.pageShows403Error(),
                ErrorMessages.CREATE_PROPERTY_SHOULD_BLOCK_NON_HOST_WITH_403
        );
    }
}
