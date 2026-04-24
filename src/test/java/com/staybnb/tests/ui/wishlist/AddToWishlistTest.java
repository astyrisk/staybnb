package com.staybnb.tests.ui.wishlist;

import com.staybnb.assertions.ErrorMessages;
import com.staybnb.config.TestDataConstants;
import com.staybnb.pages.PropertyDetailsPage;
import com.staybnb.pages.PropertyListingPage;
import com.staybnb.pages.WishlistPage;
import com.staybnb.tests.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("Wishlist")
@Feature("Add Property to Wishlist")
@Tag("regression")
public class AddToWishlistTest extends BaseTest {
    private PropertyListingPage propertyListingPage;
    private PropertyDetailsPage propertyDetailsPage;
    private WishlistPage wishlistPage;

    @BeforeEach
    public void setup() {
        propertyListingPage = new PropertyListingPage(driver);
        propertyDetailsPage = new PropertyDetailsPage(driver);
        wishlistPage = new WishlistPage(driver);
    }

    // AC1: clicking outline heart on property card adds it to wishlist
    @Test
    @DisplayName("Clicking outline heart on property card fills the heart")
    public void testClickHeartOnCardFillsHeart() {
        loginAndClearWishlist();
        String propertyId = wishlistPage.getFirstPropertyIdViaApi();
        propertyListingPage.navigateTo();
        propertyListingPage.clickFavoriteOnCardById(propertyId);
        propertyListingPage.waitForCardFavoritedById(propertyId);
        assertTrue(
                propertyListingPage.isCardFavoritedById(propertyId),
                ErrorMessages.WISHLIST_HEART_SHOULD_BECOME_FILLED_ON_CARD
        );
    }

    // AC2: clicking outline heart on detail page adds it to wishlist
    @Test
    @DisplayName("Clicking outline heart on property detail page fills the heart")
    public void testClickHeartOnDetailPageFillsHeart() {
        loginAndClearWishlist();
        String propertyId = wishlistPage.getFirstPropertyIdViaApi();
        propertyDetailsPage.navigateTo(propertyId);
        propertyDetailsPage.clickWishlistButton();
        propertyDetailsPage.waitForFavorite();
        assertTrue(
                propertyDetailsPage.isFavorite(),
                ErrorMessages.WISHLIST_HEART_SHOULD_BECOME_FILLED_ON_DETAIL
        );
    }

    // AC3: unauthenticated user clicking the heart is redirected to login
    @Test
    @DisplayName("Clicking heart when not authenticated redirects to login page")
    public void testUnauthenticatedClickRedirectsToLogin() {
        propertyListingPage.navigateTo();
        propertyListingPage.clickFavoriteOnFirstCard();
        assertTrue(
                propertyListingPage.urlContains("/login"),
                ErrorMessages.WISHLIST_UNAUTHENTICATED_CLICK_SHOULD_REDIRECT_TO_LOGIN
        );
    }

    // AC4: property already in wishlist shows filled heart (no duplicate created)
    @Test
    @DisplayName("Property already in wishlist shows filled heart on detail page")
    public void testPropertyAlreadyInWishlistShowsFilledHeart() {
        loginAsUser();
        String propertyId = wishlistPage.getFirstPropertyIdViaApi();
        wishlistPage.addToWishlistViaApi(propertyId);
        propertyDetailsPage.navigateTo(propertyId);
        assertTrue(
                propertyDetailsPage.isFavorite(),
                ErrorMessages.WISHLIST_ALREADY_IN_WISHLIST_HEART_SHOULD_REMAIN_FILLED
        );
    }

    // AC5: attempting to favorite a non-existent property returns 404
    @Test
    @DisplayName("Adding a non-existent property to wishlist returns 404")
    public void testAddNonExistentPropertyReturns404() {
        loginAsUser();
        long status = wishlistPage.addToWishlistViaApi(TestDataConstants.NON_EXISTENT_PROPERTY_ID);
        assertEquals(
                404L,
                status,
                ErrorMessages.WISHLIST_ADD_NON_EXISTENT_PROPERTY_SHOULD_RETURN_404
        );
    }

    private void loginAndClearWishlist() {
        loginAsUser();
        wishlistPage.clearWishlistViaApi();
    }
}
