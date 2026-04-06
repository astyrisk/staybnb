package com.staybnb.tests;

import com.staybnb.assertions.ErrorMessages;
import com.staybnb.data.Constants;
import com.staybnb.data.PropertyPayloads;
import com.staybnb.pages.DeletePropertyPage;
import com.staybnb.pages.LoginPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DeletePropertyTest extends BaseTest {
    private LoginPage loginPage;
    private DeletePropertyPage deletePropertyPage;

    @BeforeEach
    public void setup() {
        loginPage = new LoginPage(driver);
        deletePropertyPage = new DeletePropertyPage(driver);
    }

    private String createPropertyAndReturnId(String uniqueTitle) {
        String response = deletePropertyPage.createPropertyViaApi(PropertyPayloads.validCreatePropertyPayloadJson(uniqueTitle));
        return deletePropertyPage.extractCreatedPropertyId(response);
    }

    @Test
    public void testDeleteFromEditPageShowsConfirmationModalMessage() {
        loginAsTestUserAndLandOnHome(loginPage);
        deletePropertyPage.navigateToEditPage(Constants.DeleteProperty.EDITABLE_PROPERTY_ID);
        deletePropertyPage.clickDeleteOnEditPage();

        assertTrue(
                deletePropertyPage.hasDeleteConfirmationMessage(Constants.DeleteProperty.CONFIRMATION_MESSAGE),
                ErrorMessages.DELETE_PROPERTY_EDIT_PAGE_SHOULD_SHOW_CONFIRMATION_MODAL
        );
    }

    @Test
    public void testDeleteFromDashboardShowsConfirmationModalMessage() {
        loginAsTestUserAndLandOnHome(loginPage);
        deletePropertyPage.navigateToHostingDashboard();
        deletePropertyPage.clickFirstDashboardDelete();

        assertTrue(
                deletePropertyPage.hasDeleteConfirmationMessage(Constants.DeleteProperty.CONFIRMATION_MESSAGE),
                ErrorMessages.DELETE_PROPERTY_DASHBOARD_SHOULD_SHOW_CONFIRMATION_MODAL
        );
    }

    @Test
    public void testCancelDeleteKeepsPropertyUnchanged() {
        loginAsTestUserAndLandOnHome(loginPage);
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
    public void testConfirmDeleteRemovesPropertyFromDashboard() {
        loginAsTestUserAndLandOnHome(loginPage);
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

    @Test
    public void testDeletePropertyApiReturns200ForOwner() {
        loginAsTestUserAndLandOnHome(loginPage);
        String uniqueTitle = "Automation Delete API " + System.currentTimeMillis();
        String propertyId = createPropertyAndReturnId(uniqueTitle);
        long status = deletePropertyPage.deletePropertyStatusViaApi(propertyId);

        assertEquals(200L,
                status,
                ErrorMessages.DELETE_PROPERTY_API_SHOULD_RETURN_200_FOR_OWNER
        );
    }

    @Test
    public void testDeletePropertyApiReturns403ForNonOwner() {
        registerNewUserAndLandOnHome("testdeleteproperty");
        long status = deletePropertyPage.deletePropertyStatusViaApi(Constants.DeleteProperty.EDITABLE_PROPERTY_ID);

        assertEquals(
                403L,
                status,
                ErrorMessages.DELETE_PROPERTY_API_SHOULD_RETURN_403_FOR_NON_OWNER
        );
    }

    @Test
    public void testDeletePropertyApiReturns404ForNonExistentPropertyId() {
        loginAsTestUserAndLandOnHome(loginPage);
        long status = deletePropertyPage.deletePropertyStatusViaApi(Constants.DeleteProperty.NON_EXISTENT_PROPERTY_ID);

        assertEquals(
                404L,
                status,
                ErrorMessages.DELETE_PROPERTY_API_SHOULD_RETURN_404_FOR_NON_EXISTENT_PROPERTY
        );
    }

    @Test
    public void testDeletePropertyApiReturns401WhenLoggedOut() {
        long status = deletePropertyPage.deletePropertyStatusViaApi(Constants.DeleteProperty.EDITABLE_PROPERTY_ID);

        assertEquals(
                401L,
                status,
                ErrorMessages.DELETE_PROPERTY_API_SHOULD_RETURN_401_WHEN_LOGGED_OUT
        );
    }
}
