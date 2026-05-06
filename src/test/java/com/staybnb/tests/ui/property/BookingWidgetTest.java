package com.staybnb.tests.ui.property;

import static org.junit.jupiter.api.Assertions.*;

import com.staybnb.assertions.ErrorMessages;
import com.staybnb.config.TestConfig;
import com.staybnb.config.TestDataConstants;
import com.staybnb.pages.MyBookingsPage;
import com.staybnb.pages.PropertyDetailsPage;
import com.staybnb.tests.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Epic("Properties")
@Feature("Booking Widget")
@Tag("regression")
public class BookingWidgetTest extends BaseTest {

    private PropertyDetailsPage propertyDetailsPage;
    private String createdBookingId;

    @BeforeEach
    public void setup() {
        propertyDetailsPage = new PropertyDetailsPage(driver);
        propertyDetailsPage.navigateTo(TestConfig.DEFAULT_PROPERTY_ID);
    }

    @AfterEach
    public void cancelCreatedBooking() {
        if (createdBookingId != null) {
            new MyBookingsPage(driver).cancelBookingViaApi(createdBookingId);
            createdBookingId = null;
        }
    }

    // ── F4.1 AC1 — Widget Display ─────────────────────────────────────────────

    @Test
    @DisplayName("Booking widget displays the property price per night on page load")
    public void testBookingWidgetDisplaysPricePerNight() {
        assertTrue(
            propertyDetailsPage.getPrice().contains("$"),
            ErrorMessages.BOOKING_WIDGET_SHOULD_DISPLAY_PRICE_PER_NIGHT
        );
    }

    @Test
    @DisplayName("Booking widget displays check-in picker, check-out picker, guest selector, and Reserve button")
    public void testBookingWidgetHasAllRequiredControls() {
        assertAll(
            ErrorMessages.BOOKING_WIDGET_SHOULD_HAVE_ALL_REQUIRED_CONTROLS,
            () ->
                assertTrue(
                    propertyDetailsPage.isCheckInDatePickerDisplayed(),
                    ErrorMessages.BOOKING_WIDGET_SHOULD_HAVE_CHECK_IN_DATE_PICKER
                ),
            () ->
                assertTrue(
                    propertyDetailsPage.isCheckOutDatePickerDisplayed(),
                    ErrorMessages.BOOKING_WIDGET_SHOULD_HAVE_CHECK_OUT_DATE_PICKER
                ),
            () ->
                assertTrue(
                    propertyDetailsPage.isGuestSelectorDisplayed(),
                    ErrorMessages.BOOKING_WIDGET_SHOULD_HAVE_GUEST_COUNT_SELECTOR
                ),
            () ->
                assertTrue(
                    propertyDetailsPage.isReserveButtonDisplayed(),
                    ErrorMessages.BOOKING_WIDGET_SHOULD_HAVE_RESERVE_BUTTON
                )
        );
    }

    @Test
    @DisplayName("Price breakdown is displayed after valid check-in and check-out dates are selected")
    public void testPriceBreakdownDisplayedAfterDatesSelected() {
        propertyDetailsPage.openCheckInDatePicker();
        propertyDetailsPage.selectNthAvailableDate(0);
        propertyDetailsPage.selectNthAvailableDate(4);
        propertyDetailsPage.waitForDatePickerToClose();

        assertTrue(
            propertyDetailsPage.isPriceBreakdownDisplayed(),
            ErrorMessages.BOOKING_WIDGET_SHOULD_SHOW_PRICE_BREAKDOWN
        );
    }

    // ── F4.1 AC2 — Guest Count Validation ────────────────────────────────────

    @Test
    @DisplayName("Guest count defaults to 1 when booking widget loads")
    public void testGuestCountDefaultsToOne() {
        assertEquals(
            1,
            propertyDetailsPage.getGuestCount(),
            ErrorMessages.GUEST_COUNT_SHOULD_DEFAULT_TO_ONE
        );
    }

    @Test
    @DisplayName("Increment button is disabled after reaching the property's max guest capacity")
    public void testIncrementButtonDisabledAtMaxCapacity() {
        propertyDetailsPage.incrementGuestsTo(
            TestDataConstants.DefaultProperty.MAX_GUESTS
        );

        assertTrue(
            propertyDetailsPage.isIncrementButtonDisabled(),
            ErrorMessages.GUEST_INCREMENT_SHOULD_BE_DISABLED_AT_MAX
        );
    }

    // ── F4.3 AC3 — Auth error in widget ──────────────────────────────────────

    @Test
    @DisplayName("Clicking Reserve without authentication shows an inline error in the booking widget")
    public void testReserveWithoutAuthShowsBookingWidgetError() {
        propertyDetailsPage.openCheckInDatePicker();
        propertyDetailsPage.selectNthAvailableDate(0);
        propertyDetailsPage.selectNthAvailableDate(4);
        propertyDetailsPage.waitForDatePickerToClose();
        propertyDetailsPage.clickReserveButton();

        //TODO the page doesn't show up an error, it redirects to login
        assertTrue(
            propertyDetailsPage.isBookingErrorDisplayed(),
            ErrorMessages.BOOKING_WIDGET_AUTH_ERROR_SHOULD_BE_DISPLAYED
        );
    }

    @Test
    @DisplayName("Reserving a valid property booking gets it into the bookings tab")
    public void testReservingValidProperty() {
        loginAsUser();
        propertyDetailsPage.navigateTo(TestConfig.TO_BOOK_PROPERTY_ID);
        String propertyTitle = propertyDetailsPage.getTitle();

        propertyDetailsPage.openCheckInDatePicker();

        propertyDetailsPage.selectNthAvailableDate(0);
        propertyDetailsPage.selectNthAvailableDate(4);

        propertyDetailsPage.waitForDatePickerToClose();
        propertyDetailsPage.clickReserveButton();

        MyBookingsPage myBookingsPage = new MyBookingsPage(driver);
        myBookingsPage.navigateTo();

        createdBookingId = myBookingsPage.getBookingIdByPropertyTitle(propertyTitle);

        assertNotNull(
            createdBookingId,
            ErrorMessages.BOOKING_SHOULD_APPEAR_IN_BOOKINGS_TAB
        );
    }
}
