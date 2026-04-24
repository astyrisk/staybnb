package com.staybnb.tests.ui.wishlist;

import com.staybnb.assertions.ErrorMessages;
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
import static org.junit.jupiter.api.Assertions.assertFalse;

@Epic("Wishlist")
@Feature("Remove Property from Wishlist")
@Tag("regression")
public class RemoveFromWishlistTest extends BaseTest {
    private PropertyListingPage propertyListingPage;
    private PropertyDetailsPage propertyDetailsPage;
    private WishlistPage wishlistPage;

    @BeforeEach
    public void setup() {
        propertyListingPage = new PropertyListingPage(driver);
        propertyDetailsPage = new PropertyDetailsPage(driver);
        wishlistPage = new WishlistPage(driver);

        loginAsUser();
        wishlistPage.clearWishlistViaApi();
    }

    // AC1: clicking filled heart on property card removes it from wishlist
    @Test
    @DisplayName("Clicking filled heart on property card turns heart back to outline")
    public void testClickFilledHeartOnCardBecomesOutline() {
        String propertyId = wishlistPage.getFirstPropertyIdViaApi();
        wishlistPage.addToWishlistViaApi(propertyId);
        propertyListingPage.navigateTo();
        propertyListingPage.waitForCardFavoritedById(propertyId);
        propertyListingPage.clickFavoriteOnCardById(propertyId);
        propertyListingPage.acceptConfirmationIfPresent();
        propertyListingPage.waitForCardUnfavoritedById(propertyId);
        assertFalse(
                propertyListingPage.isCardFavoritedById(propertyId),
                ErrorMessages.WISHLIST_HEART_SHOULD_BECOME_OUTLINE_ON_CARD
        );
    }

    // AC2: clicking filled heart on detail page removes it from wishlist
    @Test
    @DisplayName("Clicking filled heart on property detail page turns heart back to outline")
    public void testClickFilledHeartOnDetailPageBecomesOutline() {
        String propertyId = wishlistPage.getFirstPropertyIdViaApi();
        wishlistPage.addToWishlistViaApi(propertyId);
        propertyDetailsPage.navigateTo(propertyId);
        propertyDetailsPage.clickWishlistButton();
        propertyDetailsPage.waitForWishlistUnfavorited();
        assertFalse(
                propertyDetailsPage.isWishlistFavorited(),
                ErrorMessages.WISHLIST_HEART_SHOULD_BECOME_OUTLINE_ON_DETAIL
        );
    }

    // AC3: on wishlists page, clicking heart removes property card from the list
    @Test
    @DisplayName("Clicking heart on wishlists page removes the property card")
    public void testClickHeartOnWishlistPageRemovesCard() {
        String propertyId = wishlistPage.getFirstPropertyIdViaApi();
        wishlistPage.addToWishlistViaApi(propertyId);
        wishlistPage.navigateTo();
        int countBefore = wishlistPage.getPropertyCardCount();
        wishlistPage.clickFavoriteOnFirstCard();
        wishlistPage.acceptConfirmationIfPresent();
        wishlistPage.waitForCardCountToDecrease(countBefore);
        assertEquals(
                countBefore - 1,
                wishlistPage.getPropertyCardCount(),
                ErrorMessages.WISHLIST_CARD_SHOULD_DISAPPEAR_AFTER_REMOVAL_ON_WISHLIST_PAGE
        );
    }

    // AC4: removing a property not in the wishlist returns 404
    @Test
    @DisplayName("Removing a property not in the wishlist returns 404")
    public void testRemoveNonWishlistedPropertyReturns404() {
        String propertyId = wishlistPage.getFirstPropertyIdViaApi();
        long status = wishlistPage.removeFromWishlistViaApi(propertyId);
        assertEquals(
                404L,
                status,
                ErrorMessages.WISHLIST_REMOVE_NON_WISHLISTED_PROPERTY_SHOULD_RETURN_404
        );
    }
}
