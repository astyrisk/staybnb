package com.staybnb.tests.ui;

import com.staybnb.assertions.ErrorMessages;
import com.staybnb.config.AppConstants;
import com.staybnb.data.PropertyPayloads;
import com.staybnb.pages.DeletePropertyPage;
import com.staybnb.pages.LoginPage;
import com.staybnb.tests.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("Property Management")
@Feature("Delete Property")
@Tag("regression")
public class DeletePropertyTest extends BaseTest {
    private LoginPage loginPage;
    private DeletePropertyPage deletePropertyPage;

    @BeforeEach
    public void setup() {
        loginPage = new LoginPage(driver);
        deletePropertyPage = new DeletePropertyPage(driver);
        loginAsTestUserAndLandOnHome(loginPage);
    }

    private String createPropertyAndReturnId(String uniqueTitle) {
        String response = deletePropertyPage.createPropertyViaApi(PropertyPayloads.validCreatePropertyPayloadJson(uniqueTitle));
        return deletePropertyPage.extractCreatedPropertyId(response);
    }

    @Test
    @DisplayName("Clicking delete on edit page shows confirmation modal")
    public void testDeleteFromEditPageShowsConfirmationModalMessage() {
        deletePropertyPage.navigateToEditPage(AppConstants.DeleteProperty.EDITABLE_PROPERTY_ID);
        deletePropertyPage.clickDeleteOnEditPage();

        assertTrue(
                deletePropertyPage.hasDeleteConfirmationMessage(AppConstants.DeleteProperty.CONFIRMATION_MESSAGE),
                ErrorMessages.DELETE_PROPERTY_EDIT_PAGE_SHOULD_SHOW_CONFIRMATION_MODAL
        );
    }

    @Test
    @DisplayName("Clicking delete on dashboard shows confirmation modal")
    public void testDeleteFromDashboardShowsConfirmationModalMessage() {
        deletePropertyPage.navigateToHostingDashboard();
        deletePropertyPage.clickFirstDashboardDelete();

        assertTrue(
                deletePropertyPage.hasDeleteConfirmationMessage(AppConstants.DeleteProperty.CONFIRMATION_MESSAGE),
                ErrorMessages.DELETE_PROPERTY_DASHBOARD_SHOULD_SHOW_CONFIRMATION_MODAL
        );
    }

    @Test
    @DisplayName("Cancelling deletion keeps the property on the dashboard")
    public void testCancelDeleteKeepsPropertyUnchanged() {
        String uniqueTitle = "Automation Delete Cancel " + System.currentTimeMillis();
        createPropertyAndReturnId(uniqueTitle);
        deletePropertyPage.navigateToHostingDashboard();
        deletePropertyPage.clickDeleteOnDashboardForTitle(uniqueTitle);
        deletePropertyPage.cancelDeletion();

        assertTrue(
                deletePropertyPage.isPropertyListedOnDashboard(uniqueTitle),
                ErrorMessages.DELETE_PROPERTY_CANCEL_SHOULD_KEEP_PROPERTY_UNCHANGED
        );
    }

    @Test
    @DisplayName("Confirming deletion removes the property from the dashboard")
    public void testConfirmDeleteRemovesPropertyFromDashboard() {
        String uniqueTitle = "Automation Delete Confirm " + System.currentTimeMillis();
        createPropertyAndReturnId(uniqueTitle);
        deletePropertyPage.navigateToHostingDashboard();
        deletePropertyPage.clickDeleteOnDashboardForTitle(uniqueTitle);
        deletePropertyPage.confirmDeletion();

        assertTrue(
                !deletePropertyPage.isPropertyListedOnDashboard(uniqueTitle),
                ErrorMessages.DELETE_PROPERTY_CONFIRM_SHOULD_REMOVE_PROPERTY_FROM_DASHBOARD
        );
    }
}
