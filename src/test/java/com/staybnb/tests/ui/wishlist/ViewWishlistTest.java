package com.staybnb.tests.ui.wishlist;

import com.staybnb.assertions.ErrorMessages;
import com.staybnb.config.TestDataConstants;
import com.staybnb.pages.WishlistPage;
import com.staybnb.tests.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("Wishlist")
@Feature("View Wishlist Page")
@Tag("regression")
public class ViewWishlistTest extends BaseTest {
    private WishlistPage wishlistPage;

    @BeforeEach
    public void setup() {
        wishlistPage = new WishlistPage(driver);
    }

    // AC1: authenticated user with favorited properties sees a grid with filled hearts
    @Test
    @DisplayName("Authenticated user with favorited properties sees a grid of cards with filled hearts")
    public void testAuthenticatedUserWithPropertiesSeesGridWithFilledHearts() {
        loginAndClearWishlist();
        wishlistPage.addToWishlistViaApi(TestDataConstants.DEFAULT_PROPERTY_ID);
        wishlistPage.navigateTo();
        Assertions.assertAll(
                () -> assertTrue(
                        wishlistPage.getPropertyCardCount() > 0,
                        ErrorMessages.WISHLIST_PAGE_SHOULD_DISPLAY_PROPERTY_CARDS
                ),
                () -> assertTrue(
                        wishlistPage.areAllCardsShowingFilledHeart(),
                        ErrorMessages.WISHLIST_PAGE_CARDS_SHOULD_SHOW_FILLED_HEARTS
                )
        );
    }

    // AC2: authenticated user with no favorited properties sees empty state
    @Test
    @DisplayName("Authenticated user with no favorited properties sees the empty state message")
    public void testEmptyWishlistShowsEmptyStateMessage() {
        loginAndClearWishlist();
        wishlistPage.navigateTo();
        Assertions.assertAll(
                () -> assertTrue(
                        wishlistPage.isEmptyStateDisplayed(),
                        ErrorMessages.WISHLIST_PAGE_EMPTY_STATE_SHOULD_BE_VISIBLE
                ),
                () -> assertTrue(
                        wishlistPage.getEmptyStateText().contains("You haven't saved any properties yet"),
                        ErrorMessages.WISHLIST_PAGE_EMPTY_STATE_TEXT_SHOULD_MATCH
                )
        );
    }

    // AC3: unauthenticated user sees a login prompt (no redirect — page stays at /wishlists)
    @Test
    @DisplayName("Unauthenticated user navigating to the wishlist page sees a login prompt")
    public void testUnauthenticatedUserSeesLoginPrompt() {
        wishlistPage.navigateTo();
        Assertions.assertAll(
                () -> assertTrue(
                        wishlistPage.isEmptyStateDisplayed(),
                        ErrorMessages.WISHLIST_PAGE_UNAUTHENTICATED_SHOULD_SHOW_LOGIN_PROMPT
                ),
                () -> assertTrue(
                        wishlistPage.getEmptyStateText().contains("Log in to view your wishlists"),
                        ErrorMessages.WISHLIST_PAGE_UNAUTHENTICATED_LOGIN_PROMPT_TEXT_SHOULD_MATCH
                )
        );
    }

    // AC4 (remove from wishlist page → card disappears) is covered by
    // RemoveFromWishlistTest#testClickHeartOnWishlistPageRemovesCard

    private void loginAndClearWishlist() {
        loginAsUser();
        wishlistPage.clearWishlistViaApi();
    }
}
