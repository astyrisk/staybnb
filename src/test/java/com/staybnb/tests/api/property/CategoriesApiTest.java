package com.staybnb.tests.api.property;

import com.staybnb.assertions.ErrorMessages;
import com.staybnb.tests.BaseApiTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("Properties")
@Feature("Categories API")
@Tag("api")
public class CategoriesApiTest extends BaseApiTest {

    @Test
    @DisplayName("Categories API returns list with id, name, and icon fields")
    public void testCategoriesApiReturnsListWithIdNameAndIcon() {
        String body = unauthedRequest().get("/categories").asString();

        assertTrue(
                body.contains("\"id\"") && body.contains("\"name\"") && body.contains("\"icon\""),
                ErrorMessages.CATEGORIES_API_SHOULD_RETURN_LIST_WITH_ID_NAME_ICON
        );
    }
}
