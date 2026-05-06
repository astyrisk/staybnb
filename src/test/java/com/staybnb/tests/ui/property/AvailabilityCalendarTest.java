package com.staybnb.tests.ui.property;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.staybnb.assertions.ErrorMessages;
import com.staybnb.config.TestConfig;
import com.staybnb.pages.BookingApiPage;
import com.staybnb.pages.PropertyDetailsPage;
import com.staybnb.tests.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Epic("Properties")
@Feature("Availability Calendar")
@Tag("regression")
public class AvailabilityCalendarTest extends BaseTest {

    private PropertyDetailsPage propertyDetailsPage;
    private BookingApiPage bookingApiPage;

    @BeforeEach
    public void setup() {
        propertyDetailsPage = new PropertyDetailsPage(driver);

        bookingApiPage = new BookingApiPage(driver);

        propertyDetailsPage.navigateTo(TestConfig.DEFAULT_PROPERTY_ID);
    }

    @Test
    @DisplayName(
        "Booked dates have the disabled attribute and cannot be selected"
    )
    public void testBookedDatesAreDisabled() {
        List<LocalDate> bookedDates = bookingApiPage.getAllBookedDates(
            TestConfig.DEFAULT_PROPERTY_ID
        );

        assertTrue(
            propertyDetailsPage.areDatesDisabled(bookedDates),
            ErrorMessages.BOOKED_DATE_FROM_API_SHOULD_BE_DISABLED_IN_CALENDAR
        );
    }

    @Test
    @DisplayName("Unbooked dates are selectable in the date picker")
    public void testUnbookedDatesAreSelectable() {
        List<LocalDate> availableDates = propertyDetailsPage.getAvailableDates(
            TestConfig.DEFAULT_PROPERTY_ID
        );

        assertTrue(
            propertyDetailsPage.areDatesSelectable(availableDates),
            ErrorMessages.AVAILABLE_DATE_SHOULD_BE_SELECTABLE
        );
    }
}
