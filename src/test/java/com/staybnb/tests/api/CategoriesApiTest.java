package com.staybnb.tests.api;

import com.staybnb.assertions.ErrorMessages;
import com.staybnb.pages.CategoriesApiPage;
import com.staybnb.pages.HomePage;
import com.staybnb.tests.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("Properties")
@Feature("Categories API")
@Tag("api")
public class CategoriesApiTest extends BaseTest {

    @BeforeEach
    public void setup() {
        HomePage homePage = new HomePage(driver);
        homePage.navigateTo();
    }

    @Test
    @DisplayName("Categories API returns list with id, name, and icon fields")
    public void testCategoriesApiReturnsListWithIdNameAndIcon() {
        CategoriesApiPage categoriesApiPage = new CategoriesApiPage(driver);

        assertTrue(
                categoriesApiPage.categoriesResponseHasRequiredFieldsViaApi(),
                ErrorMessages.CATEGORIES_API_SHOULD_RETURN_LIST_WITH_ID_NAME_ICON
        );
    }
}
